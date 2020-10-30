package com.fzy.admin.fp.order.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class UnFreezeVO {
    @ApiModelProperty("押金总额")
    BigDecimal allMoney;
    @ApiModelProperty("消费押金")
    BigDecimal usedMoney;
    @ApiModelProperty("退还押金")
    BigDecimal leftMoney;
    @ApiModelProperty("门店名称")
    String storeName;
    @ApiModelProperty("用户名称")
    String username;
    @ApiModelProperty("支付类型")
    Integer payWay;
    @ApiModelProperty("支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date payTime;
    @ApiModelProperty("消费单号")
    String orderNumber;
    @ApiModelProperty("消费金额")
    BigDecimal totalPrice;
    @ApiModelProperty("折扣价")
    BigDecimal disCountPrice;

    public void setAllMoney(BigDecimal allMoney) {
        this.allMoney = allMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public void setLeftMoney(BigDecimal leftMoney) {
        this.leftMoney = leftMoney;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDisCountPrice(BigDecimal disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UnFreezeVO)) return false;
        UnFreezeVO other = (UnFreezeVO) o;
        if (!other.canEqual(this)) return false;
        Object this$allMoney = getAllMoney(), other$allMoney = other.getAllMoney();
        if ((this$allMoney == null) ? (other$allMoney != null) : !this$allMoney.equals(other$allMoney)) return false;
        Object this$usedMoney = getUsedMoney(), other$usedMoney = other.getUsedMoney();
        if ((this$usedMoney == null) ? (other$usedMoney != null) : !this$usedMoney.equals(other$usedMoney))
            return false;
        Object this$leftMoney = getLeftMoney(), other$leftMoney = other.getLeftMoney();
        if ((this$leftMoney == null) ? (other$leftMoney != null) : !this$leftMoney.equals(other$leftMoney))
            return false;
        Object this$storeName = getStoreName(), other$storeName = other.getStoreName();
        if ((this$storeName == null) ? (other$storeName != null) : !this$storeName.equals(other$storeName))
            return false;
        Object this$username = getUsername(), other$username = other.getUsername();
        if ((this$username == null) ? (other$username != null) : !this$username.equals(other$username)) return false;
        Object this$payWay = getPayWay(), other$payWay = other.getPayWay();
        if ((this$payWay == null) ? (other$payWay != null) : !this$payWay.equals(other$payWay)) return false;
        Object this$payTime = getPayTime(), other$payTime = other.getPayTime();
        if ((this$payTime == null) ? (other$payTime != null) : !this$payTime.equals(other$payTime)) return false;
        Object this$orderNumber = getOrderNumber(), other$orderNumber = other.getOrderNumber();
        if ((this$orderNumber == null) ? (other$orderNumber != null) : !this$orderNumber.equals(other$orderNumber))
            return false;
        Object this$totalPrice = getTotalPrice(), other$totalPrice = other.getTotalPrice();
        if ((this$totalPrice == null) ? (other$totalPrice != null) : !this$totalPrice.equals(other$totalPrice))
            return false;
        Object this$disCountPrice = getDisCountPrice(), other$disCountPrice = other.getDisCountPrice();
        return !((this$disCountPrice == null) ? (other$disCountPrice != null) : !this$disCountPrice.equals(other$disCountPrice));
    }

    protected boolean canEqual(Object other) {
        return other instanceof UnFreezeVO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $allMoney = getAllMoney();
        result = result * 59 + (($allMoney == null) ? 43 : $allMoney.hashCode());
        Object $usedMoney = getUsedMoney();
        result = result * 59 + (($usedMoney == null) ? 43 : $usedMoney.hashCode());
        Object $leftMoney = getLeftMoney();
        result = result * 59 + (($leftMoney == null) ? 43 : $leftMoney.hashCode());
        Object $storeName = getStoreName();
        result = result * 59 + (($storeName == null) ? 43 : $storeName.hashCode());
        Object $username = getUsername();
        result = result * 59 + (($username == null) ? 43 : $username.hashCode());
        Object $payWay = getPayWay();
        result = result * 59 + (($payWay == null) ? 43 : $payWay.hashCode());
        Object $payTime = getPayTime();
        result = result * 59 + (($payTime == null) ? 43 : $payTime.hashCode());
        Object $orderNumber = getOrderNumber();
        result = result * 59 + (($orderNumber == null) ? 43 : $orderNumber.hashCode());
        Object $totalPrice = getTotalPrice();
        result = result * 59 + (($totalPrice == null) ? 43 : $totalPrice.hashCode());
        Object $disCountPrice = getDisCountPrice();
        return result * 59 + (($disCountPrice == null) ? 43 : $disCountPrice.hashCode());
    }

    public String toString() {
        return "UnFreezeVO(allMoney=" + getAllMoney() + ", usedMoney=" + getUsedMoney() + ", leftMoney=" + getLeftMoney() + ", storeName=" + getStoreName() + ", username=" + getUsername() + ", payWay=" + getPayWay() + ", payTime=" + getPayTime() + ", orderNumber=" + getOrderNumber() + ", totalPrice=" + getTotalPrice() + ", disCountPrice=" + getDisCountPrice() + ")";
    }


    public BigDecimal getAllMoney() {
        return this.allMoney;
    }

    public BigDecimal getUsedMoney() {
        return this.usedMoney;
    }

    public BigDecimal getLeftMoney() {
        return this.leftMoney;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getPayWay() {
        return this.payWay;
    }


    public Date getPayTime() {
        return this.payTime;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public BigDecimal getDisCountPrice() {
        return this.disCountPrice;
    }

}
