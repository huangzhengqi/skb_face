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
 * @author Created by wtl on 2019-05-21 15:36
 * @description 易融码支付参数
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_yrm_config")
public class YrmConfig extends BaseEntity {


    private String merchantId; // 商户id

    // --------------易融码----------------
    private String mid;

    private String appKey;
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal aliInterestRate; // 支付宝利率
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wxInterestRate; // 微信利率
}
