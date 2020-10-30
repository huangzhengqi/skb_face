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
 * @author Created by wsj on 2019-07-20 14:05
 * @description 商户富友支付参数配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_fy_config")
public class FyConfig extends BaseEntity {
    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 支付宝利率
     */
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal aliInterestRate;
    /**
     * 微信利率
     */
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wxInterestRate;

    //=======通道参数===========

    /**
     * 商户号
     */
    private String mchntCd;

}
