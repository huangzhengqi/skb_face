package com.fzy.admin.fp.order.order.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import com.fzy.admin.fp.sdk.pay.feign.YrmPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-21 21:02
 * @description 易融码支付业务
 */
@Service
@Slf4j
@Transactional
public class YrmOrderService extends BaseContent {

    @Resource
    private YrmPayServiceFeign yrmPayServiceFeign;

    public YrmPayServiceFeign getYrmPayServiceFeign() {
        return yrmPayServiceFeign;
    }

    @Resource
    private OrderService orderService;
    @Resource
    private CommissionService commissionService;

    @Value("${yrmpay.successXml}")
    public String successXml;

    @Value("${yrmpay.failXml}")
    public String failXml;

    /**
     * @author Created by wtl on 2019/05/21 21:40
     * @Description 构建易融码通用支付参数
     */
    public YrmPayParam createYrmPayParam(Order order) {
        YrmPayParam yrmPayParam = new YrmPayParam();
        yrmPayParam.setAuthCode(order.getAuthCode());
        yrmPayParam.setTotalFee(order.getActPayPrice());
        yrmPayParam.setOutTradeNo(order.getOrderNumber());
        yrmPayParam.setMerchantId(order.getMerchantId());
        return yrmPayParam;
    }

    /**
     * @author Created by wtl on 2019/05/21 21:39
     * @Description 易融码网页支付
     */
    public Map<String, Object> yrmWapPay(Order order, YrmPayParam.PayType payType) throws Exception {
        // 构建易融码通用支付参数
        YrmPayParam yrmPayParam = createYrmPayParam(order);
        yrmPayParam.setPayType(payType.getCode());
        // 调用易融码易融码支付
        PayRes payRes = yrmPayServiceFeign.yrmJsPay(yrmPayParam);
        // 下单失败
        if (!PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
        // 保存易融码平台的订单号
        order.setTransactionId(resultMap.get("transactionId"));
        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", resultMap.get("codeUrl"));
//        map.put("openWay", PayChannelConstant.OpenWay.OPEN.getCode());
        map.put("channel", PayChannelConstant.Channel.YRM.getCode());
        return map;
    }

    /**
     * @author Created by wtl on 2019/5/21 22:06
     * @Description 易融码扫码支付
     */
    public void yrmScanPay(Order order, YrmPayParam.PayType payType, Order.InterFaceWay interFaceWay) throws Exception {
        // 构建易融码通用支付参数
        YrmPayParam yrmPayParam = createYrmPayParam(order);
        yrmPayParam.setPayType(payType.getCode());
        // 易融码扫码支付
        PayRes payRes = yrmPayServiceFeign.yrmScanPay(yrmPayParam);
        // 支付失败
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 保存易融码订单号
        order.setTransactionId((String) payRes.getObject());
        // 支付成功或开放接口给第三方，不需要这里进行轮询查询订单
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            // 支付成功
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setPayTime(new Date());
            return;
        }
        // 开放接口给第三方，不需要这里进行轮询查询订单
        if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
            order.setPayTime(new Date());
            return;
        }
        commissionService.EditCommissionStatus(order.getId(),order.getStatus());
        // 需要输入支付密码
        boolean queryFlag = OrderUtil.payQuery(() -> yrmPayServiceFeign.yrmOrderQuery(order.getMerchantId(), order.getOrderNumber()), payRes);
        order.setStatus(Order.Status.SUCCESSPAY.getCode());
        order.setPayTime(new Date());
        if (!queryFlag) {
            // TODO:该接口尚未开放，订单错误或者超时，撤销/关闭订单
            // 撤销支付
//            order.setStatus(Order.Status.CANCELPAY.getCode());
            order.setStatus(Order.Status.PLACEORDER.getCode());
        }
    }


    /**
     * @author Created by wtl on 2019/5/10 17:09
     * @Description 易融码退款
     */
    public void yrmRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        // 构建易融码通用支付参数
        YrmPayParam yrmPayParam = createYrmPayParam(order);
        yrmPayParam.setTransactionId(order.getTransactionId());
        yrmPayParam.setRefundAmount(refundPayPrice);
        // 调用易融码退款
        PayRes payRes = yrmPayServiceFeign.yrmRefund(yrmPayParam);
        orderService.refundResult(order, refundPayPrice, payRes);
    }


    /**
     * @author Created by wtl on 2019/5/21 11:40
     * @Description 易融码支付回调
     */
    public String yrmOrderCallBack() throws Exception {
        log.info("进入易融码异步回调");
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        Map<String, String> map = WXPayUtil.xmlToMap(sb.toString());
        // 商户订单号
        String orderNo = map.get("outTradeNo");
        // 交易状态
        String status = map.get("status");
        BigDecimal totalFee = new BigDecimal(map.get("totalFee"));
        //查询出本系统订单记录
        Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNo, CommonConstant.NORMAL_FLAG);
        // TODO:校验签名
        String sign = map.get("sign");

        if ((totalFee.compareTo(order.getActPayPrice().stripTrailingZeros()) == 0) && orderNo.equals(order.getOrderNumber())) {
            if ("success".equals(status)) {
                // 订单状态为下单的才能改成已支付
                if (Order.Status.PLACEORDER.getCode().equals(order.getStatus())) {
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    orderService.getRepository().save(order);
                    commissionService.EditCommissionStatus(order.getId(),order.getStatus());
                    /**
                     * 会员支付需要生成消费记录和积分变化
                     */
                    if (!ParamUtil.isBlank(order.getMemberId())) {
                        orderService.getMemberOrderService().createMemberPayRecord(order);
                    }
                }
            }
            return successXml;
        } else {
            return failXml;
        }
    }


}
