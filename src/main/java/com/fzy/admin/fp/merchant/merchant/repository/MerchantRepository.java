package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户dao
 */
public interface MerchantRepository extends BaseRepository<Merchant> {

    Integer countByCompanyIdAndDelFlag(String companyId, Integer delFlag);

    List<Merchant> findByCompanyIdIn(String[] companyIds);

    List<Merchant> findByCompanyIdIn(List<String> companyIds);

    List<Merchant> findByIdIn(List<String> ids);

    List<Merchant> findByCompanyIdInAndDelFlag(String[] companyIds, Integer delFlag);

    List<Merchant> findAllByTypeAndCreateTimeBetweenAndServiceProviderId(Integer type, Date begin, Date end,String serviceProviderId);

    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from Merchant s where s.companyId LIKE CONCAT('',:companyId,'') and " +
            "s.name LIKE CONCAT('',:name,'') and s.delFlag=1 ")
    Page<SelectItem> selectItem(@Param("companyId") String companyId, @Param("name") String name, Pageable pageable);

    List<Merchant> findByCompanyIdAndDelFlag(String companyId, Integer delFlag);

    List<Merchant> findByManagerIdAndTypeAndDelFlag(String managerId,Integer type, Integer delFlag);

    Integer countByManagerIdAndTypeAndDelFlagAndCreateTimeGreaterThanEqual(String companyId, Integer type, Integer delFlag, Date monthTime);

    /**
     * 每月新增发展商户
     * @param companyId
     * @param type
     * @param delFlag
     * @param monthTime
     * @param lastMonthTime
     * @return
     */
    Integer countByManagerIdAndTypeAndDelFlagAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(String companyId, Integer type, Integer delFlag, Date monthTime,Date lastMonthTime);

    Integer countByPhoneAndDelFlag(String phone, Integer delFlag);

    /**
     * 查询业务员对应的商户
     * @param managerId
     * @return
     */
    @Query("select new com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem(m.id,m.name) from Merchant m where m.managerId=?1")
    List<MerchantSelectItem> findByManagerIdSelectItem(String managerId);


    /**
     * 获取一级代理商下属二级代理商的商户
     * @param name
     * @param contact
     * @param companyIds
     * @param pageable
     * @return
     */
    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.companyId in (:companyIds)")
    Page<Merchant> findByCompanyIds(@Param("name") String name, @Param("contact") String contact, @Param("companyIds") List<String> companyIds, Pageable pageable);

    /**
     * 获取服务商下面的所有商户
     * @param name
     * @param contact
     * @param serviceProviderId
     * @param pageable
     * @return
     */
    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.serviceProviderId in (:serviceProviderId)")
    Page<Merchant> findByServiceProviderId(@Param("name") String name, @Param("contact") String contact, @Param("serviceProviderId") List<String> serviceProviderId, Pageable pageable);

    /**
     * 获取一级代理商下属二级代理商的商户
     * @param name
     * @param contact
     * @param companyIds
     * @return
     */
    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.companyId in (:companyIds)")
    List<Merchant> findByCompanyIds1(@Param("name") String name, @Param("contact") String contact, @Param("companyIds") List<String> companyIds);


    /**
     * 获取一级代理商下属二级代理商的商户
     * @param name
     * @param contact
     * @param companyName
     * @param companyIds
     * @param pageable
     * @return
     */
    @Query("select m from Merchant m,com.fzy.admin.fp.auth.domain.Company c WHERE m.companyId=c.id and m.name LIKE CONCAT('%',:name,'%') and m.companyId in (:companyIds) and m.contact LIKE CONCAT('%',:contact,'%') and c.name like CONCAT('%',:companyName,'%')")
    Page<Merchant> findMerchantByItem(@Param("name") String name, @Param("contact") String contact, @Param("companyName") String companyName,@Param("companyIds") List<String> companyIds, Pageable pageable);

    /**
     * 获取现有商户的类型
     * @param companyIds
     * @return
     */
    @Query("select m.businessLevThree from Merchant m WHERE  m.companyId in (:companyIds) group by m.businessLevThree")
    List<String> findMerchantType(@Param("companyIds") List<String> companyIds);


    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.companyId in (:companyIds) and m.status = :status")
    Page<Merchant> findByCompanyIds(@Param("status") Integer status,@Param("name") String name, @Param("contact") String contact, @Param("companyIds") List<String> companyIds, Pageable pageable);

    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.serviceProviderId in (:serviceProviderId) and m.status = :status")
    Page<Merchant> findByServiceProviderId(@Param("status") Integer status,@Param("name") String name, @Param("contact") String contact, @Param("serviceProviderId") List<String> serviceProviderId, Pageable pageable);

    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.companyId in (:companyIds) and m.status = :status")
    List<Merchant> findByCompanyIds1(@Param("status") Integer status,@Param("name") String name, @Param("contact") String contact, @Param("companyIds") List<String> companyIds);


    /**
     * 获取启用商户selectItem
     * @param serviceId
     * @return
     */
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from Merchant s where s.delFlag=1 and s.serviceProviderId=?1")
    List<SelectItem> mchSelectItem(String serviceId);

    @Query("from Merchant m WHERE m.name LIKE CONCAT('%',:name,'%') and m.contact LIKE CONCAT('%',:contact,'%') and m.managerId LIKE CONCAT('%',:managerId,'%') and m.companyId in(:companyIds)")
    Page<Merchant> findByCompanyIdsAndManagerId(@Param("name") String paramString1, @Param("contact") String paramString2, @Param("managerId") String paramString3, @Param("companyIds") List<String> paramList, Pageable paramPageable);

    List<Merchant> findByCompanyId(String companyId);

    List<Merchant> findByManagerIdAndTypeOrderByCreateTimeDesc(String companyId,Integer type);

    Integer countByTypeAndCreateTimeBetweenAndServiceProviderId(Integer type, Date begin, Date end,String serviceProviderId);

    List<Merchant> findByManagerIdInAndType(List<String> ids,Integer type);

    /**
     * 根据城市Id获取服务商下面的所有商户
     * @param serviceProviderId
     * @param city
     * @return
     */
    List<Merchant> findByServiceProviderIdAndCityIn(String serviceProviderId,String[] city);

    /**
     * 获取当前服务商下面的所有商户
     * @param serviceProviderId
     * @return
     */
    List<Merchant> findByServiceProviderId(String serviceProviderId);

    /**
     * 根据服务商id和类型获取商户
     * @param serviceProviderId
     * @param type
     * @return
     */
    List<Merchant> findByServiceProviderIdAndType(String serviceProviderId,Integer type);

    /**
     * 获取当前服务商下指定的商户
     * @param serviceProviderId  服务商ID
     * @param ids  商户ID
     * @return
     */
    List<Merchant> findByIdInAndServiceProviderId(String [] ids,String serviceProviderId);


    /**
     * 查询分销商下的商户
     * @param merchantId
     * @return
     */
    @Query(nativeQuery = true,value =" SELECT sum(act_pay_price) AS totalPrice,oe.merchant_id AS merchantId,m.name AS merchantName,date_format(m.create_time, '%Y-%m-%d' ) AS createTime,IFNULL(od.faseNum,0)  AS faseNum FROM   lysj_merchant_merchant  m "+
            " LEFT JOIN lysj_order_order oe ON m.id=oe.merchant_id"+
            " LEFT JOIN (SELECT COUNT(1) as faseNum,merchant_id FROM lysj_order_order WHERE merchant_id =:merchantId  AND  `status` in ('2','6') AND pay_type='3'  AND act_pay_price >=2 ) od ON oe.merchant_id=od.merchant_id "+
            " WHERE oe.merchant_id =:merchantId  AND oe.status in ('2','6') GROUP BY faseNum  ORDER BY m.create_time DESC" )
    List<Object[]> getMyMerchantList(@Param("merchantId")String  merchantId);

    /**
     *  商户,店长
     * 根据门店Id ，按月,天统计
     * @param storeId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Query(nativeQuery = true,value ="SELECT IFNULL( SUM( act_pay_price ),0) AS totalPrice,COUNT( 1 ) AS totalNum,pd.faseNum,pa.facePrice,pa.fase2Num " +
            " FROM lysj_order_order od,(SELECT  COUNT( 1 ) AS fase2Num, IFNULL(SUM( act_pay_price ),0) AS facePrice FROM lysj_order_order " +
            " WHERE store_id in(:storeId)  AND `status` = '2' AND pay_type = '3' AND pay_time >= :beginTime AND pay_time <= :endTime) pa," +
            " (SELECT COUNT( 1 ) AS faseNum,merchant_id FROM lysj_order_order WHERE store_id in(:storeId)  AND `status` = '2' " +
            "AND pay_type = '3' AND act_pay_price >= 2 AND pay_time >= :beginTime AND pay_time <= :endTime) pd" +
            " WHERE  pay_time >= :beginTime " +
            " AND pay_time <= :endTime " +
            " AND `status` = '2' " +
            " AND od.store_id in(:storeId) ")
    List<Object[]> getMyMerchantMonth(@Param("storeId")List<String> storeId, @Param("beginTime") String beginTime, @Param("endTime")String endTime);

    /**
     *  店员查询自己的
     * 根据userId ，按月,天统计
     * @param userId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Query(nativeQuery = true,value ="SELECT IFNULL( SUM( act_pay_price ),0) AS totalPrice,COUNT( 1 ) AS totalNum,pd.faseNum,pa.facePrice,pa.fase2Num " +
            " FROM lysj_order_order od,(SELECT  COUNT( 1 ) AS fase2Num, IFNULL(SUM( act_pay_price ),0) AS facePrice FROM lysj_order_order " +
            " WHERE user_id =:userId  AND `status` = '2' AND pay_type = '3' AND pay_time >= :beginTime AND pay_time <= :endTime) pa," +
            " (SELECT COUNT( 1 ) AS faseNum,merchant_id FROM lysj_order_order WHERE user_id =:userId  AND `status` = '2' " +
            "AND pay_type = '3' AND act_pay_price >= 2 AND pay_time >= :beginTime AND pay_time <= :endTime) pd" +
            " WHERE  pay_time >= :beginTime " +
            " AND pay_time <= :endTime " +
            " AND `status` = '2' " +
            " AND od.user_id =:userId")
    List<Object[]> getMyUserIdMonth(@Param("userId") String userId, @Param("beginTime") String beginTime, @Param("endTime")String endTime);

    /**
     * 根据设置返佣查询出商户
     * @param rebateType
     * @param DelFlag
     * @return
     */
    List<Merchant> findByRebateTypeAndDelFlag(Integer rebateType,Integer DelFlag);

    /**
     * 根据电话号码查询商户
     * @param phone
     * @return
     */
    Merchant findByPhone(String phone);

    /**
     * 根据商户openId和电话号码查询商户
     * @param id
     * @param phone
     * @return
     */
    Merchant findByOpenIdAndPhone(String id ,String phone);

    /**
     * 根据openId获取商户
     * @param openId
     * @return
     */
    Merchant findByOpenId(String openId);

    /**
     * 根据设置奖励查询出商户
     * @param rewardType
     * @param DelFlag
     * @return
     */
    List<Merchant> findAllByRewardTypeAndDelFlag(Integer rewardType,Integer DelFlag);
}