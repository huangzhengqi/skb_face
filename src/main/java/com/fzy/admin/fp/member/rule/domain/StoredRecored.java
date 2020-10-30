package com.fzy.admin.fp.member.rule.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.enumeration.CodeEnum;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ：drj.
 * @Date  ：Created in 16:09 2019/5/14
 * @Description: 储值明细表
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_stored_recored")
public class StoredRecored extends BaseEntity {


    @Getter
    @AllArgsConstructor
    public enum TradeType implements CodeEnum {
        /**
         * 状态类型
         */
        RECHARGE(1, "充值"),
        CONSUME(2, "消费"),
        REFUND(3, "退款"),
        IMPORT(4, "导入"),
        MEMBERUPDATE(5,"会员升级赠送");

        private Integer code;
        private String description;

    }

    @Getter
    @AllArgsConstructor
    public enum Source implements CodeEnum {
        /**
         * 来源类型
         */
        PC(1, "PC"),
        ANDROID(2, "ANDROID"),
        IOS(3, "IOS"),
        H5(4, "H5");

        private Integer code;
        private String description;

    }

    @Getter
    @AllArgsConstructor
    public enum PayWay implements CodeEnum {
        /**
         * 支付类型
         */
        WECHAT(1, "微信"),
        ALIPAY(2, "阿里"),
        CARDS(3, "会员储值卡"),
        H5(4, "H5"),
        IMPORT(5, "导入"),
        ;

        private Integer code;
        private String description;

    }

    @Getter
    @AllArgsConstructor
    public enum Status implements CodeEnum {
        /**
         * 状态
         */
        CARD(1, "储值卡"),
        COUPON(2, "卡券"),
        ALLIN(3, "都有");

        private Integer code;
        private String description;

    }

    @Getter
    @AllArgsConstructor
    public enum PayStatus implements CodeEnum {
        /**
         * 支付状态
         */
        PLACEORDER(1, "未支付"),
        SUCCESSPAY(2, "支付成功"),
        FAILPAY(3, "支付失败"),
        CANCELPAY(4, "已撤销"),
        REFUNDTOTAL(5, "全额退款成功"),
        REFUNDPART(6, "部分退款成功"),
        REFUNDFAIL(7, "退款失败");
        private Integer code;
        private String description;

    }

    @ApiModelProperty("订单表订单编号")
    private String orderNumber;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("会员id")
    private String memberId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("交易单号   8819开头+20微随机字符")
    private String storedNum;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("会员编号")
    private String memberNum;

    @ApiModelProperty("交易类型")
    private Integer tradeType;

    @ApiModelProperty("来源")
    private Integer source;

    @ApiModelProperty("支付方式")
    private Integer payWay;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("操作人")
    private String operationUser;

    @ApiModelProperty("赠送金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal giftMoney;

    @ApiModelProperty("交易金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal tradingMoney;

    @ApiModelProperty("交易后余额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal postTradingMoney;

    @ApiModelProperty("优惠金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal discountMoney;

    @ApiModelProperty("本次积分")
    private Integer scores;

    @ApiModelProperty("可退积分")
    private Integer remainScore;

    @ApiModelProperty("储值规则id")
    private String storeRuleId;

    @ApiModelProperty("支付状态")
    private Integer payStatus;

    @ApiModelProperty("使用卡券消费时记录卡券Id")
    private String couponId;

    @ApiModelProperty("支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date payTime;

    @ApiModelProperty("开始时间")
    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_createTime;

    @ApiModelProperty("结束时间")
    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_createTime;
}
