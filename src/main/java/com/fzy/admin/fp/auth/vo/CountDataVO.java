package com.fzy.admin.fp.auth.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-04-29 20:15
 * @description
 */
@Data
public class CountDataVO {

    @ApiModelProperty("订单金额")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal orderAmount = BigDecimal.ZERO;

    @ApiModelProperty("订单数")
    private Integer orderNum = 0;

    @ApiModelProperty("退款金额")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @ApiModelProperty("退款数")
    private Integer refundNum = 0;

    @ApiModelProperty("有效交易基数")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal validDealAmount = BigDecimal.ZERO;

    @ApiModelProperty("佣金金额")
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    @ApiModelProperty("顾客实付")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal customerPaidAmount = BigDecimal.ZERO;

    @ApiModelProperty("优惠金额")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @ApiModelProperty("一级代理商佣金")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal agentCommissionAmount = BigDecimal.ZERO;

    @ApiModelProperty("二级代理商佣金")
    @JsonSerialize(using = MoneyJsonSerializer.class)
    private BigDecimal subAgentCommissionAmount = BigDecimal.ZERO;

    private String formatPayTime;

    @ApiModelProperty("支付方式")
    private Integer payWay;

    @ApiModelProperty("支付通道")
    private Integer payChannel;
}
