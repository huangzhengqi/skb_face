package com.fzy.admin.fp.goods.dto;

import lombok.Data;

/**
 * 商品销量DTO
 */
@Data
public class GoodsOrderSalesDTO{

    private String goodsId; //商品id

    private String merchantId; //商户id

    private String goodsName ; //商品名称

    private String goodsImg; //商品图片

    private Double salesNum; //销售额

    private Integer salesCountNum; //销量（份）

    private Integer tradingVolume; //交易量（笔）



}
