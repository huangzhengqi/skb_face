package com.fzy.admin.fp.distribution.order.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-16 13:56:00
 * @DESP 申请分销订单
 **/
@Data
@Entity
@Table(name="lysj_dist_order")
public class DistOrder extends CompanyBaseEntity{
    private String orderNumber;//订单号

    @Column(name = "user_id")
    private String userId;//用户id

    @Column(name="type",columnDefinition = "int(1)")
    private Integer type;//1普通代理 2VIP代理 3合伙人

    private BigDecimal price;//价格

    @Column(columnDefinition = "int(1) default 0")
    private Integer status;//状态 0未支付 1已支付 2异常

    private Integer firstCommissions;//一级提成

    private Integer secondCommissions;//二级提成

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable=true)
    private DistUser distUser;

    private String record;//异常纪录
}
