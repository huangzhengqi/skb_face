package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.AliConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 支付宝配置dao
 */
public interface AliConfigRepository extends BaseRepository<AliConfig> {

    /**
     * 根据商户id获取支付宝配置
     */
    AliConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
