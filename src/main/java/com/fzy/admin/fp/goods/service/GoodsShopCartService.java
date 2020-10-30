package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.GoodsShopCart;
import com.fzy.admin.fp.goods.repository.GoodsShopCartRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsShopCartService implements BaseService<GoodsShopCart> {

    @Resource
    private GoodsShopCartRepository goodsShopCartRepository;

    public GoodsShopCartRepository getRepository() {
        return goodsShopCartRepository;
    }

    public void deleteByUserId(String userId) {
        goodsShopCartRepository.deleteByUserId(userId);
    }
}
