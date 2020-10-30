package com.fzy.admin.fp.order.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-04-29 20:15
 * @description
 */
@Data
public class CommissionSummaryVO {
    @ApiModelProperty(value = "交易总额")
    BigDecimal orderTotal;
    @ApiModelProperty(value = "佣金总额")
    BigDecimal commissionTotal;

    @ApiModelProperty(value = "支付宝交易总额")
    BigDecimal zfbOrderTotal;
    @ApiModelProperty(value = "支付宝佣金总额")
    BigDecimal zfbCommissionTotal;

    @ApiModelProperty(value = "微信交易总额")
    BigDecimal wxOrderTotal;
    @ApiModelProperty(value = "微信佣金总额")
    BigDecimal wxCommissionTotal;

    @ApiModelProperty(value = "随行付交易总额")
    BigDecimal sxfOrderTotal;
    @ApiModelProperty(value = "随行付佣金总额")
    BigDecimal sxfCommissionTotal;

    @ApiModelProperty(value = "富有交易总额")
    BigDecimal fyOrderTotal;
    @ApiModelProperty(value = "富有佣金总额")
    BigDecimal fyCommissionTotal;

    @ApiModelProperty(value = "已结算佣金")
    BigDecimal settleCommisson;

    @ApiModelProperty(value = "未结算佣金")
    BigDecimal unSettleCommisson;


}
