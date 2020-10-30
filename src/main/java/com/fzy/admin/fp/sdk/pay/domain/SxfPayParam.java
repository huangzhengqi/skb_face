package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-21 15:00
 * @description 随行付支付参数
 */
@Data
public class SxfPayParam {

    @Getter
    public enum PayType {
        WECHAT("WECHAT", "微信"),
        ALIPAY("ALIPAY", "支付宝"),
        JSAPI("JSAPI", "公众号支付"),
        FWC("FWC", "支付宝服务窗");
        private String code;

        private String status;

        PayType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String merchantId;

    private String outTradeNo; // 原支付订单号
    private String transactionId; // 随行付订单号
    private String authCode; // 付款码
    private String payType; // 支付类型，
    private BigDecimal totalFee; // 支付金额，分
    private BigDecimal refundAmount; // 退款金额，分
    private String userId;
    private String subOpenid;
    private String subAppid;

}
