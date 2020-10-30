package com.fzy.admin.fp.pay.channel.feignImpl;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.channel.service.PayChannelService;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayChannelRate;
import com.fzy.admin.fp.sdk.pay.feign.PayChannelServiceFeign;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.channel.domain.PayChannel;
import com.fzy.admin.fp.pay.channel.service.PayChannelService;
import com.fzy.admin.fp.pay.pay.domain.*;
import com.fzy.admin.fp.pay.pay.service.*;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayChannelRate;
import com.fzy.admin.fp.sdk.pay.feign.PayChannelServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-04-28 22:06
 * @description 商户支付通道配置feign接口实现
 */
@Slf4j
@Service
public class PayChannelServiceFeignImpl implements PayChannelServiceFeign {

    @Resource
    private PayChannelService payChannelService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private AliPayService aliPayService;

    @Resource
    private HybPayService hybPayService;

    @Resource
    private YrmPayService yrmPayService;

    @Resource
    private HsfPayService hsfPayService;

    @Resource
    private SxfPayService sxfPayService;

    @Resource
    private FyPayService fyPayService;

    @Resource
    private TqSxfPayService tqSxfPayService;

    /**
     * @author Created by wtl on 2019/5/7 14:38
     * @Description 获取商户的支付通道和对应的利率
     */
    @Override
    public PayChannelRate findMerchantChannel(String merchantId, Integer payWay, Integer payType) {

        // 下单需要的参数，包括支付通道和利率
        PayChannelRate payChannelRate = new PayChannelRate();
        // 支付通道
        PayChannel payChannel = payChannelService.getRepository().findByMerchantIdAndPayWay(merchantId, payWay);
        if (ParamUtil.isBlank(payChannel)) {
            log.info("该商户未配置支付通道");
            payChannelRate.setErrMsg("该商户未配置支付通道");
            return payChannelRate;
        }
        payChannelRate.setScanChannel(payChannel.getScanPayChannel());
        payChannelRate.setWebChannel(payChannel.getWebPayChannel());

        BigDecimal rate = null;

        // 支付方式为微信，
        if (PayChannelConstant.PayWay.WXPAY.getCode().equals(payWay)) {
            // 微信刷卡支付
            if (payType == 1) {
                // 判断微信支付通道
                if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(payChannel.getScanPayChannel())) {
                    // 官方通道
                    WxConfig wxConfig = wxPayService.getWxConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (wxConfig != null) {
                        // 获取利率
                        rate = wxConfig.getInterestRate();
                    }
                } else if (PayChannelConstant.Channel.YRM.getCode().equals(payChannel.getScanPayChannel())) {
                    // 易融码通道
                    YrmConfig yrmConfig = yrmPayService.getYrmConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (yrmConfig != null) {
                        rate = yrmConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.HSF.getCode().equals(payChannel.getScanPayChannel())) {
                    // 微信条码，惠闪付通道
                    HsfConfig hsfConfig = hsfPayService.getHsfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (hsfConfig != null) {
                        rate = hsfConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.SXF.getCode().equals(payChannel.getScanPayChannel())) {
                    SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (sxfConfig != null) {
                        rate = sxfConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.FY.getCode().equals(payChannel.getScanPayChannel())) {
                    FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (fyConfig != null) {
                        rate = fyConfig.getWxInterestRate();
                    }
                } else if(PayChannelConstant.Channel.TQSXF.getCode().equals(payChannel.getScanPayChannel())){
                    //天阙随行付
                    TqSxfConfig tqSxfConfig=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if(tqSxfConfig != null){
                        rate = tqSxfConfig.getWxInterestRate();
                    }
                }
            }
            // 微信网页支付
            else {
                if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(payChannel.getWebPayChannel())) {
                    WxConfig wxConfig = wxPayService.getWxConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (wxConfig != null) {
                        rate = wxConfig.getInterestRate();
                    }
                } else if (PayChannelConstant.Channel.HYB.getCode().equals(payChannel.getWebPayChannel())) {
                    HybConfig hybConfig = hybPayService.getHybConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (hybConfig != null) {
                        rate = hybConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.YRM.getCode().equals(payChannel.getWebPayChannel())) {
                    YrmConfig yrmConfig = yrmPayService.getYrmConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (yrmConfig != null) {
                        rate = yrmConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.SXF.getCode().equals(payChannel.getWebPayChannel())) {
                    SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (sxfConfig != null) {
                        rate = sxfConfig.getWxInterestRate();
                    }
                } else if (PayChannelConstant.Channel.FY.getCode().equals(payChannel.getWebPayChannel())) {
                    FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (fyConfig != null) {
                        rate = fyConfig.getWxInterestRate();
                    }
                } else if(PayChannelConstant.Channel.TQSXF.getCode().equals(payChannel.getScanPayChannel())){
                    //天阙随行付
                    TqSxfConfig tqSxfConfig=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if(tqSxfConfig != null){
                        rate = tqSxfConfig.getWxInterestRate();
                    }
                }
            }
        }
        // 支付方式为支付宝
        else if (PayChannelConstant.PayWay.ALIPAY.getCode().equals(payWay)) {
            // 支付宝刷卡支付
            if (payType == 1) {
                // 判断支付宝支付通道
                if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(payChannel.getScanPayChannel())) {
                    // 官方通道
                    AliConfig aliConfig = aliPayService.getAliConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (aliConfig != null) {
                        // 获取利率
                        rate = aliConfig.getInterestRate();
                    }
                } else if (PayChannelConstant.Channel.YRM.getCode().equals(payChannel.getScanPayChannel())) {
                    // 易融码通道
                    YrmConfig yrmConfig = yrmPayService.getYrmConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (yrmConfig != null) {
                        rate = yrmConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.HSF.getCode().equals(payChannel.getScanPayChannel())) {
                    // 支付宝条码支付，惠闪付通道
                    HsfConfig hsfConfig = hsfPayService.getHsfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (hsfConfig != null) {
                        rate = hsfConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.SXF.getCode().equals(payChannel.getScanPayChannel())) {
                    SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (sxfConfig != null) {
                        rate = sxfConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.FY.getCode().equals(payChannel.getScanPayChannel())) {
                    FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (fyConfig != null) {
                        rate = fyConfig.getAliInterestRate();
                    }
                } else if(PayChannelConstant.Channel.TQSXF.getCode().equals(payChannel.getScanPayChannel())){
                    //天阙随行付
                    TqSxfConfig tqSxfConfig=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if(tqSxfConfig != null){
                        rate = tqSxfConfig.getAliInterestRate();
                    }
                }
            }
            // 支付宝网页支付
            else {
                if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(payChannel.getWebPayChannel())) {
                    AliConfig aliConfig = aliPayService.getAliConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (aliConfig != null) {
                        rate = aliConfig.getInterestRate();
                    }
                } else if (PayChannelConstant.Channel.HYB.getCode().equals(payChannel.getWebPayChannel())) {
                    HybConfig hybConfig = hybPayService.getHybConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (hybConfig != null) {
                        rate = hybConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.YRM.getCode().equals(payChannel.getWebPayChannel())) {
                    YrmConfig yrmConfig = yrmPayService.getYrmConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (yrmConfig != null) {
                        rate = yrmConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.SXF.getCode().equals(payChannel.getWebPayChannel())) {
                    SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (sxfConfig != null) {
                        rate = sxfConfig.getAliInterestRate();
                    }
                } else if (PayChannelConstant.Channel.FY.getCode().equals(payChannel.getWebPayChannel())) {
                    FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if (fyConfig != null) {
                        rate = fyConfig.getAliInterestRate();
                    }
                } else if(PayChannelConstant.Channel.TQSXF.getCode().equals(payChannel.getScanPayChannel())){
                    //天阙随行付
                    TqSxfConfig tqSxfConfig=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
                    if(tqSxfConfig != null){
                        rate = tqSxfConfig.getAliInterestRate();
                    }
                }
            }
        }
        if (rate != null) {
            payChannelRate.setInterestRate(rate);
        } else {
            payChannelRate.setErrMsg("该商户未配置支付通道参数");
        }

        return payChannelRate;
    }
}
