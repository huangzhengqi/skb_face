package com.fzy.admin.fp.merchant.thirdMchInfo.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchInfo;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:53 2019/6/25
 * @ Description:
 **/

public interface HsfMchInfoRepository extends BaseRepository<HsfMchInfo> {

    //根据商户id查询
    HsfMchInfo findByMerchantId(String merchantId);

    //进件回调根据shop_id找到进件
    HsfMchInfo findByShopId(String shop_id);
}
