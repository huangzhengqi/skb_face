package com.fzy.admin.fp.distribution.order.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author  yy
 * @Date 2019-12-3 17:02:04
 * @Description:  订单内的商品信息
 *
*/


@Data
@Entity
@Table(name="lysj_dist_order_goods")
public class OrderGoods extends BaseEntity{

    @ApiModelProperty("商品订单id")
    private String shopOrderId;

    @ApiModelProperty("商品图片")
    private String img;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品数量")
    private Integer num;

    @ApiModelProperty("商品id")
    private String goodsId;
}
