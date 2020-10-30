package com.fzy.admin.fp.order;


import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.domain.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-12 22:35
 * @description
 */
public interface OrderConstant {
    //支付失败的订单状态
    Integer[] failedOrderStatus = {
            Order.Status.PLACEORDER.getCode(),
            Order.Status.FAILPAY.getCode(),
            Order.Status.CANCELPAY.getCode()};
    Set<Integer> failedOrderStatusSet = Arrays.stream(failedOrderStatus).collect(Collectors.toSet());
    //商户统计订单缓存
    Cache<String, List<Order>> MERCHANT_STATISTICS_CACHE = CacheUtil.newLFUCache(1000, 24 * 60 * 60 * 1000);

    Cache<String, String> ORDERID = CacheUtil.newLFUCache(2000, 60 * 1000);

}
