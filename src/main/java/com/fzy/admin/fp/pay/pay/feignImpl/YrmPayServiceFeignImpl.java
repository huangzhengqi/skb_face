package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import com.fzy.admin.fp.sdk.pay.feign.YrmPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.YrmPayService;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import com.fzy.admin.fp.sdk.pay.feign.YrmPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-05-21 21:21
 * @description 易融码支付feign实现
 */
@Service
public class YrmPayServiceFeignImpl implements YrmPayServiceFeign {

    @Resource
    private YrmPayService yrmPayService;

    @Override
    public PayRes yrmScanPay(YrmPayParam model) throws Exception {
        return yrmPayService.microPay(model);
    }

    @Override
    public PayRes yrmOrderQuery(String merchantId, String orderNumber) {
        try {
            return yrmPayService.query(merchantId, orderNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayRes yrmJsPay(YrmPayParam model) throws Exception {
        return yrmPayService.jsapiPay(model);
    }

    @Override
    public PayRes yrmRefund(YrmPayParam model) throws Exception {
        return yrmPayService.refund(model);
    }
}
