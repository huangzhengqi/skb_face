package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.Data;

/**
 * @author lb
 * @date 2019/5/29 16:41
 * @Description 返回门店信息的dto
 */
@Data
public class StoreInfo {
    private String name;
    private String phone;
    private String address;
    private String province;
    private String city;

    public StoreInfo(String name, String phone, String address,String province,String city) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.province = province;
        this.city = city;
    }
}
