package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发票抬头补全")
public class InvoiceHeaderCompletionRequest {
    @ApiModelProperty(notes = "销方纳税人识别号")
    @JacksonXmlProperty(localName = "shnsrsbh")
    private String salesTaxpayerIdentificationNumber;
    @ApiModelProperty(notes = "发票请求流水号", required = true)
    @JacksonXmlProperty(localName = "fpqqlsh")
    private String invoiceSn;
    @ApiModelProperty(notes = "购货单位识别号，如果不为空必须为 15-20 位,数字或大写字母")
    @JacksonXmlProperty(localName = "ghdwsbh")
    private String purchaseUnitIdentificationNum;

    public void setSalesTaxpayerIdentificationNumber(String salesTaxpayerIdentificationNumber) {
        this.salesTaxpayerIdentificationNumber = salesTaxpayerIdentificationNumber;
    }

    @ApiModelProperty(notes = "������������", required = true)
    @JacksonXmlProperty(localName = "ghdwmc")
    private String purchaseUnitName;
    @ApiModelProperty(notes = "����������������")
    @JacksonXmlProperty(localName = "ghdwdzdh")
    private String purchaseUnitTel;
    @ApiModelProperty(notes = "����������������")
    @JacksonXmlProperty(localName = "ghdwyhzh")
    private String purchaseUnitBankAccount;

    public void setInvoiceSn(String invoiceSn) {
        this.invoiceSn = invoiceSn;
    }

    public void setPurchaseUnitIdentificationNum(String purchaseUnitIdentificationNum) {
        this.purchaseUnitIdentificationNum = purchaseUnitIdentificationNum;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
    }

    public void setPurchaseUnitTel(String purchaseUnitTel) {
        this.purchaseUnitTel = purchaseUnitTel;
    }

    public void setPurchaseUnitBankAccount(String purchaseUnitBankAccount) {
        this.purchaseUnitBankAccount = purchaseUnitBankAccount;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InvoiceHeaderCompletionRequest)) return false;
        InvoiceHeaderCompletionRequest other = (InvoiceHeaderCompletionRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber(), other$salesTaxpayerIdentificationNumber = other.getSalesTaxpayerIdentificationNumber();
        if ((this$salesTaxpayerIdentificationNumber == null) ? (other$salesTaxpayerIdentificationNumber != null) : !this$salesTaxpayerIdentificationNumber.equals(other$salesTaxpayerIdentificationNumber))
            return false;
        Object this$invoiceSn = getInvoiceSn(), other$invoiceSn = other.getInvoiceSn();
        if ((this$invoiceSn == null) ? (other$invoiceSn != null) : !this$invoiceSn.equals(other$invoiceSn))
            return false;
        Object this$purchaseUnitIdentificationNum = getPurchaseUnitIdentificationNum(), other$purchaseUnitIdentificationNum = other.getPurchaseUnitIdentificationNum();
        if ((this$purchaseUnitIdentificationNum == null) ? (other$purchaseUnitIdentificationNum != null) : !this$purchaseUnitIdentificationNum.equals(other$purchaseUnitIdentificationNum))
            return false;
        Object this$purchaseUnitName = getPurchaseUnitName(), other$purchaseUnitName = other.getPurchaseUnitName();
        if ((this$purchaseUnitName == null) ? (other$purchaseUnitName != null) : !this$purchaseUnitName.equals(other$purchaseUnitName))
            return false;
        Object this$purchaseUnitTel = getPurchaseUnitTel(), other$purchaseUnitTel = other.getPurchaseUnitTel();
        if ((this$purchaseUnitTel == null) ? (other$purchaseUnitTel != null) : !this$purchaseUnitTel.equals(other$purchaseUnitTel))
            return false;
        Object this$purchaseUnitBankAccount = getPurchaseUnitBankAccount(), other$purchaseUnitBankAccount = other.getPurchaseUnitBankAccount();
        return !((this$purchaseUnitBankAccount == null) ? (other$purchaseUnitBankAccount != null) : !this$purchaseUnitBankAccount.equals(other$purchaseUnitBankAccount));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InvoiceHeaderCompletionRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber();
        result = result * 59 + (($salesTaxpayerIdentificationNumber == null) ? 43 : $salesTaxpayerIdentificationNumber.hashCode());
        Object $invoiceSn = getInvoiceSn();
        result = result * 59 + (($invoiceSn == null) ? 43 : $invoiceSn.hashCode());
        Object $purchaseUnitIdentificationNum = getPurchaseUnitIdentificationNum();
        result = result * 59 + (($purchaseUnitIdentificationNum == null) ? 43 : $purchaseUnitIdentificationNum.hashCode());
        Object $purchaseUnitName = getPurchaseUnitName();
        result = result * 59 + (($purchaseUnitName == null) ? 43 : $purchaseUnitName.hashCode());
        Object $purchaseUnitTel = getPurchaseUnitTel();
        result = result * 59 + (($purchaseUnitTel == null) ? 43 : $purchaseUnitTel.hashCode());
        Object $purchaseUnitBankAccount = getPurchaseUnitBankAccount();
        return result * 59 + (($purchaseUnitBankAccount == null) ? 43 : $purchaseUnitBankAccount.hashCode());
    }

    public String toString() {
        return "InvoiceHeaderCompletionRequest(salesTaxpayerIdentificationNumber=" + getSalesTaxpayerIdentificationNumber() + ", invoiceSn=" + getInvoiceSn() + ", purchaseUnitIdentificationNum=" + getPurchaseUnitIdentificationNum() + ", purchaseUnitName=" + getPurchaseUnitName() + ", purchaseUnitTel=" + getPurchaseUnitTel() + ", purchaseUnitBankAccount=" + getPurchaseUnitBankAccount() + ")";
    }


    public String getSalesTaxpayerIdentificationNumber() {
        return this.salesTaxpayerIdentificationNumber;
    }


    public String getInvoiceSn() {
        return this.invoiceSn;
    }


    public String getPurchaseUnitIdentificationNum() {
        return this.purchaseUnitIdentificationNum;
    }


    public String getPurchaseUnitName() {
        return this.purchaseUnitName;
    }


    public String getPurchaseUnitTel() {
        return this.purchaseUnitTel;
    }


    public String getPurchaseUnitBankAccount() {
        return this.purchaseUnitBankAccount;
    }
}
