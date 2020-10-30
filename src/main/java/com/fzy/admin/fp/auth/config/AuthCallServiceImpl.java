package com.fzy.admin.fp.auth.config;

import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.wechatopen.service.business.service.IAuthCallService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huang
 * @create 2019-07-19 12:17
 **/
@Slf4j
@Component
public class AuthCallServiceImpl implements IAuthCallService {

    @Resource
    private MerchantAppletConfigRepository merchantAppletConfigRepository;

    @Override
    public void authHandler(WxOpenAuthorizerInfoResult authorizerInfoResult, String params) {
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigRepository.findByMerchantId(params);
        if (merchantAppletConfig == null) {
            merchantAppletConfig = new MerchantAppletConfig();
            merchantAppletConfig.setMerchantId(params);
        }
        WxOpenAuthorizationInfo authorizationInfo = authorizerInfoResult.getAuthorizationInfo();
        WxOpenAuthorizerInfo wxOpenAuthorizerInfo = authorizerInfoResult.getAuthorizerInfo();
        merchantAppletConfig.setAppId(authorizationInfo.getAuthorizerAppid());
        merchantAppletConfig.setHeadImg(wxOpenAuthorizerInfo.getHeadImg());
        merchantAppletConfig.setNickName(wxOpenAuthorizerInfo.getNickName());
        merchantAppletConfig.setUserName(wxOpenAuthorizerInfo.getUserName());
        merchantAppletConfig.setPrincipalName(wxOpenAuthorizerInfo.getPrincipalName());
        merchantAppletConfigRepository.save(merchantAppletConfig);
    }
}
