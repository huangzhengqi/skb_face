package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author Created by zk on 2019-05-29 15:22
 * @description
 */
@Slf4j
@Service
@Transactional
public class MerchantAppletConfigService implements BaseService<MerchantAppletConfig> {

    @Resource
    private MerchantAppletConfigRepository merchantAppletConfigRepository;

    @Override
    public BaseRepository<MerchantAppletConfig> getRepository() {
        return merchantAppletConfigRepository;
    }


    public MerchantAppletConfig findByMerchantId(String merchantId) {
        return merchantAppletConfigRepository.findByMerchantId(merchantId);
    }

    public boolean countByMerchantId(String merchantId) {
        return merchantAppletConfigRepository.countByMerchantId(merchantId) > 0;
    }

    public MerchantAppletConfig findByAppId(String appId) {
        return merchantAppletConfigRepository.findByAppId(appId);
    }
}
