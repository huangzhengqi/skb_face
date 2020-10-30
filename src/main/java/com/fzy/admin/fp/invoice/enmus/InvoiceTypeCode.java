package com.fzy.admin.fp.invoice.enmus;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发票类型代码")
public  enum InvoiceTypeCode
{
    VAT_SPECIAL("004"),


    VAT_ORDINARY("007"),


    ROLL("025"),


    ELECTRONIC("026");

    InvoiceTypeCode(String code) { this.code = code; }

    public String getCode() { return this.code; }


    public String toString() { return "InvoiceTypeCode(code=" + getCode() + ")"; }

    public static InvoiceTypeCode valueOfByCode(String code) {
        switch (code) {
            case "004":
                return VAT_SPECIAL;
            case "007":
                return VAT_ORDINARY;
            case "025":
                return ROLL;
            case "026":
                return ELECTRONIC;
        }
        return null;
    }

    @ApiModelProperty("code")
    private String code;
}
