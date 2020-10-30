package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import jodd.util.StringUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class PayDepositDTO {
    @ApiModelProperty("订单编号")
    private String orderNumber;
    @ApiModelProperty("微信分配的公众账号ID")
    private String appid;
    @ApiModelProperty("微信用户openId")
    private String openId;
    @ApiModelProperty("微信分配的子商户公众账号ID")
    private String subAppid;
    @ApiModelProperty("微信支付分配的商户号")
    private String mchId;
    @ApiModelProperty("微信支付分配的子商户号，开发者模式下必填")
    private String subMchId;
    @ApiModelProperty("商品或支付单简要描述，格式要求：门店品牌名-城市分店名-实际商品名称")
    private String body;

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @ApiModelProperty("金额，单位 分")
    private Integer totalFee;
    @ApiModelProperty("人脸凭证，与付款码二选一")
    private String faceCode;
    @ApiModelProperty("付款码，与人脸凭证二选一")
    private String wxAuthCode;
    @ApiModelProperty("终端ip")
    private String spbillCreateIp;
    private String equipmentId;
    @ApiModelProperty("支付授权码")
    private String authCode;
    @ApiModelProperty("买家userId")
    private String buyerId;
    @ApiModelProperty("1微信 2支付宝")
    private Integer type;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public void setBody(String body) {
        this.body = body;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PayDepositDTO)) return false;
        PayDepositDTO other = (PayDepositDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$appid = getAppid(), other$appid = other.getAppid();
        if ((this$appid == null) ? (other$appid != null) : !this$appid.equals(other$appid)) return false;
        Object this$openId = getOpenId(), other$openId = other.getOpenId();
        if ((this$openId == null) ? (other$openId != null) : !this$openId.equals(other$openId)) return false;
        Object this$subAppid = getSubAppid(), other$subAppid = other.getSubAppid();
        if ((this$subAppid == null) ? (other$subAppid != null) : !this$subAppid.equals(other$subAppid)) return false;
        Object this$mchId = getMchId(), other$mchId = other.getMchId();
        if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;
        Object this$subMchId = getSubMchId(), other$subMchId = other.getSubMchId();
        if ((this$subMchId == null) ? (other$subMchId != null) : !this$subMchId.equals(other$subMchId)) return false;
        Object this$body = getBody(), other$body = other.getBody();
        if ((this$body == null) ? (other$body != null) : !this$body.equals(other$body)) return false;
        Object this$totalFee = getTotalFee(), other$totalFee = other.getTotalFee();
        if ((this$totalFee == null) ? (other$totalFee != null) : !this$totalFee.equals(other$totalFee)) return false;
        Object this$faceCode = getFaceCode(), other$faceCode = other.getFaceCode();
        if ((this$faceCode == null) ? (other$faceCode != null) : !this$faceCode.equals(other$faceCode)) return false;
        Object this$spbillCreateIp = getSpbillCreateIp(), other$spbillCreateIp = other.getSpbillCreateIp();
        if ((this$spbillCreateIp == null) ? (other$spbillCreateIp != null) : !this$spbillCreateIp.equals(other$spbillCreateIp))
            return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        if ((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId))
            return false;
        Object this$authCode = getAuthCode(), other$authCode = other.getAuthCode();
        if ((this$authCode == null) ? (other$authCode != null) : !this$authCode.equals(other$authCode)) return false;
        Object this$buyerId = getBuyerId(), other$buyerId = other.getBuyerId();
        if ((this$buyerId == null) ? (other$buyerId != null) : !this$buyerId.equals(other$buyerId)) return false;
        Object this$type = getType(), other$type = other.getType();
        return !((this$type == null) ? (other$type != null) : !this$type.equals(other$type));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PayDepositDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $appid = getAppid();
        result = result * 59 + (($appid == null) ? 43 : $appid.hashCode());
        Object $openId = getOpenId();
        result = result * 59 + (($openId == null) ? 43 : $openId.hashCode());
        Object $subAppid = getSubAppid();
        result = result * 59 + (($subAppid == null) ? 43 : $subAppid.hashCode());
        Object $mchId = getMchId();
        result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode());
        Object $subMchId = getSubMchId();
        result = result * 59 + (($subMchId == null) ? 43 : $subMchId.hashCode());
        Object $body = getBody();
        result = result * 59 + (($body == null) ? 43 : $body.hashCode());
        Object $totalFee = getTotalFee();
        result = result * 59 + (($totalFee == null) ? 43 : $totalFee.hashCode());
        Object $faceCode = getFaceCode();
        result = result * 59 + (($faceCode == null) ? 43 : $faceCode.hashCode());
        Object $spbillCreateIp = getSpbillCreateIp();
        result = result * 59 + (($spbillCreateIp == null) ? 43 : $spbillCreateIp.hashCode());
        Object $equipmentId = getEquipmentId();
        result = result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
        Object $authCode = getAuthCode();
        result = result * 59 + (($authCode == null) ? 43 : $authCode.hashCode());
        Object $buyerId = getBuyerId();
        result = result * 59 + (($buyerId == null) ? 43 : $buyerId.hashCode());
        Object $type = getType();
        return result * 59 + (($type == null) ? 43 : $type.hashCode());
    }

    public String toString() {
        return "PayDepositDTO(orderNumber=" + getOrderNumber() + ", appid=" + getAppid() + ", openId=" + getOpenId() + ", subAppid=" + getSubAppid() + ", mchId=" + getMchId() + ", subMchId=" + getSubMchId() + ", body=" + getBody() + ", totalFee=" + getTotalFee() + ", faceCode=" + getFaceCode() + ", spbillCreateIp=" + getSpbillCreateIp() + ", equipmentId=" + getEquipmentId() + ", authCode=" + getAuthCode() + ", buyerId=" + getBuyerId() + ", type=" + getType() + ")";
    }


    public String getOrderNumber() {
        return this.orderNumber;
    }


    public String getAppid() {
        return this.appid;
    }


    public String getOpenId() {
        return this.openId;
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


    public String getBody() {
        return this.body;
    }


    public Integer getTotalFee() {
        return this.totalFee;
    }


    public String getFaceCode() {
        return this.faceCode;
    }


    public String getSpbillCreateIp() {
        return this.spbillCreateIp;
    }


    public String getEquipmentId() {
        return this.equipmentId;
    }

    public Map<String, String> ininMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("deposit", "Y");
        map.put("openid", this.openId);
        map.put("appid", getAppid());
        if (!StringUtil.isEmpty(getSubAppid())) {
            map.put("sub_appid", getSubAppid());
        }
        map.put("mch_id", getMchId());
        map.put("sub_mch_id", getSubMchId());
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        map.put("body", getBody());
        map.put("total_fee", getTotalFee().toString());
        map.put("fee_type", "CNY");
        map.put("spbill_create_ip", getSpbillCreateIp());
        map.put("face_code", getFaceCode());
        map.put("sign_type", "HMAC-SHA256");

        return map;
    }

    public Map<String, String> ininOrderMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", getAppid());
        map.put("mch_id", getMchId());
        if (!StringUtil.isEmpty(getSubAppid())) {
            map.put("sub_appid", getSubAppid());
        }
        map.put("sub_mch_id", getSubMchId());
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        map.put("sign_type", "HMAC-SHA256");

        return map;
    }


    public String getAuthCode() {
        return this.authCode;
    }


    public String getBuyerId() {
        return this.buyerId;
    }


    public Integer getType() {
        return this.type;
    }
}
