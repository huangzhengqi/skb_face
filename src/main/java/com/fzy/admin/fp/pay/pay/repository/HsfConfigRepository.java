package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.HsfConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 惠闪付配置dao
 */
public interface HsfConfigRepository extends BaseRepository<HsfConfig> {

    /**
     * 根据商户id获取惠闪付配置
     */
    HsfConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
