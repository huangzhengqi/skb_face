package com.fzy.admin.fp.member.sem.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_level")
public class MemberAliLevel extends BaseEntity{

    @ApiModelProperty(value = "自定义背景")
    private String background;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "充值金额")
    @Column(columnDefinition = "decimal(10,2) COMMENT '充值金额'")
    private BigDecimal memberLimitAmount;

    @ApiModelProperty(value = "折扣类型 1-打折 2-不打折")
    private Integer discountType;

    @ApiModelProperty(value = "折扣")
    @Column(columnDefinition = "decimal(10,2) COMMENT '优惠折扣'")
    private BigDecimal discount;

    @ApiModelProperty(value = "特权说明")
    @Column(length = 1024)
    private String rightExplain;

    @ApiModelProperty(value = "赠送类型 0-不赠送 1-送积分 2-送金额")
    private Integer giftType;

    @ApiModelProperty(value = "赠送积分")
    private Integer giftScore;

    @ApiModelProperty(value = "赠送金额")
    @Column(columnDefinition = "decimal(10,2) COMMENT '赠送金额'")
    private BigDecimal giftMoney;

    @ApiModelProperty(value = "商户ID")
    private String merchantId;
}
