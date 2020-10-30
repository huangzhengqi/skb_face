package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-11 20:58
 * @description 服务商支付参数
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_top_config")
public class TopConfig extends CompanyBaseEntity {

    @ApiModelProperty(value = "公众号")
    @Column(length = 50)
    private String wxAppId;

    @ApiModelProperty(value = "API密钥")
    @Column(length = 240)
    private String wxAppKey;

    @ApiModelProperty(value = "支付密钥")
    @Column(length = 240)
    private String wxAppSecret;

    @ApiModelProperty("APIV3密钥")
    @Column(length = 240)
    private String wxApiv3key;

    @ApiModelProperty("apiclient_key")
    private String apiClientKey;

    @ApiModelProperty(value = "商户id")
    @Column(length = 100)
    private String wxMchId;

    @ApiModelProperty(value = "证书路径")
    private String wxCertPath;

    @ApiModelProperty(value = "cert证书路径pem")
    private String wxApiCert;

    @ApiModelProperty(value = "key证书路径pem")
    private String wxApiKey;

    @ApiModelProperty(value = "微信利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wxRate;


    @ApiModelProperty(value = "第三方应用ID")
    @Column(length = 240)
    private String aliAppId;

    @ApiModelProperty(value = "应用私钥")
    @Column(columnDefinition = "TEXT")
    private String aliPrivateKey;

    @ApiModelProperty(value = "支付宝平台公钥")
    @Column(columnDefinition = "TEXT")
    private String aliPublicKey;

    @ApiModelProperty(value = "支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal zfbRate;

    @ApiModelProperty(value = "随行付私钥")
    @Column(columnDefinition = "TEXT")
    private String sxfPrivateKey;


    @ApiModelProperty(value = "随行付机构编码")
    private String sxfOrgId;

    @ApiModelProperty(value = "随行付微信利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal sxfWxRate;

    @ApiModelProperty(value = "随行付支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal sxfAliRate;


    @ApiModelProperty(value = "富友私钥")
    @Column(columnDefinition = "TEXT")
    private String fyInsPrivateKey;

    @ApiModelProperty(value = "富友机构编码")
    private String fyInsCd;

    @ApiModelProperty(value = "富友订单编码")
    private String fyPreOrder;

    @ApiModelProperty(value = "富友微信利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal fyWxRate;

    @ApiModelProperty(value = "富友支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal fyAliRate;

    @ApiModelProperty("服务商pid")
    @Column(columnDefinition = "TEXT")
    private String aliPartnerId;

    @ApiModelProperty("支付宝小程序应用ID")
    private String aliAppletAppId;

    @ApiModelProperty("支付宝小程序应用秘钥")
    @Column(columnDefinition = "TEXT")
    private String aliApplePrivateKey;

    @ApiModelProperty("支付宝移动应用id")
    private String aliMobilePayId;

    @ApiModelProperty(value = "支付宝移动应用私钥")
    @Column(columnDefinition = "TEXT")
    private String aliMobilePrivateKey;

    @ApiModelProperty(value = "支付宝移动应用公钥")
    @Column(columnDefinition = "TEXT")
    private String aliMobilePublicKey;

    @ApiModelProperty("支付宝生活号APPID")
    @Column(length = 32)
    private String aliLifeServiceAppId;

    @ApiModelProperty("支付宝生活号支付宝公钥")
    @Column(columnDefinition = "TEXT")
    private String aliLifeServicePublicKey;

    @ApiModelProperty("支付宝生活号应用私钥")
    @Column(columnDefinition = "TEXT")
    private String aliLifeServicePrivateKey;

    @ApiModelProperty("随行付subappid编码")
    private String sxfSubAppId;

    @ApiModelProperty("富友公钥")
    @Column(columnDefinition = "TEXT")
    private String fyPublicKey;

    @ApiModelProperty("乐刷服务商商户号")
    @Column(name = "ls_merchant_id")
    private String lsMerchantId;

    @ApiModelProperty("乐刷支付时使用的加密key")
    @Column(name = "ls_pay_key")
    private String lsPayKey;

    @ApiModelProperty("乐刷通知时使用的key")
    @Column(name = "ls_notice_key")
    private String lsNoticeKey;

    @ApiModelProperty("乐刷微信利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal lsWxRate;

    @ApiModelProperty("乐刷支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal lsAliRate;

    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal chAliInterestRate;

    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal chWxInterestRate;

    @ApiModelProperty("天阀随行付私钥")
    @Column(columnDefinition = "TEXT")
    private String tqSxfPrivateKey;

    @ApiModelProperty("天阀随行付机构编码")
    private String tqSxfOrgId;

    @ApiModelProperty("天阀随行付subappid编码")
    private String tqSxfSubAppId;

    @ApiModelProperty("天阀随行付微信利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal tqSxfWxRate;

    @ApiModelProperty("天阀随行付支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal tqSxfAliRate;


    @ApiModelProperty("青蛙proAppId")
    private String frogAppId;

    @ApiModelProperty("青蛙proAppSecret")
    private String frogAppCert;
}
