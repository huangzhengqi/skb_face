package com.fzy.admin.fp.goods.bo;

import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.domain.GoodsOrderItems;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品订单等级入参
 */

@Data
public class GoodsOrderDetailsBO extends GoodsOrder {

        @ApiModelProperty("商店名称")
        private String storeName;

        @ApiModelProperty("昵称")
        private String nickname;

        @ApiModelProperty("会员等级")
        private String memberLevelId;

        @ApiModelProperty("会员头像")
        private String head;

        @ApiModelProperty("商品")
        private List<GoodsOrderItems> goodsOrderItemsList;
}
