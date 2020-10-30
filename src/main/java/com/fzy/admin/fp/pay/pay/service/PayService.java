package com.fzy.admin.fp.pay.pay.service;

import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.DomainInterface;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by wtl on 2019-07-02 16:38
 * @description
 */
@Service
public class PayService implements DomainInterface {

    @Resource
    protected HttpServletRequest httpServletRequest;

//    @Value("${prefix.url}")
//    protected String notifyUrl;

    // 商户授权token
    protected String appAuthToken;

    @Resource
    protected MerchantService merchantService;

    @Resource
    protected TopConfigRepository topConfigRepository;

    @Override
    public HttpServletRequest getRequest() {
        return httpServletRequest;
    }
}
