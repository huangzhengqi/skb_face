package com.fzy.admin.fp.order.order.service;

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
import com.fzy.admin.fp.sdk.pay.domain.TqSxfPayParam;
import com.fzy.admin.fp.sdk.pay.feign.TqSxfPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hzq
 * @Description: 天阙随行付订单业务层
 */
@Service
@Slf4j
@Transactional
public class TqSxfOrderService extends BaseContent {

    @Resource
    private TqSxfPayServiceFeign tqSxfPayServiceFeign;

    public TqSxfPayServiceFeign getTqSxfPayServiceFeign() {
        return tqSxfPayServiceFeign;
    }

    @Resource
    private OrderService orderService;

    /**
     * 构建通用支付参数
     */
    public TqSxfPayParam createSxfPayParam(Order order) {
        TqSxfPayParam tqSxfPayParam = new TqSxfPayParam();
        tqSxfPayParam.setAuthCode(order.getAuthCode());
        tqSxfPayParam.setTotalFee(order.getActPayPrice());
        tqSxfPayParam.setOutTradeNo(order.getOrderNumber());
        tqSxfPayParam.setMerchantId(order.getMerchantId());
        tqSxfPayParam.setHbFqNum(order.getHbFqNum() == null ? null : order.getHbFqNum());
        tqSxfPayParam.setEquipmentId(order.getEquipmentId() == null ? null : order.getEquipmentId());
        tqSxfPayParam.setStoreId(order.getStoreId());
        return tqSxfPayParam;
    }

    /**
     * 网页支付
     */
    public Map<String, Object> tqSxfWapPay(Order order, TqSxfPayParam.PayType payType) throws Exception {

        // 构建通用支付参数
        TqSxfPayParam tqSxfPayParam = createSxfPayParam(order);
        tqSxfPayParam.setPayType(payType.getCode());
        PayRes payRes = tqSxfPayServiceFeign.TqSxfWapPay(tqSxfPayParam);
        log.info("响应参数，{}", payRes.getObject().toString());
        // 下单失败
        if (!PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());

        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", resultMap.get("payUrl"));
        map.put("channel", PayChannelConstant.Channel.SXF.getCode());
        return map;
    }

    /**
     * 扫码支付
     */
    public PayRes tqSxfScanPay(Order order, TqSxfPayParam.PayType payType, Order.InterFaceWay interFaceWay) throws Exception {

        TqSxfPayParam tqSxfPayParam = createSxfPayParam(order);
        tqSxfPayParam.setPayType(payType.getCode());
        PayRes payRes = this.tqSxfPayServiceFeign.TqSxfScanPay(tqSxfPayParam);
        CommonQueryParam commonQueryParam = new CommonQueryParam();
        commonQueryParam.setMerchantId(tqSxfPayParam.getMerchantId());
        commonQueryParam.setOrderNumber(tqSxfPayParam.getOutTradeNo());

        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            order.setName(payRes.getMsg());
            order.setStatus(Order.Status.FAILPAY.getCode());
            return payRes;
        }
        if (PayRes.ResultStatus.PAYING.equals(payRes.getStatus())) {

            log.info("天阙随行付支付中 -------  ");
            boolean queryFlag = OrderUtil.payQuery(() -> this.tqSxfPayServiceFeign.TqSxfOrderQuery(commonQueryParam), payRes);
            log.info("queryFlag          {}", queryFlag);
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            log.info("判断最终状态再继续查询一遍订单是否成功的 -------  ");

            order.setPayTime(new Date());
            if (!queryFlag) {
                log.info(tqSxfPayParam.getPayType() + "     ================== " + tqSxfPayParam.getPayType());
                //订单错误或者超时，撤销订单
                tqSxfPayServiceFeign.tqSxfReverse(order.getMerchantId(), order.getOrderNumber());
                order.setName(Order.Status.PLACEORDER.getStatus());
                order.setStatus(Order.Status.PLACEORDER.getCode());
            }
            payRes = this.tqSxfPayServiceFeign.TqSxfOrderQuery(commonQueryParam);
        }
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            log.info("响应参数 ======= {}", payRes.getObject().toString());
            Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());

            String tranSts = resultMap.get("tranSts");
            if ("FAIL".equals(tranSts)) {
                order.setName("交易失败");
                order.setStatus(Order.Status.FAILPAY.getCode());
                order.setPayTime(new Date());
            } else if ("PAYING".equals(tranSts)) {
                order.setName("支付中");
                order.setStatus(Order.Status.FAILPAY.getCode());
                order.setPayTime(new Date());
            } else if ("NOTPAY".equals(tranSts)) {
                order.setName("未支付");
                order.setStatus(Order.Status.PLACEORDER.getCode());
                order.setPayTime(new Date());
            } else if ("CLOSED".equals(tranSts)) {
                order.setName("已关闭");
                order.setStatus(Order.Status.PLACEORDER.getCode());
                order.setPayTime(new Date());
            } else if ("CANCELED".equals(tranSts)) {
                order.setName("已撤销");
                order.setStatus(Order.Status.CANCELPAY.getCode());
                order.setPayTime(new Date());
            } else {
                log.info("天阙随行付最终支付完成 -------  ");
                order.setTransactionId((String) resultMap.get("uuid"));
                order.setRefundTransactionId((String) resultMap.get("sxfUuid"));

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
        return payRes;
    }

    /**
     * 退款
     *
     * @param order
     * @param refundPayPrice
     * @throws Exception
     */
    public void tqSxfRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        TqSxfPayParam tqSxfPayParam = createSxfPayParam(order);
        tqSxfPayParam.setTransactionId(order.getRefundTransactionId());
        tqSxfPayParam.setOutTradeNo(order.getTransactionId());
        tqSxfPayParam.setRefundAmount(refundPayPrice);
        PayRes payRes = tqSxfPayServiceFeign.TqSxfRefund(tqSxfPayParam);

        CommonQueryParam commonQueryParam = new CommonQueryParam();
        commonQueryParam.setMerchantId(tqSxfPayParam.getMerchantId());
        commonQueryParam.setOrderNumber(tqSxfPayParam.getOutTradeNo());

        //退款失败
        if (PayRes.ResultStatus.REFUNDFAIL.equals(payRes.getStatus())) {
            orderService.refundResult(order, refundPayPrice, payRes);
        }
        //退款中
        if (PayRes.ResultStatus.REFUNDING.equals(payRes.getStatus())) {
            log.info("天阙随行付退款查询中 -------  ");
            boolean queryFlag = OrderUtil.refundPayQuery(() -> this.tqSxfPayServiceFeign.TqSxfRefundQuery(commonQueryParam), payRes);
            if (!queryFlag) {
                order.setStatus(Order.Status.REFUNDING.getCode());
            }
            //在查询一遍
            payRes = this.tqSxfPayServiceFeign.TqSxfRefundQuery(commonQueryParam);
        }
        // 已退款
        if (PayRes.ResultStatus.REFUND.equals(payRes.getStatus())) {
            //退款处理结果
            Map<String, String> resultMap = JacksonUtil.toStringMap(payRes.getObject().toString());
            order.setTransactionId(resultMap.get("origUuid"));
            orderService.refundResult(order, refundPayPrice, payRes);
        }
    }

    /**
     * 支付回调
     */
    public String sxfOrderCallBack(Map<String, String> map) {
        log.info("进入天阙随行付异步回调 ==============");
        if (map != null) {
            // 商户订单号
            String orderNo = map.get("ordNo");
            // 交易状态
            String resultcode = map.get("bizCode");
            String amt = map.get("amt");
            BigDecimal amount = new BigDecimal(amt);
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
