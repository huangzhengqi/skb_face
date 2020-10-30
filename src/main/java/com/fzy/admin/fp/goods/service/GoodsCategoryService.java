package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.bo.GoodsCategoryBO;
import com.fzy.admin.fp.goods.domain.GoodsCategory;
import com.fzy.admin.fp.goods.repository.GoodsCategoryRepository;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsCategoryService implements BaseService<GoodsCategory> {
    @Resource
    private GoodsCategoryRepository goodsCategoryRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    public GoodsCategoryRepository getRepository() {
        return this.goodsCategoryRepository;
    }


    public Resp saveCategory(GoodsCategory entity) {
        GoodsCategory goodsCategory=this.goodsCategoryRepository.findByStoreIdAndParentIdAndCagegoryNameAndIndustryCategory(entity.getStoreId(), entity.getParentId(), entity.getCagegoryName(), entity.getIndustryCategory());
        if (goodsCategory != null && !goodsCategory.getId().equals(entity.getId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "分类名称重复");
        }
        this.goodsCategoryRepository.save(entity);
        return Resp.success("操作成功");
    }


    public List<GoodsCategory> getList(String merchantId, String parentId,Integer industryCategory,String storeId) {
        return this.goodsCategoryRepository.findByMerchantIdAndParentIdAndIndustryCategoryAndStoreIdOrderBySort(merchantId, parentId,industryCategory,storeId);
    }


    public Page<GoodsCategoryBO> getCategoryPage(GoodsCategory goodsCategory, Pageable pageable) {
        Page<GoodsCategoryBO> goodsCategoryBOPage=this.goodsCategoryRepository.getCategoryPage(goodsCategory.getMerchantId(), goodsCategory.getParentId(), goodsCategory.getIndustryCategory(), goodsCategory.getStoreId(), pageable);
        List<GoodsCategoryBO> categoryBOList=goodsCategoryBOPage.getContent();
        if (categoryBOList != null && categoryBOList.size() > 0) {
            for (GoodsCategoryBO categoryBO : categoryBOList) {
                categoryBO.setGoodsNum(this.goodsRepository.countByStoreIdAndGoodsCategory(goodsCategory.getStoreId(), categoryBO.getId()));
            }
        }
        return goodsCategoryBOPage;
    }
}
