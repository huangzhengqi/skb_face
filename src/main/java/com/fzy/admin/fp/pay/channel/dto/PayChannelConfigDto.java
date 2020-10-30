package com.fzy.admin.fp.pay.channel.dto;

import lombok.Data;

/**
 * @author Created by wtl on 2019-04-26 17:41
 * @description 商户支付通道配置json数据解析
 */
@Data
public class PayChannelConfigDto {

    private String wxPay; // 微信支付
    private String aliPay; // 支付宝支付


}
