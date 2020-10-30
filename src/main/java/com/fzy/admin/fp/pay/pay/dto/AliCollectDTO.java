package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;

public class AliCollectDTO {
    @ApiModelProperty(value = "商户id,用来支付模块查询支付配置参数", hidden = true)
    private String merchantId;
    @ApiModelProperty("支付授权码")
    private String authCode;
    @ApiModelProperty("买家userId")
    private String buyerId;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @ApiModelProperty("金额 单位，分")
    private Integer totalFee;
    @ApiModelProperty("订单编号")
    private String orderNumber;
    private String equipmentId;

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AliCollectDTO)) return false;
        AliCollectDTO other = (AliCollectDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$authCode = getAuthCode(), other$authCode = other.getAuthCode();
        if ((this$authCode == null) ? (other$authCode != null) : !this$authCode.equals(other$authCode)) return false;
        Object this$buyerId = getBuyerId(), other$buyerId = other.getBuyerId();
        if ((this$buyerId == null) ? (other$buyerId != null) : !this$buyerId.equals(other$buyerId)) return false;
        Object this$totalFee = getTotalFee(), other$totalFee = other.getTotalFee();
        if ((this$totalFee == null) ? (other$totalFee != null) : !this$totalFee.equals(other$totalFee)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        return !((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AliCollectDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $authCode = getAuthCode();
        result = result * 59 + (($authCode == null) ? 43 : $authCode.hashCode());
        Object $buyerId = getBuyerId();
        result = result * 59 + (($buyerId == null) ? 43 : $buyerId.hashCode());
        Object $totalFee = getTotalFee();
        result = result * 59 + (($totalFee == null) ? 43 : $totalFee.hashCode());
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $equipmentId = getEquipmentId();
        return result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
    }

    public String toString() {
        return "AliCollectDTO(merchantId=" + getMerchantId() + ", authCode=" + getAuthCode() + ", buyerId=" + getBuyerId() + ", totalFee=" + getTotalFee() + ", orderNumber=" + getOrderNumber() + ", equipmentId=" + getEquipmentId() + ")";
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getAuthCode() {
        return this.authCode;
    }


    public String getBuyerId() {
        return this.buyerId;
    }


    public Integer getTotalFee() {
        return this.totalFee;
    }


    public String getOrderNumber() {
        return this.orderNumber;
    }

    public String getEquipmentId() {
        return this.equipmentId;
    }
}

