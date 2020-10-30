package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.sdk.pay.domain.*;
import com.fzy.admin.fp.sdk.pay.domain.AliPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;

/**
 * @author Created by wtl on 2019-04-23 23:31
 * @description 支付宝支付feign接口
 */
public interface AliPayServiceFeign {

    /**
     * 支付宝（B扫C接口）
     * @param model
     * @return
     * @throws Exception
     */
    PayRes aliScanPay(AliPayParam model) throws Exception;

    /**
     * 支付宝支付查询订单，扫码后客户端轮询用
     * @param merchantId
     * @param orderNumber
     * @return
     */
    PayRes aliOrderQuery(String merchantId, String orderNumber);

    /**
     * 支付宝支付撤销订单，订单错误或者超时调用
     * @param merchantId
     * @param orderNumber
     * @throws Exception
     */
    void aliReverse(String merchantId, String orderNumber) throws Exception;

    /**
     * 支付宝（C扫B接口）
     * @param model
     * @return
     * @throws Exception
     */
    PayRes aliWapPay(AliPayParam model) throws Exception;

    /**
     * 支付宝退款
     * @param model
     * @return
     * @throws Exception
     */
    PayRes aliRefund(AliPayParam model) throws Exception;

    /**
     * 支付宝解冻资金
     * @param paramAliUnFreezeParam
     * @return
     * @throws Exception
     */
    PayRes unfreeze(AliUnFreezeParam paramAliUnFreezeParam) throws Exception;

    /**
     * 支付宝冻结资金
     * @param paramAlifreezeParam
     * @return
     * @throws Exception
     */
    PayRes freeze(AlifreezeParam paramAlifreezeParam) throws Exception;

    /**
     * 支付宝押金消费
     * @param paramAlifreezePayParam
     * @return
     * @throws Exception
     */
    PayRes freezePay(AlifreezePayParam paramAlifreezePayParam) throws Exception;

    /**
     * 支付宝退款申请
     * @param paramAliRefundDepoistParam
     * @return
     * @throws Exception
     */
    PayRes refundDeposit(AliRefundDepoistParam paramAliRefundDepoistParam) throws Exception;





}
