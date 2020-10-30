package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Created by wtl on 2019-06-12 11:26
 * @description 第三方支付设备SN码配置
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_sn_config")
public class SnConfig extends CompanyBaseEntity {

    private String sn; // 设备sn码

    private String merchantId; // 商户号，系统的商户id

    @Transient
    private String merchantName; //商户名称

    private Integer flag; // 是否已下载过一次，sdk中 SnConfigVO中枚举


}
