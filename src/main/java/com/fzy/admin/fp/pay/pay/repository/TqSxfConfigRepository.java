package com.fzy.admin.fp.pay.pay.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;

public interface TqSxfConfigRepository extends BaseRepository<TqSxfConfig> {

    /**
     * 根据商户id获取随行付配置
     *
     * @param merchantId
     * @param delFlag
     * @return
     */
    TqSxfConfig findByMerchantIdAndDelFlag(String merchantId, Integer delFlag);
}
