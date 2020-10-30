package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;

/**
 * @author Created by wsj on 2019-07-22 14:06
 * @description 富友支付feign接口
 */
public interface FyPayServiceFeign {

    /**
     * 富友扫码支付接口
     */
    PayRes fyScanPay(FyPayParam model) throws Exception;
    /**
     * 富友支付，用户扫商户
     */
    PayRes fyWapPay(FyPayParam model) throws Exception;


    /**
     * 富友支付查询订单
     */
    PayRes fyOrderQuery(FyPayParam model) ;

    /**
     *  富友退款
     */
    PayRes fyRefund(FyPayParam model) throws Exception;


}
