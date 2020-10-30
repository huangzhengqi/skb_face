package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;

/**
 * @author Created by wtl on 2019-04-22 20:28
 * @description 微信支付feign接口
 */
public interface WxPayServiceFeign {


    /**
     * 微信native支付
     * @param wxPayParam
     * @return
     * @throws Exception
     */
    PayRes wxJsPay(WxPayParam wxPayParam) throws Exception;


    /**
     * 微信扫码支付接口
     * @param wxPayParam
     * @param order
     * @return
     * @throws Exception
     */
    PayRes wxScanPay(WxPayParam wxPayParam, Order order) throws Exception;

    /**
     * 微信支付查询订单，扫码后客户端轮询用
     * @param merchantId
     * @param order
     * @return
     */
    PayRes wxOrderQuery(String merchantId, Order order);

    /**
     * 微信支付撤销订单，订单错误或者超时调用
     * @param merchantId
     * @param orderNumber
     * @return
     * @throws Exception
     */
    PayRes wxReverse(String merchantId, String orderNumber) throws Exception;

    /**
     * 微信退款申请
     * @param wxPayParam
     * @return
     * @throws Exception
     */
    PayRes wxRefund(WxPayParam wxPayParam) throws Exception;

    /**
     * 小程序支付
     * @param wxPayParam
     * @return
     * @throws Exception
     */
    PayRes appletPay(WxPayParam wxPayParam) throws Exception;


}
