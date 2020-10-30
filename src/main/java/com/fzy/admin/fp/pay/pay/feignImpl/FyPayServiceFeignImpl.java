package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.FyPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.FyPayService;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.feign.FyPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-05-21 21:21
 * @description 富友支付feign实现
 */
@Service
public class FyPayServiceFeignImpl implements FyPayServiceFeign {

    @Resource
    private FyPayService fyPayService;


    @Override
    public PayRes fyScanPay(FyPayParam model) throws Exception {
        return fyPayService.micropay(model);
    }

    @Override
    public PayRes fyWapPay(FyPayParam model) throws Exception {
        return fyPayService.jsapiPay(model);
    }

    @Override
    public PayRes fyOrderQuery(FyPayParam model) {
        try {
            return fyPayService.query(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayRes fyRefund(FyPayParam model) throws Exception{
        return fyPayService.refund(model);
    }
}
