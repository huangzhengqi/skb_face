package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;

public class AlifreezePayParam {
    @ApiModelProperty("商户id，用来支付模块查询支付配置参数")
    private String merchantId;
    @ApiModelProperty("商户订单号，需要保证不重复")
    private String out_trade_no;
    @ApiModelProperty("支付宝资金授权单号")
    private String auth_no;
    @ApiModelProperty("产品码，当面授权场景传固定值PRE_AUTH")
    private String product_code = "PRE_AUTH";
    @ApiModelProperty("订单标题")
    private String subject;
    @ApiModelProperty("买家支付宝uid,即资金授权用户uid，必传")
    private String buyer_id;
    @ApiModelProperty("卖家支付宝uid,即资金授权收款方uid，必传")
    private String seller_id;
    @ApiModelProperty("订单金额 单位元 0.01")
    private String total_amount;
    @ApiModelProperty("预授权转交易时结束预授权（并解冻剩余资金）时需要传入COMPLETE或NOT_COMPLETE；其中当传入COMPLETE时，执行转支付给收款方并剩余授权资金解除；当传入NOT_COMPLETE或不传时，仅执行转支付给收款方")
    private String auth_confirm_mode;


    public String getProduct_code() {
        return this.product_code;
    }

    public String getAuth_no() {
        return this.auth_no;
    }

    public String getSubject() {
        return this.subject;
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public void setAuth_confirm_mode(String auth_confirm_mode) {
        this.auth_confirm_mode = auth_confirm_mode;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AlifreezePayParam)) return false;
        AlifreezePayParam other = (AlifreezePayParam) o;
        if (!other.canEqual(this)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$out_trade_no = getOut_trade_no(), other$out_trade_no = other.getOut_trade_no();
        if ((this$out_trade_no == null) ? (other$out_trade_no != null) : !this$out_trade_no.equals(other$out_trade_no))
            return false;
        Object this$auth_no = getAuth_no(), other$auth_no = other.getAuth_no();
        if ((this$auth_no == null) ? (other$auth_no != null) : !this$auth_no.equals(other$auth_no)) return false;
        Object this$product_code = getProduct_code(), other$product_code = other.getProduct_code();
        if ((this$product_code == null) ? (other$product_code != null) : !this$product_code.equals(other$product_code))
            return false;
        Object this$subject = getSubject(), other$subject = other.getSubject();
        if ((this$subject == null) ? (other$subject != null) : !this$subject.equals(other$subject)) return false;
        Object this$buyer_id = getBuyer_id(), other$buyer_id = other.getBuyer_id();
        if ((this$buyer_id == null) ? (other$buyer_id != null) : !this$buyer_id.equals(other$buyer_id)) return false;
        Object this$seller_id = getSeller_id(), other$seller_id = other.getSeller_id();
        if ((this$seller_id == null) ? (other$seller_id != null) : !this$seller_id.equals(other$seller_id))
            return false;
        Object this$total_amount = getTotal_amount(), other$total_amount = other.getTotal_amount();
        if ((this$total_amount == null) ? (other$total_amount != null) : !this$total_amount.equals(other$total_amount))
            return false;
        Object this$auth_confirm_mode = getAuth_confirm_mode(), other$auth_confirm_mode = other.getAuth_confirm_mode();
        return !((this$auth_confirm_mode == null) ? (other$auth_confirm_mode != null) : !this$auth_confirm_mode.equals(other$auth_confirm_mode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AlifreezePayParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $out_trade_no = getOut_trade_no();
        result = result * 59 + (($out_trade_no == null) ? 43 : $out_trade_no.hashCode());
        Object $auth_no = getAuth_no();
        result = result * 59 + (($auth_no == null) ? 43 : $auth_no.hashCode());
        Object $product_code = getProduct_code();
        result = result * 59 + (($product_code == null) ? 43 : $product_code.hashCode());
        Object $subject = getSubject();
        result = result * 59 + (($subject == null) ? 43 : $subject.hashCode());
        Object $buyer_id = getBuyer_id();
        result = result * 59 + (($buyer_id == null) ? 43 : $buyer_id.hashCode());
        Object $seller_id = getSeller_id();
        result = result * 59 + (($seller_id == null) ? 43 : $seller_id.hashCode());
        Object $total_amount = getTotal_amount();
        result = result * 59 + (($total_amount == null) ? 43 : $total_amount.hashCode());
        Object $auth_confirm_mode = getAuth_confirm_mode();
        return result * 59 + (($auth_confirm_mode == null) ? 43 : $auth_confirm_mode.hashCode());
    }

    public String toString() {
        return "AlifreezePayParam(merchantId=" + getMerchantId() + ", out_trade_no=" + getOut_trade_no() + ", auth_no=" + getAuth_no() + ", product_code=" + getProduct_code() + ", subject=" + getSubject() + ", buyer_id=" + getBuyer_id() + ", seller_id=" + getSeller_id() + ", total_amount=" + getTotal_amount() + ", auth_confirm_mode=" + getAuth_confirm_mode() + ")";
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getOut_trade_no() {
        return this.out_trade_no;
    }


    public String getBuyer_id() {
        return this.buyer_id;
    }


    public String getSeller_id() {
        return this.seller_id;
    }


    public String getTotal_amount() {
        return this.total_amount;
    }


    public String getAuth_confirm_mode() {
        return this.auth_confirm_mode;
    }
}
