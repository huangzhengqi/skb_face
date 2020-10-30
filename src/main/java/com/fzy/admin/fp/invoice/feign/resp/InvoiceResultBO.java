package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModelProperty;

public class InvoiceResultBO extends FamilyBaseBO {
    @ApiModelProperty(notes = "开票截止时间，自助开票时返回，自助开票截止时间，格式yyyymmdd")
    @JacksonXmlProperty
    private String kpjzsj;
    @ApiModelProperty(notes = "可选，自助开票的二维码链接")
    @JacksonXmlProperty
    private String urlQRCode;

    public InvoiceResultBO setKpjzsj(String kpjzsj) {
        this.kpjzsj = kpjzsj;
        return this;
    }

    public InvoiceResultBO setUrlQRCode(String urlQRCode) {
        this.urlQRCode = urlQRCode;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InvoiceResultBO)) return false;
        InvoiceResultBO other = (InvoiceResultBO) o;
        if (!other.canEqual(this)) return false;
        Object this$kpjzsj = getKpjzsj(), other$kpjzsj = other.getKpjzsj();
        if ((this$kpjzsj == null) ? (other$kpjzsj != null) : !this$kpjzsj.equals(other$kpjzsj)) return false;
        Object this$urlQRCode = getUrlQRCode(), other$urlQRCode = other.getUrlQRCode();
        return !((this$urlQRCode == null) ? (other$urlQRCode != null) : !this$urlQRCode.equals(other$urlQRCode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InvoiceResultBO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $kpjzsj = getKpjzsj();
        result = result * 59 + (($kpjzsj == null) ? 43 : $kpjzsj.hashCode());
        Object $urlQRCode = getUrlQRCode();
        return result * 59 + (($urlQRCode == null) ? 43 : $urlQRCode.hashCode());
    }

    public String toString() {
        return "InvoiceResultBO(super=" + super.toString() + ", kpjzsj=" + getKpjzsj() + ", urlQRCode=" + getUrlQRCode() + ")";
    }


    public String getKpjzsj() {
        return this.kpjzsj;
    }


    public String getUrlQRCode() {
        return this.urlQRCode;
    }
}
