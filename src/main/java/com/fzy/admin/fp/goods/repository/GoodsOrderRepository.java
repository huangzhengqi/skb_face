package com.fzy.admin.fp.goods.repository;


import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.bo.GoodsOrderBO;
import com.fzy.admin.fp.goods.bo.GoodsOrderDetailsBO;
import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.domain.GoodsOrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface GoodsOrderRepository extends BaseRepository<GoodsOrder> {
    //@Query("select new com.fzy.admin.fp.goods.bo.GoodsOrderBO(a.id,b.name,a.orderNo,a.payTime,a.orderPrice,a.payPrice,a.payType) from com.fzy.admin.fp.goods.domain.GoodsOrder a, com.fzy.admin.fp.merchant.merchant.domain.Store b where a.merchantId = ?1 and a.status=2 and a.storeId = b.id and (a.orderNo like concat('%',?2,'%') or b.name like concat('%',?2,'%')) and a.payTime >= ?3 and a.payTime <= ?4 and a.payType = ?5 ")
    //Page<GoodsOrderBO> getPageByPayType(String paramString1, String paramString2, Date paramDate1, Date paramDate2, Integer paramInteger, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.goods.bo.GoodsOrderBO(a.id,b.name,a.orderNo,a.payTime,a.orderPrice,a.payPrice,a.payType) from com.fzy.admin.fp.goods.domain.GoodsOrder a, com.fzy.admin.fp.merchant.merchant.domain.Store b where a.merchantId = ?1 and a.status=2 and a.storeId = b.id and (a.orderNo like concat('%',?2,'%') or b.name like concat('%',?2,'%')) and a.payTime >= ?3 and a.payTime <= ?4 and a.payType = ?5 and (a.industryCategory = ?6 or a.industryCategory = ?7) and a.storeId = ?8 ")
    Page<GoodsOrderBO> getPageByPayType(String paramString1, String paramString2, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString3, Pageable paramPageable);

    //@Query("select new com.fzy.admin.fp.goods.bo.GoodsOrderBO(a.id,b.name,a.orderNo,a.payTime,a.orderPrice,a.payPrice,a.payType) from com.fzy.admin.fp.goods.domain.GoodsOrder a, com.fzy.admin.fp.merchant.merchant.domain.Store b where a.merchantId = ?1 and a.status=2 and a.storeId = b.id and (a.orderNo like concat('%',?2,'%') or b.name like concat('%',?2,'%')) and a.payTime >= ?3 and a.payTime <= ?4 ")
    //Page<GoodsOrderBO> getPage(String paramString1, String paramString2, Date paramDate1, Date paramDate2, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.goods.bo.GoodsOrderBO(a.id,b.name,a.orderNo,a.payTime,a.orderPrice,a.payPrice,a.payType) from com.fzy.admin.fp.goods.domain.GoodsOrder a, com.fzy.admin.fp.merchant.merchant.domain.Store b where a.merchantId = ?1 and a.status=2 and a.storeId = b.id and (a.orderNo like concat('%',?2,'%') or b.name like concat('%',?2,'%')) and a.payTime >= ?3 and a.payTime <= ?4 and (a.industryCategory = ?5 or a.industryCategory = ?6) and a.storeId = ?7 ")
    Page<GoodsOrderBO> getPage(String paramString1, String paramString2, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, String paramString3, Pageable paramPageable);

    @Query(value = "SELECT a.*,b.name as storeName,c.nickname,c.member_level_id,c.head FROM goods_order a LEFT JOIN lysj_merchant_store b ON a.store_id = b.id LEFT JOIN lysj_member_member c ON a.member_id = c.id WHERE a.id = ?1", nativeQuery = true)
    GoodsOrderDetailsBO getDetails(String paramString);
}
