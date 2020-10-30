package com.fzy.admin.fp.merchant.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@ApiModel(value = "门店名称/商户logo")
@Data
public class StoreNameMerchantLogoVO {

    @ApiModelProperty("图片id")
    private String photoId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;
}
