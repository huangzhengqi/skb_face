package com.fzy.admin.fp.member.member.domain;


import com.fzy.admin.fp.common.enumeration.CodeEnum;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Created by zk on 2019-05-28 23:46
 * @description  会员卡
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_member_card")
public class MemberCard extends BaseEntity {
    @Getter
    @AllArgsConstructor
    public enum AcceptWay implements CodeEnum {
        /**
         *  类型
         */
        FREE(1, "免费领取"),
        COMPENSATED(2, "付费领取");
        private Integer code;
        private String description;
    }

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("会员卡背景图")
    private String backgroundPictureId;

    @NotBlank(message = "会员卡名称不能为空")
    @Length(min = "0", max = "10", message = "会员卡名称请控制在9个字以内")
    @ApiModelProperty("会员卡名称")
    private String name;

    @NotBlank(message = "商户联系电话不能为空")
    @ApiModelProperty("商户联系电话")
    private String phone;

    @NotBlank(message = "会员卡特权说明不能为空")
    @Length(min = "0", max = "1025", message = "会员卡特权说明字数请控制在1024")
    @Column(length = 1024)
    @ApiModelProperty("会员卡特权说明不能为空")
    private String privilegeExplain;

    @NotBlank(message = "会员卡使用须知不能为空")
    @Length(min = "0", max = "501", message = "会员卡使用须知字数请控制在500")
    @Column(length = 500)
    @ApiModelProperty("会员卡使用须知不能为空")
    private String description;

    @NotNull(message = "请提供领取方式")
    @ApiModelProperty("请提供领取方式")
    private Integer acceptWay;

    @ApiModelProperty("赠送积分数量")
    private Integer presentScores;

    @ApiModelProperty("赠送优惠券")
    private String couponId;

    @ApiModelProperty("会员卡号")
    private String cardNumber;

    @ApiModelProperty("卡券ID")
    private String cardId;

    @Transient
    @ApiModelProperty("商户头像id")
    private String merchantPhotoId;

    @Transient
    @ApiModelProperty("商户名称")
    private String merchantName;

    @ApiModelProperty("会员卡类型 1:微信 2支付宝 ")
    private Integer type;

    @ApiModelProperty("卡券审核状态 1待审核 2审核失败 3通过审核 4卡券被商户删除 5在公众平台投放过的卡券 ")
    private Integer status;

    @ApiModelProperty("审核不通过原因")
    private String refuseReason;
}
