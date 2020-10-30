package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2020-1-10 15:11:57
 * @Desp
 **/
@Entity
@Data
@Table(name = "lysj_dist_rate_standards")
public class RateStandards extends CompanyBaseEntity{
    @ApiModelProperty("运营中心-下级合伙人比例")
    private Integer operatorPartner;

    @ApiModelProperty("运营中心-一级团长比例")
    private Integer operatorColonel;

    @ApiModelProperty("团长-下级合伙人比例")
    private Integer colonelPartner;

    @ApiModelProperty("团长-一级团长比例")
    private Integer stairColonel;

    @ApiModelProperty("升级到团长需要的团队人数")
    private Integer teamNum;

    @ApiModelProperty("团队激活设备")
    private Integer teamActivate;

    @ApiModelProperty("合伙人-一级比例")
    private Integer firstLevel;

    @ApiModelProperty("合伙人-二级比例")
    private Integer secondLevel;

    @ApiModelProperty("合伙人-三级比例")
    private Integer thirdLevel;

    @ApiModelProperty("直邀请商户")
    private Integer directlyMerchant;

    @ApiModelProperty("升级合伙人")
    private Integer upgradeOperator;

}
