package com.fzy.admin.fp.order.pc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderActPayPriceDto {

    private BigDecimal totalPrice;

    private BigDecimal refundPrice;

    public OrderActPayPriceDto() {
    }

    public OrderActPayPriceDto(BigDecimal totalPrice,BigDecimal refundPrice) {
        this.totalPrice = totalPrice;
        this.refundPrice = refundPrice;
    }
}
