package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import com.fzy.admin.fp.pay.pay.domain.Deposit;


public class AlifreezeParam {
    @ApiModelProperty("商户id，用来支付模块查询支付配置参数")
    private String merchantId;
    @ApiModelProperty("商户id，用来支付模块查询支付配置参数")
    private String auth_code;
    @ApiModelProperty("授权码类型 目前仅支持 bar_code二维码 security_code刷脸")
    private String auth_code_type = "security_code";
    @ApiModelProperty("商户授权资金订单号，不能包含除中文、英文、数字以为的字符，创建后不能修改，需要保证在商户端不重复")
    private String out_order_no;
    @ApiModelProperty("商户本次资金操作的请求流水号，用于标识请求流水的唯一性，不能包含除中文、英文、数字以为的字符，需要保证在商户端不重复")
    private String out_request_no;
    @ApiModelProperty("业务订单的简单描述")
    private String order_title;
    @ApiModelProperty("需要冻结的金额，单位为：元（人民币），精确到小数点后两位")
    private String amount;
    @ApiModelProperty("收款方的支付宝唯一用户号")
    private String payee_user_id;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public void setAuth_code_type(String auth_code_type) {
        this.auth_code_type = auth_code_type;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPayee_user_id(String payee_user_id) {
        this.payee_user_id = payee_user_id;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AlifreezeParam)) return false;
        AlifreezeParam other = (AlifreezeParam) o;
        if (!other.canEqual(this)) return false;
        Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
        if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
            return false;
        Object this$auth_code = getAuth_code(), other$auth_code = other.getAuth_code();
        if ((this$auth_code == null) ? (other$auth_code != null) : !this$auth_code.equals(other$auth_code))
            return false;
        Object this$auth_code_type = getAuth_code_type(), other$auth_code_type = other.getAuth_code_type();
        if ((this$auth_code_type == null) ? (other$auth_code_type != null) : !this$auth_code_type.equals(other$auth_code_type))
            return false;
        Object this$out_order_no = getOut_order_no(), other$out_order_no = other.getOut_order_no();
        if ((this$out_order_no == null) ? (other$out_order_no != null) : !this$out_order_no.equals(other$out_order_no))
            return false;
        Object this$out_request_no = getOut_request_no(), other$out_request_no = other.getOut_request_no();
        if ((this$out_request_no == null) ? (other$out_request_no != null) : !this$out_request_no.equals(other$out_request_no))
            return false;
        Object this$order_title = getOrder_title(), other$order_title = other.getOrder_title();
        if ((this$order_title == null) ? (other$order_title != null) : !this$order_title.equals(other$order_title))
            return false;
        Object this$amount = getAmount(), other$amount = other.getAmount();
        if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;
        Object this$payee_user_id = getPayee_user_id(), other$payee_user_id = other.getPayee_user_id();
        if ((this$payee_user_id == null) ? (other$payee_user_id != null) : !this$payee_user_id.equals(other$payee_user_id))
            return false;
        Object this$product_code = getProduct_code(), other$product_code = other.getProduct_code();
        return !((this$product_code == null) ? (other$product_code != null) : !this$product_code.equals(other$product_code));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AlifreezeParam;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $merchantId = getMerchantId();
        result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
        Object $auth_code = getAuth_code();
        result = result * 59 + (($auth_code == null) ? 43 : $auth_code.hashCode());
        Object $auth_code_type = getAuth_code_type();
        result = result * 59 + (($auth_code_type == null) ? 43 : $auth_code_type.hashCode());
        Object $out_order_no = getOut_order_no();
        result = result * 59 + (($out_order_no == null) ? 43 : $out_order_no.hashCode());
        Object $out_request_no = getOut_request_no();
        result = result * 59 + (($out_request_no == null) ? 43 : $out_request_no.hashCode());
        Object $order_title = getOrder_title();
        result = result * 59 + (($order_title == null) ? 43 : $order_title.hashCode());
        Object $amount = getAmount();
        result = result * 59 + (($amount == null) ? 43 : $amount.hashCode());
        Object $payee_user_id = getPayee_user_id();
        result = result * 59 + (($payee_user_id == null) ? 43 : $payee_user_id.hashCode());
        Object $product_code = getProduct_code();
        return result * 59 + (($product_code == null) ? 43 : $product_code.hashCode());
    }

    public String toString() {
        return "AlifreezeParam(merchantId=" + getMerchantId() + ", auth_code=" + getAuth_code() + ", auth_code_type=" + getAuth_code_type() + ", out_order_no=" + getOut_order_no() + ", out_request_no=" + getOut_request_no() + ", order_title=" + getOrder_title() + ", amount=" + getAmount() + ", payee_user_id=" + getPayee_user_id() + ", product_code=" + getProduct_code() + ")";
    }


    public String getMerchantId() {
        return this.merchantId;
    }


    public String getAuth_code() {
        return this.auth_code;
    }


    public String getAuth_code_type() {
        return this.auth_code_type;
    }


    public String getOut_order_no() {
        return this.out_order_no;
    }


    public String getOut_request_no() {
        return this.out_request_no;
    }


    public String getOrder_title() {
        return this.order_title;
    }


    public String getAmount() {
        return this.amount;
    }


    public String getPayee_user_id() {
        return this.payee_user_id;
    }

    private String product_code = "PRE_AUTH";

    public String getProduct_code() {
        return this.product_code;
    }


}
