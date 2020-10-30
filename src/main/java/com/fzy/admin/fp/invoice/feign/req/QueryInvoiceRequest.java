package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发票查询")
public class QueryInvoiceRequest {
    @ApiModelProperty("机身编码")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodyNumber;
    @ApiModelProperty("发票类型代码")
    @JacksonXmlProperty(localName = "fplxdm")
    private String invoiceTypeCode;

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    @ApiModelProperty(value = "查询方式", notes = "查询方式 0：按发票号码查询 1：按请求流水号查询")
    @JacksonXmlProperty(localName = "cxfs")
    private String queryType;
    @ApiModelProperty(value = "查询条件", notes = "查询条件 cxfs为 0时 发票代码+发票号码 cxfs为 1时： 发票交易流水号")
    @JacksonXmlProperty(localName = "cxtj")
    private String queryCondition;

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof QueryInvoiceRequest)) return false;
        QueryInvoiceRequest other = (QueryInvoiceRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$bodyNumber = getBodyNumber(), other$bodyNumber = other.getBodyNumber();
        if ((this$bodyNumber == null) ? (other$bodyNumber != null) : !this$bodyNumber.equals(other$bodyNumber))
            return false;
        Object this$invoiceTypeCode = getInvoiceTypeCode(), other$invoiceTypeCode = other.getInvoiceTypeCode();
        if ((this$invoiceTypeCode == null) ? (other$invoiceTypeCode != null) : !this$invoiceTypeCode.equals(other$invoiceTypeCode))
            return false;
        Object this$queryType = getQueryType(), other$queryType = other.getQueryType();
        if ((this$queryType == null) ? (other$queryType != null) : !this$queryType.equals(other$queryType))
            return false;
        Object this$queryCondition = getQueryCondition(), other$queryCondition = other.getQueryCondition();
        return !((this$queryCondition == null) ? (other$queryCondition != null) : !this$queryCondition.equals(other$queryCondition));
    }

    protected boolean canEqual(Object other) {
        return other instanceof QueryInvoiceRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $bodyNumber = getBodyNumber();
        result = result * 59 + (($bodyNumber == null) ? 43 : $bodyNumber.hashCode());
        Object $invoiceTypeCode = getInvoiceTypeCode();
        result = result * 59 + (($invoiceTypeCode == null) ? 43 : $invoiceTypeCode.hashCode());
        Object $queryType = getQueryType();
        result = result * 59 + (($queryType == null) ? 43 : $queryType.hashCode());
        Object $queryCondition = getQueryCondition();
        return result * 59 + (($queryCondition == null) ? 43 : $queryCondition.hashCode());
    }

    public String toString() {
        return "QueryInvoiceRequest(bodyNumber=" + getBodyNumber() + ", invoiceTypeCode=" + getInvoiceTypeCode() + ", queryType=" + getQueryType() + ", queryCondition=" + getQueryCondition() + ")";
    }


    public String getBodyNumber() {
        return this.bodyNumber;
    }


    public String getInvoiceTypeCode() {
        return this.invoiceTypeCode;
    }


    public String getQueryType() {
        return this.queryType;
    }


    public String getQueryCondition() {
        return this.queryCondition;
    }
}
