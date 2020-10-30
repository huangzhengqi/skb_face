package com.fzy.admin.fp.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-11 14:51:37
 * @Desp
 **/
@Data
public class StatisticsGoodsOrderVO {
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String dateTime;

    @ApiModelProperty(value = "销量")
    private Integer salesCountNum;


}
