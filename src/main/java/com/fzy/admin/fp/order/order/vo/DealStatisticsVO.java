package com.fzy.admin.fp.order.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-12 11:06:43
 * @Desp
 **/
@Data
public class DealStatisticsVO {
    private BigDecimal total;//交易金额

    private Integer totalCount;//交易笔数

    private String payTime;//支付时间
}
