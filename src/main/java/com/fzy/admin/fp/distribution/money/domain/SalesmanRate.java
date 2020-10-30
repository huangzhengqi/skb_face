package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-11-18 11:08:08
 **/
@Entity
@Data
@Table(name = "lysj_dist_salesman_rate")
public class SalesmanRate extends CompanyBaseEntity{
    private Integer rate;
}
