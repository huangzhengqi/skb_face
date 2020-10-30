package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-04-25 17:31
 * @description 会员宝支付参数
 */
@Data
public class HybPayParam {

    @Getter
    public enum PayType {
        WXPAY("SCANPAY_WEIXIN", "微信"),
        ALIPAY("SCANPAY_ALIPAY", "支付宝");
        private String code;

        private String status;

        PayType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String merchantId;

    private String orderNo; // 原支付订单号
    private String refundOrderNo; // 退款订单号
    private String payType; // 支付类型，微信：SCANPAY_WEIXIN，支付宝：SCANPAY_ALIPAY
    private BigDecimal amount; // 支付金额，分
    private BigDecimal refundAmount; // 退款金额，分
    private String asynNotifyUrl; // 异步通知地址

}
