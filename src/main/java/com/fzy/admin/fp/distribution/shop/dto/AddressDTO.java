package com.fzy.admin.fp.distribution.shop.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-2 14:16:43
 * @Desp
 **/
@Data
public class AddressDTO {
    private String id;

    @ApiModelProperty("收货人")
    @NotBlank(message = "收货人不能为空")
    private String name;

    @ApiModelProperty("状态为1时是默认地址")
    private Integer status;

    @ApiModelProperty("地址")
    @NotBlank(message = "地址不能为空")
    private String place;

    @ApiModelProperty("详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailPlace;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
