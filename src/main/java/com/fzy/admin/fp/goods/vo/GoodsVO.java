package com.fzy.admin.fp.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * 自助点餐商品类
 */
@Data
public class GoodsVO {

    @ApiModelProperty("商品id")
    private String id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品分类")
    private String cagegoryName;

    @ApiModelProperty("商品编号")
    private String itemNumber;

    @ApiModelProperty("价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("上下架")
    private Integer isShelf;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("库存")
    private Integer stockNum;

    @ApiModelProperty("销量")
    private Integer salesNum;

    public GoodsVO(String id, String goodsName, String cagegoryName, String itemNumber, BigDecimal goodsPrice, Integer isShelf, Integer sort, Integer stockNum, Integer salesNum, String unit, String goodsPic) {
        this.id = id;
        this.goodsName = goodsName;
        this.cagegoryName = cagegoryName;
        this.itemNumber = itemNumber;
        this.goodsPrice = goodsPrice;
        this.isShelf = isShelf;
        this.sort = sort;
        this.stockNum = stockNum;
        this.salesNum = salesNum;
        this.unit = unit;
        this.goodsPic = goodsPic;
    }

    @ApiModelProperty("计价单位")
    private String unit;

    @ApiModelProperty("封面")
    private String goodsPic;

}
