package com.fzy.admin.fp.distribution.money.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 费率分润
 * @Author yy
 * @Date 2019-11-16 11:12:14
 **/
@Data
public class RateDTO {
    private String id;

    @ApiModelProperty("一级费率")
    private Integer firstFixedRate;//一级费率

    @ApiModelProperty("二级费率")
    private Integer secondFixedRate;//二级费率

    @ApiModelProperty("代理id")
    private String serviceProviderId;

    @ApiModelProperty("直推费率")
    private Integer pushPrice;//直推费率

    @ApiModelProperty("备注")
    private String remark;//备注

    @ApiModelProperty("名称")
    private String name;
}
