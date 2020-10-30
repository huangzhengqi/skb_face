package com.fzy.admin.fp.order.third.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-04 15:20
 * @description 第三方订单查询返回
 */
@Data
public class ThirdQueryVO {

    private Integer status; // 订单状态，1：支付中；2：支付成功；3：失败

    private String nonceStr; // 随机数

    private String outTradeNo; // 商户订单号

    private String orderNumber; // 平台订单号

    private BigDecimal totalFee; // 交易金额，元

    private BigDecimal remainFee; // 剩余可退款金额


    private String mid; // 商户id

    private String sign; // 签名


}
