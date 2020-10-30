package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;

public class AliRefundDepoistParam {
    @ApiModelProperty("商户id,用来支付模块查询支付配置参数")
    private String merchantId;
    @ApiModelProperty("支付时传入的商户订单号，与trade_no必填一个")
    private String out_trade_no;

    public void setMerchantId(String merchantId) { this.merchantId = merchantId; } @ApiModelProperty("本次退款请求流水号，部分退款时必传") private String out_request_no; @ApiModelProperty("本次退款金额") private String refund_amount; public void setOut_trade_no(String out_trade_no) { this.out_trade_no = out_trade_no; } public void setOut_request_no(String out_request_no) { this.out_request_no = out_request_no; } public void setRefund_amount(String refund_amount) { this.refund_amount = refund_amount; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof AliRefundDepoistParam)) return false;  AliRefundDepoistParam other = (AliRefundDepoistParam)o; if (!other.canEqual(this)) return false;  Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId(); if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId)) return false;  Object this$out_trade_no = getOut_trade_no(), other$out_trade_no = other.getOut_trade_no(); if ((this$out_trade_no == null) ? (other$out_trade_no != null) : !this$out_trade_no.equals(other$out_trade_no)) return false;  Object this$out_request_no = getOut_request_no(), other$out_request_no = other.getOut_request_no(); if ((this$out_request_no == null) ? (other$out_request_no != null) : !this$out_request_no.equals(other$out_request_no)) return false;  Object this$refund_amount = getRefund_amount(), other$refund_amount = other.getRefund_amount(); return !((this$refund_amount == null) ? (other$refund_amount != null) : !this$refund_amount.equals(other$refund_amount)); } protected boolean canEqual(Object other) { return other instanceof AliRefundDepoistParam; } public int hashCode() { int PRIME = 59; int result = 1; Object $merchantId = getMerchantId(); result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode()); Object $out_trade_no = getOut_trade_no(); result = result * 59 + (($out_trade_no == null) ? 43 : $out_trade_no.hashCode()); Object $out_request_no = getOut_request_no(); result = result * 59 + (($out_request_no == null) ? 43 : $out_request_no.hashCode()); Object $refund_amount = getRefund_amount(); return result * 59 + (($refund_amount == null) ? 43 : $refund_amount.hashCode()); } public String toString() { return "AliRefundDepoistParam(merchantId=" + getMerchantId() + ", out_trade_no=" + getOut_trade_no() + ", out_request_no=" + getOut_request_no() + ", refund_amount=" + getRefund_amount() + ")"; }



    public String getMerchantId() { return this.merchantId; }


    public String getOut_trade_no() { return this.out_trade_no; }



    public String getOut_request_no() { return this.out_request_no; }


    public String getRefund_amount() { return this.refund_amount; }


}
