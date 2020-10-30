package com.fzy.admin.fp.goods.repository;


import cn.hutool.core.date.DateTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface GoodsRepository extends BaseRepository<Goods> {
    @Query("FROM com.fzy.admin.fp.goods.domain.Goods a WHERE a.merchantId = ?1 and (a.goodsName like CONCAT('%',?2,'%') OR a.itemNumber like CONCAT('%',?2,'%')) and a.industryCategory = ?3 and a.storeId = ?4")
    Page<Goods> getPage(String paramString1, String paramString2, Integer paramInteger, String paramString3, Pageable paramPageable);

    @Query("FROM com.fzy.admin.fp.goods.domain.Goods a WHERE a.merchantId = ?1 and (a.goodsName like CONCAT('%',?2,'%') OR a.itemNumber like CONCAT('%',?2,'%')) and a.industryCategory = ?3 and a.isShelf = ?4 and a.storeId = ?5")
    Page<Goods> getPage(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.goods.vo.GoodsVO(a.id,a.goodsName,g.cagegoryName,a.itemNumber,a.goodsPrice,a.isShelf,a.sort,a.stockNum,a.salesNum,a.unit,a.goodsPic) " +
            "FROM com.fzy.admin.fp.goods.domain.Goods a,com.fzy.admin.fp.goods.domain.GoodsCategory g WHERE " +
            " a.goodsCategory=g.id and a.merchantId = ?1 and (a.goodsName like CONCAT('%',?2,'%') " +
            "OR a.itemNumber like CONCAT('%',?2,'%')) and a.industryCategory = ?3 and a.storeId = ?4 order by a.sort desc")
    Page<Goods> getGoodsPage(String paramString1, String paramString2, Integer paramInteger, String paramString3, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.goods.vo.GoodsVO(a.id,a.goodsName,g.cagegoryName,a.itemNumber,a.goodsPrice,a.isShelf,a.sort,a.stockNum,a.salesNum,a.unit,a.goodsPic) " +
            " FROM com.fzy.admin.fp.goods.domain.Goods a,com.fzy.admin.fp.goods.domain.GoodsCategory g WHERE a.goodsCategory=g.id and a.merchantId = ?1 and (a.goodsName like CONCAT('%',?2,'%') OR a.itemNumber like CONCAT('%',?2,'%')) and a.industryCategory = ?3 and a.isShelf = ?4 and a.storeId = ?5 order by a.sort desc ")
    Page<Goods> getGoodsPage(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3, Pageable paramPageable);
    Goods findByStoreIdAndGoodsCodeAndIndustryCategory(String paramString1, String paramString2, Integer paramInteger);

    Goods findByStoreIdAndItemNumberAndIndustryCategory(String paramString1, String paramString2, Integer paramInteger);

    Integer countByStoreIdAndGoodsCategory(String paramString1, String paramString2);

    Integer countByMerchantIdAndIsShelfAndIndustryCategoryAndStoreId(String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2);

    List<Goods> findByMerchantId(String id);

    Goods findByStoreIdAndGoodsCode(String store, String code);

    List<Goods> findByGoodsCategoryAndStoreId(String goodsCategory,String storeId);

    Goods findByStoreIdAndGoodsName(String store, String goodsName);

    List<Goods> findByStoreIdAndGoodsCategoryAndIndustryCategoryOrderByCreateTimeDesc(String storeId, String id, Integer industryCategory);

    Goods findByIdAndStoreId(String id ,String storeId);
}
