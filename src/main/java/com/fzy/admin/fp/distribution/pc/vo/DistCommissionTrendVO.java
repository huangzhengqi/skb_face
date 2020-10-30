package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-12-30 11:20:07
 * @Desp
 **/
@Data
public class DistCommissionTrendVO {

    @ApiModelProperty(value = "日期")
    private String dateTime;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;
}
