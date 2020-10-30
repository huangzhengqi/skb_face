package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxFacePayDTO {


    @ApiModelProperty(value = "商品描述")
    private String describe;
    @ApiModelProperty(value = "订单编号）")
    private String orderNum;
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalFee;
    @ApiModelProperty(value = "调用微信支付 API 的机器 IP")
    private String ip;
    @ApiModelProperty(value = "openId")
    private String openId;
    @ApiModelProperty(value = "faceCode")
    private String faceCode;
    @ApiModelProperty(value = "设备id")
    private String deviceNo;
}
