package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-31 9:56
 * @description 惠闪付支付通道配置
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_hsf_config")
public class HsfConfig extends BaseEntity {

    private String merchantId; // 商户id

    private String shopId; // 商户id
    private String appKey; // 签名密钥
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal aliInterestRate; // 支付宝利率
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wxInterestRate; // 微信利率


}
