package com.fzy.admin.fp.merchant.management.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zk
 * @description 用户表数据处理层
 * @create 2018-07-25 15:02:19
 **/
public interface MerchantUserRepository extends BaseRepository<MerchantUser> {


    MerchantUser findByUsernameAndDelFlag(String username, Integer delFlag);

    MerchantUser findByMerchantIdAndUserTypeAndDelFlag(String merchantId, String userType, Integer delFlag);

    /**
     * 商户下拉框
     */
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(u.id,u.name) from MerchantUser u where u.storeId is null and u.delFlag=1 ")
    List<SelectItem> selectItem();


    //后台获取商户下所有员工
    @Query("select new com.fzy.admin.fp.merchant.management.vo.UserVo(u.id,u.name,u.username,u.phone,u.sex,u.storeId,u.userType,u.status,u.photoId) from MerchantUser u  " +
            "WHERE u.merchantId LIKE CONCAT('%',:merchantId,'%') and u.storeId LIKE CONCAT('%',:storeId,'%') and u.name LIKE CONCAT('%',:name,'%') and u.phone LIKE CONCAT('%',:phone,'%') and u.id LIKE CONCAT('%',:id,'%') and u.delFlag=1 and u.userType in(2,3) ")
    Page<UserVo> findByMerchantId(@Param("merchantId") String merchantId, @Param("storeId") String storeId, @Param("name") String name, @Param("phone") String phone, @Param("id") String id, Pageable pageable);

    //APP获取商户下所有员工
    @Query("select new com.fzy.admin.fp.merchant.management.vo.UserVo(u.id,u.name,u.username,u.phone,u.sex,u.storeId,u.userType,u.status,u.photoId) from MerchantUser u  " +
            "WHERE u.merchantId LIKE CONCAT('%',:merchantId,'%') and u.storeId LIKE CONCAT('%',:storeId,'%') and u.userType LIKE CONCAT('%',:userType,'%') and u.status LIKE CONCAT('%',:status,'%') and u.name LIKE CONCAT('%',:name,'%') and u.delFlag=1 and u.userType in(2,3) ")
    Page<UserVo> findByMerchantIdApp(@Param("merchantId") String merchantId, @Param("storeId") String storeId, @Param("userType") String userType, @Param("status") String status, @Param("name") String name, Pageable pageable);


    //统计门店下的员工人数(启用状态下)
    Integer countByStoreIdAndStatusAndDelFlag(String storeId, Integer status, Integer delFlag);

    //统计商户的所有员工人数
    Integer countByMerchantId(String storeId);

    //获取商户下的所有用户(不包含商户)
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from MerchantUser s where s.merchantId=?1 and s.delFlag=1 and s.status=1 and s.userType in(2,3)")
    List<SelectItem> selectItemByMchId(String merchantId);

    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from MerchantUser s where  s.storeId=?1 and s.delFlag=1 and s.status=1 and s.userType in(2,3)")
    List<SelectItem> selectItemByStoreId(String storeId);


    Integer countByUsernameAndDelFlag(String userName, Integer delFlag);

    //根据ids获取数据
    @Query("select new com.fzy.admin.fp.merchant.management.vo.UserVo(u.id,u.name,u.username,u.phone,u.sex,u.storeId,u.userType,u.status,u.photoId) from MerchantUser u  " +
            "WHERE u.id in (:asList) and u.delFlag=1 order by u.createTime desc")
    List<UserVo> findByIdsInOrderByCreateTime(@Param("asList") List<String> asList);

    MerchantUser findByUsernameAndUserType(String userName,String userType);

    /**
     * 根据门店查询用户
     * @param storeId
     * @return
     */
    List<MerchantUser> findByStoreId(String storeId);
}