package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.fzy.admin.fp.common.util.RedisUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.config.RedisConfiguration;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(OrderStatisticsService.class);


    @Resource
    private OrderRepository orderRepository;



    @Resource
    private StoreRepository storeRepository;

    @Resource
    private RedisConfiguration redisConfiguration;


    @Resource
    private MerchantService merchantService;



    public void setOrderCache(String key, List<Order> orderList, int timeout) {
        String json = JSONArray.toJSONString(orderList);
        RedisUtil.set(key, json, Integer.valueOf(timeout));
    }







    public List<Order> getOrderCache(String key, Class<Order> c) {
        String str = RedisUtil.get(key);
        return JSONArray.parseArray(str, c);
    }









    public void setEveryDayOrder(Merchant merchant, String oneDay) {
        String merchantId = merchant.getId();
        DateTime dateTime = DateUtil.parse(oneDay);

        List<Order> orderList = getMerchantAppointDate(merchant, oneDay);


        if (orderList != null) {
            String datestring = DateUtil.formatDate(dateTime);
            String key = merchantId + "_" + datestring + "_orderList_merchant";
            setOrderCache(key, orderList, 86400);
        }
    }





























    public List<Order> getMerchantAppointDate(Merchant merchant, String day) {
        String merchantId = merchant.getId();


        List<Order> orderList = this.orderRepository.getOrderByMerchantAppointTime(merchantId, day);

        Jedis resource = redisConfiguration.redisPoolFactory().getResource();
        String key2 = merchantId + "_" + day + "_orderList_merchant";
        Boolean exists = resource.exists(key2);
        if(exists){
            resource.del(key2);
        }

        String key = merchantId + "_" + day + "_orderList_merchant";
        String json = JSONArray.toJSONString(orderList);
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        resource.set(key, json, "NX", "PX", 86400);
        resource.close();

        return orderList;
    }









    public List<Order> getStoreAppointDate(Store store, String day) {
        String storeId = store.getId();


        List<Order> orderList1 = this.orderRepository.getOrderByStroeAppointTime(storeId, day);

        String storeKey = storeId + "_" + day + "_orderList_store";
        setOrderCache(storeKey, orderList1, 86400);

        return orderList1;
    }








    public List<Order> getMerchantAppointDateOrderList(Merchant merchant, String oneDay) {
        return this.orderRepository.getOrderByMerchantAppointTime(merchant.getId(), oneDay);
    }














    public List<Order> getTodayCurrentOrderListMerchant(Merchant merchant) {
        String toDay = DateUtil.today();


        DateTime dateTime = DateUtil.parseDateTime(DateUtil.now());
        int currentHour = dateTime.getField(DateField.HOUR_OF_DAY);

        return this.orderRepository.findOrderByCurrentTime(merchant.getId(), toDay, currentHour);
    }








    public List<Order> getTodayBeforeOrderListMerchant(Merchant merchant) {
        String toDay = DateUtil.today();


        DateTime dateTime = DateUtil.parseDateTime(DateUtil.now());
        int currentHour = dateTime.getField(DateField.HOUR_OF_DAY);

        int before = currentHour - 1;

        List<Order> result = this.orderRepository.findOrderByBeforeCurrentTime(merchant.getId(), toDay, before);

        if (result != null) {
            String key = merchant.getId() + "_" + toDay + "_orderList_merchant_" + before;
            setOrderCache(key, result, 86400);
        }

        return result;
    }







    public List<Order> getTodayBeforeOrderListStore(Store store) {
        String toDay = DateUtil.today();


        DateTime dateTime = DateUtil.parseDateTime(DateUtil.now());
        int currentHour = dateTime.getField(DateField.HOUR_OF_DAY);

        int before = currentHour - 1;

        List<Order> result = this.orderRepository.findOrderByBeforeCurrentTimeStore(store.getId(), toDay, before);

        if (result != null) {
            String storeKey = store.getId() + "_" + toDay + "_orderList_store_" + before;
            setOrderCache(storeKey, result, 86400);
        }


        return result;
    }








    public List<Order> getTodayCurrentOrderListStore(Store store) {
        String toDay = DateUtil.today();


        DateTime dateTime = DateUtil.parseDateTime(DateUtil.now());
        int currentHour = dateTime.getField(DateField.HOUR_OF_DAY);

        return this.orderRepository.findOrderByCurrentTimeStore(store.getId(), toDay, currentHour);
    }












    public void setBeforeOrderList(Merchant merchant, String toDay, int hour) {
        String merchantId = merchant.getId();


        List<Order> orderList = this.orderRepository.findOrderByBeforeCurrentTime(merchantId, toDay, hour);
        if (orderList != null) {
            String key = merchantId + "_" + toDay + "_orderList_merchant_" + hour;
            setOrderCache(key, orderList, 86400);
        }



        List<Store> storeList = this.storeRepository.findByMerchantId(merchantId);

        if (storeList != null) {
            for (Store store : storeList) {

                String storeId = store.getId();
                List<Order> orderList1 = this.orderRepository.findOrderByBeforeCurrentTimeStore(storeId, toDay, hour);


                if (orderList1 != null) {
                    String storeKey = storeId + "_" + toDay + "_orderList_store_" + hour;
                    setOrderCache(storeKey, orderList1, 86400);
                }
            }
        }
    }













    public List<Order> getMerchantAllOrderList(String merchantId) {
        DateTime dateTime = DateUtil.yesterday();

        List<Order> allOrderList = this.orderRepository.findByMerchantIdAndPayTimeLessThanEqual(merchantId, dateTime);


        String key = merchantId + "_all_orderList_merchant";
        setOrderCache(key, allOrderList, 86400);

        return allOrderList;
    }









    public List<Order> getStoreAllOrderList(String storeId) {
        DateTime dateTime = DateUtil.yesterday();

        List<Order> allOrderList = this.orderRepository.findByStoreIdAndPayTimeLessThanEqual(storeId, dateTime);


        String key = storeId + "_all_orderList_store";
        setOrderCache(key, allOrderList, 86400);

        return allOrderList;
    }










    public List<Order> getMerchantTodayAll(DateTime toDay, Merchant merchant) {
        List<Order> orderList = getMerchantAppointDateOrderList(merchant, DateUtil.formatDate(toDay));

        if (orderList == null || 0 == orderList.size()) {
            return null;
        }
        return orderList;
    }

    public List<Order> getUserTodayAll(DateTime toDay, MerchantUser merchantUser ,String storeId) {
        List<Order> orderList = getUserAppointDateOrderList(merchantUser, storeId, DateUtil.formatDate(toDay));

        if (orderList == null || 0 == orderList.size()) {
            return null;
        }
        return orderList;
    }

    private List<Order> getUserAppointDateOrderList(MerchantUser merchantUser,String storeId, String oneDay) {
        return this.orderRepository.getOrderByUserAppointTime(merchantUser.getId(),storeId, oneDay);
    }

    public List<Order> getUserAppointDate(MerchantUser merchantUser,String storeId, String day) {

        String userId = merchantUser.getId();

        List<Order> orderList = this.orderRepository.getOrderByUserAppointTime(userId,storeId ,day);

        Jedis resource = redisConfiguration.redisPoolFactory().getResource();
        String key2 = userId + "_" + day + "_" + storeId +"_" + "_orderList_user";
        Boolean exists = resource.exists(key2);
        if(exists){
            resource.del(key2);
        }

        String key = userId + "_" + day + "_" + storeId +"_" + "_orderList_user";
//        setOrderCache(key, orderList, 86400);
        String json = JSONArray.toJSONString(orderList);
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        resource.set(key, json, "NX", "PX", 86400);
        resource.close();

        return orderList;
    }
}
