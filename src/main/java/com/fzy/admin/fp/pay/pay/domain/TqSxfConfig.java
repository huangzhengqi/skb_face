package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_tq_sxf_config")
public class TqSxfConfig extends BaseEntity {

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

    //========随行付参数===========

    /**
     * 商户编码
     */
    private String mno;

    @ApiModelProperty("子商户号")
    private String subMchId;

}
