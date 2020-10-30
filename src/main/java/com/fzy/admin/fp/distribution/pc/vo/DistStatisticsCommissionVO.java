package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-12-28 16:16:32
 * @Desp
 **/
@Data
public class DistStatisticsCommissionVO {

    @ApiModelProperty("流水佣金")
    private BigDecimal commission;

    @ApiModelProperty("上一时段流水佣金")
    private BigDecimal lastCommission;

    @ApiModelProperty("分佣（支出）")
    private BigDecimal expend;

    @ApiModelProperty("上一时段分佣（支出）")
    private BigDecimal lastExpend;

    @ApiModelProperty("交易流水")
    private BigDecimal orderPrice;

    @ApiModelProperty("上一时段交易流水")
    private BigDecimal lastOrderPrice;

}
