package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 购物袋表
 */

@Data
@Entity
@Table(name="goods_shop_cart")
public class GoodsShopCart extends BaseEntity {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("商品属性")
    private String property;

    @ApiModelProperty("门店id")
    private String storeId;
}
