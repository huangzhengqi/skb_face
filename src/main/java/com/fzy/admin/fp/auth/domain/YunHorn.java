package com.fzy.admin.fp.auth.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:38 2019/7/1
 * @ Description: 云喇叭配置表
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_yun_horn")
public class YunHorn extends CompanyBaseEntity {

    private String hornToken;//云喇叭token
    private String hornSerial;//云喇叭序列号

}
