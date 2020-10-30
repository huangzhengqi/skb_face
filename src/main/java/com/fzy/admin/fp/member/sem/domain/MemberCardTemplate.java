package com.fzy.admin.fp.member.sem.domain;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.Length;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_card_template")
public class MemberCardTemplate extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum AcceptWay {
        FREE(1, "免费领取"),
        COMPENSATED(2, "付费领取");
        private Integer code;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum Term {
        PERMANENT(1, "永久"),
        VALID(2, "期限");
        private Integer code;
        private String description;
    }

    @ApiModelProperty(value = "支付宝请求ID")
    private String requestId;

    @ApiModelProperty(value = "LOGO")
    private String logoId;

    @ApiModelProperty(value = "LOGO图片地址")
    private String logoUrl;

    @ApiModelProperty(value = "背景图片")
    private String backgroundId;

    @ApiModelProperty(value = "背景图片地址")
    private String backgroundUrl;

    @ApiModelProperty(value = "支付宝创建模板ID")
    private String tempateId;

    @ApiModelProperty(value = "背景颜色")
    private String color;

    @ApiModelProperty(value = "字体颜色")
    private String bgColor;

    @ApiModelProperty(value = "商户ID")
    private String merchantId; //商户id

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "使用期限")
    private Integer term;

    @ApiModelProperty(value = "有效期开始时间")
    private Date termStartTime;

    @ApiModelProperty(value = "有效期结束时间")
    private Date termEndTime;

    @NotBlank(message = "会员卡名称不能为空")
    @Length(min = "0", max = "10", message = "会员卡名称请控制在9个字以内")
    private String name;

    @ApiModelProperty(value = "商户联系电话不能为空")
    private String phone;//商户联系电话

    @NotBlank(message = "会员卡特权说明不能为空")
    @Length(min = "0", max = "1025", message = "会员卡特权说明字数请控制在1024")
    @Column(length = 1024)
    private String privilegeExplain;


    @Length(min = "0", max = "501", message = "会员卡使用须知字数请控制在500")
    @Column(length = 500)
    private String tip;

    @NotNull(message = "请提供领取方式默认为1")
    private Integer acceptWay;

    @ApiModelProperty(value = "赠送积分数量")
    private Integer presentScores;

    @ApiModelProperty(value = "赠送优惠卷")
    private String couponId;

    @ApiModelProperty(value = "会员卡号显示格式")
    private String cardNumber;

    @Transient
    private String merchantPhotoId;

    @ApiModelProperty(value = "是否设置开卡表单 ")
    private Integer isSet = CommonConstant.IS_FALSE;

    @ApiModelProperty(value = "会员领卡连接")
    private String applyCardUrl;

    private String backgroudId;
}