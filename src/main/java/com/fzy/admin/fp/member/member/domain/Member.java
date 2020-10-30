package com.fzy.admin.fp.member.member.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.enumeration.CodeEnum;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.Range;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-05-14 10:26
 * @description
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_member", indexes = {@Index(columnList = "merchantId,createTime", name = "索引1")})
public class Member extends BaseEntity {
    @Getter
    @AllArgsConstructor
    public enum Sex implements CodeEnum {
        /**
         * 性别
         */
        SECRET(0, "保密"),
        MEM(1, "男"),
        FEMALE(2, "女");
        private Integer code;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum Channel implements CodeEnum {
        /**
         * 添加渠道
         */
        MERCHANT_APP(1, "商户APP"),
        WECHAT_APPLET(2, "小程序"),
        MERCHANT_IMPORT(3, "商户导入"),
        EQUIPMENT_WECHAT(4,"微信app"),
        EQUIPMENT_ALI(5,"支付宝app");
        private Integer code;
        private String description;
    }

    @Excel(name = "昵称",orderNum = "2",isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "请提供昵称", isTrim = true)
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @Excel(name = "电话",orderNum = "3",isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "请提供电话号码", isTrim = true)
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String head;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @Excel(name = "性别",orderNum = "4",replace = {"男_1" ,"女_2"},height = 20,width = 20)
    @Range(message = "请输入性别", min = "0", max = "3")
    @ApiModelProperty(value = "男_1 女_2")
    private Integer sex;

    @ApiModelProperty(value = "会员编号")
    @Excel(name = "卡号",orderNum = "1",height = 20,width = 30,isImportField = "true_st")
    private String memberNum;

    @Excel(name = "余额(元)",orderNum = "5",height = 20, width = 30,type = 10,isImportField = "true_st")
    @ApiModelProperty(value = "储值余额")
    private BigDecimal balance;

    @Excel(name = "积分",orderNum = "6",height = 20, width = 30,type = 10, isImportField = "true_st")
    @ApiModelProperty(value = "积分")
    private Integer scores;

    @ApiModelProperty(value = "小程序openId")
    private String openId;

    @ApiModelProperty(value = "公众号openId")
    private String officialOpenId;

    @ApiModelProperty(value = "添加渠道 商户APP_1 小程序_2")
    private Integer channel;

    @NotBlank(message = "系统错误(merchantId is blank)，请重试", isTrim = true)
    @ApiModelProperty(value = "商户Id")
    private String merchantId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @ApiModelProperty(value = "上次消费 时间")
    @Excel(name = "上次消费",orderNum = "6",height = 20, width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private Date lastPayDate;


    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "开始创建时间")
    private Date startCreateTime;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

    @Transient
    @ApiModelProperty(value = "可用优惠券数量")
    private Integer couponCount;

    @Transient
    @ApiModelProperty(value = "已使用优惠券数量")
    private Integer usedCouponCount;

    @Transient
    @ApiModelProperty(value = "作废")
    private Integer invalidCouponCount;

    @Column(columnDefinition = "varchar(250) default 1 COMMENT '会员等级'")
    @ApiModelProperty(value = "会员等级")
    private String memberLevelId;

    @ApiModelProperty(value = "不可用金额")
    private BigDecimal freezeBalance;

    @ApiModelProperty("支付宝用户id")
    private String buyerId;

    @ApiModelProperty("用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的 unionid 是唯一的。")
    private String unionId;
}
