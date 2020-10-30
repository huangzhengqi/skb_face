package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import jodd.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RefundDepositDTO {
    @ApiModelProperty("微信用户openId")
    private String openId;
    @ApiModelProperty("微信分配的公众账号ID")
    private String appId;
    @ApiModelProperty("微信分配的公众账号ID")
    private String subAppId;
    @ApiModelProperty("微信支付分配的商户号")
    private String mchId;
    @ApiModelProperty("微信支付分配的子商户号")
    private String subMchId;
    @ApiModelProperty("微信支付订单号")
    private String transactionId;
    @ApiModelProperty("商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@，同一退款单号多次请求只退一笔")
    private String outRefundNo;
    @ApiModelProperty("已消费金额")
    private Integer usedFee;
    @ApiModelProperty(value = "商户id，用来支付模块查询支付配置参数", hidden = true)
    private String merchantId;
    @ApiModelProperty("支付授权码")
    private String authCode;
    @ApiModelProperty("买家userId")
    private String buyerId;
    @ApiModelProperty("消费单号")
    private String orderNumber;
    @ApiModelProperty("设备id")
    private String equipmentId;
    @ApiModelProperty("类型")
    private Integer Type;

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public void setUsedFee(Integer usedFee) {
        this.usedFee = usedFee;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setType(Integer Type) {
        this.Type = Type;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof RefundDepositDTO)) return false;
        RefundDepositDTO other = (RefundDepositDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$openId = getOpenId(), other$openId = other.getOpenId();
        if ((this$openId == null) ? (other$openId != null) : !this$openId.equals(other$openId)) return false;
        Object this$appId = getAppId(), other$appId = other.getAppId();
        if ((this$appId == null) ? (other$appId != null) : !this$appId.equals(other$appId)) return false;
        Object this$subAppId = getSubAppId(), other$subAppId = other.getSubAppId();
        if ((this$subAppId == null) ? (other$subAppId != null) : !this$subAppId.equals(other$subAppId)) return false;
        Object this$mchId = getMchId(), other$mchId = other.getMchId();
        if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;
        Object this$subMchId = getSubMchId(), other$subMchId = other.getSubMchId();
        if ((this$subMchId == null) ? (other$subMchId != null) : !this$subMchId.equals(other$subMchId)) return false;
        Object this$transactionId = getTransactionId(), other$transactionId = other.getTransactionId();
        if ((this$transactionId == null) ? (other$transactionId != null) : !this$transactionId.equals(other$transactionId))
            return false;
        Object this$outRefundNo = getOutRefundNo(), other$outRefundNo = other.getOutRefundNo();
        if ((this$outRefundNo == null) ? (other$outRefundNo != null) : !this$outRefundNo.equals(other$outRefundNo))
            return false;
        Object this$usedFee = getUsedFee(), other$usedFee = other.getUsedFee();
        if ((this$usedFee == null) ? (other$usedFee != null) : !this$usedFee.equals(other$usedFee)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$authCode = getAuthCode(), other$authCode = other.getAuthCode();
        if ((this$authCode == null) ? (other$authCode != null) : !this$authCode.equals(other$authCode)) return false;
        Object this$buyerId = getBuyerId(), other$buyerId = other.getBuyerId();
        if ((this$buyerId == null) ? (other$buyerId != null) : !this$buyerId.equals(other$buyerId)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        if ((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId))
            return false;
        Object this$Type = getType(), other$Type = other.getType();
        return !((this$Type == null) ? (other$Type != null) : !this$Type.equals(other$Type));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RefundDepositDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $openId = getOpenId();
        result = result * 59 + (($openId == null) ? 43 : $openId.hashCode());
        Object $appId = getAppId();
        result = result * 59 + (($appId == null) ? 43 : $appId.hashCode());
        Object $subAppId = getSubAppId();
        result = result * 59 + (($subAppId == null) ? 43 : $subAppId.hashCode());
        Object $mchId = getMchId();
        result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode());
        Object $subMchId = getSubMchId();
        result = result * 59 + (($subMchId == null) ? 43 : $subMchId.hashCode());
        Object $transactionId = getTransactionId();
        result = result * 59 + (($transactionId == null) ? 43 : $transactionId.hashCode());
        Object $outRefundNo = getOutRefundNo();
        result = result * 59 + (($outRefundNo == null) ? 43 : $outRefundNo.hashCode());
        Object $usedFee = getUsedFee();
        result = result * 59 + (($usedFee == null) ? 43 : $usedFee.hashCode());
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $authCode = getAuthCode();
        result = result * 59 + (($authCode == null) ? 43 : $authCode.hashCode());
        Object $buyerId = getBuyerId();
        result = result * 59 + (($buyerId == null) ? 43 : $buyerId.hashCode());
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $equipmentId = getEquipmentId();
        result = result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
        Object $Type = getType();
        return result * 59 + (($Type == null) ? 43 : $Type.hashCode());
    }

    public String toString() {
        return "RefundDepositDTO(openId=" + getOpenId() + ", appId=" + getAppId() + ", subAppId=" + getSubAppId() + ", mchId=" + getMchId() + ", subMchId=" + getSubMchId() + ", transactionId=" + getTransactionId() + ", outRefundNo=" + getOutRefundNo() + ", usedFee=" + getUsedFee() + ", merchantId=" + getMerchantId() + ", authCode=" + getAuthCode() + ", buyerId=" + getBuyerId() + ", orderNumber=" + getOrderNumber() + ", equipmentId=" + getEquipmentId() + ", Type=" + getType() + ")";
    }


    public String getOpenId() {
        return this.openId;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getSubAppId() {
        return this.subAppId;
    }

    public String getMchId() {
        return this.mchId;
    }

    public String getSubMchId() {
        return this.subMchId;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public String getOutRefundNo() {
        return this.outRefundNo;
    }

    public Integer getUsedFee() {
        return this.usedFee;
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

    public String getOrderNumber() {
        return this.orderNumber;
    }


    public String getEquipmentId() {
        return this.equipmentId;
    }

    public Integer getType() {
        return this.Type;
    }


    public Map<String, String> ininMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", getAppId());
        if (!StringUtil.isEmpty(getSubAppId())) {
            map.put("sub_appid", getSubAppId());
        }
        map.put("mch_id", getMchId());
        map.put("sub_mch_id", getSubMchId());
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        map.put("fee_type", "CNY");
        map.put("sign_type", "HMAC-SHA256");

        return map;
    }


    public Map<String, String> ininRefundMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", getAppId());
        if (!StringUtil.isEmpty(getSubAppId())) {
            map.put("sub_appid", getSubAppId());
        }
        map.put("mch_id", getMchId());
        map.put("sub_mch_id", getSubMchId());
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        map.put("sign_type", "HMAC-SHA256");

        return map;
    }


}
