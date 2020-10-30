package com.fzy.admin.fp.order.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-05-13 22:52
 * @description 退款后打印显示内容
 */
@Data
public class RefundResult {

    @ApiModelProperty(value = "门店名称")
    private String storeName;  
    @ApiModelProperty(value = "收银员名称")
    private String userName;  
    @ApiModelProperty(value = "退款人")
    private String refundUserName;  
    @ApiModelProperty(value = "订单编号")
    private String orderNumber;  
    @ApiModelProperty(value = "支付结果")
    private Integer status;  
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;  
    @ApiModelProperty(value = "支付终端")
    private Integer payClient;  
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;  
    @ApiModelProperty(value = "退款时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;  
    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalPrice = BigDecimal.ZERO;  
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountPrice = BigDecimal.ZERO;  
    @ApiModelProperty(value = "总退款金额")
    private BigDecimal refundPrice = BigDecimal.ZERO;  
    @ApiModelProperty(value = "当前退款额")
    private BigDecimal curRefundPrice = BigDecimal.ZERO;  

}
