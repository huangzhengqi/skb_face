package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-16 20:00
 * @description 通用的支付参数
 */
@Data
public class CommonPayParam {

    /**
     * 惠闪付支付类型
     */
    @Getter
    public enum HsfType {
        WXPAY("weixin", "微信"),
        ALIPAY("alipay", "支付宝");
        private String code;

        private String status;

        HsfType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String merchantId; // 商户id
    private String orderNumber; // 订单号
    private String type; // 支付类型
    private BigDecimal totalFee; // 支付金额，元

    private String authCode; // 付款码，条码支付需要

}
