package com.fzy.admin.fp.invoice.enmus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发票状态")
public enum InvoiceStatus {

    POSITIVE_SUCCESS("00"),


    NEGATIVE_SUCCESS("01"),


    OBSOLETE_WAIT("02"),


    OBSOLETE_POSITIVE_SUCCESS("03"),


    OBSOLETE_NEGATIVE_SUCCESS("04");

    InvoiceStatus(String code) { this.code = code; }

    public String getCode() { return this.code; }


    public String toString() { return "InvoiceStatus(code=" + getCode() + ")"; }

    public static InvoiceStatus valueOfByCode(String code) {
        switch (code) {
            case "00":
                return POSITIVE_SUCCESS;
            case "01":
                return NEGATIVE_SUCCESS;
            case "02":
                return OBSOLETE_WAIT;
            case "03":
                return OBSOLETE_POSITIVE_SUCCESS;
            case "04":
                return OBSOLETE_NEGATIVE_SUCCESS;
        }
        return null;
    }

    @ApiModelProperty("code")
    private String code;
}
