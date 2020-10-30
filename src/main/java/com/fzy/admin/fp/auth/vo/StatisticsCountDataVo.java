package com.fzy.admin.fp.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 *  @author Administrator
 */
@Data
public class StatisticsCountDataVo {

    @ApiModelProperty("公司ID")
    private String companyId;

    @ApiModelProperty("计算每日时间")
    private String saveDay;

    @ApiModelProperty("订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal orderAmount;

    @ApiModelProperty("一级代理商")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal agentCommissionAmount;

    @ApiModelProperty("二级代理商")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal subAgentCommissionAmount;

    @ApiModelProperty("优惠金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @ApiModelProperty("三级代理商")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal tertiaryCommissionAmount;

    @ApiModelProperty("支付时间")
    private String formatPayTime;

    @ApiModelProperty("支付方式")
    private Integer payWay;

    @ApiModelProperty("支付通道")
    private Integer payChannel;

    @ApiModelProperty("订单数")
    private Integer orderNum = Integer.valueOf(0);

    @ApiModelProperty("退款金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @ApiModelProperty("退款数")
    private Integer refundNum = Integer.valueOf(0);

    @ApiModelProperty("有效交易基数")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal validDealAmount;

    @ApiModelProperty("佣金金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    @ApiModelProperty("顾客实付")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal customerPaidAmount = BigDecimal.ZERO;

}
