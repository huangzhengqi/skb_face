package com.fzy.admin.fp.order.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-06 16:13
 * @description 商户首页订单统计
 */
@Data
public class OrderIndexCount {

    private Integer todayOrderAmount; // 今日订单数量
    private BigDecimal todayOrderPrice = BigDecimal.ZERO; // 今日订单金额

    private Integer yesterdayOrderAmount;// 昨日订单数量
    private BigDecimal yesterdayOrderPrice = BigDecimal.ZERO; // 昨日订单金额

}
