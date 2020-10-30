package com.fzy.admin.fp.sdk.member.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-20 22:41
 * @description 会员卡支付参数
 */
@Data
public class MemberPayParam {

    private String merchantId; // 商户id
    private String storeId; // 门店id
    private String userName; // 收银员名称
    private String storeName; // 门店名称
    private String orderNumber; // 订单编号
    private Integer payWay; // 支付方式

    private String authCode; // 授权码、条形码
    private BigDecimal totalPrice = BigDecimal.ZERO; // 交易金额
    private BigDecimal disCountPrice = BigDecimal.ZERO;//优惠金额
    private BigDecimal actPayPrice = BigDecimal.ZERO;//实付金额

    private String memberId;// 会员id，H5页面支付会员用会员卡支付需要传此参数
    private String code; // 核销码，使用卡券后核销

}
