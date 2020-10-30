package com.fzy.admin.fp.order.third.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-04 0:35
 * @description 第三方支付接口返回
 */
@Data
public class ThirdPayVO {

    private BigDecimal totalFee; // 买家实际扣款金额
    private Integer status; // 订单状态，1：支付中；2：支付成功；3：支付失败
    private String nonceStr; // 随机数
    private String outTradeNo; // 商户订单号
    private String orderNumber; // 平台订单号
    private String sign; // 签名

}
