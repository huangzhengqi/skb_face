package com.fzy.admin.fp.ali.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 营业执照图片
 */
@Data
public class BusinessLicenseVO {

    @ApiModelProperty(value="商户类型 个体工商户 企业 小微商户")
    private String subjectType;

    @ApiModelProperty(value="营业执照注册号")
    private String license;

    @ApiModelProperty(value="商户全称")
    private String merchantName;

    @ApiModelProperty(value="经营地址")
    private String registerAddress;
}
