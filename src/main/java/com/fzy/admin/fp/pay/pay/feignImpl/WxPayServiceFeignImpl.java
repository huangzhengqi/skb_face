package com.fzy.admin.fp.pay.pay.feignImpl;


import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.fzy.admin.fp.sdk.pay.feign.WxPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.WxPayService;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.fzy.admin.fp.sdk.pay.feign.WxPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-04-22 20:33
 * @description 微信支付feign接口实现
 */
@Service
public class WxPayServiceFeignImpl implements WxPayServiceFeign {

    @Resource
    private WxPayService wxPayService;

    @Override
    public PayRes wxJsPay(WxPayParam wxPayParam) throws Exception {
        return wxPayService.jsPay(wxPayParam);
    }

    @Override
    public PayRes wxScanPay(WxPayParam wxPayParam, Order order) throws Exception {
        return wxPayService.microPay(wxPayParam, order);
    }

    @Override
    public PayRes wxOrderQuery(String merchantId, Order order) {
        try {
            return wxPayService.orderQuery(merchantId, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayRes wxReverse(String merchantId, String orderNumber) throws Exception {
        return wxPayService.reverse(merchantId, orderNumber);
    }

    @Override
    public PayRes wxRefund(WxPayParam wxPayParam) throws Exception {
        return wxPayService.wxRefund(wxPayParam);
    }

    @Override
    public PayRes appletPay(WxPayParam wxPayParam) throws Exception {
        return wxPayService.appletPay(wxPayParam);
    }


}
