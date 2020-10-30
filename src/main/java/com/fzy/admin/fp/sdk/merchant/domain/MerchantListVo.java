package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 发展商户列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantListVo {

    private String merchant_id; //商户id

    private String storeName ; // 门店名称

    private Integer companyType; //一级代理商或二级代理商id

    @Transient
    private String managerName;//业务员名称

    @Transient
    private String companyName;//一级代理商或二级代理商名称

    private BigDecimal countPayPrice ; // 销售总金额

    private BigDecimal CommissionMoney; //佣金总额

    private int merchantNum = 0;  //商户总数

    private String companyId ; //部门id
}
