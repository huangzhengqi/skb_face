package com.fzy.admin.fp.order.order.service;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
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
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import com.fzy.admin.fp.sdk.pay.feign.TtsPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-31 21:02
 * @description 秒到支付业务
 */
@Service
@Slf4j
@Transactional
public class TtsOrderService extends BaseContent {

    @Resource
    private TtsPayServiceFeign ttsPayServiceFeign;

    @Resource
    private OrderService orderService;

    /**
     * @author Created by wtl on 2019/05/31 21:40
     * @Description 构建秒到通用支付参数
     */
    public TtsPayParam createTtsPayParam(Order order) {
        TtsPayParam ttsPayParam = new TtsPayParam();
        ttsPayParam.setAuthCode(order.getAuthCode());
        ttsPayParam.setTotalFee(order.getActPayPrice());
        ttsPayParam.setOutTradeNo(order.getOrderNumber());
        ttsPayParam.setMerchantId(order.getMerchantId());
        return ttsPayParam;
    }

    /**
     * @author Created by wtl on 2019/05/31 21:39
     * @Description 秒到网页支付
     */
    public Map<String, Object> ttsWapPay(Order order, TtsPayParam.PayType payType) {
        // 构建秒到通用支付参数
        TtsPayParam ttsPayParam = createTtsPayParam(order);
        ttsPayParam.setPayType(payType.getCode());
        // 调用秒到秒到支付
        PayRes payRes = ttsPayServiceFeign.ttsJsPay(ttsPayParam);
        // 下单失败
        if (!PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", resultMap.get("codeUrl"));
        // TODO: 秒到通道，微信扫码是native支付，返回的URL打开不能支付，只能生成二维码的方式打开，支付宝测试不支持
        map.put("openWay", PayChannelConstant.OpenWay.JSBRIDGE.getCode());
        return map;
    }

    /**
     * @author Created by wtl on 2019/5/31 22:06
     * @Description 秒到付款码支付
     */
    public void ttsScanPay(Order order, TtsPayParam.PayType payType) throws Exception {
        // 构建秒到通用支付参数
        TtsPayParam ttsPayParam = createTtsPayParam(order);
        ttsPayParam.setPayType(payType.getCode());
        // 秒到扫码支付
        PayRes payRes = ttsPayServiceFeign.ttsScanPay(ttsPayParam);
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            // 支付失败
            order.setStatus(Order.Status.FAILPAY.getCode());
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 支付成功
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
        } else { // 需要输入支付密码
            // 客户端轮询查询当前支付订单的状态
            int queryCount = 45; // 查询次数，45次，45秒
            boolean queryFlag = false; // 查询结果
            while (queryCount > 0) {
                PayRes queryRes = ttsPayServiceFeign.ttsOrderQuery(ttsPayParam);
                if (PayRes.ResultStatus.PAYING.getCode().equals(queryRes.getStatus().getCode())) {
                    queryCount = queryCount - 1;
                } else {
                    queryCount = 0;
                    queryFlag = true;
                }
                Thread.sleep(1000);
            }
            // 支付成功
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setPayTime(new Date());
            if (!queryFlag) {
                // TODO:该接口尚未开放，订单错误或者超时，撤销/关闭订单

                // 撤销支付
                order.setStatus(Order.Status.CANCELPAY.getCode());
            }
        }
    }


    /**
     * @author Created by wtl on 2019/5/31 11:40
     * @Description 秒到支付回调
     */
    public String ttsOrderCallBack() {
        log.info("进入秒到异步回调");
        Map<String, String[]> requestParams = request.getParameterMap();
        // 回调结果转map
        Map<String, String> params = OrderUtil.params2Map(requestParams);
        Map map = JacksonUtil.toStringMap(params.get("data"));
        // 商户订单号
        String orderNo = (String) map.get("outTradeNo");
        // 交易状态
        String status = (String) map.get("orderStatus");
        BigDecimal totalFee = new BigDecimal((String) map.get("totalFee"));
        //查询出本系统订单记录
        Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNo, CommonConstant.NORMAL_FLAG);
        if ((totalFee.compareTo(order.getActPayPrice().stripTrailingZeros()) == 0) && orderNo.equals(order.getOrderNumber())) {
            if ("1".equals(status)) {
                // 订单状态为下单的才能改成已支付
                if (Order.Status.PLACEORDER.getCode().equals(order.getStatus())) {
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    orderService.getRepository().save(order);
                    /**
                     * 会员支付需要生成消费记录和积分变化
                     */
                    if (!ParamUtil.isBlank(order.getMemberId())) {
                        orderService.getMemberOrderService().createMemberPayRecord(order);
                    }
                }
            }
            return "success";
        } else {
            return "fail";
        }
    }


}
