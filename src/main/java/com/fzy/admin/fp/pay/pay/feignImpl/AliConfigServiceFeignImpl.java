package com.fzy.admin.fp.pay.pay.feignImpl;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-04-25 21:17
 * @description 支付宝支付配置feign实现
 */
@Service
@Slf4j
public class AliConfigServiceFeignImpl implements AliConfigServiceFeign {

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    private MerchantService merchantService;

    private String findCompanyIdByMerchantId(String merchantId) {
        Merchant merchant = merchantService.getRepository().findOne(merchantId);
        if (ParamUtil.isBlank(merchant)) {
            log.error("支付宝配置获取商户失败，获取服务商ID失败");
        }
        return merchant.getServiceProviderId();
    }

    @Override
    public String getPublicKey(String merchantId) {
        return topConfigRepository.findByServiceProviderIdAndDelFlag(findCompanyIdByMerchantId(merchantId), CommonConstant.NORMAL_FLAG).getAliPublicKey();
    }

    @Override
    public String getAppId(String merchantId) {
        return topConfigRepository.findByServiceProviderIdAndDelFlag(findCompanyIdByMerchantId(merchantId), CommonConstant.NORMAL_FLAG).getWxAppId();
    }

    @Override
    public String getAppSecret(String merchantId) {
        return topConfigRepository.findByServiceProviderIdAndDelFlag(findCompanyIdByMerchantId(merchantId), CommonConstant.NORMAL_FLAG).getWxAppSecret();
    }
}
