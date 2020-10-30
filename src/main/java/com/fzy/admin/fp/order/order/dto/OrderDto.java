package com.fzy.admin.fp.order.order.dto;


import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-20 16:11
 * @description
 */
@Data
public class OrderDto {

    @ApiModelProperty(value="商户id")
    private String merchantId;

    @ApiModelProperty(value="门店id")
    private String storeId;

    @ApiModelProperty(value="二维码，28开头是支付宝，10-15开头是微信，会员宝随机数")
    private String authCode;

    @ApiModelProperty(value="微信官方支付 需要openid")
    private String openId;

    @ApiModelProperty(value="门店名称")
    private String storeName;

    @ApiModelProperty(value="收银员id")
    private String userId;

    @ApiModelProperty(value="收银员名称")
    private String userName;

    @ApiModelProperty(value="退款员id")
    private String refundUserId;

    @ApiModelProperty(value="订单金额")
    private BigDecimal totalPrice=BigDecimal.ZERO;

    @ApiModelProperty(value="优惠金额")
    private BigDecimal disCountPrice=BigDecimal.ZERO;

    @ApiModelProperty(value="支付方式")
    private Integer payWay;

    @ApiModelProperty(value="支付端")
    private Integer payClient;

    @ApiModelProperty(value="支付时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty(value="订单状态")
    private Integer status;

    @ApiModelProperty(value="第三方开放接口订单号")
    private String outTradeNo;

    @ApiModelProperty(value="支付通道 小程序支付只能用微信官方通道")
    private Integer payChannel=PayChannelConstant.Channel.OFFICIAL.getCode();

    @ApiModelProperty(value="支付通道利率")
    private BigDecimal interestRate=BigDecimal.ZERO;

    @ApiModelProperty(value="小程序支付回调地址不能携带参数，生成订单后在订单里面存储会员id和储值规则参数json，以便回调后生成储值记录等操作")
    private String appletStore;

    @ApiModelProperty(value="是否是充值订单，充值订单不给于退款")
    private Integer rechargeFlag;

    @ApiModelProperty(value="H5会员支付需要传会员id")
    private String memberId;

    @ApiModelProperty(value="卡券核销码，使用优惠券必传")
    private String code;

    @ApiModelProperty(value="备注")
    protected String remarks;

    @ApiModelProperty(value="uuid")
    private String uuid;

    @ApiModelProperty(value="本系统订单编号")
    private String orderNumber;

    @ApiModelProperty(value="设备id")
    private String equipmentId;

    @ApiModelProperty("账户余额")
    private BigDecimal balance;

    @ApiModelProperty("商品信息")
    private List<Goods> goods;

    @ApiModelProperty("满减规则id")
    private String memberRuleId;

    @ApiModelProperty("满减金额")
    private BigDecimal memberRuleMoney;

    @ApiModelProperty("取号")
    private String takeNumber;

    @ApiModelProperty("使用方式  0默认  1外带 2堂食 3堂食餐号")
    private Integer useType;

    @ApiModelProperty("商品预下单ID")
    private String goodsOrderId;

    @ApiModelProperty("挂单餐号")
    private String deskNumberId;

    @ApiModelProperty("分期付款 分期数")
    private String hbFqNum;

    @ApiModelProperty("订单标题（显示在支付凭证中）")
    private String subject;

    @ApiModelProperty("订单类型 0系统订单 1扫码点餐订单（不允许退款） 区分是否允许退款")
    @Column(columnDefinition="int(2) default 0")
    private Integer orderType;

    @ApiModelProperty("扫码点餐订单号")
    private String smdcOrderNum;

    @ApiModelProperty("支付方式")
    private Integer payType;
}
