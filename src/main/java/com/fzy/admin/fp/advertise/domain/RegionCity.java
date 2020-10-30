package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lb
 * @date 2019/7/2 15:53
 * @Description 策略关联城市
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_region_city")
public class RegionCity extends BaseEntity {

    private String province;//省名

    private String cityName;//城市名称

    private String strategicId;//策略Id
}
