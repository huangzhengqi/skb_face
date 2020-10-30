package com.fzy.admin.fp.invoice.feign.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FamilyBaseBO implements FamilyBaseResultBO {
    @ApiModelProperty(notes = "消息")
    @JacksonXmlProperty(localName = "returnmsg")
    protected String returnMessage;

    public FamilyBaseBO setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
        return this;
    }

    @ApiModelProperty(notes = "返回码")
    @JacksonXmlProperty(localName = "returncode")
    protected String returnCode;
}
