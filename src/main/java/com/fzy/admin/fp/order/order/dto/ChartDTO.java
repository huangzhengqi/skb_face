package com.fzy.admin.fp.order.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-06 23:13
 * @description
 */
@Data
public class ChartDTO {

    private BigDecimal price = BigDecimal.ZERO;
    private Integer amount = 0;
}
