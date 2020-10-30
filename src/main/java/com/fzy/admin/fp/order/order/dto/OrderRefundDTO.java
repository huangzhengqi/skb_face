package com.fzy.admin.fp.order.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-10 16:36
 * @description 订单退款需要参数
 */
@Data
public class OrderRefundDTO {

    @ApiModelProperty(value = "密码")
    private String password;  
    @ApiModelProperty(value = "订单编号")
    private String orderNumber;  
    @ApiModelProperty(value = "收银员id")
    private String refundUserId;  
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPayPrice;  

}
