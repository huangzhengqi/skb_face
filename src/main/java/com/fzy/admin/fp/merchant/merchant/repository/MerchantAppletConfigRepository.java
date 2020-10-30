package com.fzy.admin.fp.merchant.merchant.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;

/**
 * @author Created by zk on 2019-05-29 15:21
 * @description
 */
public interface MerchantAppletConfigRepository extends BaseRepository<MerchantAppletConfig> {

    /**
     * 根据商户id获取小程序配置
     * @param merchantId
     * @return
     */
    MerchantAppletConfig findByMerchantId(String merchantId);

    /**
     * 根据商户id统计小程序配置
     * @param merchantId
     * @return
     */
    long countByMerchantId(String merchantId);

    /**
     * 根据商户appid获取小程序配置
     * @param appId
     * @return
     */
    MerchantAppletConfig findByAppId(String appId);
}
