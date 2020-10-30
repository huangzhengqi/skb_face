package com.fzy.admin.fp.order.order.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.sdk.pay.feign.SxfPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-21 21:02
 * @description 随行付支付业务
 */
@Service
@Slf4j
@Transactional
public class SxfOrderService extends BaseContent {

    @Resource
    private SxfPayServiceFeign sxfPayServiceFeign;

    public SxfPayServiceFeign getSxfPayServiceFeign() {
        return sxfPayServiceFeign;
    }

    @Resource
    private OrderService orderService;
    @Resource
    private CommissionService commissionService;

    /**
     * 构建通用支付参数
     */
    public SxfPayParam createSxfPayParam(Order order) {
        SxfPayParam sxfPayParam = new SxfPayParam();
        sxfPayParam.setAuthCode(order.getAuthCode());
        sxfPayParam.setTotalFee(order.getActPayPrice());
        sxfPayParam.setOutTradeNo(order.getOrderNumber());
        sxfPayParam.setMerchantId(order.getMerchantId());
        return sxfPayParam;
    }

    /**
     * 网页支付
     */
    public Map<String, Object> sxfWapPay(Order order, SxfPayParam.PayType payType) throws Exception {
        // 构建通用支付参数
        SxfPayParam sxfPayParam = createSxfPayParam(order);
        sxfPayParam.setPayType(payType.getCode());
        PayRes payRes = sxfPayServiceFeign.sxfWapPay(sxfPayParam);
        log.info("响应参数，{}", payRes.getObject().toString());
        // 下单失败
        if (!PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());

        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", resultMap.get("payUrl"));
//        map.put("openWay", PayChannelConstant.OpenWay.OPEN.getCode());
        map.put("channel", PayChannelConstant.Channel.SXF.getCode());
        log.info("sxf网页支付返回参数{}"  + map.toString());
        return map;
    }

    /**
     * 扫码支付
     */
//    public PayRes sxfScanPay(Order order, SxfPayParam.PayType payType, Order.InterFaceWay interFaceWay) throws Exception {
//        // 构建通用支付参数
//        SxfPayParam sxfPayParam = createSxfPayParam(order);
//        sxfPayParam.setPayType(payType.getCode());
//        PayRes payRes = sxfPayServiceFeign.sxfScanPay(sxfPayParam);
//        log.info("返回的内容-------------    " + payRes);
//        log.info("响应参数，{}", payRes.getObject().toString());
//        // 支付失败
//        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
//            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
//        }
//        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
//
//        // 保存平台的订单号
//        order.setTransactionId(resultMap.get("uuid"));
//        order.setRefundTransactionId(resultMap.get("sxfUuid"));
//        // 支付成功或开放接口给第三方，不需要这里进行轮询查询订单
//        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
//            // 支付成功
//            order.setStatus(Order.Status.SUCCESSPAY.getCode());
//            order.setPayTime(new Date());
//        }
//        // 开放接口给第三方，不需要这里进行轮询查询订单
//        if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
//            order.setPayTime(new Date());
//        }
//        commissionService.EditCommissionStatus(order.getId(), order.getStatus());
//        return payRes;
//    }

    public PayRes sxfScanPay(Order order, SxfPayParam.PayType payType, Order.InterFaceWay interFaceWay) throws Exception {
        SxfPayParam sxfPayParam = createSxfPayParam(order);
        sxfPayParam.setPayType(payType.getCode());
        PayRes payRes = this.sxfPayServiceFeign.sxfScanPay(sxfPayParam);
        log.info("随行付返回的状态 ================{}",payRes.getStatus());
        CommonQueryParam commonQueryParam = new CommonQueryParam();
        commonQueryParam.setMerchantId(sxfPayParam.getMerchantId());
        commonQueryParam.setOrderNumber(sxfPayParam.getOutTradeNo());

        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            order.setName(payRes.getMsg());
            order.setStatus(Order.Status.FAILPAY.getCode());
            return payRes;
        }  if (PayRes.ResultStatus.PAYING.equals(payRes.getStatus())) {


            boolean queryFlag = OrderUtil.payQuery(() -> this.sxfPayServiceFeign.sxfOrderQuery(commonQueryParam), payRes);

            order.setStatus(Order.Status.SUCCESSPAY.getCode());

            order.setPayTime(new Date());
            if (!queryFlag)
            {
                order.setStatus(Order.Status.PLACEORDER.getCode());
            }
            payRes = this.sxfPayServiceFeign.sxfOrderQuery(commonQueryParam);
        }
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            log.info("响应参数{}", payRes.getObject().toString());
            Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
            log.info("resultMap{}", resultMap.toString());

            String tranSts=resultMap.get("tranSts");
            if("FAIL".equals(tranSts)){
                order.setName("订单超时,支付失败");
                order.setStatus(Order.Status.FAILPAY.getCode());
                order.setPayTime(new Date());
            }else if("PAYING".equals(tranSts)){
                order.setName("支付中");
                order.setStatus(Order.Status.FAILPAY.getCode());
                order.setPayTime(new Date());
            }else if("NOTPAY".equals(tranSts)){
                order.setName("未支付");
                order.setStatus(Order.Status.PLACEORDER.getCode());
                order.setPayTime(new Date());
            }else if("CLOSED".equals(tranSts)){
                order.setName("已关闭");
                order.setStatus(Order.Status.PLACEORDER.getCode());
                order.setPayTime(new Date());
            }else if("CANCELED".equals(tranSts)){
                order.setName("已撤销");
                order.setStatus(Order.Status.CANCELPAY.getCode());
                order.setPayTime(new Date());
            }else {
                log.info("随行付最终支付完成 -------  ");
                order.setTransactionId((String)resultMap.get("uuid"));
                order.setRefundTransactionId((String)resultMap.get("sxfUuid"));

                if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
                    order.setName(payRes.getMsg());
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    order.setPayTime(new Date());
                }
            }

            if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
                order.setPayTime(new Date());
            }
        }

        this.orderService.getRepository().saveAndFlush(order);

        return payRes;
    }


    /**
     * 退款
     */
    public void sxfRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        SxfPayParam sxfPayParam = createSxfPayParam(order);
        sxfPayParam.setTransactionId(order.getRefundTransactionId());
        sxfPayParam.setOutTradeNo(order.getTransactionId());
        sxfPayParam.setRefundAmount(refundPayPrice);
        PayRes payRes = sxfPayServiceFeign.sxfRefund(sxfPayParam);
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
        order.setTransactionId(resultMap.get("origUuid"));
        orderService.refundResult(order, refundPayPrice, payRes);
    }


    /**
     * 支付回调
     */
    public String sxfOrderCallBack(Map<String, String> map) {
        log.info("进入随行付异步回调");
        if (map != null) {
            // 商户订单号
            String orderNo = map.get("ordNo");
            // 交易状态
            String resultcode = map.get("bizCode");
            BigDecimal amount = new BigDecimal(map.get("amt"));
            //查询出本系统订单记录
            Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNo, CommonConstant.NORMAL_FLAG);
            // 保存平台的订单号
            order.setTransactionId(map.get("uuid"));
            order.setRefundTransactionId(map.get("sxfUuid"));
            if ("success".equals(orderService.getCallBackResult(orderNo, resultcode, amount.multiply(new BigDecimal(100)).stripTrailingZeros(), order, "0000"))) {
                return "{\"code\":\"success\",\"msg\":\"成功\"}";
            }
        }
        return "fail";

    }


}
