package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTransactionVO {

    /**
     * 交易总额
     */
    private BigDecimal transactionMoney = BigDecimal.ZERO;

    /**
     * 上个月交易总额
     */
    private BigDecimal lastTransactionMoney = BigDecimal.ZERO;

    /**
     * 交易笔数
     */
    private Integer transactionCount = 0;

    /**
     * 上个月交易笔数
     */
    private Integer lastTransactionCount = 0;

    /**
     * 退款总额
     */
    private BigDecimal refundMoney = BigDecimal.ZERO;

    /**
     * 上个月退款总额
     */
    private BigDecimal lastRefundMoney = BigDecimal.ZERO;

    /**
     * 退款笔数
     */
    private Integer refundCount = 0;

    /**
     * 上个月退款笔数
     */
    private Integer lastRefundCount = 0;

    /**
     * 实际营收
     */
    private BigDecimal actualMoney = BigDecimal.ZERO;

    /**
     * 上个月实际营收
     */
    private BigDecimal lastActualMoney = BigDecimal.ZERO;

    /**
     * 佣金
     */
    private BigDecimal commissionMoney = BigDecimal.ZERO;


    /**
     * 上个月佣金
     */
    private BigDecimal lastCommissionMoney = BigDecimal.ZERO;

    /**
     * 支付宝交易笔数
     */
    private Integer zfbPayTimes = 0;
    /**
     * 微信交易笔数
     */
    private Integer wxPayTimes = 0;
    /**
     * 其他交易笔数
     */
    private Integer ohterPayTimes = 0;

}
