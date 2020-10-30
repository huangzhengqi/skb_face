package com.fzy.admin.fp.distribution.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-12-2 11:18:53
 * @Desp
 **/
@Data
public class ShopCartVO {
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("规格")
    private String property;

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("商品id")
    private String id;

    public ShopCartVO(String name, BigDecimal price, Integer num,String property,String img,String id) {
        this.name = name;
        this.price = price;
        this.num = num;
        this.property=property;
        this.img=img;
        this.id=id;
    }
}
