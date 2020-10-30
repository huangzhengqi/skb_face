package com.fzy.admin.fp.order.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单控制层
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order_commission")
public class Commission extends CompanyBaseEntity {

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "支付通道")
    private Integer payChannel;

    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    @ApiModelProperty(value = "支付通道利率")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal ChannelProrata;

    @ApiModelProperty(value = "订单金额")
    @Column(columnDefinition = "decimal(10,2) default 0")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "店铺抽佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal merchantProrata;

    @ApiModelProperty(value = "服务商利率")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "服务商抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal oemCommission;

    @ApiModelProperty(value = "一级代理商抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal firstCommission;

    @ApiModelProperty(value = "二级代理抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal secondCommission;

    @ApiModelProperty(value = "三级代理抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal thirdCommission;

    @ApiModelProperty(value = "服务商抽成比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal oemPayProrata;

    @ApiModelProperty(value = "一级代理抽成比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal firstPayProrata;

    @ApiModelProperty(value = "二级代理抽成比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal secondPayProrata;

    @ApiModelProperty(value = "三级代理抽成比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal thirdPayProrata;

    @ApiModelProperty(value = "服务商id")
    private String oemId;

    @ApiModelProperty(value = "一级代理id")
    private String firstId;

    @ApiModelProperty(value = "二级代理id")
    private String secondId;

    @ApiModelProperty(value = "三级代理id")
    private String thirdId;

    @ApiModelProperty(value = "店铺id")
    private String merchantId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "退款说明")
    private String remarks;


    /**
     * 分销模式-------------------------------------------------2020-1-11 16:24:38 yy
     */
    @ApiModelProperty(value = "0普通分佣 1分销分佣")
    private Integer type;

    @ApiModelProperty(value = "直接邀请抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal directCommission;

    @ApiModelProperty(value = "直接邀请id")
    private String directId;

    @ApiModelProperty(value = "运营中心提成抽成")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal operationCommission;

    @ApiModelProperty(value = "运营中心用户id")
    private String operationId;

    @ApiModelProperty(value = "一级抽佣抽成")
    private BigDecimal oneLevelCommission;

    @ApiModelProperty(value = "一级代理id")
    private String oneLevelId;

    @ApiModelProperty(value = "二级抽佣抽成")
    private BigDecimal twoLevelCommission;

    @ApiModelProperty(value = "二级代理id")
    private String twoLevelId;

    @ApiModelProperty(value = "三级抽佣抽成")
    private BigDecimal threeLevelCommission;

    @ApiModelProperty(value = "三级代理id")
    private String threeLevelId;

    /**
     * 返佣模式-------------------------------------------------2020-09-15 hzq
     */
    @ApiModelProperty("设置返佣金 1不设置 2设置")
    @Column(nullable=false,name="rebateType",columnDefinition="int default 1 COMMENT '设置返佣金 1设置 2设置'")
    private Integer rebateType;

    @ApiModelProperty("返佣的用户id")
    @Column(name="openId",columnDefinition="varchar(50) COMMENT '返佣的用户id'")
    private String openId;

    @ApiModelProperty(value = "商户返佣抽成")
    @Column(name="merchantCommission",columnDefinition="decimal(16,10) default 0  COMMENT '商户返佣抽成'")
    private BigDecimal merchantCommission;
}