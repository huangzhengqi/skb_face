package com.fzy.admin.fp.order.app.dto;

import com.fzy.admin.fp.goods.domain.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author yy
 * @Date 2020-2-20 11:05:27
 * @Desp
 **/
@Data
public class PreOrderBindDTO {

    @ApiModelProperty("订单金额")
    private BigDecimal totalPrice;

    @ApiModelProperty("优惠金额")
    private BigDecimal discountPrice;

    @ApiModelProperty("会员ID")
    private String memberId;

    @ApiModelProperty("商品信息")
    private List<Goods> goods;

    @ApiModelProperty("满减规则ID")
    private String memberRuleId;

    @ApiModelProperty("满减规则金额")
    private BigDecimal memberRuleMoney;

    @ApiModelProperty("优惠券核销码")
    private String code;

    @ApiModelProperty("本次积分")
    private Integer score;

    @ApiModelProperty("取号")
    private String takeNumber;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;
}
