package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lb
 * @date 2019/7/4 9:38
 * @Description 消费信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_cost_money")
public class CostMoney extends BaseEntity {

    private Integer showNumber;//当天展示数

    private Integer clickNumber;//当天点击量

    private Integer runNumber;//当天跳转量

    private String day;//当前时间

    private String onId;//投放Id

}
