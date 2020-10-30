package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.SxfConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 随行付配置dao
 */

public interface SxfConfigRepository extends BaseRepository<SxfConfig> {

    /**
     * 根据商户id获取随行付配置
     *
     * @param merchantId
     * @param delFlag
     * @return
     */
    SxfConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
