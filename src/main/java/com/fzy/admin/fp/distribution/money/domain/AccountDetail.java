package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-16 17:35:28
 **/
@Data
@Entity
@Table(name = "lysj_dist_account_detail")
public class AccountDetail extends CompanyBaseEntity{

    @ApiModelProperty("金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal sum;

    @ApiModelProperty("余额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal balance;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("0 提成 1消费 2申请提现 3提现驳回")
    private Integer type;

    @ApiModelProperty("0入账 1提现")
    private Integer status;

}
