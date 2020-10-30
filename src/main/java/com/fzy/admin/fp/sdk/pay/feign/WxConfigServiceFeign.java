package com.fzy.admin.fp.sdk.pay.feign;


import java.util.Map;

/**
 * @author Created by wtl on 2019-04-25 21:15
 * @description 微信支付配置feign接口
 */
public interface WxConfigServiceFeign {

    /**
     * 根据服务商id获取支付配置
     */
    Map<String, String> getWxConfig(String companyId);


}
