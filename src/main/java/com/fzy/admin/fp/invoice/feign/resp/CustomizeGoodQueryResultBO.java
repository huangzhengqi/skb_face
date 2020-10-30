package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("查询自定义商品编码结果")
@Data
public class CustomizeGoodQueryResultBO extends FamilyBaseBO {
    @ApiModelProperty("自定义商品结果")
    @JacksonXmlProperty(localName = "spbm")
    private int result;



    protected boolean canEqual(Object other) {
        return other instanceof CustomizeGoodQueryResultBO;
    }


    public static class GoodsResult {
        @ApiModelProperty("数量")
        @JacksonXmlProperty(localName = "count", isAttribute = true)
        private String count;
        @ApiModelProperty("商品列表")
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "group")
        private List<CustomizeGoodQueryResultBO.GoodsItem> goodsItems;

        public void setCount(String count) {
            this.count = count;
        }

        public void setGoodsItems(List<CustomizeGoodQueryResultBO.GoodsItem> goodsItems) {
            this.goodsItems = goodsItems;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof GoodsResult)) return false;
            GoodsResult other = (GoodsResult) o;
            if (!other.canEqual(this)) return false;
            Object this$count = getCount(), other$count = other.getCount();
            if ((this$count == null) ? (other$count != null) : !this$count.equals(other$count)) return false;
            Object this$goodsItems = getGoodsItems(), other$goodsItems = other.getGoodsItems();
            return !((this$goodsItems == null) ? (other$goodsItems != null) : !this$goodsItems.equals(other$goodsItems));
        }

        protected boolean canEqual(Object other) {
            return other instanceof GoodsResult;
        }



        public String toString() {
            return "CustomizeGoodQueryResultBO.GoodsResult(count=" + getCount() + ", goodsItems=" + getGoodsItems() + ")";
        }


        public String getCount() {
            return this.count;
        }


        public List<CustomizeGoodQueryResultBO.GoodsItem> getGoodsItems() {
            return this.goodsItems;
        }
    }

    public static class GoodsItem {
        @ApiModelProperty("序号，从1开始")
        @JacksonXmlProperty(localName = "xh", isAttribute = true)
        private String orderIndex;
        @ApiModelProperty("机身编号")
        @JacksonXmlProperty(localName = "jsbh")
        private String bodySn;
        @ApiModelProperty("纳税人识别号")
        @JacksonXmlProperty(localName = "nsrsbh")
        private String taxpayerIdentificationNum;
        @ApiModelProperty("商品编码名称")
        @JacksonXmlProperty(localName = "spmc")
        private String goodsCodeName;
        @ApiModelProperty("商品编码简称")
        @JacksonXmlProperty(localName = "spbmjc")
        private String goodsCodeAbbreviation;
        @ApiModelProperty("规则型号")
        @JacksonXmlProperty(localName = "ggxh")
        private String specificationModel;
        @ApiModelProperty("计量单位")
        @JacksonXmlProperty(localName = "jldw")
        private String unitOfMeasurement;
        @ApiModelProperty("单价")
        @JacksonXmlProperty(localName = "dj")
        private BigDecimal unitPrice;
        private int result;

        public void setOrderIndex(String orderIndex) {
            this.orderIndex = orderIndex;
        }

        @ApiModelProperty("可用税率")
        @JacksonXmlProperty(localName = "kysl")
        private String availableTaxRate;
        @ApiModelProperty("税率")
        @JacksonXmlProperty(localName = "sl")
        private BigDecimal taxRate;
        @ApiModelProperty("含税标志")
        @JacksonXmlProperty(localName = "hsbz")
        private String taxIncludedMark;
        @ApiModelProperty(value = "商品编码", notes = "为空时创建新的商品项,非 空为更新")
        @JacksonXmlProperty(localName = "bm")
        private String goodsCode;
        @ApiModelProperty("商品编码上级节点")
        @JacksonXmlProperty(localName = "pid")
        private String parentId;
        @ApiModelProperty("零税率标识")
        @JacksonXmlProperty(localName = "lslbs")
        private String zeroTaxRateIdentification;
        @ApiModelProperty("说明")
        @JacksonXmlProperty(localName = "sm")
        private String description;
        @ApiModelProperty("增值税特殊管理")
        @JacksonXmlProperty(localName = "zzstsgl")
        private String vatSpecialManagement;
        @ApiModelProperty("优惠政策标识")
        @JacksonXmlProperty(localName = "yhzcbs")
        private String preferentialPolicyIdentification;

        public void setBodySn(String bodySn) {
            this.bodySn = bodySn;
        }

        public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
            this.taxpayerIdentificationNum = taxpayerIdentificationNum;
        }

        public void setGoodsCodeName(String goodsCodeName) {
            this.goodsCodeName = goodsCodeName;
        }

        public void setGoodsCodeAbbreviation(String goodsCodeAbbreviation) {
            this.goodsCodeAbbreviation = goodsCodeAbbreviation;
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

        public void setAvailableTaxRate(String availableTaxRate) {
            this.availableTaxRate = availableTaxRate;
        }

        public void setTaxRate(BigDecimal taxRate) {
            this.taxRate = taxRate;
        }

        public void setTaxIncludedMark(String taxIncludedMark) {
            this.taxIncludedMark = taxIncludedMark;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public void setZeroTaxRateIdentification(String zeroTaxRateIdentification) {
            this.zeroTaxRateIdentification = zeroTaxRateIdentification;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setVatSpecialManagement(String vatSpecialManagement) {
            this.vatSpecialManagement = vatSpecialManagement;
        }

        public void setPreferentialPolicyIdentification(String preferentialPolicyIdentification) {
            this.preferentialPolicyIdentification = preferentialPolicyIdentification;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof GoodsItem)) return false;
            GoodsItem other = (GoodsItem) o;
            if (!other.canEqual(this)) return false;
            Object this$orderIndex = getOrderIndex(), other$orderIndex = other.getOrderIndex();
            if ((this$orderIndex == null) ? (other$orderIndex != null) : !this$orderIndex.equals(other$orderIndex))
                return false;
            Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
            if ((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn)) return false;
            Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
            if ((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum))
                return false;
            Object this$goodsCodeName = getGoodsCodeName(), other$goodsCodeName = other.getGoodsCodeName();
            if ((this$goodsCodeName == null) ? (other$goodsCodeName != null) : !this$goodsCodeName.equals(other$goodsCodeName))
                return false;
            Object this$goodsCodeAbbreviation = getGoodsCodeAbbreviation(), other$goodsCodeAbbreviation = other.getGoodsCodeAbbreviation();
            if ((this$goodsCodeAbbreviation == null) ? (other$goodsCodeAbbreviation != null) : !this$goodsCodeAbbreviation.equals(other$goodsCodeAbbreviation))
                return false;
            Object this$specificationModel = getSpecificationModel(), other$specificationModel = other.getSpecificationModel();
            if ((this$specificationModel == null) ? (other$specificationModel != null) : !this$specificationModel.equals(other$specificationModel))
                return false;
            Object this$unitOfMeasurement = getUnitOfMeasurement(), other$unitOfMeasurement = other.getUnitOfMeasurement();
            if ((this$unitOfMeasurement == null) ? (other$unitOfMeasurement != null) : !this$unitOfMeasurement.equals(other$unitOfMeasurement))
                return false;
            Object this$unitPrice = getUnitPrice(), other$unitPrice = other.getUnitPrice();
            if ((this$unitPrice == null) ? (other$unitPrice != null) : !this$unitPrice.equals(other$unitPrice))
                return false;
            Object this$availableTaxRate = getAvailableTaxRate(), other$availableTaxRate = other.getAvailableTaxRate();
            if ((this$availableTaxRate == null) ? (other$availableTaxRate != null) : !this$availableTaxRate.equals(other$availableTaxRate))
                return false;
            Object this$taxRate = getTaxRate(), other$taxRate = other.getTaxRate();
            if ((this$taxRate == null) ? (other$taxRate != null) : !this$taxRate.equals(other$taxRate)) return false;
            Object this$taxIncludedMark = getTaxIncludedMark(), other$taxIncludedMark = other.getTaxIncludedMark();
            if ((this$taxIncludedMark == null) ? (other$taxIncludedMark != null) : !this$taxIncludedMark.equals(other$taxIncludedMark))
                return false;
            Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
            if ((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode))
                return false;
            Object this$parentId = getParentId(), other$parentId = other.getParentId();
            if ((this$parentId == null) ? (other$parentId != null) : !this$parentId.equals(other$parentId))
                return false;
            Object this$zeroTaxRateIdentification = getZeroTaxRateIdentification(), other$zeroTaxRateIdentification = other.getZeroTaxRateIdentification();
            if ((this$zeroTaxRateIdentification == null) ? (other$zeroTaxRateIdentification != null) : !this$zeroTaxRateIdentification.equals(other$zeroTaxRateIdentification))
                return false;
            Object this$description = getDescription(), other$description = other.getDescription();
            if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description))
                return false;
            Object this$vatSpecialManagement = getVatSpecialManagement(), other$vatSpecialManagement = other.getVatSpecialManagement();
            if ((this$vatSpecialManagement == null) ? (other$vatSpecialManagement != null) : !this$vatSpecialManagement.equals(other$vatSpecialManagement))
                return false;
            Object this$preferentialPolicyIdentification = getPreferentialPolicyIdentification(), other$preferentialPolicyIdentification = other.getPreferentialPolicyIdentification();
            return !((this$preferentialPolicyIdentification == null) ? (other$preferentialPolicyIdentification != null) : !this$preferentialPolicyIdentification.equals(other$preferentialPolicyIdentification));
        }

        protected boolean canEqual(Object other) {
            return other instanceof GoodsItem;
        }

        public int hashCode() {
            int PRIME = 59;
            result = 1;
            Object $orderIndex = getOrderIndex();
            result = result * 59 + (($orderIndex == null) ? 43 : $orderIndex.hashCode());
            Object $bodySn = getBodySn();
            result = result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
            Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
            result = result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
            Object $goodsCodeName = getGoodsCodeName();
            result = result * 59 + (($goodsCodeName == null) ? 43 : $goodsCodeName.hashCode());
            Object $goodsCodeAbbreviation = getGoodsCodeAbbreviation();
            result = result * 59 + (($goodsCodeAbbreviation == null) ? 43 : $goodsCodeAbbreviation.hashCode());
            Object $specificationModel = getSpecificationModel();
            result = result * 59 + (($specificationModel == null) ? 43 : $specificationModel.hashCode());
            Object $unitOfMeasurement = getUnitOfMeasurement();
            result = result * 59 + (($unitOfMeasurement == null) ? 43 : $unitOfMeasurement.hashCode());
            Object $unitPrice = getUnitPrice();
            result = result * 59 + (($unitPrice == null) ? 43 : $unitPrice.hashCode());
            Object $availableTaxRate = getAvailableTaxRate();
            result = result * 59 + (($availableTaxRate == null) ? 43 : $availableTaxRate.hashCode());
            Object $taxRate = getTaxRate();
            result = result * 59 + (($taxRate == null) ? 43 : $taxRate.hashCode());
            Object $taxIncludedMark = getTaxIncludedMark();
            result = result * 59 + (($taxIncludedMark == null) ? 43 : $taxIncludedMark.hashCode());
            Object $goodsCode = getGoodsCode();
            result = result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
            Object $parentId = getParentId();
            result = result * 59 + (($parentId == null) ? 43 : $parentId.hashCode());
            Object $zeroTaxRateIdentification = getZeroTaxRateIdentification();
            result = result * 59 + (($zeroTaxRateIdentification == null) ? 43 : $zeroTaxRateIdentification.hashCode());
            Object $description = getDescription();
            result = result * 59 + (($description == null) ? 43 : $description.hashCode());
            Object $vatSpecialManagement = getVatSpecialManagement();
            result = result * 59 + (($vatSpecialManagement == null) ? 43 : $vatSpecialManagement.hashCode());
            Object $preferentialPolicyIdentification = getPreferentialPolicyIdentification();
            return result * 59 + (($preferentialPolicyIdentification == null) ? 43 : $preferentialPolicyIdentification.hashCode());
        }

        public String toString() {
            return "CustomizeGoodQueryResultBO.GoodsItem(orderIndex=" + getOrderIndex() + ", bodySn=" + getBodySn() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ", goodsCodeName=" + getGoodsCodeName() + ", goodsCodeAbbreviation=" + getGoodsCodeAbbreviation() + ", specificationModel=" + getSpecificationModel() + ", unitOfMeasurement=" + getUnitOfMeasurement() + ", unitPrice=" + getUnitPrice() + ", availableTaxRate=" + getAvailableTaxRate() + ", taxRate=" + getTaxRate() + ", taxIncludedMark=" + getTaxIncludedMark() + ", goodsCode=" + getGoodsCode() + ", parentId=" + getParentId() + ", zeroTaxRateIdentification=" + getZeroTaxRateIdentification() + ", description=" + getDescription() + ", vatSpecialManagement=" + getVatSpecialManagement() + ", preferentialPolicyIdentification=" + getPreferentialPolicyIdentification() + ")";
        }


        public String getOrderIndex() {
            return this.orderIndex;
        }


        public String getBodySn() {
            return this.bodySn;
        }


        public String getTaxpayerIdentificationNum() {
            return this.taxpayerIdentificationNum;
        }


        public String getGoodsCodeName() {
            return this.goodsCodeName;
        }


        public String getGoodsCodeAbbreviation() {
            return this.goodsCodeAbbreviation;
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


        public String getAvailableTaxRate() {
            return this.availableTaxRate;
        }


        public BigDecimal getTaxRate() {
            return this.taxRate;
        }


        public String getTaxIncludedMark() {
            return this.taxIncludedMark;
        }


        public String getGoodsCode() {
            return this.goodsCode;
        }


        public String getParentId() {
            return this.parentId;
        }


        public String getZeroTaxRateIdentification() {
            return this.zeroTaxRateIdentification;
        }


        public String getDescription() {
            return this.description;
        }


        public String getVatSpecialManagement() {
            return this.vatSpecialManagement;
        }


        public String getPreferentialPolicyIdentification() {
            return this.preferentialPolicyIdentification;
        }
    }

}
