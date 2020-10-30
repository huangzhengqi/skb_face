package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.Data;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:14 2019/5/5
 * @ Description: 商户id,name
 **/
@Data
public class MerchantSelectItem {

    private String value; // 商户id

    private String name;//商户名称

    public MerchantSelectItem() {
    }

    public MerchantSelectItem(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
