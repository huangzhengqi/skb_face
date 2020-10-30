package com.fzy.admin.fp.order.order.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExternalOrderDto {

    @ApiModelProperty(value = "商户ID")
    private String appid;

    @ApiModelProperty(value = "external")
    private String return_type;

    @ApiModelProperty(value = "支付标识,app、PC、mobile")
    private String pay_type;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal amount_true;

    @ApiModelProperty(value = "回调地址")
    private String callback_url;

    @ApiModelProperty(value = "成功跳转")
    private String success_url;

    @ApiModelProperty(value = "失败跳转")
    private String error_url;

    @ApiModelProperty(value = "系统用户ID")
    private String out_uid;

    @ApiModelProperty(value = "商户交易号")
    private String out_trade_no;

    @ApiModelProperty(value = "接口版本")
    private String version;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "二维码")
    private String qrcode;

    @ApiModelProperty(value = "系统订单号")
    private String order_no;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "支付状态")
    private Date pay_time;

    @ApiModelProperty(value = "回调状态")
    private String callback_status;

}