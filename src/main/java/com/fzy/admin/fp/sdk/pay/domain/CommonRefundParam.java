package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-16 20:39
 * @description 通用支付退款参数
 */
@Data
public class CommonRefundParam {

    private String merchantId; // 商户Id
    private String orderNumber; // 商户订单号
    private String transactionId; // 平台订单号，有的只能用平台订单号退款
    private BigDecimal refundFee; // 退款金额，元


}
