package com.fzy.admin.fp.order.third.dto;

import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.*;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-03 17:20
 * @description 第三方支付接口请求参数
 */
@Data
public class ThirdPayDTO {

    @NotBlank(message = "商户ID为空")
    private String mid; // 商户id

    @Min(value = "0", message = "交易金额保留2位小数并且要大于0")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal totalFee; // 交易金额，元

    @NotBlank(message = "商户订单号为空")
    private String outTradeNo; // 商户订单号

    @NotBlank(message = "随机数为空")
    private String nonceStr; // 随机数

    @NotBlank(message = "签名为空")
    private String sign; // 签名

    @NotBlank(message = "授权码为空")
    private String authCode;

}
