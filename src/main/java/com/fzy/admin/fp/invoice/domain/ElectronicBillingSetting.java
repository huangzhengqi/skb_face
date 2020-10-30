package com.fzy.admin.fp.invoice.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.invoice.enmus.TaxExemptionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@ApiModel("电子开票设置")
@Entity
@Data
@Table(name = "lysj_electronic_billing_setting")
public class ElectronicBillingSetting extends BaseEntity {
    private static final long serialVersionUID = 7541738165606371978L;
    @ApiModelProperty("商户id")
    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @ApiModelProperty(value = "开票金额上限，单位：分", notes = "-1表示不限制")
    @Column(name = "upper_limit", nullable = false)
    private Integer upperLimit;

    @ApiModelProperty(value = "开票金额下限，单位：分", notes = "-1表示不限制")
    @Column(name = "lower_limit", nullable = false)
    private Integer lowerLimit;

    @ApiModelProperty("是否默认设置")
    @Column(name = "default_setting", nullable = false)
    private Boolean defaultSetting;

    @ApiModelProperty("纳税人识别号")
    @Column(name = "taxpayer_identification_num", nullable = false, length = 64)
    private String taxpayerIdentificationNum;

    @ApiModelProperty("开票关联的税务局分类编码")
    @Column(name = "goods_code", nullable = false)
    private String goodsCode;

    @ApiModelProperty("增值税税率，百分比")
    @Column(name = "vat_rate", nullable = false)
    private Integer vatRate;

    @ApiModelProperty("免税类型")
    @Column(name = "tax_exemption_type", length = 4)
    @Enumerated(EnumType.STRING)
    private TaxExemptionType taxExemptionType;

    @ApiModelProperty("优惠政策")
    @Column(name = "preferential_policy", length = 128)
    private String preferentialPolicy;

    @ApiModelProperty(value = "数组自定义商品编码", notes = "用户自行编码 前 19 位为商品编码；后 20 位为用户自行编码 ")
    @Column(name = "family_goods_code", length = 128, nullable = false)
    private String familyGoodsCode;

    @ApiModelProperty(notes = "收款人")
    @Column(name = "payee", length = 32)
    private String payee;

    @ApiModelProperty(notes = "复核人")
    @Column(name = "reviewer", length = 32)
    private String reviewer;

    @ApiModelProperty(notes = "开票人")
    @Column(name = "issuer", length = 32, nullable = false)
    private String issuer;

}
