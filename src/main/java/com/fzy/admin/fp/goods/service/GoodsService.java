package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;


@Service
@Transactional
public class GoodsService implements BaseService<Goods> {
    @Resource
    private GoodsRepository goodsRepository;

    public GoodsRepository getRepository() {
        return this.goodsRepository;
    }


    public Page<Goods> findByGoodsNameOriOrItemNumber(Goods goods, Pageable pageable) {
        if (StringUtils.isBlank(goods.getGoodsName())) {
            goods.setGoodsName("");
        }
        if (goods.getIsShelf() == null) {
            return this.goodsRepository.getPage(goods.getMerchantId(), goods.getGoodsName(),goods.getIndustryCategory(),goods.getStoreId(), pageable);
        }
        return this.goodsRepository.getPage(goods.getMerchantId(), goods.getGoodsName(), goods.getIndustryCategory(), goods.getIsShelf(), goods.getStoreId(), pageable);
    }

    public Page<Goods> findByGoodsList(Goods goods, Pageable pageable) {
        if (StringUtils.isBlank(goods.getGoodsName())) {
            goods.setGoodsName("");
        }
        if (goods.getIsShelf() == null) {
            return this.goodsRepository.getGoodsPage(goods.getMerchantId(), goods.getGoodsName(),goods.getIndustryCategory(),goods.getStoreId(), pageable);
        }
        return this.goodsRepository.getGoodsPage(goods.getMerchantId(), goods.getGoodsName(), goods.getIndustryCategory(), goods.getIsShelf(), goods.getStoreId(), pageable);
    }


    public Resp saveGoods(Goods entity) {
        Goods goods=this.goodsRepository.findByStoreIdAndGoodsCodeAndIndustryCategory(entity.getStoreId(), entity.getGoodsCode(), entity.getIndustryCategory());
        if (goods != null && !goods.getId().equals(entity.getId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "条形码已存在");
        }
        if (StringUtils.isNotBlank(entity.getItemNumber())) {
            goods=this.goodsRepository.findByStoreIdAndItemNumberAndIndustryCategory(entity.getStoreId(), entity.getItemNumber(), entity.getIndustryCategory());
            if (goods != null && !goods.getId().equals(entity.getId())) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "商品编码");
            }
        }
        if (entity.getId() == null) {
            entity.setSort(Integer.valueOf(1));
            entity.setSalesNum(Integer.valueOf(0));
        }
        entity.setSource(Integer.valueOf(1));
        this.goodsRepository.save(entity);
        return Resp.success("操作成功");
    }

}
