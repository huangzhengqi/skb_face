package com.fzy.admin.fp.wx.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;
import com.fzy.admin.fp.wx.domain.WxRewardDetail;

import java.util.Date;
import java.util.List;

/**
 * @Author hzq
 * @Date 2020/10/9 15:35
 * @Version 1.0
 * @description
 */
public interface WxRewardDetailRepository extends BaseRepository<WxRewardDetail> {

    /**
     * 根据商户id查询奖励记录
     * @param merchantId
     * @return
     */
    List<WxRewardDetail> findByMerchantIdOrderByCreateTimeDesc(String merchantId);

    /**
     * 根据红包状态去查询列表
     * @param returnType
     * @returnr
     */
    List<WxRewardDetail> findByReturnTypeAndStatusIsNullAndCreateTimeBetween(Integer returnType,Date startTime ,Date endTime);

    /**
     * 根据订单号查询
     * @param mchBillno
     * @return
     */
    WxRewardDetail findByMchBillno(String mchBillno);
}
