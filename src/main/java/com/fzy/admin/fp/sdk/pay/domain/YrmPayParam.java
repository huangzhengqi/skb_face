package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-21 15:00
 * @description 易融码支付参数
 */
@Data
public class YrmPayParam {

    @Getter
    public enum PayType {
        WXPAY("wx", "微信"),
        ALIPAY("ali", "支付宝");
        private String code;

        private String status;

        PayType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String merchantId;

    private String outTradeNo; // 原支付订单号
    private String transactionId; // 易融码订单号
    private String authCode; // 付款码
    private String payType; // 支付类型，
    private BigDecimal totalFee; // 支付金额，分
    private BigDecimal refundAmount; // 退款金额，分

}
