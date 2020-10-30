package com.fzy.admin.fp.wx.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.wx.domain.WxOpenIdMerchant;

/**
 * @Author hzq
 * @Date 2020/9/9 18:08
 * @Version 1.0
 * @description
 */
public interface WxOpenIdMerchantRepository extends BaseRepository<WxOpenIdMerchant> {

    /**
     * 根据商户id和openId获取绑定商户信息
     * @param merchantId
     * @param openId
     * @return
     */
    WxOpenIdMerchant findByMerchantIdAndOpenId(String merchantId, String openId);
}
