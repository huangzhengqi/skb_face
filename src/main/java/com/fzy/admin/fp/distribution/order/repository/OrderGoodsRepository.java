package com.fzy.admin.fp.distribution.order.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.order.domain.OrderGoods;

import java.util.List;

public interface OrderGoodsRepository extends BaseRepository<OrderGoods> {
    List<OrderGoods> findAllByShopOrderId(String shopOrderId);
}
