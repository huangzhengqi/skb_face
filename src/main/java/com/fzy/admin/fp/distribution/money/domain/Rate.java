package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-11-16 10:51:02
 **/
@Entity
@Data
@Table(name = "lysj_dist_rate")
public class Rate extends CompanyBaseEntity {

    private Integer firstFixedRate;//一级费率

    private Integer secondFixedRate;//二级费率

    private Integer pushPrice;//直推费率

    private String remark;//备注

    private Integer type;//1 普通代理 2VIP代理 3合伙人
}
