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
 * @description 商户支付参数配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_wx_config")
public class WxConfig extends BaseEntity {

    private String merchantId; // 商户id

    private String subMchId; // 子商户id

    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal interestRate; // 微信利率


}
