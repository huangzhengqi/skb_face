package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;

/**
 * 2
 *
 * @author Created by wtl on 2019-05-31 17:51
 * @description 统统收支付feign接口，d0没有退款
 */
public interface TtsPayServiceFeign {

    /**
     * 统统收付款码支付接口，商户扫客户
     */
    PayRes ttsScanPay(TtsPayParam model);

    /**
     * 统统收支付查询订单，扫码后客户端轮询用
     */
    PayRes ttsOrderQuery(TtsPayParam model);

    /**
     * 统统收扫码支付，用户扫商户
     */
    PayRes ttsJsPay(TtsPayParam model);


}
