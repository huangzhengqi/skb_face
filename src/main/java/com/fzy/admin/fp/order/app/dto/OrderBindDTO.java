package com.fzy.admin.fp.order.app.dto;

import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.order.order.domain.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author yy
 * @Date 2019-11-22 19:14:26
 * @Desp
 **/

@ApiModel
@Data
public class OrderBindDTO {

    @ApiModelProperty("支付订单")
    private Order order;

    @ApiModelProperty("商品信息")
    private List<Goods> goods;
}
