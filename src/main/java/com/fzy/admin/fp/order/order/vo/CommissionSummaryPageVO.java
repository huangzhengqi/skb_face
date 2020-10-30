package com.fzy.admin.fp.order.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-04-29 20:15
 * @description
 */
@Data
public class CommissionSummaryPageVO {

    @ApiModelProperty(value = "公司名称")
    String companyId;

    @ApiModelProperty(value = "公司名称")
    String companyName;

    @ApiModelProperty(value = "佣金日期")
    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "结算日期")
    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    @ApiModelProperty(value = "凭证url")
    private String voucherUrl;

    @ApiModelProperty(value = "交易总额")
    BigDecimal orderTotal;
    @ApiModelProperty(value = "佣金总额")
    BigDecimal commissionTotal;

    @ApiModelProperty(value = "支付宝交易总额")
    BigDecimal zfbOrderTotal;
    @ApiModelProperty(value = "支付宝佣金总额")
    BigDecimal zfbCommissionTotal;

    @ApiModelProperty(value = "微信交易总额")
    BigDecimal wxOrderTotal;
    @ApiModelProperty(value = "微信佣金总额")
    BigDecimal wxCommissionTotal;

    @ApiModelProperty(value = "随行付交易总额")
    BigDecimal sxfOrderTotal;
    @ApiModelProperty(value = "随行付佣金总额")
    BigDecimal sxfCommissionTotal;


    @ApiModelProperty(value = "结算状态 1结算 0未结算")
    private Integer status;

    @ApiModelProperty(value = "支付方式")
    private Integer payWay;

    @ApiModelProperty(value = "支付渠道")
    private Integer payChannel;
    public CommissionSummaryPageVO(){

    }

    public CommissionSummaryPageVO(String companyId,String companyName,BigDecimal orderTotal,
                                   BigDecimal commissionTotal,Date createTime,Date settleTime,Integer status,
                                   String voucherUrl,Integer payChannel,Integer payWay){
        this.companyId = companyId;
        this.companyName = companyName;
        this.orderTotal = orderTotal;
        this.commissionTotal = commissionTotal;
        this.createTime = createTime;
        this.settleTime = settleTime;
        this.status = status;
        this.voucherUrl = voucherUrl;
        this.payChannel = payChannel;
        this.payWay = payWay;
    }
}
