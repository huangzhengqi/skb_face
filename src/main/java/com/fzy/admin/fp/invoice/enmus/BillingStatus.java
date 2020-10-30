package com.fzy.admin.fp.invoice.enmus;

import io.swagger.annotations.ApiModel;

@ApiModel("开票")
public enum  BillingStatus {
    APPLY("申请中"),


    INVOICED("已开票");

    private String desc;
    public String getDesc() {
        return this.desc;
    }

    BillingStatus(String desc) {
        this.desc = desc;
    }
}
