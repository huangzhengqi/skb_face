package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HsfPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.HsfPayService;
import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HsfPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-06-17 22:42
 * @description 惠闪付feign接口实现
 */
@Service
public class HsfPayServiceFeignImpl implements HsfPayServiceFeign {

    @Resource
    private HsfPayService hsfPayService;

    @Override
    public PayRes hsfScanPay(CommonPayParam model) throws Exception {
        return hsfPayService.micropay(model);
    }

    @Override
    public PayRes hsfOrderQuery(CommonQueryParam model) {
        return hsfPayService.query(model);
    }

    @Override
    public PayRes hsfRefund(CommonRefundParam model) throws Exception {
        return hsfPayService.refund(model);
    }

    @Override
    public boolean hsfInit(String merchantId, String shop_id, String hsfKey) {
        return hsfPayService.hsfInit(merchantId, shop_id, hsfKey);
    }


}
