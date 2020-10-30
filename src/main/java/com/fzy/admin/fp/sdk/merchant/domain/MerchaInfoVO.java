package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchaInfoVO {

    /**
     * 证件持有人姓名
     */
    private String representativeName;
    /**
     * 银行账号
     */
    private String accountNumber;
    /**
     * 服务商id
     */
    private String serviceProviderId;
    /**
     * 商户名称
     */
    private String merchantName;


}
