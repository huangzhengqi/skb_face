package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.invoice.enmus.TaxExemptionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@ApiModel("开票设置数据")
@Data
public class ElectronicBillingSettingDTO {

    @ApiModelProperty("唯一标识")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty(value = "开票金额上限，单位：分", notes = "-1表示不限制")
    private Integer upperLimit;

    @ApiModelProperty(value = "开票金额上限，单位：分", notes = "-1表示不限制")
    private Integer lowerLimit;

    @ApiModelProperty("是否默认设置")
    private Boolean defaultSetting;

    @ApiModelProperty("纳税人识别号")
    private String taxpayerIdentificationNum;

    @ApiModelProperty("开票关联的税务局分类编码")
    private String goodsCode;
    @ApiModelProperty("增值税税率，百分比")
    private Integer vatRate;
    @ApiModelProperty("免税类型")
    private TaxExemptionType taxExemptionType;
    @ApiModelProperty("优惠政策")
    private String preferentialPolicy;
    @ApiModelProperty(value = "数组自定义商品编码", notes = "用户自行编码 前 19 位为商品编码； 后 20 位为用户自行编码 ")
    private String familyGoodsCode;
    @ApiModelProperty(notes = "收款人")
    private String payee;
    @ApiModelProperty(notes = "复核人")
    private String reviewer;
    @ApiModelProperty(notes = "开票人")
    private String issuer;
    @ApiModelProperty(notes = "开票类目名称")
    private String goodsName;
    @ApiModelProperty("开票类目的增值税税率")
    @Column(name = "var_rate")
    private String goodsVatRate;
}
