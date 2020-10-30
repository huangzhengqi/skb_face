package com.fzy.admin.fp.sdk.pay.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-07 21:40
 * @description 支付通道和对应的利率
 */
@Data
public class PayChannelRate {

    /**
     * 直连和间连利率不一样
     */

    private Integer scanChannel; // 支付宝和微信条形码支付通道，1是官方，
    private Integer webChannel; // 支付宝和微信网页支付通道，1是官方，2是会员宝，3是易融码
    private BigDecimal interestRate; // 支付利率

    private String errMsg; // 错误信息，传递给客户端

}
