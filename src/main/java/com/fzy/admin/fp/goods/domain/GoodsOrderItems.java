package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 订单明细表
 */

@Entity
@Data
@Table(name = "goods_order_items")
public class GoodsOrderItems extends BaseEntity {

    @ApiModelProperty("订单id")
    private String goodsOrderId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("封面")
    private String goodsPic;

    @ApiModelProperty("价格")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal goodsPrice;

    @ApiModelProperty("销量")
    private Integer salesNum;

    @ApiModelProperty("价格")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal totalPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("计价单位")
    private String unit;

    @ApiModelProperty("商品ID")
    private String goodsId;
}
