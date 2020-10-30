package com.fzy.admin.fp.order.app.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-06-10 15:13
 * @description
 */
@Data
public class MerchantAppStatisticsVO {
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal orderTotalMoney = BigDecimal.ZERO;//订单总金额
    private Integer orderCount = 0;//订单数
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal merchantDiscountMoney = BigDecimal.ZERO;//商户优惠
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal refundMoney = BigDecimal.ZERO;//退款金额
    private Integer refundCount = 0;//退款数
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal otherDiscountMoney = BigDecimal.ZERO;//其他优惠
}
