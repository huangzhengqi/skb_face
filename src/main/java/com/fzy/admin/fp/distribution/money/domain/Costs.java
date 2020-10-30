package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author yy
 * @Date 2019-11-15 14:39:04
 */
@Entity
@Data
@Table(name = "lysj_dist_costs")
public class Costs extends CompanyBaseEntity {
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal price;//价格

    private Integer firstCommissions;//一级提成

    private Integer secondCommissions;//二级提成

    private String remark;//备注

    private Integer type;//1 普通代理 2VIP代理 3合伙人

    private String introduce;//介绍图片
}
