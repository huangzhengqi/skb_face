package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.HuaBeiConfig;

/**
 * @Author hzq
 * @Date 2020/9/1 17:03
 * @Version 1.0
 * @description 花呗分期持久层
 */
public interface HuabeiConfigRepository extends BaseRepository<HuaBeiConfig> {
    /**
     * 根据门店和设备id和状态查询花呗分期设置
     * @param paramString1
     * @param paramString2
     * @param paramInteger
     * @return
     */
    HuaBeiConfig findByStoreIdAndEquipmentIdAndStatus(String paramString1, String paramString2, Integer paramInteger);

    /**
     * 根据门店和设备id查询花呗分期设置
     * @param paramString1
     * @param paramString2
     * @return
     */
    HuaBeiConfig findByStoreIdAndEquipmentId(String paramString1, String paramString2);
}
