package com.fzy.admin.fp.distribution.commission.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-12-26 17:54:20
 * @Desp
 **/
@Data
public class CommissionTotalVO {
    private BigDecimal totalOrder;//总流水

    private BigDecimal totalCommission;//总分佣

    public CommissionTotalVO(BigDecimal totalOrder, BigDecimal totalCommission) {
        this.totalOrder = totalOrder;
        this.totalCommission = totalCommission;
    }
}
