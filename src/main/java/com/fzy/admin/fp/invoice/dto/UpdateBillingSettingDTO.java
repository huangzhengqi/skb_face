package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.invoice.enmus.TaxExemptionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("编辑开票设置")
@Data
public class UpdateBillingSettingDTO {
    @ApiModelProperty("id")
    @NotNull
    private String id;
    @ApiModelProperty("商户ID")
    @NotNull
    private String merchantId;
    @ApiModelProperty(value = "开票金额下限，单位：分", notes = "-1表示不限制")
    private Integer upperLimit;
    @ApiModelProperty(value = "开票金额下限，单位：分", notes = "-1表示不限制")
    private Integer lowerLimit;
    @ApiModelProperty("开票名称")
    private String name;
    @ApiModelProperty("增值税税率，百分比")
    private Integer vatRate;
    @ApiModelProperty("免税类型")
    private TaxExemptionType taxExemptionType;
    @ApiModelProperty("优惠政策")
    private String preferentialPolicy;
    @ApiModelProperty(notes = "收款人")
    private String payee;
    @ApiModelProperty(notes = "复核人")
    private String reviewer;
    @ApiModelProperty(notes = "开票人")
    private String issuer;
}
