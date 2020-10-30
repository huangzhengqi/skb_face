package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询终端设置")
public class FindTerminalRequest {
    public String toString() {
        return "FindTerminalRequest(taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ")";
    }

    @ApiModelProperty("纳税人识别号")
    @JacksonXmlProperty(localName = "nsrsbh")
    private String taxpayerIdentificationNum;

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
        return result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
    }

    protected boolean canEqual(Object other) {
        return other instanceof FindTerminalRequest;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FindTerminalRequest)) return false;
        FindTerminalRequest other = (FindTerminalRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
        return !((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum));
    }

    public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
        this.taxpayerIdentificationNum = taxpayerIdentificationNum;
    }


    public String getTaxpayerIdentificationNum() {
        return this.taxpayerIdentificationNum;
    }
}
