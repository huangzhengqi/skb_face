package com.fzy.admin.fp.order.order.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderIndexDayCount {

    private Integer todayOrderAmount; // 今日订单数量
    private BigDecimal todayOrderPrice = BigDecimal.ZERO; // 今日订单金额
    private BigDecimal todayRefund ; //今日退款金额
    private Integer todayRefundCount; //今日退款笔数
    private BigDecimal todayTotal ;//今日实际营收

    private Integer yesterdayOrderAmount;// 昨日订单数量
    private BigDecimal yesterdayOrderPrice = BigDecimal.ZERO; // 昨日订单金额
    private BigDecimal yesterdayRefund ; //昨日退款金额
    private Integer yesterdayRefundCount;//昨日退款笔数
    private BigDecimal yesterdayTotal ; //昨日实际营收
}
