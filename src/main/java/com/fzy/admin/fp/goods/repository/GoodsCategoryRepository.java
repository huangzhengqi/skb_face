package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.bo.GoodsCategoryBO;
import com.fzy.admin.fp.goods.domain.GoodsCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsCategoryRepository extends BaseRepository<GoodsCategory> {

    GoodsCategory findByMerchantIdAndCagegoryName(String paramString1, String paramString2);

    GoodsCategory findByStoreIdAndParentIdAndCagegoryNameAndIndustryCategory(String paramString1, String paramString2, String paramString3, Integer paramInteger);

    List<GoodsCategory> findByMerchantIdAndParentIdAndIndustryCategoryAndStoreIdOrderBySort(String paramString1, String paramString2, Integer paramInteger, String paramString3);

    @Query("SELECT new com.fzy.admin.fp.goods.bo.GoodsCategoryBO(a.id,a.cagegoryName,a.level,a.sort,a.parentId) FROM com.fzy.admin.fp.goods.domain.GoodsCategory a WHERE a.merchantId = ?1 and a.parentId = ?2 and a.industryCategory = ?3 and a.storeId = ?4 ")
    Page<GoodsCategoryBO> getCategoryPage(String paramString1, String paramString2, Integer paramInteger, String paramString3, Pageable paramPageable);

    List<GoodsCategory> findByParentId(String paramString);

    List<GoodsCategory> findByStoreIdAndIndustryCategoryOrderBySort(String storeId ,Integer industryCategory);
}
