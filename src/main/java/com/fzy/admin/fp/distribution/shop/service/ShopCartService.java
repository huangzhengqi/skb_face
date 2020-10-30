package com.fzy.admin.fp.distribution.shop.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.repository.GoodsPropertyRepository;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.domain.ShopCart;
import com.fzy.admin.fp.distribution.shop.repository.DistGoodsRepository;
import com.fzy.admin.fp.distribution.shop.repository.ShopCartRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-2 10:46:29
 * @Desp
 **/
@Service
public class ShopCartService implements BaseService<ShopCart> {

    @Resource
    private ShopCartRepository shopCartRepository;

    public ShopCartRepository getRepository() {
        return shopCartRepository;
    }


    public void deleteByUserId(String userId) {
        shopCartRepository.deleteByUserId(userId);
    }
}
