package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;

/**
 * @author Created by wtl on 2019-04-23 23:31
 * @description 会员宝支付feign接口
 */
public interface HybPayServiceFeign {

    /**
     * 会员宝网页支付
     */
    String hybWapPay(HybPayParam model);

    PayRes hybRefund(HybPayParam model);
}
