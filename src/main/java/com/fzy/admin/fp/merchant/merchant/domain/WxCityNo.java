package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2020-2-18 11:12:31
 * @Desp
 **/
@Data
@Entity
@Table(name = "wx_city_no")
public class WxCityNo extends BaseEntity{
    private String no;

    private String city;
}
