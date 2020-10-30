package com.fzy.admin.fp.order.third.dto;

import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.*;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-10 15:55
 * @description 第三方开放接口退款DTO
 */
@Data
public class ThirdRefundDTO {

    @NotBlank(message = "密码为空")
    private String password; // 商户登录密码
    @NotBlank(message = "商户号为空")
    private String mid; // 商户号
    @NotBlank(message = "订单号为空")
    private String outTradeNo; // 商户订单号
    @Min(value = "0", message = "退款金额保留2位小数并且要大于0")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal refundPrice = BigDecimal.ZERO;// 退款金额，元
    @NotBlank(message = "随机数为空")
    private String nonceStr; // 随机数
    @NotBlank(message = "签名为空")
    private String sign; // 签名

}
