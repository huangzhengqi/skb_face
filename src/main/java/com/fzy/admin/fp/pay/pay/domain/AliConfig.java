package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-04-23 22:05
 * @description 商户支付宝支付参数配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_ali_config", uniqueConstraints = @UniqueConstraint(columnNames = ("merchantId")))
public class AliConfig extends BaseEntity {
    @ApiModelProperty("商户id")
    private String merchantId;
    @ApiModelProperty("商户pid")
    private String pid;
    @ApiModelProperty("支付宝第三方应用商户授权token，自用型应用不需要")
    private String appAuthToken;
    @ApiModelProperty("支付宝利率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal interestRate;


}
