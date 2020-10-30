package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.GoodsOrderItems;
import com.fzy.admin.fp.goods.repository.GoodsOrderItemsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Transactional
@Service
public class GoodsOrderItemsService extends Object implements BaseService<GoodsOrderItems> {
    @Resource
    private GoodsOrderItemsRepository goodsOrderItemsRepository;

    public BaseRepository<GoodsOrderItems> getRepository() { return this.goodsOrderItemsRepository; }
}
