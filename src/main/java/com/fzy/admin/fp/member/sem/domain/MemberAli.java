package com.fzy.admin.fp.member.sem.domain;


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


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_member", indexes = {@Index(columnList = "merchantId,createTime", name = "索引1")})
public class MemberAli extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum Sex implements CodeEnum {
        SECRET(0, "保密"),
        MEM(1, "男"),
        FEMALE(2, "女");
        private Integer code;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum Channel implements CodeEnum {
        MERCHANT_APP(1, "商户APP"),
        WECHAT_APPLET(2, "小程序"),
        MERCHANT_IMPORT(3, "商户导入"),
        EQUIPMENT_WECHAT(4,"微信app"),
        EQUIPMENT_ALI(5,"支付宝app");
        private Integer code;
        private String description;
    }

    @Excel(name = "名称",orderNum = "2",isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "请提供昵称", isTrim = true)
    @ApiModelProperty(value = "昵称")
    private String nickname;//昵称

    @Excel(name = "电话",orderNum = "3",isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "请提供电话号码", isTrim = true)
    @ApiModelProperty(value = "电话")
    private String phone;//

    private String head;//头像

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "生日")
    private Date birthday;//

    @Range(message = "请输入性别", min = "0", max = "3")
    @ApiModelProperty(value = "男_1 女_2")
    private Integer sex;//性别

    @ApiModelProperty(value = "会员编号")
    @Excel(name = "卡号",orderNum = "1",height = 20,width = 30,isImportField = "true_st")
    private String memberNum;//

    @Excel(name = "余额(元)",orderNum = "4",height = 20, width = 30,type = 10,isImportField = "true_st")
    @ApiModelProperty(value = "储值余额")
    private BigDecimal balance;//

    @Excel(name = "可用积分",orderNum = "5",height = 20, width = 30,type = 10, isImportField = "true_st")
    @ApiModelProperty(value = "积分")
    private Integer scores;//


    //@Excel(name = "添加渠道", height = 20, width = 30, replace = {"商户APP_1", "小程序_2"})
    @ApiModelProperty(value = "添加渠道 商户APP_1 小程序_2")
    private Integer channel;//

    @NotBlank(message = "系统错误(merchantId is blank)，请重试", isTrim = true)
    @ApiModelProperty(value = "商户Id")
    private String merchantId;//

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @ApiModelProperty(value = "上次消费 时间")
    @Excel(name = "上次消费",orderNum = "6",height = 20, width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private Date lastPayDate;//


    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty(value = "创建时间")
    private Date createTime;//创建时间 数据插入时自动赋值

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date start_createTime;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date end_createTime;

    @Transient
    @ApiModelProperty(value = "可用优惠券数量")
    private Integer couponCount;//
    @Transient
    @ApiModelProperty(value = "已使用优惠券数量")
    private Integer usedCouponCount;//已使用
    @Transient
    private Integer invalidCouponCount;//作废

    @Column(columnDefinition = "varchar(250) default 1 COMMENT '会员等级'")
    private String memberLevelId;

    private BigDecimal freezeBalance; //不可用金额

    @ApiModelProperty("支付宝用户id")
    private String buyerId;

}
