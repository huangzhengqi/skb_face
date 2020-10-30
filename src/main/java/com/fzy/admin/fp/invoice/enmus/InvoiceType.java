package com.fzy.admin.fp.invoice.enmus;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("抬头类型")
public enum InvoiceType
{
    ENTERPRISE_UNIT,

    ORGAN_UNIT,

    PERSONAL;
}
