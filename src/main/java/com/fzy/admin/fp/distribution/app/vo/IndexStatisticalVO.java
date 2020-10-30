package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2020-1-15 16:32:19
 * @Desp
 **/
@Data
public class IndexStatisticalVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String dateTime;

    @ApiModelProperty("一级代理费")
    private BigDecimal oneFee;

}
