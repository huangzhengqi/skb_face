package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantVO {
    private String id;

    private String companyId;

    private String name;

    private BigDecimal payProrata;//分佣比例

    private Date createTime;//创建时间

    private String managerId;//业务员Id

    private String photoId;//

    private String serviceProviderId;

    private Integer delFlag;

    private Integer status;//状态

}
