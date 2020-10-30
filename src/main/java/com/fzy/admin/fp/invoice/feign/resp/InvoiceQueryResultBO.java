package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("发票查询结果")
public class InvoiceQueryResultBO extends FamilyBaseBO {
    @ApiModelProperty("响应数据")
    @JacksonXmlElementWrapper(localName = "returndata")
    @JacksonXmlProperty(localName = "kpxx")
    private List<InvoiceInfo> invoiceInfos;

    public String toString() {
        return "InvoiceQueryResultBO(invoiceInfos=" + getInvoiceInfos() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $invoiceInfos = getInvoiceInfos();
        return result * 59 + (($invoiceInfos == null) ? 43 : $invoiceInfos.hashCode());
    }

    protected boolean canEqual(Object other) {
        return other instanceof InvoiceQueryResultBO;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InvoiceQueryResultBO)) return false;
        InvoiceQueryResultBO other = (InvoiceQueryResultBO) o;
        if (!other.canEqual(this)) return false;
        Object this$invoiceInfos = getInvoiceInfos(), other$invoiceInfos = other.getInvoiceInfos();
        return !((this$invoiceInfos == null) ? (other$invoiceInfos != null) : !this$invoiceInfos.equals(other$invoiceInfos));
    }

    public void setInvoiceInfos(List<InvoiceInfo> invoiceInfos) {
        this.invoiceInfos = invoiceInfos;
    }


    public List<InvoiceInfo> getInvoiceInfos() {
        return this.invoiceInfos;
    }

    @ApiModel("发票信息")
    public static class InvoiceInfo {
        @ApiModelProperty("发票代码")
        @JacksonXmlProperty(localName = "fpdm")
        private String invoiceCode;
        @ApiModelProperty("发票号码")
        @JacksonXmlProperty(localName = "fphm")
        private String invoiceNumber;
        @ApiModelProperty(value = "发票状态", notes = "发票状态 00：已开具的正数发票 01：已开具的负数发票 02：未开具发票的作废发票 03：已开具正数发票的作废发票 04：已开具负数发票的作废发票;")
        @JacksonXmlProperty(localName = "fpzt")
        private String invoiceStatus;
        @ApiModelProperty("税控设备编号")
        @JacksonXmlProperty(localName = "sksbbh")
        private String taxControlEquipmentNum;
        @ApiModelProperty("开票日期")
        @JacksonXmlProperty(localName = "kprq")
        private String billingDate;
        @ApiModelProperty("购买单位识别号")
        @JacksonXmlProperty(localName = "ghdwsbh")
        private String purchaseUnitIdentificationNumber;

        public void setInvoiceCode(String invoiceCode) {
            this.invoiceCode = invoiceCode;
        }

        @ApiModelProperty("购买单位名称")
        @JacksonXmlProperty(localName = "ghdwmc")
        private String purchaseUnitName;
        @ApiModelProperty("合计金额")
        @JacksonXmlProperty(localName = "hjje")
        private String totalAmount;
        @ApiModelProperty("合计税额")
        @JacksonXmlProperty(localName = "hjse")
        private String totalNumber;
        @ApiModelProperty("价税合计")
        @JacksonXmlProperty(localName = "jshj")
        private String totalPriceTax;
        @ApiModelProperty("电子发票下载地址")
        @JacksonXmlProperty(localName = "url")
        private String downloadUrl;
        @ApiModelProperty("密码区")
        @JacksonXmlProperty(localName = "mmq")
        private String passwordArea;

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public void setInvoiceStatus(String invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
        }

        public void setTaxControlEquipmentNum(String taxControlEquipmentNum) {
            this.taxControlEquipmentNum = taxControlEquipmentNum;
        }

        public void setBillingDate(String billingDate) {
            this.billingDate = billingDate;
        }

        public void setPurchaseUnitIdentificationNumber(String purchaseUnitIdentificationNumber) {
            this.purchaseUnitIdentificationNumber = purchaseUnitIdentificationNumber;
        }

        public void setPurchaseUnitName(String purchaseUnitName) {
            this.purchaseUnitName = purchaseUnitName;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setTotalNumber(String totalNumber) {
            this.totalNumber = totalNumber;
        }

        public void setTotalPriceTax(String totalPriceTax) {
            this.totalPriceTax = totalPriceTax;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public void setPasswordArea(String passwordArea) {
            this.passwordArea = passwordArea;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof InvoiceInfo)) return false;
            InvoiceInfo other = (InvoiceInfo) o;
            if (!other.canEqual(this)) return false;
            Object this$invoiceCode = getInvoiceCode(), other$invoiceCode = other.getInvoiceCode();
            if ((this$invoiceCode == null) ? (other$invoiceCode != null) : !this$invoiceCode.equals(other$invoiceCode))
                return false;
            Object this$invoiceNumber = getInvoiceNumber(), other$invoiceNumber = other.getInvoiceNumber();
            if ((this$invoiceNumber == null) ? (other$invoiceNumber != null) : !this$invoiceNumber.equals(other$invoiceNumber))
                return false;
            Object this$invoiceStatus = getInvoiceStatus(), other$invoiceStatus = other.getInvoiceStatus();
            if ((this$invoiceStatus == null) ? (other$invoiceStatus != null) : !this$invoiceStatus.equals(other$invoiceStatus))
                return false;
            Object this$taxControlEquipmentNum = getTaxControlEquipmentNum(), other$taxControlEquipmentNum = other.getTaxControlEquipmentNum();
            if ((this$taxControlEquipmentNum == null) ? (other$taxControlEquipmentNum != null) : !this$taxControlEquipmentNum.equals(other$taxControlEquipmentNum))
                return false;
            Object this$billingDate = getBillingDate(), other$billingDate = other.getBillingDate();
            if ((this$billingDate == null) ? (other$billingDate != null) : !this$billingDate.equals(other$billingDate))
                return false;
            Object this$purchaseUnitIdentificationNumber = getPurchaseUnitIdentificationNumber(), other$purchaseUnitIdentificationNumber = other.getPurchaseUnitIdentificationNumber();
            if ((this$purchaseUnitIdentificationNumber == null) ? (other$purchaseUnitIdentificationNumber != null) : !this$purchaseUnitIdentificationNumber.equals(other$purchaseUnitIdentificationNumber))
                return false;
            Object this$purchaseUnitName = getPurchaseUnitName(), other$purchaseUnitName = other.getPurchaseUnitName();
            if ((this$purchaseUnitName == null) ? (other$purchaseUnitName != null) : !this$purchaseUnitName.equals(other$purchaseUnitName))
                return false;
            Object this$totalAmount = getTotalAmount(), other$totalAmount = other.getTotalAmount();
            if ((this$totalAmount == null) ? (other$totalAmount != null) : !this$totalAmount.equals(other$totalAmount))
                return false;
            Object this$totalNumber = getTotalNumber(), other$totalNumber = other.getTotalNumber();
            if ((this$totalNumber == null) ? (other$totalNumber != null) : !this$totalNumber.equals(other$totalNumber))
                return false;
            Object this$totalPriceTax = getTotalPriceTax(), other$totalPriceTax = other.getTotalPriceTax();
            if ((this$totalPriceTax == null) ? (other$totalPriceTax != null) : !this$totalPriceTax.equals(other$totalPriceTax))
                return false;
            Object this$downloadUrl = getDownloadUrl(), other$downloadUrl = other.getDownloadUrl();
            if ((this$downloadUrl == null) ? (other$downloadUrl != null) : !this$downloadUrl.equals(other$downloadUrl))
                return false;
            Object this$passwordArea = getPasswordArea(), other$passwordArea = other.getPasswordArea();
            return !((this$passwordArea == null) ? (other$passwordArea != null) : !this$passwordArea.equals(other$passwordArea));
        }

        protected boolean canEqual(Object other) {
            return other instanceof InvoiceInfo;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $invoiceCode = getInvoiceCode();
            result = result * 59 + (($invoiceCode == null) ? 43 : $invoiceCode.hashCode());
            Object $invoiceNumber = getInvoiceNumber();
            result = result * 59 + (($invoiceNumber == null) ? 43 : $invoiceNumber.hashCode());
            Object $invoiceStatus = getInvoiceStatus();
            result = result * 59 + (($invoiceStatus == null) ? 43 : $invoiceStatus.hashCode());
            Object $taxControlEquipmentNum = getTaxControlEquipmentNum();
            result = result * 59 + (($taxControlEquipmentNum == null) ? 43 : $taxControlEquipmentNum.hashCode());
            Object $billingDate = getBillingDate();
            result = result * 59 + (($billingDate == null) ? 43 : $billingDate.hashCode());
            Object $purchaseUnitIdentificationNumber = getPurchaseUnitIdentificationNumber();
            result = result * 59 + (($purchaseUnitIdentificationNumber == null) ? 43 : $purchaseUnitIdentificationNumber.hashCode());
            Object $purchaseUnitName = getPurchaseUnitName();
            result = result * 59 + (($purchaseUnitName == null) ? 43 : $purchaseUnitName.hashCode());
            Object $totalAmount = getTotalAmount();
            result = result * 59 + (($totalAmount == null) ? 43 : $totalAmount.hashCode());
            Object $totalNumber = getTotalNumber();
            result = result * 59 + (($totalNumber == null) ? 43 : $totalNumber.hashCode());
            Object $totalPriceTax = getTotalPriceTax();
            result = result * 59 + (($totalPriceTax == null) ? 43 : $totalPriceTax.hashCode());
            Object $downloadUrl = getDownloadUrl();
            result = result * 59 + (($downloadUrl == null) ? 43 : $downloadUrl.hashCode());
            Object $passwordArea = getPasswordArea();
            return result * 59 + (($passwordArea == null) ? 43 : $passwordArea.hashCode());
        }

        public String toString() {
            return "InvoiceQueryResultBO.InvoiceInfo(invoiceCode=" + getInvoiceCode() + ", invoiceNumber=" + getInvoiceNumber() + ", invoiceStatus=" + getInvoiceStatus() + ", taxControlEquipmentNum=" + getTaxControlEquipmentNum() + ", billingDate=" + getBillingDate() + ", purchaseUnitIdentificationNumber=" + getPurchaseUnitIdentificationNumber() + ", purchaseUnitName=" + getPurchaseUnitName() + ", totalAmount=" + getTotalAmount() + ", totalNumber=" + getTotalNumber() + ", totalPriceTax=" + getTotalPriceTax() + ", downloadUrl=" + getDownloadUrl() + ", passwordArea=" + getPasswordArea() + ")";
        }


        public String getInvoiceCode() {
            return this.invoiceCode;
        }


        public String getInvoiceNumber() {
            return this.invoiceNumber;
        }


        public String getInvoiceStatus() {
            return this.invoiceStatus;
        }


        public String getTaxControlEquipmentNum() {
            return this.taxControlEquipmentNum;
        }


        public String getBillingDate() {
            return this.billingDate;
        }


        public String getPurchaseUnitIdentificationNumber() {
            return this.purchaseUnitIdentificationNumber;
        }


        public String getPurchaseUnitName() {
            return this.purchaseUnitName;
        }


        public String getTotalAmount() {
            return this.totalAmount;
        }


        public String getTotalNumber() {
            return this.totalNumber;
        }


        public String getTotalPriceTax() {
            return this.totalPriceTax;
        }


        public String getDownloadUrl() {
            return this.downloadUrl;
        }


        public String getPasswordArea() {
            return this.passwordArea;
        }
    }

}

