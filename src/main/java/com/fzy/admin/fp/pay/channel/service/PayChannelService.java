package com.fzy.admin.fp.pay.channel.service;

import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.channel.repository.PayChannelRepository;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.channel.domain.PayChannel;
import com.fzy.admin.fp.pay.channel.repository.PayChannelRepository;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-26 16:45
 * @description 支付通道业务
 */
@Service
@Transactional
public class PayChannelService implements BaseService<PayChannel> {

    @Resource
    private PayChannelRepository payChannelRepository;

    @Override
    public PayChannelRepository getRepository() {
        return payChannelRepository;
    }

    /**
     * @author Created by wtl on 2019/4/28 21:35
     * @Description 根据商户id查询对应的支付配置
     */
    public List<PayChannel> findPayChannel(String merchantId) {
        return getRepository().findByMerchantId(merchantId);
    }


    /**
     * @param payConfig  商户支付参数配置json字符串
     * @param merchantId 商户id
     * @author Created by wtl on 2019/4/26 16:54
     * @Description 支付配置，选择对应的支付通道
     */
    public String configSave(String merchantId, String payConfig) {
        // 先删除原有的配置
        List<PayChannel> payChannels = getRepository().findByMerchantId(merchantId);
        getRepository().delete(payChannels);

        Map map = JacksonUtil.toStringMap(payConfig);
        // 微信支付配置
        Map wxMap = ((Map) map.get("wx"));
        // 支付宝支付配置
        Map aliMap = ((Map) map.get("ali"));

        // 保存微信支付配置
        PayChannel wxPayChannel = new PayChannel();
        wxPayChannel.setMerchantId(merchantId);
        wxPayChannel.setPayWay(PayChannelConstant.PayWay.WXPAY.getCode());
        wxPayChannel.setScanPayChannel(0);
        wxPayChannel.setWebPayChannel(0);
        if (!ParamUtil.isBlank((String) wxMap.get("scanPay"))) {
            wxPayChannel.setScanPayChannel(Integer.parseInt((String) wxMap.get("scanPay")));
        }
        if (!ParamUtil.isBlank((String) wxMap.get("wapPay"))) {
            wxPayChannel.setWebPayChannel(Integer.parseInt((String) wxMap.get("wapPay")));
        }
        save(wxPayChannel);
        // 保存支付宝支付配置
        PayChannel aliPayChannel = new PayChannel();
        aliPayChannel.setMerchantId(merchantId);
        aliPayChannel.setPayWay(PayChannelConstant.PayWay.ALIPAY.getCode());
        aliPayChannel.setWebPayChannel(0);
        aliPayChannel.setScanPayChannel(0);
        if (!ParamUtil.isBlank((String) aliMap.get("scanPay"))) {
            aliPayChannel.setScanPayChannel(Integer.parseInt((String) aliMap.get("scanPay")));
        }
        if (!ParamUtil.isBlank((String) aliMap.get("wapPay"))) {
            aliPayChannel.setWebPayChannel(Integer.parseInt((String) aliMap.get("wapPay")));
        }
        save(aliPayChannel);
        // 保存银行卡支付配置
        return "支付配置成功保存";
    }


}
