package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lb
 * @date 2019/7/2 11:15
 * @Description 策略设置的时间
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_strategic_time")
public class StrategicTime extends BaseEntity {

    private String startTime;//起始时间

    private String endTime;//结束时间

    private String strategicId;//策略Id

}
