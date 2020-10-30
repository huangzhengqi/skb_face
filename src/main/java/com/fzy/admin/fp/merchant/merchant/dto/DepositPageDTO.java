package com.fzy.admin.fp.merchant.merchant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;

public class DepositPageDTO {
    @ApiModelProperty("开始时间")
    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    @ApiModelProperty("结束时间")
    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @ApiModelProperty("押金状态 1冻结中 2冻结成功 3解冻失败 4已解冻")
    private Integer status;
    @ApiModelProperty(value = "商户id", hidden = true)
    private String merchantId;

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DepositPageDTO)) return false;
        DepositPageDTO other = (DepositPageDTO) o;
        if (!other.canEqual(this)) return false;
        Object this$beginTime = getBeginTime(), other$beginTime = other.getBeginTime();
        if ((this$beginTime == null) ? (other$beginTime != null) : !this$beginTime.equals(other$beginTime))
            return false;
        Object this$endTime = getEndTime(), other$endTime = other.getEndTime();
        if ((this$endTime == null) ? (other$endTime != null) : !this$endTime.equals(other$endTime)) return false;
        Object this$status = getStatus(), other$status = other.getStatus();
        if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        return !((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DepositPageDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $beginTime = getBeginTime();
        result = result * 59 + (($beginTime == null) ? 43 : $beginTime.hashCode());
        Object $endTime = getEndTime();
        result = result * 59 + (($endTime == null) ? 43 : $endTime.hashCode());
        Object $status = getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        Object $merchantId = getMerchantId();
        return result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
    }

    public String toString() {
        return "DepositPageDTO(beginTime=" + getBeginTime() + ", endTime=" + getEndTime() + ", status=" + getStatus() + ", merchantId=" + getMerchantId() + ")";
    }


    public Date getBeginTime() {
        return this.beginTime;
    }


    public Date getEndTime() {
        return this.endTime;
    }


    public Integer getStatus() {
        return this.status;
    }


    public String getMerchantId() {
        return this.merchantId;
    }

}
