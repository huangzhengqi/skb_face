package com.fzy.admin.fp.order.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * hzq
 * 2019-12-12
 * 交易数据统计
 */
@Data
public class OrderPeriodCount {

    private Integer thisOrderAmount; // 这个时间段的订单数量
    private BigDecimal thisOrderPrice = BigDecimal.ZERO; // 这个时间段的订单金额
    private BigDecimal thisRefund ; //这个时间段的退款金额
    private Integer thisRefundCount; //这个时间段的退款笔数
    private BigDecimal thisTotal ;//这个时间段的实际营收

    private Integer lastOrderAmount;// 上个时间段的订单数量
    private BigDecimal lastOrderPrice = BigDecimal.ZERO; //上个时间段的订单金额
    private BigDecimal lastRefund ; //上个时间段的退款金额
    private Integer lastRefundCount;//上个时间段的退款笔数
    private BigDecimal lastTotal ; //上个时间段的实际营收
}
