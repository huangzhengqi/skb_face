package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description 微信商户支付参数配置表
 */
@Data
@Entity
@Table(name = "lysj_dist_pay_wx_config")
public class DistWxConfig extends CompanyBaseEntity {

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "商户id")
    private String wxMchId;

    @ApiModelProperty(value = "API密钥")
    private String wxAppKey;

    @ApiModelProperty(value = "证书路径")
    private String wxCertPath;

    @ApiModelProperty(value = "支付密钥")
    private String wxAppSecret;

    @ApiModelProperty("APIV3密钥")
    private String wxApiv3key;




}
