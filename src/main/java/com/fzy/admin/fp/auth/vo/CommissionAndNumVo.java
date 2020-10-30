package com.fzy.admin.fp.auth.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommissionAndNumVo {

    private Integer num = 0;
    private BigDecimal commissionAmount = BigDecimal.ZERO;//佣金金额
}
