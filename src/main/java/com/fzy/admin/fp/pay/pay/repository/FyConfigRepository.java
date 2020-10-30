package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.FyConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 富友配置dao
 */

public interface FyConfigRepository extends BaseRepository<FyConfig> {

    /**
     * 根据商户id获取富友配置
     *
     * @param merchantId
     * @param delFlag
     * @return
     */
    FyConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
