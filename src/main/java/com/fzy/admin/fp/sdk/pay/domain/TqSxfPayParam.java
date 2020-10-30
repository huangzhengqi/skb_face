package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @Author hzq
 * 天阙随行付支付参数
 */
@Data
public class TqSxfPayParam {

    @Getter
    public enum PayType {
        /**
         * 支付类型
         */
        WECHAT("WECHAT", "微信"),
        ALIPAY("ALIPAY", "支付宝"),
        JSAPI("JSAPI", "公众号支付"),
        FWC("FWC", "支付宝服务窗");
        private String code;

        private String status;

        PayType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("原支付订单号")
    private String outTradeNo;

    @ApiModelProperty("随行付订单号")
    private String transactionId;

    @ApiModelProperty("付款码")
    private String authCode;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("支付金额，分")
    private BigDecimal totalFee;

    @ApiModelProperty("退款金额，分")
    private BigDecimal refundAmount;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("子商户openId")
    private String subOpenid;

    @ApiModelProperty("子商户appId")
    private String subAppid;

    @ApiModelProperty("支付宝分期参数")
    private String hbFqNum;

    @ApiModelProperty("设备id")
    private String equipmentId;

    @ApiModelProperty("门店id")
    private String storeId;

}
