package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-16 17:04:22
 **/
@Data
@Entity
@Table(name="lysj_dist_wallet")
public class Wallet extends CompanyBaseEntity {

    @ApiModelProperty(value = "余额")
    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal balance;

    @ApiModelProperty(value = "提成/奖金")
    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal bonus;

    @ApiModelProperty(value = "已提现")
    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal take;

    @ApiModelProperty(value = "用户id")
    private String userId;


}
