package com.fzy.admin.fp.order.order.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.FyPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-21 21:02
 * @description 富友支付业务
 */
@Service
@Slf4j
@Transactional
public class FyOrderService extends BaseContent {

    @Resource
    private FyPayServiceFeign fyPayServiceFeign;

    public FyPayServiceFeign getFyPayServiceFeign() {
        return fyPayServiceFeign;
    }

    @Resource
    private OrderService orderService;
    @Resource
    private CommissionService commissionService;

    /**
     * 构建通用支付参数
     */
    public FyPayParam createFyPayParam(Order order) {
        FyPayParam fyPayParam = new FyPayParam();
        fyPayParam.setAuthCode(order.getAuthCode());
        fyPayParam.setTotalFee(order.getActPayPrice());
        fyPayParam.setOutTradeNo(order.getOrderNumber());
        fyPayParam.setMerchantId(order.getMerchantId());
        return fyPayParam;
    }

    /**
     * 网页支付
     */
    public Map<String, Object> fyWapPay(Order order, FyPayParam.PayType payType) throws Exception {
        // 构建通用支付参数
        FyPayParam fyPayParam = createFyPayParam(order);
        fyPayParam.setPayType(payType.getCode());
        PayRes payRes = fyPayServiceFeign.fyWapPay(fyPayParam);

        // 下单失败
        if (!PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        log.info("响应参数，{}", payRes.getObject().toString());
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());

        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", resultMap.get("qr_code"));
        map.put("channel", PayChannelConstant.Channel.FY.getCode());
        return map;
    }

    /**
     * 扫码支付
     */
    public void fyScanPay(Order order, FyPayParam.PayType payType, Order.InterFaceWay interFaceWay) throws Exception {
        // 构建通用支付参数
        FyPayParam fyPayParam = createFyPayParam(order);
        fyPayParam.setPayType(payType.getCode());
        PayRes payRes = fyPayServiceFeign.fyScanPay(fyPayParam);

        // 支付失败
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        log.info("响应参数，{}", payRes.getObject().toString());
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
        // 保存平台的订单号
        order.setTransactionId(resultMap.get("reserved_fy_trace_no"));
        order.setRefundTransactionId(resultMap.get("reserved_mchnt_order_no"));
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
        }
        commissionService.EditCommissionStatus(order.getId(),order.getStatus());
    }


    /**
     * 退款
     */
    public void fyRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        FyPayParam fyPayParam = createFyPayParam(order);
        fyPayParam.setTransactionId(order.getTransactionId());
        fyPayParam.setRefundAmount(refundPayPrice);
        if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
            fyPayParam.setPayType(FyPayParam.PayType.WECHAT.getCode());
        } else if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
            fyPayParam.setPayType(FyPayParam.PayType.ALIPAY.getCode());
        }
        PayRes payRes = fyPayServiceFeign.fyRefund(fyPayParam);
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
        // 保存平台的订单号
        order.setTransactionId(resultMap.get("reserved_fy_trace_no"));
        orderService.refundResult(order, refundPayPrice, payRes);
    }


    /**
     * 支付回调
     */
    public Integer fyOrderCallBack() {
        log.info("进入富友异步回调");
        Map<String, String[]> requestParams = request.getParameterMap();
        // 回调结果转map
        Map<String, String> params = OrderUtil.params2Map(requestParams);
        try {
            // 进行一次编码
            String res = URLDecoder.decode(params.toString(), "GBK");
            // 截取出xml内容
            String xml = res.substring(5, res.lastIndexOf("}"));
            // xml转map
            Map<String, String> map = WXPayUtil.xmlToMap(xml);
            log.info("notify prams，{}", map);
            // 商户订单号
            String orderNo = map.get("mchnt_order_no").substring(4);
            // 交易状态
            String resultcode = map.get("result_code");
            String orderAmt = map.get("order_amt");
            BigDecimal amount = new BigDecimal(orderAmt);
            //查询出本系统订单记录
            Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNo, CommonConstant.NORMAL_FLAG);
            order.setTransactionId(map.get("reserved_fy_trace_no"));
            order.setRefundTransactionId(map.get("mchnt_order_no"));
            if("success".equals( orderService.getCallBackResult(orderNo, resultcode, amount.multiply(new BigDecimal(100)).stripTrailingZeros(), order, "000000"))){
                return 1;
            }else {
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }


}
