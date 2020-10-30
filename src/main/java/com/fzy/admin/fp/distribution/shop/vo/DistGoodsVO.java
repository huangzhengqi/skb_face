package com.fzy.admin.fp.distribution.shop.vo;

import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-11-30 16:40:43
 * @Desp
 **/
@Data
public class DistGoodsVO {
    private String img;//商品图片

    private Integer num;//库存

    private BigDecimal price;//价格

    private String remake;//介绍

    private List<GoodsProperty> shops = new ArrayList<>();

    private Integer weight;//权重

    private Integer status;//上架0 下架1
}
