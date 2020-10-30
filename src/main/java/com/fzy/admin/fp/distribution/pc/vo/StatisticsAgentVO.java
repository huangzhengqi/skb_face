package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-27 11:10:58
 * @Desp
 **/
@Data
public class StatisticsAgentVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String dateTime;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty("一级代理费")
    private Integer oneAgent;

    @ApiModelProperty("二级代理费")
    private Integer twoAgent;

    @ApiModelProperty("三级代理费")
    private Integer threeAgent;
}
