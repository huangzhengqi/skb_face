package com.fzy.admin.fp.invoice.feign.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("开票通知响应")
public class FamilyNoticeResponseBO extends FamilyBaseBO {
    public String toString() {
        return "FamilyNoticeResponseBO(invoiceSn=" + getInvoiceSn() + ")";
    }

    @ApiModelProperty(notes = "发票请求流水号", required = true)
    @JacksonXmlProperty(localName = "fpqqlsh")
    private String invoiceSn;
}
