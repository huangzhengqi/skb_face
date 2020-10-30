package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DisMchInfoDTO {

    @ApiModelProperty(value = "归属业务员")
    private String managerName;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
