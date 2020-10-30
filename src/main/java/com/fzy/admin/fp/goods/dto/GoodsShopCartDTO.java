package com.fzy.admin.fp.goods.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsShopCartDTO {

    @ApiModelProperty("商品id")
    @NotBlank(message = "请选择商品")
    private String goodsId;


    @ApiModelProperty("数量")
    @NotNull(message = "请输入数量")
    private Integer num;

    @ApiModelProperty("规格")
    private String property;
}
