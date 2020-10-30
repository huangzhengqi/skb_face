package com.fzy.admin.fp.distribution.app.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-3 09:12:03
 * @Desp
 **/
@Data
public class PayDTO {
    @ApiModelProperty("地址")
    @NotBlank(message = "请选择收货地址")
    private String addressId;

    @ApiModelProperty("备注")
    private String remark;

    /*@ApiModelProperty("支付方式 支付宝0 余额1")
    @NotNull(message = "请选择支付方式")
    private Integer payType;*/
/*
    @ApiModelProperty("登录密码")
    private String password;*/
}
