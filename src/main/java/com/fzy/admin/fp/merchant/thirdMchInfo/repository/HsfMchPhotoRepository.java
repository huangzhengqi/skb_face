package com.fzy.admin.fp.merchant.thirdMchInfo.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchPhoto;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:55 2019/6/27
 * @ Description:
 **/

public interface HsfMchPhotoRepository extends BaseRepository<HsfMchPhoto> {

    HsfMchPhoto findByMerchantId(String merchantId);
}
