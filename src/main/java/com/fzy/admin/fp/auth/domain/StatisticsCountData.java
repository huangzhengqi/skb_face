package com.fzy.admin.fp.auth.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "lysj_statistics_count_data", indexes = {@Index(columnList = "companyId,saveDay", name = "联合索引1")})
public class StatisticsCountData extends BaseEntity {
    @ApiModelProperty("公司ID")
    private String companyId;
    @ApiModelProperty("计算每日时间")
    private String saveDay;
    @ApiModelProperty("订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal orderAmount;

    @ApiModelProperty("一级代理商")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal agentCommissionAmount;
    @ApiModelProperty("二级代理商")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal subAgentCommissionAmount;
    @ApiModelProperty("优惠金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal discountAmount = BigDecimal.ZERO;
    @ApiModelProperty("三级代理商")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal tertiaryCommissionAmount;

    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    @ApiModelProperty("支付时间")
    private String formatPayTime;
    @ApiModelProperty("支付方式")
    private Integer payWay;
    @ApiModelProperty("支付通道")
    private Integer payChannel;

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setSaveDay(String saveDay) {
        this.saveDay = saveDay;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = refundNum;
    }

    public void setValidDealAmount(BigDecimal validDealAmount) {
        this.validDealAmount = validDealAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public void setCustomerPaidAmount(BigDecimal customerPaidAmount) {
        this.customerPaidAmount = customerPaidAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setAgentCommissionAmount(BigDecimal agentCommissionAmount) {
        this.agentCommissionAmount = agentCommissionAmount;
    }

    public void setSubAgentCommissionAmount(BigDecimal subAgentCommissionAmount) {
        this.subAgentCommissionAmount = subAgentCommissionAmount;
    }

    public void setTertiaryCommissionAmount(BigDecimal tertiaryCommissionAmount) {
        this.tertiaryCommissionAmount = tertiaryCommissionAmount;
    }

    public void setFormatPayTime(String formatPayTime) {
        this.formatPayTime = formatPayTime;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public String toString() {
        return "StatisticsCountData(companyId=" + getCompanyId() + ", saveDay=" + getSaveDay() + ", orderAmount=" + getOrderAmount() + ", orderNum=" + getOrderNum() + ", refundAmount=" + getRefundAmount() + ", refundNum=" + getRefundNum() + ", validDealAmount=" + getValidDealAmount() + ", commissionAmount=" + getCommissionAmount() + ", customerPaidAmount=" + getCustomerPaidAmount() + ", discountAmount=" + getDiscountAmount() + ", agentCommissionAmount=" + getAgentCommissionAmount() + ", subAgentCommissionAmount=" + getSubAgentCommissionAmount() + ", tertiaryCommissionAmount=" + getTertiaryCommissionAmount() + ", formatPayTime=" + getFormatPayTime() + ", payWay=" + getPayWay() + ", payChannel=" + getPayChannel() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof StatisticsCountData)) return false;
        StatisticsCountData other = (StatisticsCountData) o;
        if (!other.canEqual(this)) return false;
        if (!super.equals(o)) return false;
        Object this$companyId = getCompanyId(), other$companyId = other.getCompanyId();
        if ((this$companyId == null) ? (other$companyId != null) : !this$companyId.equals(other$companyId))
            return false;
        Object this$saveDay = getSaveDay(), other$saveDay = other.getSaveDay();
        if ((this$saveDay == null) ? (other$saveDay != null) : !this$saveDay.equals(other$saveDay)) return false;
        Object this$orderAmount = getOrderAmount(), other$orderAmount = other.getOrderAmount();
        if ((this$orderAmount == null) ? (other$orderAmount != null) : !this$orderAmount.equals(other$orderAmount))
            return false;
        Object this$orderNum = getOrderNum(), other$orderNum = other.getOrderNum();
        if ((this$orderNum == null) ? (other$orderNum != null) : !this$orderNum.equals(other$orderNum)) return false;
        Object this$refundAmount = getRefundAmount(), other$refundAmount = other.getRefundAmount();
        if ((this$refundAmount == null) ? (other$refundAmount != null) : !this$refundAmount.equals(other$refundAmount))
            return false;
        Object this$refundNum = getRefundNum(), other$refundNum = other.getRefundNum();
        if ((this$refundNum == null) ? (other$refundNum != null) : !this$refundNum.equals(other$refundNum))
            return false;
        Object this$validDealAmount = getValidDealAmount(), other$validDealAmount = other.getValidDealAmount();
        if ((this$validDealAmount == null) ? (other$validDealAmount != null) : !this$validDealAmount.equals(other$validDealAmount))
            return false;
        Object this$commissionAmount = getCommissionAmount(), other$commissionAmount = other.getCommissionAmount();
        if ((this$commissionAmount == null) ? (other$commissionAmount != null) : !this$commissionAmount.equals(other$commissionAmount))
            return false;
        Object this$customerPaidAmount = getCustomerPaidAmount(), other$customerPaidAmount = other.getCustomerPaidAmount();
        if ((this$customerPaidAmount == null) ? (other$customerPaidAmount != null) : !this$customerPaidAmount.equals(other$customerPaidAmount))
            return false;
        Object this$discountAmount = getDiscountAmount(), other$discountAmount = other.getDiscountAmount();
        if ((this$discountAmount == null) ? (other$discountAmount != null) : !this$discountAmount.equals(other$discountAmount))
            return false;
        Object this$agentCommissionAmount = getAgentCommissionAmount(), other$agentCommissionAmount = other.getAgentCommissionAmount();
        if ((this$agentCommissionAmount == null) ? (other$agentCommissionAmount != null) : !this$agentCommissionAmount.equals(other$agentCommissionAmount))
            return false;
        Object this$subAgentCommissionAmount = getSubAgentCommissionAmount(), other$subAgentCommissionAmount = other.getSubAgentCommissionAmount();
        if ((this$subAgentCommissionAmount == null) ? (other$subAgentCommissionAmount != null) : !this$subAgentCommissionAmount.equals(other$subAgentCommissionAmount))
            return false;
        Object this$tertiaryCommissionAmount = getTertiaryCommissionAmount(), other$tertiaryCommissionAmount = other.getTertiaryCommissionAmount();
        if ((this$tertiaryCommissionAmount == null) ? (other$tertiaryCommissionAmount != null) : !this$tertiaryCommissionAmount.equals(other$tertiaryCommissionAmount))
            return false;
        Object this$formatPayTime = getFormatPayTime(), other$formatPayTime = other.getFormatPayTime();
        if ((this$formatPayTime == null) ? (other$formatPayTime != null) : !this$formatPayTime.equals(other$formatPayTime))
            return false;
        Object this$payWay = getPayWay(), other$payWay = other.getPayWay();
        if ((this$payWay == null) ? (other$payWay != null) : !this$payWay.equals(other$payWay)) return false;
        Object this$payChannel = getPayChannel(), other$payChannel = other.getPayChannel();
        return !((this$payChannel == null) ? (other$payChannel != null) : !this$payChannel.equals(other$payChannel));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StatisticsCountData;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $companyId = getCompanyId();
        result = result * 59 + (($companyId == null) ? 43 : $companyId.hashCode());
        Object $saveDay = getSaveDay();
        result = result * 59 + (($saveDay == null) ? 43 : $saveDay.hashCode());
        Object $orderAmount = getOrderAmount();
        result = result * 59 + (($orderAmount == null) ? 43 : $orderAmount.hashCode());
        Object $orderNum = getOrderNum();
        result = result * 59 + (($orderNum == null) ? 43 : $orderNum.hashCode());
        Object $refundAmount = getRefundAmount();
        result = result * 59 + (($refundAmount == null) ? 43 : $refundAmount.hashCode());
        Object $refundNum = getRefundNum();
        result = result * 59 + (($refundNum == null) ? 43 : $refundNum.hashCode());
        Object $validDealAmount = getValidDealAmount();
        result = result * 59 + (($validDealAmount == null) ? 43 : $validDealAmount.hashCode());
        Object $commissionAmount = getCommissionAmount();
        result = result * 59 + (($commissionAmount == null) ? 43 : $commissionAmount.hashCode());
        Object $customerPaidAmount = getCustomerPaidAmount();
        result = result * 59 + (($customerPaidAmount == null) ? 43 : $customerPaidAmount.hashCode());
        Object $discountAmount = getDiscountAmount();
        result = result * 59 + (($discountAmount == null) ? 43 : $discountAmount.hashCode());
        Object $agentCommissionAmount = getAgentCommissionAmount();
        result = result * 59 + (($agentCommissionAmount == null) ? 43 : $agentCommissionAmount.hashCode());
        Object $subAgentCommissionAmount = getSubAgentCommissionAmount();
        result = result * 59 + (($subAgentCommissionAmount == null) ? 43 : $subAgentCommissionAmount.hashCode());
        Object $tertiaryCommissionAmount = getTertiaryCommissionAmount();
        result = result * 59 + (($tertiaryCommissionAmount == null) ? 43 : $tertiaryCommissionAmount.hashCode());
        Object $formatPayTime = getFormatPayTime();
        result = result * 59 + (($formatPayTime == null) ? 43 : $formatPayTime.hashCode());
        Object $payWay = getPayWay();
        result = result * 59 + (($payWay == null) ? 43 : $payWay.hashCode());
        Object $payChannel = getPayChannel();
        return result * 59 + (($payChannel == null) ? 43 : $payChannel.hashCode());
    }


    public String getCompanyId() {
        return this.companyId;
    }


    public String getSaveDay() {
        return this.saveDay;
    }


    public BigDecimal getOrderAmount() {
        return this.orderAmount;
    }

    @ApiModelProperty("订单数")
    private Integer orderNum = Integer.valueOf(0);

    public Integer getOrderNum() {
        return this.orderNum;
    }

    @ApiModelProperty("退款金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal refundAmount = BigDecimal.ZERO;

    public BigDecimal getRefundAmount() {
        return this.refundAmount;
    }

    @ApiModelProperty("退款数")
    private Integer refundNum = Integer.valueOf(0);
    @ApiModelProperty("有效交易基数")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal validDealAmount;

    public Integer getRefundNum() {
        return this.refundNum;
    }


    public BigDecimal getValidDealAmount() {
        return this.validDealAmount;
    }

    @ApiModelProperty("佣金金额")
    @Column(columnDefinition = "decimal(16,10) default 0")
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    public BigDecimal getCommissionAmount() {
        return this.commissionAmount;
    }

    @ApiModelProperty("顾客实付")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal customerPaidAmount = BigDecimal.ZERO;

    public BigDecimal getCustomerPaidAmount() {
        return this.customerPaidAmount;
    }


    public BigDecimal getAgentCommissionAmount() {
        return this.agentCommissionAmount;
    }


    public BigDecimal getSubAgentCommissionAmount() {
        return this.subAgentCommissionAmount;
    }


    public BigDecimal getTertiaryCommissionAmount() {
        return this.tertiaryCommissionAmount;
    }


    public String getFormatPayTime() {
        return this.formatPayTime;
    }


    public Integer getPayWay() {
        return this.payWay;
    }


    public Integer getPayChannel() {
        return this.payChannel;
    }
}
