package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 门店dao
 */
public interface StoreRepository extends BaseRepository<Store> {

    //获取启用门店的下拉框
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from Store s where s.merchantId=?1 and s.delFlag=1 and s.status=1 ")
    List<SelectItem> selectItem(String merchantId);

    List<Store> findByMerchantIdAndStatus(String merchantId, Integer status);

    //获取商户默认门店
    Store findByMerchantIdAndStoreFlag(String merchantId, Integer storeFlag);

    Integer countByMerchantId(String merchantId);

    /**
     * 根据商户下的所有门店
     *
     * @param merchantId
     * @param name
     * @return
     */
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from Store s where s.merchantId=:merchantId and s.delFlag=1 and s.status=1 AND s.name LIKE CONCAT('%',:name,'%')")
    List<SelectItem> selectItemByMchId(@Param("merchantId") String merchantId, @Param("name") String name);

    /**
     * 根据门店id查询当前门店
     *
     * @param storeId
     * @param name
     * @return
     */
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(s.id,s.name) from Store s where s.id=:storeId and s.delFlag=1 and s.status=1 AND s.name LIKE CONCAT('%',:name,'%')")
    List<SelectItem> selectItemByStoreId(@Param("storeId") String storeId, @Param("name") String name);

    List<Store> findByMerchantId(String merchantId);

    List<Store> findByMerchantIdIn(List<String> merchantId);
}