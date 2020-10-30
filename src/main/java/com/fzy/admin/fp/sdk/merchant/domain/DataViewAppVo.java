package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author hzq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataViewAppVo {

    /**
     * 销售总额
     */
    private BigDecimal transactionMoney = BigDecimal.ZERO;

    /**
     * 佣金总额
     */
    private BigDecimal CommissionMoney = BigDecimal.ZERO;

    /**
     * 交易笔数
     */
    private Integer transactionCount = 0;

    /**
     * 退款笔数
     */
    private Integer refundCount = 0;

    /**
     * 退款总额
     */
    private BigDecimal refundMoney = BigDecimal.ZERO;

    /**
     * 代理总数
     */
    private Integer countAgentNum = 0;

    /**
     * 一级代理商数
     */
    private Integer firstAgentNum = 0;

    /**
     * 二级代理商数
     */
    private Integer secondAgentNum = 0;

    /**
     * 三级代理商数
     */
    private Integer thirdAgentNum = 0;

    /**
     * 业务员数
     */
    private Integer salesmanNum = 0;

    /**
     * 商户总数
     */
    private Integer merchantNum = 0;

    private Integer type ;


}
