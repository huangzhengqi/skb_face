package com.fzy.admin.fp.order.pc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCommissionDto {

    private BigDecimal commission;

    public OrderCommissionDto(BigDecimal commission) {
        this.commission = commission;
    }
}
