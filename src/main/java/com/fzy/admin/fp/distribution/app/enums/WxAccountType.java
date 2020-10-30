package com.fzy.admin.fp.distribution.app.enums;

import io.swagger.annotations.ApiModel;

@ApiModel("微信自动进件账户类型")
public enum WxAccountType {

    BANK_ACCOUNT_TYPE_CORPORATE("对公银行账户"),


    BANK_ACCOUNT_TYPE_PERSONAL("经营者个人银行卡");


    private final String desc;
    public String getDesc() {
        return this.desc;
    }


    WxAccountType(String desc) {

        this.desc = desc;
    }
}
