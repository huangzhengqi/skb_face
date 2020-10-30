package com.fzy.admin.fp.distribution.app.dto;

import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-5 09:45:43
 * @Desp
 **/
@Data
public class ShopCartDTO {
    @ApiModelProperty("商品id")
    @NotBlank(message = "请选择商品")
    private String goodsId;


    @ApiModelProperty("数量")
    @NotNull(message = "请输入数量")
    private Integer num;

    @ApiModelProperty("规格")
    private String property;
}
