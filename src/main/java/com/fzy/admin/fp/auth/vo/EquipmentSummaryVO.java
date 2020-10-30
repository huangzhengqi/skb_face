package com.fzy.admin.fp.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentSummaryVO {
    @ApiModelProperty("订单总数")
    private Integer orderNum;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty("客户付款")
    private BigDecimal payMoney;

    @ApiModelProperty("优惠")
    private BigDecimal DiscountMoney;

}
