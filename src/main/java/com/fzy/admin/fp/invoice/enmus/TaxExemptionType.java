package com.fzy.admin.fp.invoice.enmus;

import io.swagger.annotations.ApiModel;

@ApiModel("免税类型")
public enum TaxExemptionType
{
    EXPORT(
            Integer.valueOf(1)),

    NONE(
            Integer.valueOf(2)),

    ZERO(
            Integer.valueOf(3));

    public Integer getCode() { return this.code; }

    TaxExemptionType(Integer code) { this.code = code; }

    public String toString() { return "TaxExemptionType(code=" + getCode() + ")"; }

    private Integer code;
}
