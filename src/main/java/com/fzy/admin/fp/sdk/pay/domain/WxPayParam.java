package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;

/**
 * @author Created by wtl on 2019-04-23 14:34
 * @description 微信支付参数
 */
@Data
public class WxPayParam {

    /**
     * 商户id，用来支付模块查询支付配置参数
     */
    private String merchantId;

    /**
     * 微信用户openId，小程序授权获取openid方法不一样
     */
    private String openid;

    /**
     * 小程序appId
     */
    private String appletAppId;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 订单金额
     */
    private String total_fee;

    /**
     * 退款金额
     */
    private String refund_fee;

    /**
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     */
    private String auth_code;

}
