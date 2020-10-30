package com.fzy.admin.fp.order.pc.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-04-30 17:24
 * @description sql返回的统计数据接收对象
 */
@Data
public class OrderCountDto {

    private Long count;
    private BigDecimal totalPrice;
    private BigDecimal refundPrice;

    public OrderCountDto() {
    }

    public OrderCountDto(Long count, BigDecimal totalPrice, BigDecimal refundPrice) {
        this.count = count;
        this.totalPrice = totalPrice;
        this.refundPrice = refundPrice;
    }
}
