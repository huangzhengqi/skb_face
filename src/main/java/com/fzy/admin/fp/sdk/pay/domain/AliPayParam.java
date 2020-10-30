package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wtl on 2019-04-23 21:49
 * @description 支付宝支付参数
 */
@Data
public class AliPayParam {

    @ApiModelProperty("商户id，用来支付模块查询支付配置参数")
    private String merchantId;

    @ApiModelProperty("订单号")
    private String out_trade_no;

    @ApiModelProperty("授权码、条形码")
    private String auth_code;

    @ApiModelProperty("金额")
    private String total_amount;

    @ApiModelProperty("退款金额")
    private String refund_amount;

    @ApiModelProperty("标识一次退款请求，部分退款必传数")
    private String out_request_no;

    @ApiModelProperty("标题")
    private String subject;

    @ApiModelProperty("支付场景")
    private String scene;

    @ApiModelProperty("销售产品码，wap支付必须")
    private String product_code;

    @ApiModelProperty("花呗分期的值")
    private AliHuaBeiPay extend_params;

}
