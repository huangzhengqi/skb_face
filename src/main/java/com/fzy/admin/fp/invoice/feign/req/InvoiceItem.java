package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class InvoiceItem {
    @ApiModelProperty(value = "序号", notes = "从1开始")
    @JacksonXmlProperty(isAttribute = true, localName = "xh")
    private Integer orderIndex;
    @ApiModelProperty(notes = "发票行性质: 0 正常行 1 折扣行 2 被折扣行", required = true)
    @JacksonXmlProperty(localName = "fphxz")
    private Integer invoiceLineNature;
    @ApiModelProperty(notes = "商品名称, 如果为折扣行， 商品名称项 与被折扣行的商品名称相同，不能多行折扣", required = true)
    @JacksonXmlProperty(localName = "spmc")
    private String goodsName;
    @ApiModelProperty(notes = "金额, 小数点后 2 位（不含税）", required = true)
    @JacksonXmlProperty(localName = "je")
    private BigDecimal amount;
    @ApiModelProperty(notes = "税额， 小数点最多 3 位", required = true)
    @JacksonXmlProperty(localName = "sl")
    private BigDecimal taxRate;
    @ApiModelProperty(notes = "税额, 小数点后 2 位 正负校验:金额*税率=税额误差正负 0.06", required = true)
    @JacksonXmlProperty(localName = "se")
    private BigDecimal tax;
    @ApiModelProperty(notes = "规格型号")
    @JacksonXmlProperty(localName = "ggxh")
    private String specificationModel;

    public InvoiceItem setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    @ApiModelProperty(notes = "单位")
    @JacksonXmlProperty(localName = "dw")
    private String unit;
    @ApiModelProperty(notes = "商品数量, 校验:商品数量*单价=金额")
    @JacksonXmlProperty(localName = "spsl")
    private Integer goodsNum;
    @ApiModelProperty(notes = "单价, 误差正负 0.01（不含税）")
    @JacksonXmlProperty(localName = "dj")
    private BigDecimal goodsPrice;
    @ApiModelProperty(notes = "商品编码，总局固定编码，不能修改")
    @JacksonXmlProperty(localName = "spbm")
    private String goodsCode;
    @ApiModelProperty(notes = "纳税人自行编码，自定义编码以 2 位为一级，共10级，每级可用编码值为 00-99或AA-ZZ")
    @JacksonXmlProperty(localName = "zxbm")
    private String taxpayerSelfCode;
    @ApiModelProperty(notes = "优惠政策标示，0 未使用 1使用")
    @JacksonXmlProperty(localName = "yhzcbs")
    private String preferentialPolicyLabel;
    @ApiModelProperty(notes = "税率标示：空代表正常税率，1 出口免税和其他免税优惠政策（免税） 2 不征增值税（不征税） 3 普通零税率（0%）")
    @JacksonXmlProperty(localName = "lslbs")
    private String taxRateIndication;
    @ApiModelProperty("增值税特殊管理")
    @JacksonXmlProperty(localName = "zzstsgl")
    private String vatSpecialManagement;

    public InvoiceItem setInvoiceLineNature(Integer invoiceLineNature) {
        this.invoiceLineNature = invoiceLineNature;
        return this;
    }

    public InvoiceItem setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public InvoiceItem setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public InvoiceItem setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public InvoiceItem setTax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public InvoiceItem setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
        return this;
    }

    public InvoiceItem setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public InvoiceItem setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
        return this;
    }

    public InvoiceItem setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public InvoiceItem setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
        return this;
    }

    public InvoiceItem setTaxpayerSelfCode(String taxpayerSelfCode) {
        this.taxpayerSelfCode = taxpayerSelfCode;
        return this;
    }

    public InvoiceItem setPreferentialPolicyLabel(String preferentialPolicyLabel) {
        this.preferentialPolicyLabel = preferentialPolicyLabel;
        return this;
    }

    public InvoiceItem setTaxRateIndication(String taxRateIndication) {
        this.taxRateIndication = taxRateIndication;
        return this;
    }

    public InvoiceItem setVatSpecialManagement(String vatSpecialManagement) {
        this.vatSpecialManagement = vatSpecialManagement;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InvoiceItem)) return false;
        InvoiceItem other = (InvoiceItem) o;
        if (!other.canEqual(this)) return false;
        Object this$orderIndex = getOrderIndex(), other$orderIndex = other.getOrderIndex();
        if ((this$orderIndex == null) ? (other$orderIndex != null) : !this$orderIndex.equals(other$orderIndex))
            return false;
        Object this$invoiceLineNature = getInvoiceLineNature(), other$invoiceLineNature = other.getInvoiceLineNature();
        if ((this$invoiceLineNature == null) ? (other$invoiceLineNature != null) : !this$invoiceLineNature.equals(other$invoiceLineNature))
            return false;
        Object this$goodsName = getGoodsName(), other$goodsName = other.getGoodsName();
        if ((this$goodsName == null) ? (other$goodsName != null) : !this$goodsName.equals(other$goodsName))
            return false;
        Object this$amount = getAmount(), other$amount = other.getAmount();
        if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;
        Object this$taxRate = getTaxRate(), other$taxRate = other.getTaxRate();
        if ((this$taxRate == null) ? (other$taxRate != null) : !this$taxRate.equals(other$taxRate)) return false;
        Object this$tax = getTax(), other$tax = other.getTax();
        if ((this$tax == null) ? (other$tax != null) : !this$tax.equals(other$tax)) return false;
        Object this$specificationModel = getSpecificationModel(), other$specificationModel = other.getSpecificationModel();
        if ((this$specificationModel == null) ? (other$specificationModel != null) : !this$specificationModel.equals(other$specificationModel))
            return false;
        Object this$unit = getUnit(), other$unit = other.getUnit();
        if ((this$unit == null) ? (other$unit != null) : !this$unit.equals(other$unit)) return false;
        Object this$goodsNum = getGoodsNum(), other$goodsNum = other.getGoodsNum();
        if ((this$goodsNum == null) ? (other$goodsNum != null) : !this$goodsNum.equals(other$goodsNum)) return false;
        Object this$goodsPrice = getGoodsPrice(), other$goodsPrice = other.getGoodsPrice();
        if ((this$goodsPrice == null) ? (other$goodsPrice != null) : !this$goodsPrice.equals(other$goodsPrice))
            return false;
        Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
        if ((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode))
            return false;
        Object this$taxpayerSelfCode = getTaxpayerSelfCode(), other$taxpayerSelfCode = other.getTaxpayerSelfCode();
        if ((this$taxpayerSelfCode == null) ? (other$taxpayerSelfCode != null) : !this$taxpayerSelfCode.equals(other$taxpayerSelfCode))
            return false;
        Object this$preferentialPolicyLabel = getPreferentialPolicyLabel(), other$preferentialPolicyLabel = other.getPreferentialPolicyLabel();
        if ((this$preferentialPolicyLabel == null) ? (other$preferentialPolicyLabel != null) : !this$preferentialPolicyLabel.equals(other$preferentialPolicyLabel))
            return false;
        Object this$taxRateIndication = getTaxRateIndication(), other$taxRateIndication = other.getTaxRateIndication();
        if ((this$taxRateIndication == null) ? (other$taxRateIndication != null) : !this$taxRateIndication.equals(other$taxRateIndication))
            return false;
        Object this$vatSpecialManagement = getVatSpecialManagement(), other$vatSpecialManagement = other.getVatSpecialManagement();
        return !((this$vatSpecialManagement == null) ? (other$vatSpecialManagement != null) : !this$vatSpecialManagement.equals(other$vatSpecialManagement));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InvoiceItem;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $orderIndex = getOrderIndex();
        result = result * 59 + (($orderIndex == null) ? 43 : $orderIndex.hashCode());
        Object $invoiceLineNature = getInvoiceLineNature();
        result = result * 59 + (($invoiceLineNature == null) ? 43 : $invoiceLineNature.hashCode());
        Object $goodsName = getGoodsName();
        result = result * 59 + (($goodsName == null) ? 43 : $goodsName.hashCode());
        Object $amount = getAmount();
        result = result * 59 + (($amount == null) ? 43 : $amount.hashCode());
        Object $taxRate = getTaxRate();
        result = result * 59 + (($taxRate == null) ? 43 : $taxRate.hashCode());
        Object $tax = getTax();
        result = result * 59 + (($tax == null) ? 43 : $tax.hashCode());
        Object $specificationModel = getSpecificationModel();
        result = result * 59 + (($specificationModel == null) ? 43 : $specificationModel.hashCode());
        Object $unit = getUnit();
        result = result * 59 + (($unit == null) ? 43 : $unit.hashCode());
        Object $goodsNum = getGoodsNum();
        result = result * 59 + (($goodsNum == null) ? 43 : $goodsNum.hashCode());
        Object $goodsPrice = getGoodsPrice();
        result = result * 59 + (($goodsPrice == null) ? 43 : $goodsPrice.hashCode());
        Object $goodsCode = getGoodsCode();
        result = result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
        Object $taxpayerSelfCode = getTaxpayerSelfCode();
        result = result * 59 + (($taxpayerSelfCode == null) ? 43 : $taxpayerSelfCode.hashCode());
        Object $preferentialPolicyLabel = getPreferentialPolicyLabel();
        result = result * 59 + (($preferentialPolicyLabel == null) ? 43 : $preferentialPolicyLabel.hashCode());
        Object $taxRateIndication = getTaxRateIndication();
        result = result * 59 + (($taxRateIndication == null) ? 43 : $taxRateIndication.hashCode());
        Object $vatSpecialManagement = getVatSpecialManagement();
        return result * 59 + (($vatSpecialManagement == null) ? 43 : $vatSpecialManagement.hashCode());
    }

    public String toString() {
        return "InvoiceItem(orderIndex=" + getOrderIndex() + ", invoiceLineNature=" + getInvoiceLineNature() + ", goodsName=" + getGoodsName() + ", amount=" + getAmount() + ", taxRate=" + getTaxRate() + ", tax=" + getTax() + ", specificationModel=" + getSpecificationModel() + ", unit=" + getUnit() + ", goodsNum=" + getGoodsNum() + ", goodsPrice=" + getGoodsPrice() + ", goodsCode=" + getGoodsCode() + ", taxpayerSelfCode=" + getTaxpayerSelfCode() + ", preferentialPolicyLabel=" + getPreferentialPolicyLabel() + ", taxRateIndication=" + getTaxRateIndication() + ", vatSpecialManagement=" + getVatSpecialManagement() + ")";
    }


    public Integer getOrderIndex() {
        return this.orderIndex;
    }


    public Integer getInvoiceLineNature() {
        return this.invoiceLineNature;
    }


    public String getGoodsName() {
        return this.goodsName;
    }


    public BigDecimal getAmount() {
        return this.amount;
    }


    public BigDecimal getTaxRate() {
        return this.taxRate;
    }


    public BigDecimal getTax() {
        return this.tax;
    }


    public String getSpecificationModel() {
        return this.specificationModel;
    }


    public String getUnit() {
        return this.unit;
    }


    public Integer getGoodsNum() {
        return this.goodsNum;
    }


    public BigDecimal getGoodsPrice() {
        return this.goodsPrice;
    }


    public String getGoodsCode() {
        return this.goodsCode;
    }


    public String getTaxpayerSelfCode() {
        return this.taxpayerSelfCode;
    }


    public String getPreferentialPolicyLabel() {
        return this.preferentialPolicyLabel;
    }


    public String getTaxRateIndication() {
        return this.taxRateIndication;
    }


    public String getVatSpecialManagement() {
        return this.vatSpecialManagement;
    }
}

