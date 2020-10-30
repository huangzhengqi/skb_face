package com.fzy.admin.fp.member.sem.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_ruler")
public class MemberCreditsRuler extends BaseEntity {

    @ApiModelProperty(value = "商户ID")
    private String merchantId;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal consumptionAmount;

    @ApiModelProperty(value = "获得积分")
    private Integer credits;//积分

    @ApiModelProperty(value = "储值是否积分 0-不积分 1-积分")
    private Integer isTrue;
}
