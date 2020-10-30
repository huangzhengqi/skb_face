package com.fzy.admin.fp.pay.channel.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.channel.domain.PayChannel;

import java.util.List;

/**
 * @author Created by wtl on 2019-04-26 16:42
 * @description 支付通道dao
 */
public interface PayChannelRepository extends BaseRepository<PayChannel> {


    /**
     * 根据商户id查询支付通道配置
     * @param merchantId
     * @return
     */
    List<PayChannel> findByMerchantId(String merchantId);

    /**
     * 根据商户id和支付方式查询通道配置，订单模块调用，判断当前支付方式使用的是哪种通道
     * @param merchantId
     * @param payWay
     * @return
     */
    PayChannel findByMerchantIdAndPayWay(String merchantId, Integer payWay);
}
