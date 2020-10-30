package com.fzy.admin.fp.order.order.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-20 16:11
 * @description
 */
@Data
public class WxPacePayOrderDto {

    @ApiModelProperty(value = "商户id")
    private String merchantId;
    @ApiModelProperty(value = "门店id")
    private String storeId;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @ApiModelProperty("付款方式 1微信 2支付宝 3银行卡 4会员卡")
    private Integer payWay;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value="设备id")
    private String equipmentId;

}
