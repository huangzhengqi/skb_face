package com.fzy.admin.fp.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-11-27 13:52:12
 * @Desp
 **/
@Data
@Entity
@Table(name = "level_foot_info")
public class FootInfo extends CompanyBaseEntity{
    private String phone;
}
