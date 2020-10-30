package com.fzy.admin.fp.member.credits.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author lb
 * @date 2019/5/14 11:04
 * @Description 积分规则实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ruler")
public class CreditsRuler extends BaseEntity {

    @NotBlank(message = "必须传入商户id")
    @ApiModelProperty("商户Id")
    private String merchantId;

    @NotBlank(message = "消费金额不能为空")
    @ApiModelProperty("消费金额")
    private BigDecimal consumptionAmount;

    @NotBlank(message = "获得积分设置不能为空")
    @ApiModelProperty("积分")
    private Integer credits;

    @NotBlank(message = "储值消费是否积分不能为空")
    @ApiModelProperty("储值消费是否积分 0 不积分 1 积分")
    private Integer isTrue;

}
