package com.fzy.admin.fp.order.order.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.invoice.enmus.BillingStatus;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name="lysj_order_order" , uniqueConstraints = {@UniqueConstraint(columnNames = {"orderNumber"})}, indexes = {@Index(columnList = "merchantId,status,payWay", name = "索引1"), @Index(columnList = "storeId", name = "索引3"), @Index(columnList = "createTime", name = "索引2"), @Index(columnList = "equipmentId", name = "索引4"), @Index(columnList = "merchantId", name = "索引5"), @Index(columnList = "payTime", name = "索引6")})
public class Order extends CompanyBaseEntity {


    @Getter
    public enum InterFaceWay implements EnumInterface {
        SELF(1, "自用"),
        OTHER(2, "第三方"),
        FUYOU(6, "富有"),
        SUIXINGFU(7, "随行付"),
        YS(9, "威富通"),
        LS(10, "乐刷"),
        CH(11, "传化"),
        TQSXF(12, "天阀随行付");
        private Integer code;

        private String status;

        InterFaceWay(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @Getter
    public enum PayWay implements EnumInterface {
        WXPAY(1, "微信"),
        ALIPAY(2, "支付宝"),
        BANKCARD(3, "银行卡"),
        MEMBERCARD(4, "会员卡"),
        CASH(5, "现金"),
        MEMBER_WX(6, "会员+微信"),
        MEMBER_ALI(7, "会员+支付宝"),
        IMPORT(98, "导入"),
        OTHER(99, "未知");
        private Integer code;

        private String status;

        PayWay(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @Getter
    public enum PayClient implements EnumInterface {
        PC(1, "PC端"),
        APP(2, "App端"),
        OTHER(3, "其他"),
        POS(4, "POS机");
        private Integer code;

        private String status;

        PayClient(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @Getter
    public enum Status implements EnumInterface {
        PLACEORDER(1, "未支付"),
        SUCCESSPAY(2, "支付成功"),
        FAILPAY(3, "支付失败"),
        CANCELPAY(4, "已撤销"),
        REFUNDTOTAL(5, "全额退款成功"),
        REFUNDPART(6, "部分退款成功"),
        REFUNDFAIL(7, "退款失败"),
        REFUNDING(8, "退款中"),
        CANCELFAIL(9, "撤销失败");
        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @Getter
    public enum RechargeFlag implements EnumInterface {
        NOTRECHARGE(1, "非充值订单"),
        ISRECHARGE(2, "充值订单");
        private Integer code;

        private String status;

        RechargeFlag(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @Getter
    public enum PayType implements EnumInterface{
        SCANCODE(1, "扫码"),
        FACECODE(3, "刷脸");
        private Integer code;

        private String status;

        PayType(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @ApiModelProperty(value="本系统订单编号")
    @Excel(name="本系统订单编号", width=30.0D, isImportField="true")
    private String orderNumber;

    @ApiModelProperty(value="对接平台的订单号，个别平台需要此参数查询和退款")
    private String transactionId;

    @ApiModelProperty(value="对接平台的退款订单号")
    private String refundTransactionId;

    @ApiModelProperty(value="开放接口给第三方，他们传的订单号")
    private String outTradeNo;

    @ApiModelProperty(value="商户id")
    private String merchantId;

    @ApiModelProperty(value="门店id")
    private String storeId;

    @ApiModelProperty(value="门店名")
    private String storeName;

    @ApiModelProperty(value="收银员id")
    private String userId;

    @ApiModelProperty(value="收银员名称")
    private String userName;

    @ApiModelProperty(value="退款员id")
    private String refundUserId;

    @ApiModelProperty(value="订单金额")
    @Excel(name="订单金额", width=30.0D, isImportField="true")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal totalPrice;

    @ApiModelProperty(value="优惠金额")
    @Column(columnDefinition="decimal(10,2)")
    @Excel(name="优惠金额", width=30.0D, isImportField="true")
    private BigDecimal disCountPrice;

    @ApiModelProperty(value="实付金额")
    @Column(columnDefinition="decimal(10,2)")
    @Excel(name="实付金额", width=30.0D, isImportField="true")
    private BigDecimal actPayPrice;

    @ApiModelProperty(value="退款金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal refundPayPrice;

    @ApiModelProperty(value="支付方式 1微信 2支付宝 3银行卡 4会员卡 99未知")
    @Excel(name="支付方式", width=30.0D, replace={"微信_1", "支付宝_2", "银行卡_3", "会员卡_4", "未知_99"}, isImportField="true")
    private Integer payWay;

    @ApiModelProperty(value="支付通道 1自用 2第三方 6富有 7随行付 12天阙随行付")
    @Excel(name="支付通道", width=30.0D, replace={"自用_1", "第三方_2", "富有_3", "随行付_4"}, isImportField="true")
    private Integer payChannel;

    @ApiModelProperty(value="支付通道利率")
    @Column(columnDefinition="decimal(10,4)")
    private BigDecimal interestRate;

    @ApiModelProperty(value="支付端 1PC端 2App端 3其他")
    private Integer payClient;

    @ApiModelProperty(value="扫码支付授权码")
    private String authCode;

    @ApiModelProperty(value="支付时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="支付时间", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty(value="退款时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    @ApiModelProperty(value="基本描述")
    private String description;

    @ApiModelProperty(value="描述详细")
    private String detail;

    @ApiModelProperty(value="订单备注")
    private String remarks;

    @ApiModelProperty(value="订单状态")
    private Integer status;

    @ApiModelProperty(value="退款订单号，会员宝支付退款和查询退款要")
    private String refundOrderNo;

    @ApiModelProperty(value="开始时间")
    @Transient
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_payTime;

    @ApiModelProperty(value="结束时间")
    @Transient
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_payTime;

    @ApiModelProperty(value="小程序支付回调地址不能携带参数，生成订单后在订单里面存储会员id和储值规则参数json，以便回调后生成储值记录等操作")
    @Column(length=2000)
    private String appletStore;

    @ApiModelProperty(value="是否是充值订单，充值订单不给于退款")
    private Integer rechargeFlag;

    @ApiModelProperty(value="会员id")
    private String memberId;

    @ApiModelProperty(value="卡券核销码，使用优惠券必传")
    private String code;

    @ApiModelProperty(value="订单类型 0系统订单 1扫码点餐订单（不允许退款） 区分是否允许退款")
    @Column(columnDefinition="int(2) default 0")
    private Integer orderType;

    @ApiModelProperty("扫码订餐订单号")
    private String smdcOrderNum;

    @ApiModelProperty("设备id")
    private String equipmentId;

    @ApiModelProperty("开票状态")
    @Enumerated(EnumType.STRING)
    @Column(name="billing_status", length=16)
    private BillingStatus billingStatus;

    @ApiModelProperty("本次积分")
    private Integer score;

    @Transient
    @ApiModelProperty("支付宝支付账号")
    private String buyerLogonId;

    @Transient
    @ApiModelProperty("商品信息")
    private List<Goods> goods;

    @ApiModelProperty("取号")
    private String takeNumber;

    @ApiModelProperty("使用方式 0默认  1外带  2堂食  3堂食餐号")
    private Integer useType;

    @ApiModelProperty("是否是绑定的订单 1是 ")
    private Integer bind;

    @Transient
    @ApiModelProperty("支付场景")
    private String scene;

    @ApiModelProperty("商品预下单ID")
    private String goodsOrderId;

    @ApiModelProperty("挂单桌号")
    private String deskNumberId;

    @ApiModelProperty(value="微信openId")
    private String openId;

    @ApiModelProperty(value="支付类型（1：扫码，3：刷脸）")
    private Integer payType;

    @ApiModelProperty("业务员id")
    private String managerId;

    @ApiModelProperty("业务员名称")
    @Transient
    private String managerName;

    @Transient
    private String payChannelName;

    @ApiModelProperty("花呗分期 分期数")
    @Transient
    private String hbFqNum;

    @ApiModelProperty("订单标题(显示在支付凭证中)")
    @Transient
    private String subject;

    @ApiModelProperty("附加数据，在查询API和支付通知中原样返回，可作为自定义数使用")
    @Transient
    private String attach;
}