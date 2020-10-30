package com.fzy.admin.fp.order.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-12-12 16:44:13
 * @Desp
 **/
@Data
public class DealDetailStatisticsVO {
    private BigDecimal total = BigDecimal.ZERO; // 交易金额

    private Integer totalCount; // 交易笔数量

    private BigDecimal refund = BigDecimal.ZERO; // 退款金额

    private Integer refundCount;// 退款次数

    private BigDecimal ActualMoney = BigDecimal.ZERO; // 实际盈收

    private Integer zfbTotalPrice = 0; // 支付宝交易笔数

    private Integer wxTotalPrice = 0; // 支付宝交易笔数

    private Integer bkTotalPrice = 0; //银行卡交易笔数

    private Integer vipTotalPrice = 0; // 会员卡交易笔数

    private Integer ohterTotalPrice = 0; // 其他交易笔数

    private String payTime;//日期
}
