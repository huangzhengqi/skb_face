package com.fzy.admin.fp.sdk.order.feign;

import com.fzy.admin.fp.member.applet.dto.AppletOrderDTO;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by zk on 2019-04-29 19:45
 * @description
 */
public interface OrderServiceFeign {
    //根据时间区间以及商户ID列表返回订单VO，排序规则为支付时间正序
    List<OrderVo> findByMerchantIdsAsc(String[] ids, String startTime, String endTime);

    List<OrderVo> findByMerchantIdsDesc(String[] ids, String startTime, String endTime, Integer[] payWays);

    /**
     * @author Created by wtl on 2019/6/5 11:55
     * @Description 会员模块储值业务调用扫码支付
     */
    OrderVo scanPay(String userId, BigDecimal totalPrice, String authCode);

    /**
     * @author Created by wtl on 2019/6/19 15:28
     * @Description 会员小程序充值生成订单并返回订单号
     */
//    String createOrder(String merchantId, String appletStore, BigDecimal totalPrice);

    String createOrder(String merchantId, String appletStore, BigDecimal totalPrice, String memberId);

    String createSmdcOrder(String merchantId, AppletOrderDTO appletOrderDTO);
}
