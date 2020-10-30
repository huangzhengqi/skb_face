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
public class CommissionDataVO {


    /**
     * 当前企业下所有商户的流水，扣除支付通道抽成
     */
    private BigDecimal allMoney = BigDecimal.ZERO;

    /**
     * 当前企业可获取的佣金
     */
    private BigDecimal commission = BigDecimal.ZERO;


}
