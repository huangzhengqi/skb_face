package com.fzy.admin.fp.invoice.feign.req;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询自定义商品编码")
public class QueryCustomizeGoodsCodeRequest {
    @ApiModelProperty(notes = "纳税人识别号", required = true)
    @JacksonXmlProperty(localName = "nsrsbh")
    private String salesTaxpayerIdentificationNumber;

    public void setSalesTaxpayerIdentificationNumber(String salesTaxpayerIdentificationNumber) {
        this.salesTaxpayerIdentificationNumber = salesTaxpayerIdentificationNumber;
    }

    @ApiModelProperty(value = "机身编号", notes = "纳税人识别号+开票终端标识（使用英文~~符号分隔）")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodySn;

    public void setBodySn(String bodySn) {
        this.bodySn = bodySn;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof QueryCustomizeGoodsCodeRequest)) return false;
        QueryCustomizeGoodsCodeRequest other = (QueryCustomizeGoodsCodeRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber(), other$salesTaxpayerIdentificationNumber = other.getSalesTaxpayerIdentificationNumber();
        if ((this$salesTaxpayerIdentificationNumber == null) ? (other$salesTaxpayerIdentificationNumber != null) : !this$salesTaxpayerIdentificationNumber.equals(other$salesTaxpayerIdentificationNumber))
            return false;
        Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
        return !((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn));
    }

    protected boolean canEqual(Object other) {
        return other instanceof QueryCustomizeGoodsCodeRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $salesTaxpayerIdentificationNumber = getSalesTaxpayerIdentificationNumber();
        result = result * 59 + (($salesTaxpayerIdentificationNumber == null) ? 43 : $salesTaxpayerIdentificationNumber.hashCode());
        Object $bodySn = getBodySn();
        return result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
    }

    public String toString() {
        return "QueryCustomizeGoodsCodeRequest(salesTaxpayerIdentificationNumber=" + getSalesTaxpayerIdentificationNumber() + ", bodySn=" + getBodySn() + ")";
    }


    public String getSalesTaxpayerIdentificationNumber() {
        return this.salesTaxpayerIdentificationNumber;
    }


    public String getBodySn() {
        return this.bodySn;
    }
}
