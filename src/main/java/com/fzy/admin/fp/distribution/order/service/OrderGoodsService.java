package com.fzy.admin.fp.distribution.order.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.order.domain.OrderGoods;
import com.fzy.admin.fp.distribution.order.repository.OrderGoodsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-3 17:05:08
 * @Desp
 **/
@Service
public class OrderGoodsService implements BaseService<OrderGoods> {

    @Resource
    private OrderGoodsRepository orderPropertyRepository;


    public OrderGoodsRepository getRepository() {
        return orderPropertyRepository;
    }
}
