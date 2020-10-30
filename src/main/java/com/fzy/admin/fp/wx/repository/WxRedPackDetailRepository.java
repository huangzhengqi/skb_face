package com.fzy.admin.fp.wx.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;

import java.util.Date;
import java.util.List;

/**
 * @Author hzq
 * @Date 2020/9/10 14:43
 * @Version 1.0
 * @description
 */
public interface WxRedPackDetailRepository extends BaseRepository<WxRedPackDetail> {

    /**
     * 根据商户id查询红包记录
     *
     * @param merchantId
     * @return
     */
    List<WxRedPackDetail> findByMerchantIdOrderByCreateTimeDesc(String merchantId);

    /**
     * 查询已发放状态和未领取红包的记录
     * @param returnType
     * @param startTime
     * @param endTime
     * @return
     */
    List<WxRedPackDetail> findByReturnTypeAndStatusIsNullAndCreateTimeBetween(Integer returnType, Date startTime, Date endTime);

    /**
     * 根据订单号查询红包
     * @param mchBillon
     * @return
     */
    WxRedPackDetail findByMchBillno(String mchBillon);
}
