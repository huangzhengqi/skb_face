package com.fzy.admin.fp.order.order.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@Table(name = "order_commission_day")
public class CommissionDay extends CompanyBaseEntity {



    @ApiModelProperty(value = "交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "交易总额",orderNum = "3",isImportField = "true_st", type = 10,height = 20, width = 20)
    BigDecimal orderTotal;
    @ApiModelProperty(value = "佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "佣金总额",orderNum = "4",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal commissionTotal;

    @ApiModelProperty(value = "支付宝交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "支付宝交易总额",orderNum = "7",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal zfbOrderTotal;
    @ApiModelProperty(value = "支付宝佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "支付宝佣金总额",orderNum = "8",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal zfbCommissionTotal;

    @ApiModelProperty(value = "微信交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "微信交易总额",orderNum = "5",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal wxOrderTotal;
    @ApiModelProperty(value = "微信佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "微信佣金总额",orderNum = "6",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal wxCommissionTotal;

    @ApiModelProperty(value = "随行付交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "随行付交易总额",orderNum = "9",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal sxfOrderTotal;
    @ApiModelProperty(value = "随行付佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "随行付佣金总额",orderNum = "10",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal sxfCommissionTotal;

    @ApiModelProperty(value = "富有交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "富有交易总额",orderNum = "11",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal fyOrderTotal;
    @ApiModelProperty(value = "富有佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "富有佣金总额",orderNum = "12",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal fyCommissionTotal;

    @ApiModelProperty(value = "天阙随行付交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "随行付交易总额",orderNum = "9",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal tqSxfOrderTotal;
    @ApiModelProperty(value = "天阙随行付佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "随行付佣金总额",orderNum = "10",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal tqSxfCommissionTotal;


    @ApiModelProperty(value = "佣金日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "佣金生成时间",orderNum = "2",height = 20, width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "公司名称")
    @Excel(name = "代理名称",orderNum = "1",isImportField = "true_st", height = 20, width = 20)
    String companyName;

    @ApiModelProperty(value = "结算状态 1结算 0未结算")
    @Excel(name = "结算状态",orderNum = "13",replace = {"结算_1","未结算_0"},height = 20, width = 30)
    private Integer status;


    @ApiModelProperty(value = "结算日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结算时间",orderNum = "14",height = 20, width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    @ApiModelProperty(value = "凭证url")
    @Excel(name = "支付凭证",orderNum = "15",height = 20, width = 30)
    private String voucherUrl;

    @ApiModelProperty("分销分佣 0普通分佣 1分销分佣")
    @Column(columnDefinition = "int(1) default 0")
    private Integer type;
}