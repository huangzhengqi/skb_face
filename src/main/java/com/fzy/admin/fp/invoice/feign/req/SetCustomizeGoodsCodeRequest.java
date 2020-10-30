package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("设置商品自定义编码")
public class SetCustomizeGoodsCodeRequest {
    @ApiModelProperty("机身编码")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodySn;
    @ApiModelProperty("纳税人识别号")
    @JacksonXmlProperty(localName = "nsrsbh")
    private String taxpayerIdentificationNum;
    @ApiModelProperty(value = "商品编码", notes = "非空时创建新的商品项，非空为更新")
    @JacksonXmlProperty(localName = "bm")
    private String goodsCode;
    @ApiModelProperty("商品编码的上级节点")
    @JacksonXmlProperty(localName = "pid")
    private String parentId;
    @ApiModelProperty("商品编码名称")
    @JacksonXmlProperty(localName = "mc")
    private String goodsCodeName;
    @ApiModelProperty("商品编码简称")
    @JacksonXmlProperty(localName = "spbmjc")
    private String goodsCodeAbbreviation;
    @ApiModelProperty("说明")
    @JacksonXmlProperty(localName = "sm")
    private String description;
    @ApiModelProperty("增值税特殊管理")
    @JacksonXmlProperty(localName = "zzstsgl")
    private String vatSpecialManagement;
    @ApiModelProperty("增值税特殊内容代码")
    @JacksonXmlProperty(localName = "zzstsgldm")
    private String vatSpecialContentCode;
    @ApiModelProperty("消费税管理")
    @JacksonXmlProperty(localName = "xfsgl")
    private String saleTaxManagement;
    @ApiModelProperty("消费税政策依据")
    @JacksonXmlProperty(localName = "xfszcyj")
    private String saleTaxPolicyBasis;
    @ApiModelProperty("消费税特殊内容代码")
    @JacksonXmlProperty(localName = "xfstsgldm")
    private String saleTaxSpecialContentCode;
    @ApiModelProperty("关键字")
    @JacksonXmlProperty(localName = "gjz")
    private String keyword;
    @ApiModelProperty("可用状态")
    @JacksonXmlProperty(localName = "kyzt")
    private String availableState;

    public void setBodySn(String bodySn) {
        this.bodySn = bodySn;
    }

    @ApiModelProperty("版本编号")
    @JacksonXmlProperty(localName = "bb")
    private String version;
    @ApiModelProperty("过渡截止时间")
    @JacksonXmlProperty(localName = "gdqjzsj")
    private String transitionDeadline;
    @ApiModelProperty("商品编码或商品编码表的启用时间")
    @JacksonXmlProperty(localName = "qysj")
    private String enableTime;
    @ApiModelProperty("商品编码的入库时间")
    @JacksonXmlProperty(localName = "gxsj")
    private String storageTime;
    @ApiModelProperty("用户选择的优惠类型")
    @JacksonXmlProperty(localName = "yhlx")
    private String offerType;
    @ApiModelProperty(value = "免税类型", notes = "空代表无 1 出口免税和其他免税优惠政策,2 不征增值税,3 普通零税")
    @JacksonXmlProperty(localName = "mslx")
    private String taxExemptionType;
    @ApiModelProperty("可用税率")
    @JacksonXmlProperty(localName = "kysl")
    private String availableTaxRate;
    @ApiModelProperty("税率")
    @JacksonXmlProperty(localName = "sl")
    private BigDecimal taxRate;
    @ApiModelProperty("规则型号")
    @JacksonXmlProperty(localName = "ggxh")
    private String specificationModel;
    @ApiModelProperty("计量单位")
    @JacksonXmlProperty(localName = "jldw")
    private String unitOfMeasurement;
    @ApiModelProperty("单价")
    @JacksonXmlProperty(localName = "dj")
    private BigDecimal unitPrice;
    @ApiModelProperty("含税标志")
    @JacksonXmlProperty(localName = "hsbz")
    private String taxIncludedMark;
    @ApiModelProperty("增值税税率")
    @JacksonXmlProperty(localName = "zzssl")
    private String vatRate;
    @ApiModelProperty("海关进出口商品品目")
    @JacksonXmlProperty(localName = "hgpm")
    private String customsIeGoodsItems;
    @ApiModelProperty("国民统计代码")
    @JacksonXmlProperty(localName = "gmtjdm")
    private String nationalStatisticalCode;

    public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
        this.taxpayerIdentificationNum = taxpayerIdentificationNum;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setGoodsCodeName(String goodsCodeName) {
        this.goodsCodeName = goodsCodeName;
    }

    public void setGoodsCodeAbbreviation(String goodsCodeAbbreviation) {
        this.goodsCodeAbbreviation = goodsCodeAbbreviation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVatSpecialManagement(String vatSpecialManagement) {
        this.vatSpecialManagement = vatSpecialManagement;
    }

    public void setVatSpecialContentCode(String vatSpecialContentCode) {
        this.vatSpecialContentCode = vatSpecialContentCode;
    }

    public void setSaleTaxManagement(String saleTaxManagement) {
        this.saleTaxManagement = saleTaxManagement;
    }

    public void setSaleTaxPolicyBasis(String saleTaxPolicyBasis) {
        this.saleTaxPolicyBasis = saleTaxPolicyBasis;
    }

    public void setSaleTaxSpecialContentCode(String saleTaxSpecialContentCode) {
        this.saleTaxSpecialContentCode = saleTaxSpecialContentCode;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setAvailableState(String availableState) {
        this.availableState = availableState;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTransitionDeadline(String transitionDeadline) {
        this.transitionDeadline = transitionDeadline;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public void setStorageTime(String storageTime) {
        this.storageTime = storageTime;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public void setTaxExemptionType(String taxExemptionType) {
        this.taxExemptionType = taxExemptionType;
    }

    public void setAvailableTaxRate(String availableTaxRate) {
        this.availableTaxRate = availableTaxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTaxIncludedMark(String taxIncludedMark) {
        this.taxIncludedMark = taxIncludedMark;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate;
    }

    public void setCustomsIeGoodsItems(String customsIeGoodsItems) {
        this.customsIeGoodsItems = customsIeGoodsItems;
    }

    public void setNationalStatisticalCode(String nationalStatisticalCode) {
        this.nationalStatisticalCode = nationalStatisticalCode;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SetCustomizeGoodsCodeRequest)) return false;
        SetCustomizeGoodsCodeRequest other = (SetCustomizeGoodsCodeRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
        if ((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn)) return false;
        Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
        if ((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum))
            return false;
        Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
        if ((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode))
            return false;
        Object this$parentId = getParentId(), other$parentId = other.getParentId();
        if ((this$parentId == null) ? (other$parentId != null) : !this$parentId.equals(other$parentId)) return false;
        Object this$goodsCodeName = getGoodsCodeName(), other$goodsCodeName = other.getGoodsCodeName();
        if ((this$goodsCodeName == null) ? (other$goodsCodeName != null) : !this$goodsCodeName.equals(other$goodsCodeName))
            return false;
        Object this$goodsCodeAbbreviation = getGoodsCodeAbbreviation(), other$goodsCodeAbbreviation = other.getGoodsCodeAbbreviation();
        if ((this$goodsCodeAbbreviation == null) ? (other$goodsCodeAbbreviation != null) : !this$goodsCodeAbbreviation.equals(other$goodsCodeAbbreviation))
            return false;
        Object this$description = getDescription(), other$description = other.getDescription();
        if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description))
            return false;
        Object this$vatSpecialManagement = getVatSpecialManagement(), other$vatSpecialManagement = other.getVatSpecialManagement();
        if ((this$vatSpecialManagement == null) ? (other$vatSpecialManagement != null) : !this$vatSpecialManagement.equals(other$vatSpecialManagement))
            return false;
        Object this$vatSpecialContentCode = getVatSpecialContentCode(), other$vatSpecialContentCode = other.getVatSpecialContentCode();
        if ((this$vatSpecialContentCode == null) ? (other$vatSpecialContentCode != null) : !this$vatSpecialContentCode.equals(other$vatSpecialContentCode))
            return false;
        Object this$saleTaxManagement = getSaleTaxManagement(), other$saleTaxManagement = other.getSaleTaxManagement();
        if ((this$saleTaxManagement == null) ? (other$saleTaxManagement != null) : !this$saleTaxManagement.equals(other$saleTaxManagement))
            return false;
        Object this$saleTaxPolicyBasis = getSaleTaxPolicyBasis(), other$saleTaxPolicyBasis = other.getSaleTaxPolicyBasis();
        if ((this$saleTaxPolicyBasis == null) ? (other$saleTaxPolicyBasis != null) : !this$saleTaxPolicyBasis.equals(other$saleTaxPolicyBasis))
            return false;
        Object this$saleTaxSpecialContentCode = getSaleTaxSpecialContentCode(), other$saleTaxSpecialContentCode = other.getSaleTaxSpecialContentCode();
        if ((this$saleTaxSpecialContentCode == null) ? (other$saleTaxSpecialContentCode != null) : !this$saleTaxSpecialContentCode.equals(other$saleTaxSpecialContentCode))
            return false;
        Object this$keyword = getKeyword(), other$keyword = other.getKeyword();
        if ((this$keyword == null) ? (other$keyword != null) : !this$keyword.equals(other$keyword)) return false;
        Object this$availableState = getAvailableState(), other$availableState = other.getAvailableState();
        if ((this$availableState == null) ? (other$availableState != null) : !this$availableState.equals(other$availableState))
            return false;
        Object this$version = getVersion(), other$version = other.getVersion();
        if ((this$version == null) ? (other$version != null) : !this$version.equals(other$version)) return false;
        Object this$transitionDeadline = getTransitionDeadline(), other$transitionDeadline = other.getTransitionDeadline();
        if ((this$transitionDeadline == null) ? (other$transitionDeadline != null) : !this$transitionDeadline.equals(other$transitionDeadline))
            return false;
        Object this$enableTime = getEnableTime(), other$enableTime = other.getEnableTime();
        if ((this$enableTime == null) ? (other$enableTime != null) : !this$enableTime.equals(other$enableTime))
            return false;
        Object this$storageTime = getStorageTime(), other$storageTime = other.getStorageTime();
        if ((this$storageTime == null) ? (other$storageTime != null) : !this$storageTime.equals(other$storageTime))
            return false;
        Object this$offerType = getOfferType(), other$offerType = other.getOfferType();
        if ((this$offerType == null) ? (other$offerType != null) : !this$offerType.equals(other$offerType))
            return false;
        Object this$taxExemptionType = getTaxExemptionType(), other$taxExemptionType = other.getTaxExemptionType();
        if ((this$taxExemptionType == null) ? (other$taxExemptionType != null) : !this$taxExemptionType.equals(other$taxExemptionType))
            return false;
        Object this$availableTaxRate = getAvailableTaxRate(), other$availableTaxRate = other.getAvailableTaxRate();
        if ((this$availableTaxRate == null) ? (other$availableTaxRate != null) : !this$availableTaxRate.equals(other$availableTaxRate))
            return false;
        Object this$taxRate = getTaxRate(), other$taxRate = other.getTaxRate();
        if ((this$taxRate == null) ? (other$taxRate != null) : !this$taxRate.equals(other$taxRate)) return false;
        Object this$specificationModel = getSpecificationModel(), other$specificationModel = other.getSpecificationModel();
        if ((this$specificationModel == null) ? (other$specificationModel != null) : !this$specificationModel.equals(other$specificationModel))
            return false;
        Object this$unitOfMeasurement = getUnitOfMeasurement(), other$unitOfMeasurement = other.getUnitOfMeasurement();
        if ((this$unitOfMeasurement == null) ? (other$unitOfMeasurement != null) : !this$unitOfMeasurement.equals(other$unitOfMeasurement))
            return false;
        Object this$unitPrice = getUnitPrice(), other$unitPrice = other.getUnitPrice();
        if ((this$unitPrice == null) ? (other$unitPrice != null) : !this$unitPrice.equals(other$unitPrice))
            return false;
        Object this$taxIncludedMark = getTaxIncludedMark(), other$taxIncludedMark = other.getTaxIncludedMark();
        if ((this$taxIncludedMark == null) ? (other$taxIncludedMark != null) : !this$taxIncludedMark.equals(other$taxIncludedMark))
            return false;
        Object this$vatRate = getVatRate(), other$vatRate = other.getVatRate();
        if ((this$vatRate == null) ? (other$vatRate != null) : !this$vatRate.equals(other$vatRate)) return false;
        Object this$customsIeGoodsItems = getCustomsIeGoodsItems(), other$customsIeGoodsItems = other.getCustomsIeGoodsItems();
        if ((this$customsIeGoodsItems == null) ? (other$customsIeGoodsItems != null) : !this$customsIeGoodsItems.equals(other$customsIeGoodsItems))
            return false;
        Object this$nationalStatisticalCode = getNationalStatisticalCode(), other$nationalStatisticalCode = other.getNationalStatisticalCode();
        return !((this$nationalStatisticalCode == null) ? (other$nationalStatisticalCode != null) : !this$nationalStatisticalCode.equals(other$nationalStatisticalCode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SetCustomizeGoodsCodeRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $bodySn = getBodySn();
        result = result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
        Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
        result = result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
        Object $goodsCode = getGoodsCode();
        result = result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
        Object $parentId = getParentId();
        result = result * 59 + (($parentId == null) ? 43 : $parentId.hashCode());
        Object $goodsCodeName = getGoodsCodeName();
        result = result * 59 + (($goodsCodeName == null) ? 43 : $goodsCodeName.hashCode());
        Object $goodsCodeAbbreviation = getGoodsCodeAbbreviation();
        result = result * 59 + (($goodsCodeAbbreviation == null) ? 43 : $goodsCodeAbbreviation.hashCode());
        Object $description = getDescription();
        result = result * 59 + (($description == null) ? 43 : $description.hashCode());
        Object $vatSpecialManagement = getVatSpecialManagement();
        result = result * 59 + (($vatSpecialManagement == null) ? 43 : $vatSpecialManagement.hashCode());
        Object $vatSpecialContentCode = getVatSpecialContentCode();
        result = result * 59 + (($vatSpecialContentCode == null) ? 43 : $vatSpecialContentCode.hashCode());
        Object $saleTaxManagement = getSaleTaxManagement();
        result = result * 59 + (($saleTaxManagement == null) ? 43 : $saleTaxManagement.hashCode());
        Object $saleTaxPolicyBasis = getSaleTaxPolicyBasis();
        result = result * 59 + (($saleTaxPolicyBasis == null) ? 43 : $saleTaxPolicyBasis.hashCode());
        Object $saleTaxSpecialContentCode = getSaleTaxSpecialContentCode();
        result = result * 59 + (($saleTaxSpecialContentCode == null) ? 43 : $saleTaxSpecialContentCode.hashCode());
        Object $keyword = getKeyword();
        result = result * 59 + (($keyword == null) ? 43 : $keyword.hashCode());
        Object $availableState = getAvailableState();
        result = result * 59 + (($availableState == null) ? 43 : $availableState.hashCode());
        Object $version = getVersion();
        result = result * 59 + (($version == null) ? 43 : $version.hashCode());
        Object $transitionDeadline = getTransitionDeadline();
        result = result * 59 + (($transitionDeadline == null) ? 43 : $transitionDeadline.hashCode());
        Object $enableTime = getEnableTime();
        result = result * 59 + (($enableTime == null) ? 43 : $enableTime.hashCode());
        Object $storageTime = getStorageTime();
        result = result * 59 + (($storageTime == null) ? 43 : $storageTime.hashCode());
        Object $offerType = getOfferType();
        result = result * 59 + (($offerType == null) ? 43 : $offerType.hashCode());
        Object $taxExemptionType = getTaxExemptionType();
        result = result * 59 + (($taxExemptionType == null) ? 43 : $taxExemptionType.hashCode());
        Object $availableTaxRate = getAvailableTaxRate();
        result = result * 59 + (($availableTaxRate == null) ? 43 : $availableTaxRate.hashCode());
        Object $taxRate = getTaxRate();
        result = result * 59 + (($taxRate == null) ? 43 : $taxRate.hashCode());
        Object $specificationModel = getSpecificationModel();
        result = result * 59 + (($specificationModel == null) ? 43 : $specificationModel.hashCode());
        Object $unitOfMeasurement = getUnitOfMeasurement();
        result = result * 59 + (($unitOfMeasurement == null) ? 43 : $unitOfMeasurement.hashCode());
        Object $unitPrice = getUnitPrice();
        result = result * 59 + (($unitPrice == null) ? 43 : $unitPrice.hashCode());
        Object $taxIncludedMark = getTaxIncludedMark();
        result = result * 59 + (($taxIncludedMark == null) ? 43 : $taxIncludedMark.hashCode());
        Object $vatRate = getVatRate();
        result = result * 59 + (($vatRate == null) ? 43 : $vatRate.hashCode());
        Object $customsIeGoodsItems = getCustomsIeGoodsItems();
        result = result * 59 + (($customsIeGoodsItems == null) ? 43 : $customsIeGoodsItems.hashCode());
        Object $nationalStatisticalCode = getNationalStatisticalCode();
        return result * 59 + (($nationalStatisticalCode == null) ? 43 : $nationalStatisticalCode.hashCode());
    }

    public String toString() {
        return "SetCustomizeGoodsCodeRequest(bodySn=" + getBodySn() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ", goodsCode=" + getGoodsCode() + ", parentId=" + getParentId() + ", goodsCodeName=" + getGoodsCodeName() + ", goodsCodeAbbreviation=" + getGoodsCodeAbbreviation() + ", description=" + getDescription() + ", vatSpecialManagement=" + getVatSpecialManagement() + ", vatSpecialContentCode=" + getVatSpecialContentCode() + ", saleTaxManagement=" + getSaleTaxManagement() + ", saleTaxPolicyBasis=" + getSaleTaxPolicyBasis() + ", saleTaxSpecialContentCode=" + getSaleTaxSpecialContentCode() + ", keyword=" + getKeyword() + ", availableState=" + getAvailableState() + ", version=" + getVersion() + ", transitionDeadline=" + getTransitionDeadline() + ", enableTime=" + getEnableTime() + ", storageTime=" + getStorageTime() + ", offerType=" + getOfferType() + ", taxExemptionType=" + getTaxExemptionType() + ", availableTaxRate=" + getAvailableTaxRate() + ", taxRate=" + getTaxRate() + ", specificationModel=" + getSpecificationModel() + ", unitOfMeasurement=" + getUnitOfMeasurement() + ", unitPrice=" + getUnitPrice() + ", taxIncludedMark=" + getTaxIncludedMark() + ", vatRate=" + getVatRate() + ", customsIeGoodsItems=" + getCustomsIeGoodsItems() + ", nationalStatisticalCode=" + getNationalStatisticalCode() + ")";
    }


    public String getBodySn() {
        return this.bodySn;
    }


    public String getTaxpayerIdentificationNum() {
        return this.taxpayerIdentificationNum;
    }


    public String getGoodsCode() {
        return this.goodsCode;
    }


    public String getParentId() {
        return this.parentId;
    }


    public String getGoodsCodeName() {
        return this.goodsCodeName;
    }


    public String getGoodsCodeAbbreviation() {
        return this.goodsCodeAbbreviation;
    }


    public String getDescription() {
        return this.description;
    }


    public String getVatSpecialManagement() {
        return this.vatSpecialManagement;
    }


    public String getVatSpecialContentCode() {
        return this.vatSpecialContentCode;
    }


    public String getSaleTaxManagement() {
        return this.saleTaxManagement;
    }


    public String getSaleTaxPolicyBasis() {
        return this.saleTaxPolicyBasis;
    }


    public String getSaleTaxSpecialContentCode() {
        return this.saleTaxSpecialContentCode;
    }


    public String getKeyword() {
        return this.keyword;
    }


    public String getAvailableState() {
        return this.availableState;
    }


    public String getVersion() {
        return this.version;
    }


    public String getTransitionDeadline() {
        return this.transitionDeadline;
    }


    public String getEnableTime() {
        return this.enableTime;
    }


    public String getStorageTime() {
        return this.storageTime;
    }


    public String getOfferType() {
        return this.offerType;
    }


    public String getTaxExemptionType() {
        return this.taxExemptionType;
    }


    public String getAvailableTaxRate() {
        return this.availableTaxRate;
    }


    public BigDecimal getTaxRate() {
        return this.taxRate;
    }


    public String getSpecificationModel() {
        return this.specificationModel;
    }


    public String getUnitOfMeasurement() {
        return this.unitOfMeasurement;
    }


    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }


    public String getTaxIncludedMark() {
        return this.taxIncludedMark;
    }


    public String getVatRate() {
        return this.vatRate;
    }


    public String getCustomsIeGoodsItems() {
        return this.customsIeGoodsItems;
    }


    public String getNationalStatisticalCode() {
        return this.nationalStatisticalCode;
    }
}
