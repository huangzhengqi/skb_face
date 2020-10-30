package com.fzy.admin.fp.pay.pay.feignImpl;

import com.alipay.api.AlipayApiException;
import com.fzy.admin.fp.sdk.pay.domain.*;
import com.fzy.admin.fp.sdk.pay.feign.AliPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.AliPayService;
import com.fzy.admin.fp.sdk.pay.domain.AliPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.AliPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-04-22 20:33
 * @description 支付宝支付feign接口实现
 */
@Service
public class AliPayServiceFeignImpl implements AliPayServiceFeign {

    @Resource
    private AliPayService aliPayService;


    @Override
    public PayRes aliScanPay(AliPayParam model) throws Exception {
        return aliPayService.tradePay(model);
    }

    @Override
    public PayRes aliOrderQuery(String merchantId, String orderNumber) {
        try {
            return aliPayService.tradeQuery(merchantId, orderNumber);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void aliReverse(String merchantId, String orderNumber) throws Exception {
        aliPayService.tradeCancel(merchantId, orderNumber);
    }

    @Override
    public PayRes aliWapPay(AliPayParam model) throws Exception {
        return aliPayService.preCreate(model);
    }

    @Override
    public PayRes aliRefund(AliPayParam model) throws Exception {
        return aliPayService.refund(model);
    }

    @Override
    public PayRes unfreeze(AliUnFreezeParam paramAliUnFreezeParam) throws Exception {
        return this.aliPayService.unfreeze(paramAliUnFreezeParam);
    }

    @Override
    public PayRes freeze(AlifreezeParam paramAlifreezeParam) throws Exception {
        return this.aliPayService.freeze(paramAlifreezeParam);
    }

    @Override
    public PayRes freezePay(AlifreezePayParam paramAlifreezePayParam) throws Exception {
        return this.aliPayService.freezePay(paramAlifreezePayParam);
    }

    @Override
    public PayRes refundDeposit(AliRefundDepoistParam paramAliRefundDepoistParam) throws Exception {
        return this.aliPayService.refundDeposit(paramAliRefundDepoistParam);
    }
}
