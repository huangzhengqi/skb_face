package com.fzy.admin.fp.pay.channel.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by wtl on 2019-04-25 16:15
 * @description 商户支付通道配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_pay_channel")
public class PayChannel extends BaseEntity {

    private String merchantId; // 商户id

    private Integer payWay; // 支付方式

    private Integer scanPayChannel; // 付款码支付渠道

    private Integer webPayChannel; // 网页支付渠道


}
