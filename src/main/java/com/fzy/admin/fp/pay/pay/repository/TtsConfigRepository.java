package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.TtsConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 统统收配置dao
 */
public interface TtsConfigRepository extends BaseRepository<TtsConfig> {

    /**
     * 根据商户id获取微会员宝配置
     */
    TtsConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
