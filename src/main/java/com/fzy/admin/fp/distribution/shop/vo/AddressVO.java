package com.fzy.admin.fp.distribution.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-2 14:04:34
 * @Desp
 **/
@Data
public class AddressVO {
    @ApiModelProperty("收货人")
    private String name;

    @ApiModelProperty("状态为1时是默认地址")
    private Integer status;

    @ApiModelProperty("地址")
    private String place;

    @ApiModelProperty("详细地址")
    private String detailPlace;

    @ApiModelProperty("手机号")
    private String phone;
}
