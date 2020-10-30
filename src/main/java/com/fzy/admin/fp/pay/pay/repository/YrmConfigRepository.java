package com.fzy.admin.fp.pay.pay.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.YrmConfig;

/**
 * @author Created by wtl on 2019-04-25 20:36
 * @description 易融码配置dao
 */
public interface YrmConfigRepository extends BaseRepository<YrmConfig> {

    /**
     * 根据商户id获取微会员宝配置
     */
    YrmConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);

}
