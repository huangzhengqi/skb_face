package com.fzy.admin.fp.order.pc.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-05 16:11
 * @description 数量和金额’
 */
@Data
public class AmountPrice {

    private Integer amount = 0;
    private BigDecimal price = BigDecimal.ZERO;

}
