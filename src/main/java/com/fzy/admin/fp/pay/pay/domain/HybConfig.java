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
 * @author Created by wtl on 2019-04-23 22:05
 * @description 商户会员宝支付参数配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_hyb_config")
public class HybConfig extends BaseEntity {

    private String merchantId; // 商户id

    // --------------会员宝----------------
    private String appId;

    @Column(length = 2000)
    private String appKey;

    private String notifyUrl;

    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal aliInterestRate; // 支付宝利率

    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wxInterestRate; // 微信利率


}
