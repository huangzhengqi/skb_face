package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-27 10:33:03
 * @Desp
 **/
@Data
public class StatisticsFeeVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String dateTime;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal count;

    @ApiModelProperty("一级代理费")
    private BigDecimal oneFee;

    @ApiModelProperty("二级代理费")
    private BigDecimal twoFee;

    @ApiModelProperty("三级代理费")
    private BigDecimal threeFee;


}
