package com.fzy.admin.fp.order.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-05-12 23:00
 * @description
 */
@Data
public class MerchantOverviewCountVO {
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal actualRevenue = BigDecimal.ZERO;//实际营收
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal merchantDiscount = BigDecimal.ZERO;//商户优惠
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal orderAmount = BigDecimal.ZERO;//订单金额
    private int orderTotal;//订单总数
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal otherDiscount = BigDecimal.ZERO;//其他优惠
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal realPayAmount = BigDecimal.ZERO;//顾客实付
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal refundAmount = BigDecimal.ZERO;//退款总额
    private int refundCount;//退款数
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal settlementRefundFee = BigDecimal.ZERO;//商户实退
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal settlementTotalFee = BigDecimal.ZERO;//商户实收
    private Integer memberTotalToday;//今日新增会员数
}
