package com.fzy.admin.fp.order.order.repository;


import cn.hutool.core.date.DateTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.vo.CountDataVO;
import com.fzy.admin.fp.order.pc.dto.OrderActPayPriceDto;
import com.fzy.admin.fp.order.pc.dto.OrderCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单dao
 */
public interface OrderRepository extends BaseRepository<Order> {


    /**
     * @param orderNumber 本系统订单号
     * @param outTradeNo  调用本系统支付接口的第三方平台订单号
     */
    long countByOrderNumberOrOutTradeNo(String orderNumber, String outTradeNo);

    long countByOrderNumber(String orderNumber);


    Order findByOutTradeNo(String outTradeNo);


    Order findByOrderNumber(String paramString);

    /**
     * 根据订单号查询订单
     */
    Order findByOrderNumberAndDelFlag(String orderNumber, Integer delFlag);

    Order findByRefundTransactionIdAndDelFlag(String refundTranscationId, Integer delFlag);

    /**
     * 根据本平台订单号或通道订单号查询订单，退款时可能本平台订单号，可能是通道订单号
     *
     * @param orderNumber   本系统订单号
     * @param transactionId 通道订单号
     * @param outTradeNo    第三方接口商户订单号，调用本系统的接口
     */
    Order findByOrderNumberOrTransactionIdOrOutTradeNoOrRefundTransactionId(String orderNumber, String transactionId, String outTradeNo, String refundTransactionId);

    List<Order> findByPayTimeBetweenAndMerchantIdInAndStatusNotInOrderByPayTimeAsc(Date startTime, Date endTime, String[] merchantIds, Integer[] failedOrderStatus);

    List<Order> findByPayTimeBetweenAndMerchantIdInOrderByPayTimeAsc(Date startTime, Date endTime, List<String> Merchants);

    List<Order> findByPayTimeBetweenAndMerchantIdInAndPayWayInAndStatusNotInOrderByPayTimeDesc(Date startTime, Date endTime, String[] merchantIds, Integer[] payWays, Integer[] failedOrderStatus);

    List<Order> findByMerchantIdIn(List<String> mids);

    List<Order> findByMerchantIdInAndPayTimeBetween(List<String> merChantId, Date begin, Date end);

    List<Order> findByPayTimeBetween(Date begin, Date end);

    List<Order> findByMerchantIdAndPayTimeBetween(String merchantId, Date begin, Date end);

    List<Order> findByMerchantIdInAndStatusInAndPayWayNot(List<String> mids, List<Integer> status, Integer payWay);

    List<Order> findByEquipmentIdAndUserNameLikeAndOrderNumberLikeAndStatusIn(String paramString1, String paramString2, String paramString3, List<Integer> paramList);

    List<Order> findByEquipmentIdAndUserNameLikeAndOrderNumberLikeAndPayTimeBetweenAndStatusIn(String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2, List<Integer> paramList);

    List<Order> findByEquipmentId(String paramString);

    /**
     * 根据时间统计商户订单，订单数、订单金额、退款金额
     * 登录账号为商户
     */
    @Query("select new com.fzy.admin.fp.order.pc.dto.OrderCountDto(count (o.id),sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId=?1 and o.payTime between ?2 and ?3")
    OrderCountDto countMerchantOrder(String merchantId, Date startTime, Date endTime);

    /**
     * 根据时间统计商户支付成功订单，订单数、订单金额、退款金额
     * 登录账号为商户
     */
    @Query("select new com.fzy.admin.fp.order.pc.dto.OrderCountDto(count (o.id),sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId=?1 and o.payTime between ?2 and ?3 and o.status in (2,5,6)")
    OrderCountDto countMerchantOrder1(String merchantId, Date startTime, Date endTime);

    /**
     * 统计商户支付成功订单，订单数、订单金额、退款金额
     * 登录账号为商户
     */
    @Query("select new com.fzy.admin.fp.order.pc.dto.OrderCountDto(count (o.id),sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId=?1  and o.status in (2,5,6)")
    OrderCountDto countMerchantOrder2(String merchantId);

    /**
     * 根据时间统计门店订单，订单数、订单金额、退款金额
     * 登录账号为店长
     */
    @Query("select new com.fzy.admin.fp.order.pc.dto.OrderCountDto(count (o.id),sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.storeId=?1 and o.payTime between ?2 and ?3")
    OrderCountDto countStoreOrder(String storeId, Date startTime, Date endTime);

    /**
     * 根据时间统计收银员订单，订单数、订单金额、退款金额
     * 登录账号为店员
     */
    @Query("select new com.fzy.admin.fp.order.pc.dto.OrderCountDto(count (o.id),sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.userId=?1 and o.payTime between ?2 and ?3")
    OrderCountDto countCashierOrder(String userId, Date startTime, Date endTime);


    /**
     * 根据时间获取订单列表
     */
    @Query("from Order where merchantId=?1 and status not in (?2) and payTime between ?3 and ?4")
    List<Order> findOrderByTime(String merchantId, Set status, Date start, Date end);


    //根据门店id轮训支付成功的订单
    List<Order> findTop5ByStoreIdAndStatusAndPayTimeBetweenOrderByPayTimeDesc(String storeId, Integer status, Date start, Date end);

    //根据时间转台获取订单集合
    List<Order> findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(List<Integer> status, List<String> merchantIds, Date start, Date end, Integer payWay);

    List<Order> findByStatusInAndMerchantIdInAndPayWayNot(List<Integer> status, List<String> merchantIds, Integer payWay);

    //根据时间转台获取订单集合
    List<Order> findByStatusInAndMerchantIdIn(List<Integer> status, List<String> merchantIds);

    void deleteByOrderNumber(String orderNumber);

    @Query(value = "SELECT * FROM lysj_order_order WHERE INSTR(?1,merchant_id) > 0 and status not in (1,3,4) and pay_way != 4 and date(pay_time) = ?2 and hour(pay_time) <= ?3 ORDER BY pay_time desc", nativeQuery = true)
    List<Order> findOrderByBeforeCurrentTime(String paramString1, String paramString2, int paramInt);


    @Query(value = "SELECT * FROM lysj_order_order WHERE INSTR(?1,merchant_id) > 0 and status not in (1,3,4) and pay_way != 4 and date(pay_time) = ?2 and hour(pay_time) = ?3  ORDER BY pay_time desc", nativeQuery = true)
    List<Order> findOrderByCurrentTime(String paramString1, String paramString2, int paramInt);

    //查询每一个商户的总金额
    @Query(value = "select new com.fzy.admin.fp.order.pc.dto.OrderActPayPriceDto(sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId=?1 and o.status = 2")
    OrderActPayPriceDto findactPpayPrice(String id);

    //按时间查询每一个商户的总金额
    @Query(value = "select new com.fzy.admin.fp.order.pc.dto.OrderActPayPriceDto(sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId=?1 and o.status = 2 and o.payTime between ?2 and ?3")
    OrderActPayPriceDto findactPpayPriceOrder(String id, Date startTime, Date endTime);

    //计算每个代理下商户的总销售额
    @Query(value = "select new com.fzy.admin.fp.order.pc.dto.OrderActPayPriceDto(sum (o.totalPrice),sum (o.refundPayPrice)) from Order o where o.merchantId in :merchantIds and  o.status = 2")
    OrderActPayPriceDto findByPayPriceNum(@Param("merchantIds") List<String> merchantIds);

    void deleteByOrderNumberAndStatus(String paramString, Integer paramInteger);

    Order findByAuthCode(String authCode);

    @Query("select new com.fzy.admin.fp.order.order.vo.CountDataVO(sum (o.totalPrice)) from Order o where o.equipmentId=?1 and o.status=?2 and o.merchantId=?3 ")
    CountDataVO getCountByEquipmentIdAndStatus(String paramString, Integer status, String merchantId);

    @Query(value = "SELECT * FROM lysj_order_order  WHERE merchant_id = ?1  AND date(pay_time) = ?2 AND status NOT IN (1,3,4) ", nativeQuery = true)
    List<Order> getOrderByMerchantAppointTime(String paramString1, String paramString2);

    @Query(value = "SELECT * FROM lysj_order_order WHERE store_id = ?1 AND status NOT IN (1,3,4) AND date(pay_time) = ?2  ", nativeQuery = true)
    List<Order> getOrderByStroeAppointTime(String paramString1, String paramString2);

    @Query(value = "SELECT * FROM lysj_order_order WHERE INSTR(?1,store_id) > 0 and status not in (1,3,4) and pay_way != 4 and date(pay_time) = ?2 and hour(pay_time) <= ?3 ORDER BY pay_time desc", nativeQuery = true)
    List<Order> findOrderByBeforeCurrentTimeStore(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM lysj_order_order WHERE INSTR(?1,store_id) > 0 and status not in (1,3,4) and pay_way != 4 and date(pay_time) = ?2 and hour(pay_time) = ?3  ORDER BY pay_time desc", nativeQuery = true)
    List<Order> findOrderByCurrentTimeStore(String paramString1, String paramString2, int paramInt);

    List<Order> findByMerchantIdAndPayTimeLessThanEqual(String merchantId, DateTime dateTime);

    List<Order> findByStoreIdAndPayTimeLessThanEqual(String storeId, DateTime dateTime);

    @Query(value = "SELECT * FROM lysj_order_order  WHERE user_id = ?1 AND store_id = ?2 AND date(pay_time) = ?3 AND status NOT IN (1,3,4) ", nativeQuery = true)
    List<Order> getOrderByUserAppointTime(String id, String storeId, String oneDay);

    @Query(value = "SELECT * FROM lysj_order_order  WHERE merchant_id = ?1 AND date(pay_time) = ?2 AND status NOT IN (1,3,4) ", nativeQuery = true)
    List<Order> getOrderByMerchantIdAppointTime(String id, String oneDay);

    List<Order> findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(List<Integer> statusList, String id, String storeId, Date startTime, Date endTime, Integer code);

    /**
     * 商户门店最早收款记录
     *
     * @param storeIds
     * @return
     */
    Order queryTop1ByAndStoreIdInOrderByCreateTimeAsc(List<String> storeIds);

    /**
     * 员工最早的收款记录
     *
     * @param userId
     * @return
     */
    Order queryTop1ByAndUserIdOrderByCreateTimeAsc(String userId);

    Page<Order> findByStoreId(String storeId, Pageable pageable);

    Order findAllByOrderNumberAndAuthCode(String orderNum, String authCode);

    Order findAllByOrderNumber(String orderNum);
}