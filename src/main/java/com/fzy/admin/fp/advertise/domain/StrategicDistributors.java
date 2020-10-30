package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lb
 * @date 2019/7/2 11:37
 * @Description 策略渠道商关联表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_strategic_distributors")
public class StrategicDistributors extends BaseEntity {

    private String operatorId;//运营商Id

    private String operatorName;//运营商名字

    private String distributorsId;//渠道商Id

    private String distributorsName;//渠道商名字

    private String strategicId;//策略Id

}
