package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-28 15:18:34
 * @Desp
 **/
@Data
public class DistTrendVO {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String dateTime;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer num;


}
