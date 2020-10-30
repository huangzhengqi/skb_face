package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;

/**
 * @author Created by wtl on 2019-06-15 21:06
 * @description 惠闪付支付feign接口
 */
public interface HsfPayServiceFeign {

    /**
     * 惠闪付扫码支付接口
     */
    PayRes hsfScanPay(CommonPayParam model) throws Exception;

    /**
     * 惠闪付支付查询订单
     */
    PayRes hsfOrderQuery(CommonQueryParam model);

    /**
     * @author Created by wtl on 2019/4/24 16:43
     * @Description 惠闪付退款
     */
    PayRes hsfRefund(CommonRefundParam model) throws Exception;

    /**
     * 惠闪付进件成功后,完善shop_id跟key
     */
    boolean hsfInit(String merchantId, String shop_id, String hsfKey);
}
