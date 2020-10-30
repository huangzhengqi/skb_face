package com.fzy.admin.fp.order.order.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.vo.SummaryVO;
import com.fzy.admin.fp.order.pc.dto.OrderCommissionDto;
import com.fzy.admin.fp.order.pc.dto.OrderCountDto;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单dao
 */
public interface CommissionRepository extends BaseRepository<Commission> {

    Commission findByOrderIdAndOrderStatusNot(String id, Integer status);

    Commission findByOrderIdAndOrderStatus(String id, Integer status);

    Commission findByOrderId(String id);

    List<Commission> findAllByCreateTimeBetweenAndOrderStatusOrderByPayChannelDescPayWayDesc(Date begin, Date end, Integer status);

    /**
     * 根据日期和状态和设置返佣查询佣金列表
     *
     * @param begin
     * @param end
     * @param status
     * @param rebateType
     * @return
     */
    List<Commission> findAllByCreateTimeBetweenAndOrderStatusAndRebateTypeOrderByPayChannelDescPayWayDesc(Date begin, Date end, Integer status, Integer rebateType);

    List<Commission> findAllByCreateTimeBetweenAndOrderStatusAndOemIdInOrderByPayChannelDescPayWayDesc(Date begin, Date end, Integer status, List<String> oemId);

    /**
     * 根据日期和状态和设置和返佣和公司id查询佣金列表
     * @param begin
     * @param end
     * @param status
     * @param oemId
     * @param rebateType
     * @return
     */
    List<Commission> findAllByCreateTimeBetweenAndOrderStatusAndOemIdInAndRebateTypeOrderByPayChannelDescPayWayDesc(Date begin, Date end, Integer status, List<String> oemId, Integer rebateType);

    List<Commission> findAllByOemIdAndOrderStatus(String oemId, Integer status);

    List<Commission> findAllByOemIdAndCreateTimeBetweenAndOrderStatus(String oemId, Date begin, Date end, Integer status);

    List<Commission> findAllByFirstIdAndOrderStatus(String firstId, Integer status);

    List<Commission> findAllByFirstIdAndCreateTimeBetweenAndOrderStatus(String firstId, Date begin, Date end, Integer status);

    List<Commission> findAllBySecondIdAndOrderStatus(String firstId, Integer status);

    List<Commission> findAllBySecondIdAndCreateTimeBetweenAndOrderStatus(String firstId, Date begin, Date end, Integer status);

    List<Commission> findAllByThirdIdAndOrderStatus(String thirdId, Integer status);

    List<Commission> findAllByThirdIdAndCreateTimeBetweenAndOrderStatus(String thirdId, Date begin, Date end, Integer status);

    @Query(value = "SELECT * FROM order_commission WHERE first_id = ?1 and date(create_time) = ?2 and hour(create_time) = ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByFirstIdCurrentTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE first_id = ?1 and date(create_time) = ?2 and hour(create_time) <= ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByFirstIdBeforeTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE second_id = ?1 and date(create_time) = ?2 and hour(create_time) = ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionBySecondIdCurrentTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE second_id = ?1 and date(create_time) = ?2 and hour(create_time) <= ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionBySecondIdBeforeTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE third_id = ?1 and date(create_time) = ?2 and hour(create_time) = ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByThirdIdCurrentTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE third_id = ?1 and date(create_time) = ?2 and hour(create_time) <= ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByThirdIdBeforeTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE oem_id = ?1 and date(create_time) = ?2 and hour(create_time) = ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByOemIdCurrentTime(String paramString1, String paramString2, int paramInt);

    @Query(value = "SELECT * FROM order_commission WHERE oem_id = ?1 and date(create_time) = ?2 and hour(create_time) <= ?3 and order_status = 2", nativeQuery = true)
    List<Commission> getCommissionByOemIdBeforeTime(String paramString1, String paramString2, int paramInt);

    //查询每个商户的佣金结算
    @Query(value = "select new com.fzy.admin.fp.order.pc.dto.OrderCommissionDto(sum (c.oemCommission)) from Commission c where c.oemId=?1 and c.merchantId=?2 and c.orderStatus = 2")
    OrderCommissionDto getCountCommission(String companyId, String merchantId);

    // 2019-12-05
    List<Commission> findAllByOemIdAndMerchantIdAndOrderStatus(String oemId, String merchantId, Integer status);

    List<Commission> findAllByOemIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(String oemId, String merchantId, Date begin, Date end, int status);

    List<Commission> findAllByFirstIdAndMerchantIdAndOrderStatus(String firstId, String merchantId, Integer status);

    List<Commission> findAllByFirstIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(String firstId, String merchantId, Date begin, Date end, Integer status);

    List<Commission> findAllBySecondIdAndAndMerchantIdAndOrderStatus(String firstId, String merchantId, Integer status);

    List<Commission> findAllBySecondIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(String firstId, String merchantId, Date begin, Date end, Integer status);

    List<Commission> findAllByThirdIdAndMerchantIdAndOrderStatus(String thirdId, String merchantId, int status);

    List<Commission> findAllByThirdIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(String thirdId, String merchantId, Date begin, Date end, int status);

    List<Commission> findAllByCreateTimeBetweenAndTypeAndOrderStatus(Date startTime, Date endTime, int i, int status);

    /**
     * 根据订单号和状态删除佣金数据
     *
     * @param orderId
     * @param orderStatus
     */
    void deleteByOrderIdAndOrderStatus(String orderId, Integer orderStatus);

}