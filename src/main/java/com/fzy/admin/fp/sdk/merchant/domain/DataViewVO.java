package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataViewVO {

    /**
     * 服务商数
     */
    private Integer oemNum = 0;
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
     * 商户数
     */
    private Integer merchantNum = 0;
    /**
     * 交易总额
     */
    private BigDecimal transactionMoney = BigDecimal.ZERO;
    /**
     * 交易数
     */
    private Integer transactiontNum = 0;
    /**
     * 佣金数
     */
    private BigDecimal CommissionMoney = BigDecimal.ZERO;

    /**
     * 当月交易额
     */
    private BigDecimal monthMoney = BigDecimal.ZERO;

    /**
     * 本月新增区代
     */
    private Integer monthFirstAgentNum = 0;

    /**
     * 本月新增省代
     */
    private Integer monthSecondAgentNum = 0;

    /**
     * 本月新增市代
     */
    private Integer monthThirdAgentNum = 0;

    /**
     * 本月新增商户数
     */
    private Integer monthMerchantNum = 0;
}
