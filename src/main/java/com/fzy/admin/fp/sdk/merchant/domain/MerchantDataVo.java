package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDataVo {

    private String storeName ; // 门店名称

    private Integer companyType; //一级代理商或二级代理商id

    @Transient
    private String companyName;//一级代理商或二级代理商名称

    private BigDecimal countPayPrice; // 门店总金额

}
