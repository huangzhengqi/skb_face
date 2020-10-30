package com.fzy.admin.fp.order.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-05-01 21:50
 * @description 订单详情
 */
@Data
public class OrderDetail {

    @ApiModelProperty(value = "订单号")
    private String orderNumber;
    @ApiModelProperty(value = "门店名称")
    private String storeName;
    @ApiModelProperty(value = "收银员")
    private String username;
    @ApiModelProperty(value = "支付状态")
    private Integer status;
    @ApiModelProperty(value = "支付方式")
    private Integer payWay;
    @ApiModelProperty(value = "支付类型")
    private Integer payType;
    @ApiModelProperty(value = "支付渠道")
    private Integer payChannel;
    @ApiModelProperty(value = "支付终端")
    private Integer payClient;
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "订单备注")
    private String remarks;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal disCountPrice = BigDecimal.ZERO;
    @ApiModelProperty(value = "实付金额")
    private BigDecimal actPayPrice = BigDecimal.ZERO;
    @ApiModelProperty(value = "可退金额")
    private BigDecimal remainPrice;
    @ApiModelProperty(value = "基本描述")
    private String description;
    @ApiModelProperty(value = "描述详细")
    private String detail;
    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundPayPrice;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "本次积分")
    private Integer scores;
}
