package com.fzy.admin.fp.member.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class DiscountRuleVO {
    @ApiModelProperty("满减ID")
    private String id;

    @ApiModelProperty("订单金额")
    private BigDecimal full;

    @ApiModelProperty("折扣金额")
    private BigDecimal less;


}
