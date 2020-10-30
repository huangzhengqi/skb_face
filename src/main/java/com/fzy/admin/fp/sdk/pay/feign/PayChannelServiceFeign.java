package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.PayChannelRate;

/**
 * @author Created by wtl on 2019-04-23 23:31
 * @description 商户支付配置feign接口
 */
public interface PayChannelServiceFeign {

    /**
     * * 根据商户id获取商户支付通道配置
     * @param merchantId
     * @param payWay  支付方式、1：微信；2：支付宝
     * @param payType 支付类型，1：scan刷卡支付；2：web网页支付
     * @return
     */
    PayChannelRate findMerchantChannel(String merchantId, Integer payWay, Integer payType);

}
