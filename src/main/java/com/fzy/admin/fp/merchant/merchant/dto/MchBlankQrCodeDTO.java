package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class MchBlankQrCodeDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("二维码id,多个用逗号隔开")
    private String qrcode;

    @ApiModelProperty("状态 0禁用 1启用")
    private Integer status;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("门店id")
    private String storeId;
}
