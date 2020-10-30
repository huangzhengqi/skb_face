package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.enmus.InvoiceTypeCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VATElectronicInvoiceRequest {
    @ApiModelProperty(notes = "发票请求流水号，数字或字母或两者的组合，长度不超 100位，如果流水号重复，返回该流水号已开发票信息", required = true)
    @JacksonXmlProperty(localName = "fpqqlsh")
    private String requestSn;
    @ApiModelProperty(notes = "销方纳税人识别号", required = true)
    @JacksonXmlProperty(localName = "shnsrsbh")
    private String salesTaxpayerIdentificationNumber;
    @ApiModelProperty(value = "机身编码", notes = "纳税人识别号+开票终端标识(使用英文~~符号分隔)")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodySn;

    public VATElectronicInvoiceRequest setRequestSn(String requestSn) {
        this.requestSn = requestSn;
        return this;
    }

    public VATElectronicInvoiceRequest setSalesTaxpayerIdentificationNumber(String salesTaxpayerIdentificationNumber) {
        this.salesTaxpayerIdentificationNumber = salesTaxpayerIdentificationNumber;
        return this;
    }

    public VATElectronicInvoiceRequest setBodySn(String bodySn) {
        this.bodySn = bodySn;
        return this;
    }

    public VATElectronicInvoiceRequest setInvoiceTyeCode(String invoiceTyeCode) {
        this.invoiceTyeCode = invoiceTyeCode;
        return this;
    }

    public VATElectronicInvoiceRequest setBillingType(Integer billingType) {
        this.billingType = billingType;
        return this;
    }

    public VATElectronicInvoiceRequest setUseType(Integer useType) {
        this.useType = useType;
        return this;
    }

    public VATElectronicInvoiceRequest setPurchaseUnitIdentificationNum(String purchaseUnitIdentificationNum) {
        this.purchaseUnitIdentificationNum = purchaseUnitIdentificationNum;
        return this;
    }

    public VATElectronicInvoiceRequest setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
        return this;
    }

    public VATElectronicInvoiceRequest setPurchaseUnitTel(String purchaseUnitTel) {
        this.purchaseUnitTel = purchaseUnitTel;
        return this;
    }

    public VATElectronicInvoiceRequest setPurchaseUnitBankAccount(String purchaseUnitBankAccount) {
        this.purchaseUnitBankAccount = purchaseUnitBankAccount;
        return this;
    }

    public VATElectronicInvoiceRequest setBillingItems(BillingItems billingItems) {
        this.billingItems = billingItems;
        return this;
    }

    public VATElectronicInvoiceRequest setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public VATElectronicInvoiceRequest setPayee(String payee) {
        this.payee = payee;
        return this;
    }

    public VATElectronicInvoiceRequest setReviewer(String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public VATElectronicInvoiceRequest setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public VATElectronicInvoiceRequest setOriginalInvoiceCode(String originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode;
        return this;
    }

    public VATElectronicInvoiceRequest setOriginalInvoiceNum(String originalInvoiceNum) {
        this.originalInvoiceNum = originalInvoiceNum;
        return this;
    }

    public VATElectronicInvoiceRequest setInvoicersContactInfo(String invoicersContactInfo) {
        this.invoicersContactInfo = invoicersContactInfo;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof VATElectronicInvoiceRequest)) return false;
        VATElectronicInvoiceRequest other = (VATElectronicInvoiceRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$requestSn = getRequestSn(), other$requestSn = other.getRequestSn();
        if ((this$requestSn == null) ? (other$requestSn != null) : !this$requestSn.equals(other$requestSn))
            return false;
        Object this$salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber(), other$salesTaxpayerIdentificationNumber = other.getSalesTaxpayerIdentificationNumber();
        if ((this$salesTaxpayerIdentificationNumber == null) ? (other$salesTaxpayerIdentificationNumber != null) : !this$salesTaxpayerIdentificationNumber.equals(other$salesTaxpayerIdentificationNumber))
            return false;
        Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
        if ((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn)) return false;
        Object this$invoiceTyeCode = getInvoiceTyeCode(), other$invoiceTyeCode = other.getInvoiceTyeCode();
        if ((this$invoiceTyeCode == null) ? (other$invoiceTyeCode != null) : !this$invoiceTyeCode.equals(other$invoiceTyeCode))
            return false;
        Object this$billingType = getBillingType(), other$billingType = other.getBillingType();
        if ((this$billingType == null) ? (other$billingType != null) : !this$billingType.equals(other$billingType))
            return false;
        Object this$useType = getUseType(), other$useType = other.getUseType();
        if ((this$useType == null) ? (other$useType != null) : !this$useType.equals(other$useType)) return false;
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
        if ((this$purchaseUnitBankAccount == null) ? (other$purchaseUnitBankAccount != null) : !this$purchaseUnitBankAccount.equals(other$purchaseUnitBankAccount))
            return false;
        Object this$billingItems = getBillingItems(), other$billingItems = other.getBillingItems();
        if ((this$billingItems == null) ? (other$billingItems != null) : !this$billingItems.equals(other$billingItems))
            return false;
        Object this$remark = getRemark(), other$remark = other.getRemark();
        if ((this$remark == null) ? (other$remark != null) : !this$remark.equals(other$remark)) return false;
        Object this$payee = getPayee(), other$payee = other.getPayee();
        if ((this$payee == null) ? (other$payee != null) : !this$payee.equals(other$payee)) return false;
        Object this$reviewer = getReviewer(), other$reviewer = other.getReviewer();
        if ((this$reviewer == null) ? (other$reviewer != null) : !this$reviewer.equals(other$reviewer)) return false;
        Object this$issuer = getIssuer(), other$issuer = other.getIssuer();
        if ((this$issuer == null) ? (other$issuer != null) : !this$issuer.equals(other$issuer)) return false;
        Object this$originalInvoiceCode = getOriginalInvoiceCode(), other$originalInvoiceCode = other.getOriginalInvoiceCode();
        if ((this$originalInvoiceCode == null) ? (other$originalInvoiceCode != null) : !this$originalInvoiceCode.equals(other$originalInvoiceCode))
            return false;
        Object this$originalInvoiceNum = getOriginalInvoiceNum(), other$originalInvoiceNum = other.getOriginalInvoiceNum();
        if ((this$originalInvoiceNum == null) ? (other$originalInvoiceNum != null) : !this$originalInvoiceNum.equals(other$originalInvoiceNum))
            return false;
        Object this$invoicersContactInfo = getInvoicersContactInfo(), other$invoicersContactInfo = other.getInvoicersContactInfo();
        return !((this$invoicersContactInfo == null) ? (other$invoicersContactInfo != null) : !this$invoicersContactInfo.equals(other$invoicersContactInfo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof VATElectronicInvoiceRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $requestSn = getRequestSn();
        result = result * 59 + (($requestSn == null) ? 43 : $requestSn.hashCode());
        Object $salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber();
        result = result * 59 + (($salesTaxpayerIdentificationNumber == null) ? 43 : $salesTaxpayerIdentificationNumber.hashCode());
        Object $bodySn = getBodySn();
        result = result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
        Object $invoiceTyeCode = getInvoiceTyeCode();
        result = result * 59 + (($invoiceTyeCode == null) ? 43 : $invoiceTyeCode.hashCode());
        Object $billingType = getBillingType();
        result = result * 59 + (($billingType == null) ? 43 : $billingType.hashCode());
        Object $useType = getUseType();
        result = result * 59 + (($useType == null) ? 43 : $useType.hashCode());
        Object $purchaseUnitIdentificationNum = getPurchaseUnitIdentificationNum();
        result = result * 59 + (($purchaseUnitIdentificationNum == null) ? 43 : $purchaseUnitIdentificationNum.hashCode());
        Object $purchaseUnitName = getPurchaseUnitName();
        result = result * 59 + (($purchaseUnitName == null) ? 43 : $purchaseUnitName.hashCode());
        Object $purchaseUnitTel = getPurchaseUnitTel();
        result = result * 59 + (($purchaseUnitTel == null) ? 43 : $purchaseUnitTel.hashCode());
        Object $purchaseUnitBankAccount = getPurchaseUnitBankAccount();
        result = result * 59 + (($purchaseUnitBankAccount == null) ? 43 : $purchaseUnitBankAccount.hashCode());
        Object $billingItems = getBillingItems();
        result = result * 59 + (($billingItems == null) ? 43 : $billingItems.hashCode());
        Object $remark = getRemark();
        result = result * 59 + (($remark == null) ? 43 : $remark.hashCode());
        Object $payee = getPayee();
        result = result * 59 + (($payee == null) ? 43 : $payee.hashCode());
        Object $reviewer = getReviewer();
        result = result * 59 + (($reviewer == null) ? 43 : $reviewer.hashCode());
        Object $issuer = getIssuer();
        result = result * 59 + (($issuer == null) ? 43 : $issuer.hashCode());
        Object $originalInvoiceCode = getOriginalInvoiceCode();
        result = result * 59 + (($originalInvoiceCode == null) ? 43 : $originalInvoiceCode.hashCode());
        Object $originalInvoiceNum = getOriginalInvoiceNum();
        result = result * 59 + (($originalInvoiceNum == null) ? 43 : $originalInvoiceNum.hashCode());
        Object $invoicersContactInfo = getInvoicersContactInfo();
        return result * 59 + (($invoicersContactInfo == null) ? 43 : $invoicersContactInfo.hashCode());
    }

    public String toString() {
        return "VATElectronicInvoiceRequest(requestSn=" + getRequestSn() + ", salesTaxpayerIdentificationNumber=" + getSalesTaxpayerIdentificationNumber() + ", bodySn=" + getBodySn() + ", invoiceTyeCode=" + getInvoiceTyeCode() + ", billingType=" + getBillingType() + ", useType=" + getUseType() + ", purchaseUnitIdentificationNum=" + getPurchaseUnitIdentificationNum() + ", purchaseUnitName=" + getPurchaseUnitName() + ", purchaseUnitTel=" + getPurchaseUnitTel() + ", purchaseUnitBankAccount=" + getPurchaseUnitBankAccount() + ", billingItems=" + getBillingItems() + ", remark=" + getRemark() + ", payee=" + getPayee() + ", reviewer=" + getReviewer() + ", issuer=" + getIssuer() + ", originalInvoiceCode=" + getOriginalInvoiceCode() + ", originalInvoiceNum=" + getOriginalInvoiceNum() + ", invoicersContactInfo=" + getInvoicersContactInfo() + ")";
    }


    public String getRequestSn() {
        return this.requestSn;
    }


    public String getSalesTaxpayerIdentificationNumber() {
        return this.salesTaxpayerIdentificationNumber;
    }


    public String getBodySn() {
        return this.bodySn;
    }

    @ApiModelProperty(value = "������������", required = true, notes = "004: ��������������007: ��������������025����������026: �������� ")
    @JacksonXmlProperty(localName = "fplxdm")
    private String invoiceTyeCode = InvoiceTypeCode.ELECTRONIC


            .getCode();
    @ApiModelProperty(notes = "��������: 0������������ 1������������", required = true)
    @JacksonXmlProperty(localName = "kplx")
    private Integer billingType;

    public String getInvoiceTyeCode() {
        return this.invoiceTyeCode;
    }


    public Integer getBillingType() {
        return this.billingType;
    }

    @ApiModelProperty(notes = "��������������, ���������������� 15-20 ��,��������������")
    @JacksonXmlProperty(localName = "ghdwsbh")
    private String purchaseUnitIdentificationNum;
    @ApiModelProperty(notes = "������������", required = true)
    @JacksonXmlProperty(localName = "ghdwmc")
    private String purchaseUnitName;
    @ApiModelProperty(notes = "����������������")
    @JacksonXmlProperty(localName = "ghdwdzdh")
    private String purchaseUnitTel;
    @ApiModelProperty(notes = "��������: 0���������� 1��������������", required = true)
    @JacksonXmlProperty(localName = "yhlx")
    private Integer useType = Integer.valueOf(0);
    @ApiModelProperty(notes = "����������������")
    @JacksonXmlProperty(localName = "ghdwyhzh")
    private String purchaseUnitBankAccount;
    @ApiModelProperty(notes = "������������")
    @JacksonXmlProperty(localName = "fyxm")
    private BillingItems billingItems;
    @ApiModelProperty(notes = "����, ���������������� 142")
    @JacksonXmlProperty(localName = "bz")
    private String remark;

    public Integer getUseType() {
        return this.useType;
    }

    @ApiModelProperty(notes = "������")
    @JacksonXmlProperty(localName = "skr")
    private String payee;
    @ApiModelProperty(notes = "������")
    @JacksonXmlProperty(localName = "fhr")
    private String reviewer;
    @ApiModelProperty(notes = "������")
    @JacksonXmlProperty(localName = "kpr")
    private String issuer;
    @ApiModelProperty(notes = "����������")
    @JacksonXmlProperty(localName = "yfpdm")
    private String originalInvoiceCode;
    @ApiModelProperty(notes = "����������")
    @JacksonXmlProperty(localName = "yfphm")
    private String originalInvoiceNum;
    @ApiModelProperty(value = "������������������������������", required = true, notes = "������������������������������������������������������������;������������������������������������������������")
    @JacksonXmlProperty(localName = "sprsjh")
    private String invoicersContactInfo;

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


    public BillingItems getBillingItems() {
        return this.billingItems;
    }


    public String getRemark() {
        return this.remark;
    }


    public String getPayee() {
        return this.payee;
    }


    public String getReviewer() {
        return this.reviewer;
    }


    public String getIssuer() {
        return this.issuer;
    }


    public String getOriginalInvoiceCode() {
        return this.originalInvoiceCode;
    }


    public String getOriginalInvoiceNum() {
        return this.originalInvoiceNum;
    }


    public String getInvoicersContactInfo() {
        return this.invoicersContactInfo;
    }

    @ApiModel("��������")
    public static class BillingItems {
        private static final Logger log = LoggerFactory.getLogger(BillingItems.class);
        @ApiModelProperty(notes = "��������������")
        @JacksonXmlProperty(isAttribute = true)
        private Integer count;
        @ApiModelProperty(notes = "����������")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<InvoiceItem> group;

        public BillingItems setCount(Integer count) {
            this.count = count;
            return this;
        }

        public BillingItems setGroup(List<InvoiceItem> group) {
            this.group = group;
            return this;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof BillingItems)) return false;
            BillingItems other = (BillingItems) o;
            if (!other.canEqual(this)) return false;
            Object this$count = getCount(), other$count = other.getCount();
            if ((this$count == null) ? (other$count != null) : !this$count.equals(other$count)) return false;
            Object this$group = getGroup(), other$group = other.getGroup();
            return !((this$group == null) ? (other$group != null) : !this$group.equals(other$group));
        }

        protected boolean canEqual(Object other) {
            return other instanceof BillingItems;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $count = getCount();
            result = result * 59 + (($count == null) ? 43 : $count.hashCode());
            Object $group = getGroup();
            return result * 59 + (($group == null) ? 43 : $group.hashCode());
        }

        public String toString() {
            return "VATElectronicInvoiceRequest.BillingItems(count=" + getCount() + ", group=" + getGroup() + ")";
        }

        public BillingItems(Integer count, List<InvoiceItem> group) {
            this.count = count;
            this.group = group;
        }


        public BillingItems() {
        }


        public Integer getCount() {
            return this.count;
        }


        public List<InvoiceItem> getGroup() {
            return this.group;
        }
    }
}

