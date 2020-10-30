package com.fzy.admin.fp.pay.channel.controller;


import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.pay.channel.domain.PayChannel;
import com.fzy.admin.fp.pay.channel.service.PayChannelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-04-26 16:50
 * @description 支付通道接口
 */
@RestController
@RequestMapping("/pay/pay_channel")
public class PayChannelController extends BaseController<PayChannel> {

    @Resource
    private PayChannelService payChannelService;

    @Override
    public PayChannelService getService() {
        return payChannelService;
    }

    /**
     * @author Created by wtl on 2019/4/28 21:35
     * @Description 根据商户id查询对应的支付配置
     */
    @GetMapping("/find_pay_channel")
    public Resp findPayChannel(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户id为空");
        }
        return Resp.success(payChannelService.findPayChannel(merchantId));
    }


    /**
     * @param payConfig  商户支付参数配置json字符串
     * @param merchantId 商户id
     * @author Created by wtl on 2019/4/26 16:54
     * @Description 支付配置，选择对应的支付通道
     */
    @PostMapping("/config_save")
    public Resp configSave(String merchantId, String payConfig) {

        return Resp.success(payChannelService.configSave(merchantId, payConfig));
    }

}
