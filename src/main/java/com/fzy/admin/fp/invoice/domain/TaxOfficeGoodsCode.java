package com.fzy.admin.fp.invoice.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@ApiModel("税务局商品编码表")
@Entity
@Table(name = "lysj_tax_office_goods_code", indexes = {@Index(columnList = "goods_code")})
public class TaxOfficeGoodsCode extends BaseEntity {
    private static final long serialVersionUID = 5713972205976207856L;
    @ApiModelProperty("版本")
    @Column(name = "version")
    private String version;
    @ApiModelProperty("启用时间")
    @Column(name = "enable_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enableTime;
    @ApiModelProperty("过渡截止时间")
    @Column(name = "transition_deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transitionDeadline;
    @ApiModelProperty("商品编码")
    @Column(name = "goods_code", nullable = false)
    private String goodsCode;
    @ApiModelProperty("商品编码简称")
    @Column(name = "goods", nullable = false)
    private String goodsAbbreviation;
    @ApiModelProperty("说明")
    @Column(name = "description", length = 2048)
    private String description;
    @ApiModelProperty("增值税税率")
    @Column(name = "var_rate")
    private String vatRate;
    @ApiModelProperty("关键字")
    @Column(name = "keyword", length = 1024)
    private String keyword;
    @ApiModelProperty("汇总项")
    @Column(name = "summary_item", nullable = false)
    private Boolean summaryItem;
    @ApiModelProperty("可用状态")
    @Column(name = "available_state", nullable = false)
    private Boolean availableState;

    public TaxOfficeGoodsCode setVersion(String version) {
        this.version = version;
        return this;
    }

    @ApiModelProperty("增值税特殊管理")
    @Column(name = "vat_special_management")
    private String vatSpecialManagement;
    @ApiModelProperty("增值税政策依据")
    @Column(name = "vat_policy_basis")
    private String vatPolicyBasis;
    @ApiModelProperty("增值税特殊管理代码")
    @Column(name = "vat_special_management_code")
    private String vatSpecialManagementCode;
    @ApiModelProperty("消费税特殊管理")
    @Column(name = "sale_tax_special_management")
    private String saleTaxSpecialManagement;
    @ApiModelProperty("消费税政策依据")
    @Column(name = "sale_tax_policy_basis")
    private String saleTaxPolicyBasis;
    @ApiModelProperty("消费税政策依据代码")
    @Column(name = "sale_tax_policy_basis_code")
    private String saleTaxPolicyBasisCode;
    @ApiModelProperty("统计编码")
    @Column(name = "statistical_coding", length = 512)
    private String statisticalCoding;
    @ApiModelProperty("海关品目")
    @Column(name = "customs_items")
    @Lob
    private String customsItems;
    @ApiModelProperty("商品编码的上级节点")
    @Column(name = "parent_code")
    private String parentCode;
    @ApiModelProperty("编码层级，从0开始")
    @Column(name = "code_level")
    private Integer codeLevel;
    @ApiModelProperty("编码路径，拼接商品编码的路径")
    @Column(name = "code_path")
    private String codePath;

    public TaxOfficeGoodsCode setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
        return this;
    }

    public TaxOfficeGoodsCode setTransitionDeadline(Date transitionDeadline) {
        this.transitionDeadline = transitionDeadline;
        return this;
    }

    public TaxOfficeGoodsCode setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
        return this;
    }

    public TaxOfficeGoodsCode setGoodsAbbreviation(String goodsAbbreviation) {
        this.goodsAbbreviation = goodsAbbreviation;
        return this;
    }

    public TaxOfficeGoodsCode setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaxOfficeGoodsCode setVatRate(String vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    public TaxOfficeGoodsCode setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public TaxOfficeGoodsCode setSummaryItem(Boolean summaryItem) {
        this.summaryItem = summaryItem;
        return this;
    }

    public TaxOfficeGoodsCode setAvailableState(Boolean availableState) {
        this.availableState = availableState;
        return this;
    }

    public TaxOfficeGoodsCode setVatSpecialManagement(String vatSpecialManagement) {
        this.vatSpecialManagement = vatSpecialManagement;
        return this;
    }

    public TaxOfficeGoodsCode setVatPolicyBasis(String vatPolicyBasis) {
        this.vatPolicyBasis = vatPolicyBasis;
        return this;
    }

    public TaxOfficeGoodsCode setVatSpecialManagementCode(String vatSpecialManagementCode) {
        this.vatSpecialManagementCode = vatSpecialManagementCode;
        return this;
    }

    public TaxOfficeGoodsCode setSaleTaxSpecialManagement(String saleTaxSpecialManagement) {
        this.saleTaxSpecialManagement = saleTaxSpecialManagement;
        return this;
    }

    public TaxOfficeGoodsCode setSaleTaxPolicyBasis(String saleTaxPolicyBasis) {
        this.saleTaxPolicyBasis = saleTaxPolicyBasis;
        return this;
    }

    public TaxOfficeGoodsCode setSaleTaxPolicyBasisCode(String saleTaxPolicyBasisCode) {
        this.saleTaxPolicyBasisCode = saleTaxPolicyBasisCode;
        return this;
    }

    public TaxOfficeGoodsCode setStatisticalCoding(String statisticalCoding) {
        this.statisticalCoding = statisticalCoding;
        return this;
    }

    public TaxOfficeGoodsCode setCustomsItems(String customsItems) {
        this.customsItems = customsItems;
        return this;
    }

    public TaxOfficeGoodsCode setParentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public TaxOfficeGoodsCode setCodeLevel(Integer codeLevel) {
        this.codeLevel = codeLevel;
        return this;
    }

    public TaxOfficeGoodsCode setCodePath(String codePath) {
        this.codePath = codePath;
        return this;
    }

    public String toString() {
        return "TaxOfficeGoodsCode(version=" + getVersion() + ", enableTime=" + getEnableTime() + ", transitionDeadline=" + getTransitionDeadline() + ", goodsCode=" + getGoodsCode() + ", goodsAbbreviation=" + getGoodsAbbreviation() + ", description=" + getDescription() + ", vatRate=" + getVatRate() + ", keyword=" + getKeyword() + ", summaryItem=" + getSummaryItem() + ", availableState=" + getAvailableState() + ", vatSpecialManagement=" + getVatSpecialManagement() + ", vatPolicyBasis=" + getVatPolicyBasis() + ", vatSpecialManagementCode=" + getVatSpecialManagementCode() + ", saleTaxSpecialManagement=" + getSaleTaxSpecialManagement() + ", saleTaxPolicyBasis=" + getSaleTaxPolicyBasis() + ", saleTaxPolicyBasisCode=" + getSaleTaxPolicyBasisCode() + ", statisticalCoding=" + getStatisticalCoding() + ", customsItems=" + getCustomsItems() + ", parentCode=" + getParentCode() + ", codeLevel=" + getCodeLevel() + ", codePath=" + getCodePath() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TaxOfficeGoodsCode)) return false;
        TaxOfficeGoodsCode other = (TaxOfficeGoodsCode) o;
        if (!other.canEqual(this)) return false;
        if (!super.equals(o)) return false;
        Object this$version = getVersion(), other$version = other.getVersion();
        if ((this$version == null) ? (other$version != null) : !this$version.equals(other$version)) return false;
        Object this$enableTime = getEnableTime(), other$enableTime = other.getEnableTime();
        if ((this$enableTime == null) ? (other$enableTime != null) : !this$enableTime.equals(other$enableTime))
            return false;
        Object this$transitionDeadline = getTransitionDeadline(), other$transitionDeadline = other.getTransitionDeadline();
        if ((this$transitionDeadline == null) ? (other$transitionDeadline != null) : !this$transitionDeadline.equals(other$transitionDeadline))
            return false;
        Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
        if ((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode))
            return false;
        Object this$goodsAbbreviation = getGoodsAbbreviation(), other$goodsAbbreviation = other.getGoodsAbbreviation();
        if ((this$goodsAbbreviation == null) ? (other$goodsAbbreviation != null) : !this$goodsAbbreviation.equals(other$goodsAbbreviation))
            return false;
        Object this$description = getDescription(), other$description = other.getDescription();
        if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description))
            return false;
        Object this$vatRate = getVatRate(), other$vatRate = other.getVatRate();
        if ((this$vatRate == null) ? (other$vatRate != null) : !this$vatRate.equals(other$vatRate)) return false;
        Object this$keyword = getKeyword(), other$keyword = other.getKeyword();
        if ((this$keyword == null) ? (other$keyword != null) : !this$keyword.equals(other$keyword)) return false;
        Object this$summaryItem = getSummaryItem(), other$summaryItem = other.getSummaryItem();
        if ((this$summaryItem == null) ? (other$summaryItem != null) : !this$summaryItem.equals(other$summaryItem))
            return false;
        Object this$availableState = getAvailableState(), other$availableState = other.getAvailableState();
        if ((this$availableState == null) ? (other$availableState != null) : !this$availableState.equals(other$availableState))
            return false;
        Object this$vatSpecialManagement = getVatSpecialManagement(), other$vatSpecialManagement = other.getVatSpecialManagement();
        if ((this$vatSpecialManagement == null) ? (other$vatSpecialManagement != null) : !this$vatSpecialManagement.equals(other$vatSpecialManagement))
            return false;
        Object this$vatPolicyBasis = getVatPolicyBasis(), other$vatPolicyBasis = other.getVatPolicyBasis();
        if ((this$vatPolicyBasis == null) ? (other$vatPolicyBasis != null) : !this$vatPolicyBasis.equals(other$vatPolicyBasis))
            return false;
        Object this$vatSpecialManagementCode = getVatSpecialManagementCode(), other$vatSpecialManagementCode = other.getVatSpecialManagementCode();
        if ((this$vatSpecialManagementCode == null) ? (other$vatSpecialManagementCode != null) : !this$vatSpecialManagementCode.equals(other$vatSpecialManagementCode))
            return false;
        Object this$saleTaxSpecialManagement = getSaleTaxSpecialManagement(), other$saleTaxSpecialManagement = other.getSaleTaxSpecialManagement();
        if ((this$saleTaxSpecialManagement == null) ? (other$saleTaxSpecialManagement != null) : !this$saleTaxSpecialManagement.equals(other$saleTaxSpecialManagement))
            return false;
        Object this$saleTaxPolicyBasis = getSaleTaxPolicyBasis(), other$saleTaxPolicyBasis = other.getSaleTaxPolicyBasis();
        if ((this$saleTaxPolicyBasis == null) ? (other$saleTaxPolicyBasis != null) : !this$saleTaxPolicyBasis.equals(other$saleTaxPolicyBasis))
            return false;
        Object this$saleTaxPolicyBasisCode = getSaleTaxPolicyBasisCode(), other$saleTaxPolicyBasisCode = other.getSaleTaxPolicyBasisCode();
        if ((this$saleTaxPolicyBasisCode == null) ? (other$saleTaxPolicyBasisCode != null) : !this$saleTaxPolicyBasisCode.equals(other$saleTaxPolicyBasisCode))
            return false;
        Object this$statisticalCoding = getStatisticalCoding(), other$statisticalCoding = other.getStatisticalCoding();
        if ((this$statisticalCoding == null) ? (other$statisticalCoding != null) : !this$statisticalCoding.equals(other$statisticalCoding))
            return false;
        Object this$customsItems = getCustomsItems(), other$customsItems = other.getCustomsItems();
        if ((this$customsItems == null) ? (other$customsItems != null) : !this$customsItems.equals(other$customsItems))
            return false;
        Object this$parentCode = getParentCode(), other$parentCode = other.getParentCode();
        if ((this$parentCode == null) ? (other$parentCode != null) : !this$parentCode.equals(other$parentCode))
            return false;
        Object this$codeLevel = getCodeLevel(), other$codeLevel = other.getCodeLevel();
        if ((this$codeLevel == null) ? (other$codeLevel != null) : !this$codeLevel.equals(other$codeLevel))
            return false;
        Object this$codePath = getCodePath(), other$codePath = other.getCodePath();
        return !((this$codePath == null) ? (other$codePath != null) : !this$codePath.equals(other$codePath));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TaxOfficeGoodsCode;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $version = getVersion();
        result = result * 59 + (($version == null) ? 43 : $version.hashCode());
        Object $enableTime = getEnableTime();
        result = result * 59 + (($enableTime == null) ? 43 : $enableTime.hashCode());
        Object $transitionDeadline = getTransitionDeadline();
        result = result * 59 + (($transitionDeadline == null) ? 43 : $transitionDeadline.hashCode());
        Object $goodsCode = getGoodsCode();
        result = result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
        Object $goodsAbbreviation = getGoodsAbbreviation();
        result = result * 59 + (($goodsAbbreviation == null) ? 43 : $goodsAbbreviation.hashCode());
        Object $description = getDescription();
        result = result * 59 + (($description == null) ? 43 : $description.hashCode());
        Object $vatRate = getVatRate();
        result = result * 59 + (($vatRate == null) ? 43 : $vatRate.hashCode());
        Object $keyword = getKeyword();
        result = result * 59 + (($keyword == null) ? 43 : $keyword.hashCode());
        Object $summaryItem = getSummaryItem();
        result = result * 59 + (($summaryItem == null) ? 43 : $summaryItem.hashCode());
        Object $availableState = getAvailableState();
        result = result * 59 + (($availableState == null) ? 43 : $availableState.hashCode());
        Object $vatSpecialManagement = getVatSpecialManagement();
        result = result * 59 + (($vatSpecialManagement == null) ? 43 : $vatSpecialManagement.hashCode());
        Object $vatPolicyBasis = getVatPolicyBasis();
        result = result * 59 + (($vatPolicyBasis == null) ? 43 : $vatPolicyBasis.hashCode());
        Object $vatSpecialManagementCode = getVatSpecialManagementCode();
        result = result * 59 + (($vatSpecialManagementCode == null) ? 43 : $vatSpecialManagementCode.hashCode());
        Object $saleTaxSpecialManagement = getSaleTaxSpecialManagement();
        result = result * 59 + (($saleTaxSpecialManagement == null) ? 43 : $saleTaxSpecialManagement.hashCode());
        Object $saleTaxPolicyBasis = getSaleTaxPolicyBasis();
        result = result * 59 + (($saleTaxPolicyBasis == null) ? 43 : $saleTaxPolicyBasis.hashCode());
        Object $saleTaxPolicyBasisCode = getSaleTaxPolicyBasisCode();
        result = result * 59 + (($saleTaxPolicyBasisCode == null) ? 43 : $saleTaxPolicyBasisCode.hashCode());
        Object $statisticalCoding = getStatisticalCoding();
        result = result * 59 + (($statisticalCoding == null) ? 43 : $statisticalCoding.hashCode());
        Object $customsItems = getCustomsItems();
        result = result * 59 + (($customsItems == null) ? 43 : $customsItems.hashCode());
        Object $parentCode = getParentCode();
        result = result * 59 + (($parentCode == null) ? 43 : $parentCode.hashCode());
        Object $codeLevel = getCodeLevel();
        result = result * 59 + (($codeLevel == null) ? 43 : $codeLevel.hashCode());
        Object $codePath = getCodePath();
        return result * 59 + (($codePath == null) ? 43 : $codePath.hashCode());
    }


    public String getVersion() {
        return this.version;
    }


    public Date getEnableTime() {
        return this.enableTime;
    }


    public Date getTransitionDeadline() {
        return this.transitionDeadline;
    }


    public String getGoodsCode() {
        return this.goodsCode;
    }


    public String getGoodsAbbreviation() {
        return this.goodsAbbreviation;
    }


    public String getDescription() {
        return this.description;
    }


    public String getVatRate() {
        return this.vatRate;
    }


    public String getKeyword() {
        return this.keyword;
    }


    public Boolean getSummaryItem() {
        return this.summaryItem;
    }


    public Boolean getAvailableState() {
        return this.availableState;
    }


    public String getVatSpecialManagement() {
        return this.vatSpecialManagement;
    }


    public String getVatPolicyBasis() {
        return this.vatPolicyBasis;
    }


    public String getVatSpecialManagementCode() {
        return this.vatSpecialManagementCode;
    }


    public String getSaleTaxSpecialManagement() {
        return this.saleTaxSpecialManagement;
    }


    public String getSaleTaxPolicyBasis() {
        return this.saleTaxPolicyBasis;
    }


    public String getSaleTaxPolicyBasisCode() {
        return this.saleTaxPolicyBasisCode;
    }


    public String getStatisticalCoding() {
        return this.statisticalCoding;
    }


    public String getCustomsItems() {
        return this.customsItems;
    }


    public String getParentCode() {
        return this.parentCode;
    }


    public Integer getCodeLevel() {
        return this.codeLevel;
    }


    public String getCodePath() {
        return this.codePath;
    }
}

