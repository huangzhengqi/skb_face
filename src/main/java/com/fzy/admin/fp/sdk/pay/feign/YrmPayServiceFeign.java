package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;

/**
 * @author Created by wtl on 2019-05-21 17:51
 * @description 易融码支付feign接口
 */
public interface YrmPayServiceFeign {

    /**
     * 易融码扫码支付接口
     */
    PayRes yrmScanPay(YrmPayParam model) throws Exception;

    /**
     * 易融码支付查询订单，扫码后客户端轮询用
     */
    PayRes yrmOrderQuery(String merchantId, String orderNumber);

    /**
     * 易融码jsapi支付，用户扫商户
     */
    PayRes yrmJsPay(YrmPayParam model) throws Exception;

    /**
     * @author Created by wtl on 2019/4/24 16:43
     * @Description 易融码退款
     */
    PayRes yrmRefund(YrmPayParam model) throws Exception;

}
