package com.fzy.admin.fp.order.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-04-30 14:44
 * @description 订单统计显示
 */
@Data
public class OrderCountVo {


    @ApiModelProperty(value = "日统计 订单数量")
    private Long dayOrderAmount;
    @ApiModelProperty(value = "日统计 订单金额")
    private BigDecimal dayOrderPrice;
    @ApiModelProperty(value = "日统计 退款金额")
    private BigDecimal dayRefundPrice;

    @ApiModelProperty(value = "周统计 订单数量")
    private Long weekOrderAmount;
    @ApiModelProperty(value = "周统计 订单金额")
    private BigDecimal weekOrderPrice;
    @ApiModelProperty(value = "周统计 退款金额")
    private BigDecimal weekRefundPrice;


    @ApiModelProperty(value = "月统计 订单数量")
    private Long monthOrderAmount;
    @ApiModelProperty(value = "月统计 订单金额")
    private BigDecimal monthOrderPrice;
    @ApiModelProperty(value = "月统计 退款金额")
    private BigDecimal monthRefundPrice;

}
