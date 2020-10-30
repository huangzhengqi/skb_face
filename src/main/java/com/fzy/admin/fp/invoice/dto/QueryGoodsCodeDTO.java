package com.fzy.admin.fp.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询税务局商品编码")
@Data
public class QueryGoodsCodeDTO {
    @ApiModelProperty("商品编码名称")
    private String name;
    @ApiModelProperty("商品编码名称")
    private String likeName;
    @ApiModelProperty("上级商品编码")
    private String parentCode;
    @ApiModelProperty("商品编码层级，从0开始")
    private Integer level;
}
