package com.fzy.admin.fp.sdk.merchant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商户后台首页支付通道Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataPayMentChannelVo {

    String payName = "";
    BigDecimal thisPayMoney = new BigDecimal(0); //这个时间段的支付数据
    BigDecimal lastPayMoney = new BigDecimal(0) ;//上个时间段的支付数据
}
