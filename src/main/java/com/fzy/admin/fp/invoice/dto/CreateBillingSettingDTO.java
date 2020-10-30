package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.invoice.enmus.TaxExemptionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("创建开票设置")
@Data
public class CreateBillingSettingDTO {
    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("纳税人识别号")
    @NotNull
    private String taxpayerIdentificationNum;

    @ApiModelProperty(value = "开票金额上限，单位：分", notes = "为空表示不限制", required = true)
    private Integer upperLimit;

    @ApiModelProperty(value = "开票金额下限，单位：分", notes = "为空表示不限制", required = true)
    private Integer lowerLimit;

    @ApiModelProperty(value = "开票关联的税务局分类编码", required = true)
    @NotNull
    private String goodsCode;

    @ApiModelProperty(value = "开票名称", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "是否默认设置", required = true)
    @NotNull
    private Boolean defaultSetting = Boolean.valueOf(true);

    @ApiModelProperty(value = "增值税税率，百分比", required = true)
    @NotNull
    private Integer vatRate;

    @ApiModelProperty("免税类型")
    private TaxExemptionType taxExemptionType;

    @ApiModelProperty("优惠政策")
    private String preferentialPolicy;

    @ApiModelProperty(notes = "收款人")
    private String payee;

    @ApiModelProperty(notes = "复核人")
    private String reviewer;

    @ApiModelProperty(notes = "开票人", required = true)
    @NotNull
    private String issuer;


    protected boolean canEqual(Object other) {
        return other instanceof CreateBillingSettingDTO;
    }


    public String toString() {
        return "CreateBillingSettingDTO(merchantId=" + getMerchantId() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ", upperLimit=" + getUpperLimit() + ", lowerLimit=" + getLowerLimit() + ", defaultSetting=" + getDefaultSetting() + ", goodsCode=" + getGoodsCode() + ", name=" + getName() + ", vatRate=" + getVatRate() + ", taxExemptionType=" + getTaxExemptionType() + ", preferentialPolicy=" + getPreferentialPolicy() + ", payee=" + getPayee() + ", reviewer=" + getReviewer() + ", issuer=" + getIssuer() + ")";
    }
}

