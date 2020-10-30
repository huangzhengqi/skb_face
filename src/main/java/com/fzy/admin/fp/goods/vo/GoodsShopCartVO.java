package com.fzy.admin.fp.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsShopCartVO {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("图片")
    private String goodsPic;

    @ApiModelProperty("商品id")
    private String id;

    public GoodsShopCartVO(String goodsName, BigDecimal goodsPrice, Integer num,String goodsPic,String id) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.num = num;
        this.goodsPic=goodsPic;
        this.id=id;
    }
}
