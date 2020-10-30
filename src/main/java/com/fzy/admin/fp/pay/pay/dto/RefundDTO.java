package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;

public class RefundDTO {
    @ApiModelProperty("微信用户openId")
    private String openId;
    @ApiModelProperty("微信分配的公众账号ID")
    private String appId;
    @ApiModelProperty("微信分配的子商户公众账号ID")
    private String subAppid;
    @ApiModelProperty("微信支付分配的商户号")
    private String mchId;
    @ApiModelProperty("微信支付分配的子商户号")
    private String subMchId;
    @ApiModelProperty("已消费金额")
    private Integer usedFee;
    @ApiModelProperty("订单编号")
    private String orderNumber;
    @ApiModelProperty("设备id")
    private String equipmentId;

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
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
        if (!(o instanceof RefundDTO)) return false;
        RefundDTO other = (RefundDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$openId = getOpenId(), other$openId = other.getOpenId();
        if ((this$openId == null) ? (other$openId != null) : !this$openId.equals(other$openId)) return false;
        Object this$appId = getAppId(), other$appId = other.getAppId();
        if ((this$appId == null) ? (other$appId != null) : !this$appId.equals(other$appId)) return false;
        Object this$subAppid = getSubAppid(), other$subAppid = other.getSubAppid();
        if ((this$subAppid == null) ? (other$subAppid != null) : !this$subAppid.equals(other$subAppid)) return false;
        Object this$mchId = getMchId(), other$mchId = other.getMchId();
        if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;
        Object this$subMchId = getSubMchId(), other$subMchId = other.getSubMchId();
        if ((this$subMchId == null) ? (other$subMchId != null) : !this$subMchId.equals(other$subMchId)) return false;
        Object this$usedFee = getUsedFee(), other$usedFee = other.getUsedFee();
        if ((this$usedFee == null) ? (other$usedFee != null) : !this$usedFee.equals(other$usedFee)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        return !((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RefundDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $openId = getOpenId();
        result = result * 59 + (($openId == null) ? 43 : $openId.hashCode());
        Object $appId = getAppId();
        result = result * 59 + (($appId == null) ? 43 : $appId.hashCode());
        Object $subAppid = getSubAppid();
        result = result * 59 + (($subAppid == null) ? 43 : $subAppid.hashCode());
        Object $mchId = getMchId();
        result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode());
        Object $subMchId = getSubMchId();
        result = result * 59 + (($subMchId == null) ? 43 : $subMchId.hashCode());
        Object $usedFee = getUsedFee();
        result = result * 59 + (($usedFee == null) ? 43 : $usedFee.hashCode());
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $equipmentId = getEquipmentId();
        return result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
    }

    public String toString() {
        return "RefundDTO(openId=" + getOpenId() + ", appId=" + getAppId() + ", subAppid=" + getSubAppid() + ", mchId=" + getMchId() + ", subMchId=" + getSubMchId() + ", usedFee=" + getUsedFee() + ", orderNumber=" + getOrderNumber() + ", equipmentId=" + getEquipmentId() + ")";
    }


    public String getOpenId() {
        return this.openId;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getSubAppid() {
        return this.subAppid;
    }

    public String getMchId() {
        return this.mchId;
    }

    public String getSubMchId() {
        return this.subMchId;
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

