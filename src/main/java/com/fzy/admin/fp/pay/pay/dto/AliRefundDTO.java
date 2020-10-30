package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;

public class AliRefundDTO {
    @ApiModelProperty(value = "商户id,用来支付模块查询支付配置参数", hidden = true)
    private String merchantId;
    @ApiModelProperty("买家userId")
    private String buyerId;
    @ApiModelProperty("消费金额，单位 分")
    private Integer usedFee;
    @ApiModelProperty("订单编号")
    private String orderNumber;
    @ApiModelProperty("设备id")
    private String equipmentId;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setUsedFee(Integer usedFee) {
        this.usedFee = usedFee;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AliRefundDTO)) return false;
        AliRefundDTO other = (AliRefundDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$buyerId = getBuyerId(), other$buyerId = other.getBuyerId();
        if ((this$buyerId == null) ? (other$buyerId != null) : !this$buyerId.equals(other$buyerId)) return false;
        Object this$usedFee = getUsedFee(), other$usedFee = other.getUsedFee();
        if ((this$usedFee == null) ? (other$usedFee != null) : !this$usedFee.equals(other$usedFee)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        return !((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AliRefundDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $buyerId = getBuyerId();
        result = result * 59 + (($buyerId == null) ? 43 : $buyerId.hashCode());
        Object $usedFee = getUsedFee();
        result = result * 59 + (($usedFee == null) ? 43 : $usedFee.hashCode());
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $equipmentId = getEquipmentId();
        return result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
    }

    public String toString() {
        return "AliRefundDTO(merchantId=" + getMerchantId() + ", buyerId=" + getBuyerId() + ", usedFee=" + getUsedFee() + ", orderNumber=" + getOrderNumber() + ", equipmentId=" + getEquipmentId() + ")";
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getBuyerId() {
        return this.buyerId;
    }


    public Integer getUsedFee() {
        return this.usedFee;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }


    public String getEquipmentId() {
        return this.equipmentId;
    }
}
