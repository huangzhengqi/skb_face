package com.fzy.admin.fp.member.member.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_member_level")
public class MemberLevel extends BaseEntity {

    private String photo; //会员图片

    @Column(columnDefinition = "decimal(10,2) COMMENT '充值金额上限(元)'")
    private BigDecimal memberLimitAmount;

    @Column(columnDefinition = "decimal(10,2) COMMENT '优惠折扣'")
    private BigDecimal discount;

    @NotBlank(message = "请输入权益说明")
    @Column(length = 1024)
    private String rightExplain;

    private Integer giftScore; //赠送积分

    @Column(columnDefinition = "decimal(10,2) COMMENT '赠送金额'")
    private BigDecimal giftMoney;

    private String merchantId; //商户id

}
