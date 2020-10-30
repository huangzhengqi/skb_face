package com.fzy.admin.fp.order.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.goods.domain.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-06 10:08
 * @description 支付结果
 */
@Data
public class PayResult {

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "收银员")
    private String userName;

    @ApiModelProperty(value = "支付结果")
    private Integer status;

    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    @ApiModelProperty(value = "支付终端")
    private Integer payClient;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal disCountPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal actPayPrice = BigDecimal.ZERO;

    @ApiModelProperty("失败原因")
    private String name;

    @ApiModelProperty("支付宝支付账号")
    private String buyerLogonId;

    @ApiModelProperty("商品信息")
    private List<Goods> goods;

    @ApiModelProperty(value="门店id")
    private String storeId;

}
