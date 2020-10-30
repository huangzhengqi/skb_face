package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO;
import com.fzy.admin.fp.auth.vo.EquipmentVO;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EquipmentRepository extends BaseRepository<Equipment> {

    /**
     * 分销商户设备列表
     * @param paramPageable
     * @param paramList
     * @param paramString
     * @return
     */
    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,s.name,m.name,e.deviceType) from Equipment e," +
            "com.fzy.admin.fp.merchant.merchant.domain.Merchant m," +
            "com.fzy.admin.fp.merchant.merchant.domain.Store s" +
            " where e.merchantId=m.id and e.storeId=s.id  and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%'))order by e.createTime desc")
    Page<EquipmentVO> getPage3(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString);

    /**
     * 分销商户设备列表条件查询
     * @param paramPageable
     * @param paramList
     * @param paramString
     * @return
     */
    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,s.name,m.name,e.deviceType) from Equipment e," +
            "com.fzy.admin.fp.merchant.merchant.domain.Merchant m," +
            "com.fzy.admin.fp.merchant.merchant.domain.Store s" +
            " where e.merchantId=m.id and e.storeId=s.id and  e.deviceType=:type and e.merchantId in (:merchantId) and " +
            "(e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') )order by e.createTime desc")
    Page<EquipmentVO> getPage3(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString, @Param("type") Integer paramInteger);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.id, e.deviceType,s.name,m.name,e.deviceId,e.mode,m.type,m.managerId) from Equipment e,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s where e.merchantId=m.id and e.storeId=s.id  and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%')) order by e.createTime desc")
    Page<EquipmentVO> getPage2(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.id, e.deviceType,s.name,m.name,e.deviceId,e.mode,m.type,m.managerId) from Equipment e,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s where e.merchantId=m.id and e.storeId=s.id  and e.deviceType=:type and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') )order by e.createTime desc")
    Page<EquipmentVO> getPage2(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString, @Param("type") Integer paramInteger);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,s.name,m.name,c.name,e.deviceType) from Equipment e,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s,com.fzy.admin.fp.auth.domain.Company c where e.merchantId=m.id and e.storeId=s.id and m.companyId=c.id and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') or c.name like concat ('%',:keyWord,'%'))order by e.createTime desc")
    Page<EquipmentVO> getPage1(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,s.name,m.name,c.name,e.deviceType) from Equipment e,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s,com.fzy.admin.fp.auth.domain.Company c where e.merchantId=m.id and e.storeId=s.id and m.companyId=c.id and e.deviceType=:type and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') or c.name like concat ('%',:keyWord,'%'))order by e.createTime desc")
    Page<EquipmentVO> getPage1(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString, @Param("type") Integer paramInteger);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,count(e.id),s.name,m.name,c.name,e.deviceType,sum(od.actPayPrice)) from Equipment e,com.fzy.admin.fp.order.order.domain.Order od,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s,com.fzy.admin.fp.auth.domain.Company c where e.id=od.equipmentId and e.merchantId=m.id and e.storeId=s.id and m.companyId=c.id and od.status in (2,5,6) and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') or c.name like concat ('%',:keyWord,'%'))group by e.id")
    Page<EquipmentVO> getPage(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString);

    @Query("select new com.fzy.admin.fp.auth.vo.EquipmentVO(e.mode,e.id, e.deviceId,count(e.id),s.name,m.name,c.name,e.deviceType,sum(od.actPayPrice)) from Equipment e,com.fzy.admin.fp.order.order.domain.Order od,com.fzy.admin.fp.merchant.merchant.domain.Merchant m,com.fzy.admin.fp.merchant.merchant.domain.Store s,com.fzy.admin.fp.auth.domain.Company c where e.id=od.equipmentId and e.merchantId=m.id and e.storeId=s.id and m.companyId=c.id and od.status in (2,5,6) and e.merchantId in (:merchantId) and (e.deviceId like concat ('%',:keyWord,'%') or s.name like concat ('%',:keyWord,'%') or m.name like concat ('%',:keyWord,'%') or c.name like concat ('%',:keyWord,'%')) and e.deviceType=:type group by e.id")
    Page<EquipmentVO> getPage(Pageable paramPageable, @Param("merchantId") List<String> paramList, @Param("keyWord") String paramString, @Param("type") Integer paramInteger);

    @Query("select  new com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO( o.id,o.orderNumber, s.name,u.name,o.totalPrice,o.actPayPrice,o.disCountPrice,o.status,o.payWay,o.payChannel,o.createTime,o.payType) from com.fzy.admin.fp.order.order.domain.Order o,com.fzy.admin.fp.merchant.management.domain.MerchantUser u,com.fzy.admin.fp.merchant.merchant.domain.Store s where o.userId=u.id and o.equipmentId=:equipmentId and s.id=o.storeId and o.orderNumber like concat('%',:orderNum,'%')  and u.name like concat('%',:staffName,'%') ")
    Page<EquipmentDetailPageVO> getOrderPage(@Param("equipmentId") String paramString1, @Param("staffName") String paramString2, @Param("orderNum") String paramString3, Pageable paramPageable);

    @Query("select  new com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO( o.id,o.orderNumber, s.name,u.name,o.totalPrice,o.actPayPrice,o.disCountPrice,o.status,o.payWay,o.payChannel,o.createTime,o.payType) from com.fzy.admin.fp.order.order.domain.Order o,com.fzy.admin.fp.merchant.management.domain.MerchantUser u,com.fzy.admin.fp.merchant.merchant.domain.Store s where o.userId=u.id and o.equipmentId=:equipmentId and s.id=o.storeId and o.orderNumber like concat('%',:orderNum,'%')  and u.name like concat('%',:staffName,'%') and o.createTime between :begin and :endTime")
    Page<EquipmentDetailPageVO> getOrderPage(@Param("equipmentId") String paramString1, @Param("staffName") String paramString2, @Param("orderNum") String paramString3, @Param("begin") Date paramDate1, @Param("endTime") Date paramDate2, Pageable paramPageable);

    @Query("select  new com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO( o.id,o.orderNumber, s.name,u.name,o.totalPrice,o.actPayPrice,o.disCountPrice,o.status,o.payWay,o.payChannel,o.createTime,o.payType) from com.fzy.admin.fp.order.order.domain.Order o,com.fzy.admin.fp.merchant.management.domain.MerchantUser u,com.fzy.admin.fp.merchant.merchant.domain.Store s where o.userId=u.id and o.equipmentId=:equipmentId and s.id=o.storeId and o.orderNumber like concat('%',:orderNum,'%')  and u.name like concat('%',:staffName,'%') ")
    List<EquipmentDetailPageVO> getOrderList(@Param("equipmentId") String paramString1, @Param("staffName") String paramString2, @Param("orderNum") String paramString3);

    @Query("select  new com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO( o.id,o.orderNumber, s.name,u.name,o.totalPrice,o.actPayPrice,o.disCountPrice,o.status,o.payWay,o.payChannel,o.createTime,o.payType) from com.fzy.admin.fp.order.order.domain.Order o,com.fzy.admin.fp.merchant.management.domain.MerchantUser u,com.fzy.admin.fp.merchant.merchant.domain.Store s where o.userId=u.id and o.equipmentId=:equipmentId and s.id=o.storeId and o.orderNumber like concat('%',:orderNum,'%')  and u.name like concat('%',:staffName,'%') and o.createTime between :begin and :endTime")
    List<EquipmentDetailPageVO> getOrderList(@Param("equipmentId") String paramString1, @Param("staffName") String paramString2, @Param("orderNum") String paramString3, @Param("begin") Date paramDate1, @Param("endTime") Date paramDate2);

    Equipment findByDeviceIdAndDelFlag(String paramString, Integer paramInteger);

    Equipment findByDeviceIdAndDelFlagAndStoreId(String paramString1, Integer paramInteger, String paramString2);

    Integer countByMerchantIdAndStatus(String merchantId, Integer status);

    Integer countByMerchantIdInAndStatusAndActivateTimeGreaterThanEqual(List<String> merchantId, Integer status, Date monthDate);

    //每月新增激活设备
    Integer countByMerchantIdInAndStatusAndActivateTimeGreaterThanEqualAndCreateTimeLessThan(List<String> merchantId, Integer status, Date monthDate, Date lastMonthDate);

    Integer countByMerchantIdInAndStatus(List<String> merchantId, Integer status);

    Integer countByStatusAndActivateTimeBetweenAndMerchantIdIn(Integer status, Date begin, Date end, List<String> merchantId);

    List<Equipment> findAllByStatusAndActivateTimeBetweenAndServiceProviderId(Integer status, Date begin, Date end, String serviceProviderId);

    //商户设备数
    Integer countByMerchantIdIn(List<String> merchantId);

    //商户蜻蜓/青蛙设备数
    Integer countByMerchantIdInAndDeviceType(List<String> merchantId, Integer deviceType);

    /**
     * 根据商户获取设备
     * @param merchantId
     * @return
     */
    List<Equipment> findByMerchantId(String merchantId);
}
