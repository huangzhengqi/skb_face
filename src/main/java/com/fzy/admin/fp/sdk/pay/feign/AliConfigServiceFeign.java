package com.fzy.admin.fp.sdk.pay.feign;


/**
 * @author Created by wtl on 2019-04-25 21:15
 * @description 支付宝支付配置feign接口
 */
public interface AliConfigServiceFeign {

    /**
     * 根据商户id获取公钥
     */
    String getPublicKey(String merchantId);

    String getAppId(String merchantId);

    String getAppSecret(String merchantId);

}
