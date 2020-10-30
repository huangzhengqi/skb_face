package com.fzy.admin.fp.pay.pay.feignImpl;


import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import com.fzy.admin.fp.sdk.pay.feign.TtsPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.TtsPayService;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import com.fzy.admin.fp.sdk.pay.feign.TtsPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-05-31 14:40
 * @description 统统收支付feign实现
 */
@Service
public class TtsPayServiceFeignImpl implements TtsPayServiceFeign {

    @Resource
    private TtsPayService ttsPayService;

    @Override
    public PayRes ttsScanPay(TtsPayParam model) {
        return ttsPayService.barCode(model);
    }

    @Override
    public PayRes ttsOrderQuery(TtsPayParam model) {
        return ttsPayService.query(model);
    }

    @Override
    public PayRes ttsJsPay(TtsPayParam model) {
        return ttsPayService.qrCode(model);
    }
}
