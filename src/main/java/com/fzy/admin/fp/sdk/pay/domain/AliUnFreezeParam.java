package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;

public class AliUnFreezeParam {
    @ApiModelProperty("商户id，用来支付模块查询支付配置参数")
    private String merchantId;
    @ApiModelProperty("支付宝资金授权订单号")
    private String auth_no;
    @ApiModelProperty("商户本次资金操作的请求流水号，同一商户每次不同的资金操作请求，商户请求流水号不能重复")
    private String out_request_no;
    @ApiModelProperty("金额")
    private String amount;
    @ApiModelProperty("解冻说明")
    private String remark;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AliUnFreezeParam)) return false;
        AliUnFreezeParam other = (AliUnFreezeParam) o;
        if (!other.canEqual(this)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$auth_no = getAuth_no(), other$auth_no = other.getAuth_no();
        if ((this$auth_no == null) ? (other$auth_no != null) : !this$auth_no.equals(other$auth_no)) return false;
        Object this$out_request_no = getOut_request_no(), other$out_request_no = other.getOut_request_no();
        if ((this$out_request_no == null) ? (other$out_request_no != null) : !this$out_request_no.equals(other$out_request_no))
            return false;
        Object this$amount = getAmount(), other$amount = other.getAmount();
        if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;
        Object this$remark = getRemark(), other$remark = other.getRemark();
        return !((this$remark == null) ? (other$remark != null) : !this$remark.equals(other$remark));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AliUnFreezeParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $auth_no = getAuth_no();
        result = result * 59 + (($auth_no == null) ? 43 : $auth_no.hashCode());
        Object $out_request_no = getOut_request_no();
        result = result * 59 + (($out_request_no == null) ? 43 : $out_request_no.hashCode());
        Object $amount = getAmount();
        result = result * 59 + (($amount == null) ? 43 : $amount.hashCode());
        Object $remark = getRemark();
        return result * 59 + (($remark == null) ? 43 : $remark.hashCode());
    }

    public String toString() {
        return "AliUnFreezeParam(merchantId=" + getMerchantId() + ", auth_no=" + getAuth_no() + ", out_request_no=" + getOut_request_no() + ", amount=" + getAmount() + ", remark=" + getRemark() + ")";
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getAuth_no() {
        return this.auth_no;
    }


    public String getOut_request_no() {
        return this.out_request_no;
    }


    public String getAmount() {
        return this.amount;
    }


    public String getRemark() {
        return this.remark;
    }


}
