package com.fzy.admin.fp.order.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-06 16:13
 * @description 商户首页订单统计
 */
@Data
public class OrderIndexCountNew {


    private BigDecimal total = BigDecimal.ZERO; // 交易金额
    private Integer totalCount; // 交易笔数量


    private BigDecimal refund = BigDecimal.ZERO; // 退款金额
    private Integer refundCount;// 退款次数

    private BigDecimal ActualMoney = BigDecimal.ZERO; // 实际盈收

    private Integer zfbTotalPrice = 0; // 支付宝交易笔数
    private Integer wxTotalPrice = 0; // 支付宝交易笔数
    private Integer ohterTotalPrice = 0; // 其他交易笔数


}
