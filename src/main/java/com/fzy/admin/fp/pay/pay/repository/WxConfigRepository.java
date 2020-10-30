package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 微信配置dao
 */
public interface WxConfigRepository extends BaseRepository<WxConfig> {

    /**
     * 根据商户id获取微信配置
     */
    WxConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
