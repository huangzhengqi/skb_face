package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateCategoryDTO {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("行业分类 0超市 1自助点餐 2医药 3加油站 4景区")
    private Integer industryCategory;
}
