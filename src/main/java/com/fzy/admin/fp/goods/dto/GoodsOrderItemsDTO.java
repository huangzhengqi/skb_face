package com.fzy.admin.fp.goods.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品销量统计销售额DTO
 */
@Data
public class GoodsOrderItemsDTO {

    private BigDecimal totalPrice;

    public GoodsOrderItemsDTO(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
