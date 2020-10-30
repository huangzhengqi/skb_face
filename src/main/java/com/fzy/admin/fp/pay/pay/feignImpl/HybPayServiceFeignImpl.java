package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HybPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.HybPayService;
import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HybPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-04-22 20:33
 * @description 会员宝支付feign接口实现
 */
@Service
public class HybPayServiceFeignImpl implements HybPayServiceFeign {

    @Resource
    private HybPayService hybPayService;

    @Override
    public String hybWapPay(HybPayParam model) {
        return hybPayService.wapPay(model);
    }

    @Override
    public PayRes hybRefund(HybPayParam model) {
        return hybPayService.refund(model);
    }

}
