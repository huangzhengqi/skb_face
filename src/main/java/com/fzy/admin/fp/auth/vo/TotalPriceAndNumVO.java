package com.fzy.admin.fp.auth.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-05-05 17:06
 * @description
 */
@Data
public class TotalPriceAndNumVO {
    private Integer num = 0;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
