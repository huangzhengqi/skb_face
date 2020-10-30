package com.fzy.admin.fp.sdk.member.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-19 0:28
 * @description 会员退款参数
 */
@Data
public class MemberRefundParam {

    private String merchantId; // 商户id
    private String orderNumber; // 订单编号
    private Integer status; // 订单状态
    private BigDecimal refundAmount = BigDecimal.ZERO; // 退款金额

    private String storeId;
    private String storeName;
}
