package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@Table(name = "deposit")
public class Deposit extends BaseEntity {
    @ApiModelProperty("商户id")
    private String merchantId;
    @ApiModelProperty("门店id")
    private String storeId;
    @ApiModelProperty("收银员id")
    private String userId;
    @ApiModelProperty("1微信押金 2支付宝押金")
    private Integer type;
    @ApiModelProperty("微信/支付宝 用户id")
    private String officeUserId;
    @ApiModelProperty("微信/支付宝 官方订单编号")
    private String transactionId;
    @ApiModelProperty("支付宝authno")
    private String authNo;
    @ApiModelProperty("押金金额 单位分")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal money;
    @ApiModelProperty("剩余押金 单位分")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal leftMoney;
    @ApiModelProperty("押金状态 1冻结中 2冻结成功 3解冻失败 4已解冻")
    private Integer status;
    @ApiModelProperty("说明")
    private String remark;
    @ApiModelProperty("押金消费订单id")
    private String orderId;
    @ApiModelProperty("设备id")
    private String equipmentId;
    @ApiModelProperty("消费单号")
    private String orderNumber;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setOfficeUserId(String officeUserId) {
        this.officeUserId = officeUserId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setLeftMoney(BigDecimal leftMoney) { this.leftMoney = leftMoney; }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String toString() {
        return "Deposit(merchantId=" + getMerchantId() + ", storeId=" + getStoreId() + ", userId=" + getUserId() + ", type=" + getType() + ", officeUserId=" + getOfficeUserId() + ", transactionId=" + getTransactionId() + ", authNo=" + getAuthNo() + ", money=" + getMoney() + ", leftMoney=" + getLeftMoney() + ", status=" + getStatus() + ", remark=" + getRemark() + ", orderId=" + getOrderId() + ", equipmentId=" + getEquipmentId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit other = (Deposit) o;
        if (!other.canEqual(this)) return false;
        if (!super.equals(o)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$storeId = getStoreId(), other$storeId = other.getStoreId();
        if ((this$storeId == null) ? (other$storeId != null) : !this$storeId.equals(other$storeId)) return false;
        Object this$userId = getUserId(), other$userId = other.getUserId();
        if ((this$userId == null) ? (other$userId != null) : !this$userId.equals(other$userId)) return false;
        Object this$type = getType(), other$type = other.getType();
        if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;
        Object this$officeUserId = getOfficeUserId(), other$officeUserId = other.getOfficeUserId();
        if ((this$officeUserId == null) ? (other$officeUserId != null) : !this$officeUserId.equals(other$officeUserId))
            return false;
        Object this$transactionId = getTransactionId(), other$transactionId = other.getTransactionId();
        if ((this$transactionId == null) ? (other$transactionId != null) : !this$transactionId.equals(other$transactionId))
            return false;
        Object this$authNo = getAuthNo(), other$authNo = other.getAuthNo();
        if ((this$authNo == null) ? (other$authNo != null) : !this$authNo.equals(other$authNo)) return false;
        Object this$money = getMoney(), other$money = other.getMoney();
        if ((this$money == null) ? (other$money != null) : !this$money.equals(other$money)) return false;
        Object this$leftMoney = getLeftMoney(), other$leftMoney = other.getLeftMoney();
        if ((this$leftMoney == null) ? (other$leftMoney != null) : !this$leftMoney.equals(other$leftMoney))
            return false;
        Object this$status = getStatus(), other$status = other.getStatus();
        if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;
        Object this$remark = getRemark(), other$remark = other.getRemark();
        if ((this$remark == null) ? (other$remark != null) : !this$remark.equals(other$remark)) return false;
        Object this$orderId = getOrderId(), other$orderId = other.getOrderId();
        if ((this$orderId == null) ? (other$orderId != null) : !this$orderId.equals(other$orderId)) return false;
        Object this$equipmentId = getEquipmentId(), other$equipmentId = other.getEquipmentId();
        return !((this$equipmentId == null) ? (other$equipmentId != null) : !this$equipmentId.equals(other$equipmentId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Deposit;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $storeId = getStoreId();
        result = result * 59 + (($storeId == null) ? 43 : $storeId.hashCode());
        Object $userId = getUserId();
        result = result * 59 + (($userId == null) ? 43 : $userId.hashCode());
        Object $type = getType();
        result = result * 59 + (($type == null) ? 43 : $type.hashCode());
        Object $officeUserId = getOfficeUserId();
        result = result * 59 + (($officeUserId == null) ? 43 : $officeUserId.hashCode());
        Object $transactionId = getTransactionId();
        result = result * 59 + (($transactionId == null) ? 43 : $transactionId.hashCode());
        Object $authNo = getAuthNo();
        result = result * 59 + (($authNo == null) ? 43 : $authNo.hashCode());
        Object $money = getMoney();
        result = result * 59 + (($money == null) ? 43 : $money.hashCode());
        Object $leftMoney = getLeftMoney();
        result = result * 59 + (($leftMoney == null) ? 43 : $leftMoney.hashCode());
        Object $status = getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        Object $remark = getRemark();
        result = result * 59 + (($remark == null) ? 43 : $remark.hashCode());
        Object $orderId = getOrderId();
        result = result * 59 + (($orderId == null) ? 43 : $orderId.hashCode());
        Object $equipmentId = getEquipmentId();
        return result * 59 + (($equipmentId == null) ? 43 : $equipmentId.hashCode());
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getStoreId() {
        return this.storeId;
    }


    public String getUserId() {
        return this.userId;
    }


    public Integer getType() {
        return this.type;
    }


    public String getOfficeUserId() {
        return this.officeUserId;
    }


    public String getTransactionId() {
        return this.transactionId;
    }


    public String getAuthNo() {
        return this.authNo;
    }


    public BigDecimal getMoney() {
        return this.money;
    }


    public BigDecimal getLeftMoney() {
        return this.leftMoney;
    }


    public Integer getStatus() {
        return this.status;
    }


    public String getRemark() {
        return this.remark;
    }


    public String getOrderId() {
        return this.orderId;
    }


    public String getEquipmentId() {
        return this.equipmentId;
    }

    public enum STATUS implements EnumInterface {
        FREEZEING(Integer.valueOf(1), "冻结中"),
        FREEZED(Integer.valueOf(2), "冻结成功"),
        FREEZEFAIL(Integer.valueOf(3), "解冻失败"),
        UNFREEZE(Integer.valueOf(4), "已解冻");

        private Integer code;

        private String status;
        public Integer getCode() { return this.code; }

        public String getStatus() { return this.status; }

        STATUS(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    public enum TYPE implements EnumInterface {
        WX(Integer.valueOf(1), "微信"),
        ALI(Integer.valueOf(2), "支付宝");

        private Integer code; private String status;
        public Integer getCode() { return this.code; }

        public String getStatus() { return this.status; }

        TYPE(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


}
