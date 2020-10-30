package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.HybConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 微会员宝配置dao
 */
public interface HybConfigRepository extends BaseRepository<HybConfig> {

    /**
     * 根据商户id获取微会员宝配置
     */
    HybConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
