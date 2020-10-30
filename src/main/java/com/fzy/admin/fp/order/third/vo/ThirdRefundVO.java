package com.fzy.admin.fp.order.third.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-04 15:20
 * @description 第三方订单退款返回
 */
@Data
public class ThirdRefundVO {

    private Integer status; // 订单状态，5:全额退款；6：部分退款

    private String nonceStr; // 随机数

    private String outTradeNo; // 商户订单号

    private String orderNumber; // 平台订单号

    private BigDecimal refundPrice = BigDecimal.ZERO; // 退款金额，元

    private BigDecimal remainPrice = BigDecimal.ZERO; // 可退金额，元

    private String sign; // 签名


}
