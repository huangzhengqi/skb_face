package com.fzy.admin.fp.goods.repository;

import cn.hutool.core.date.DateTime;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.GoodsOrderItems;
import com.fzy.admin.fp.goods.dto.GoodsOrderItemsDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface GoodsOrderItemsRepository extends BaseRepository<GoodsOrderItems> {

    List<GoodsOrderItems> findByGoodsOrderId(String paramString);

    List<GoodsOrderItems> findByGoodsNameAndCreateTimeBetween(String goodsName, DateTime startTime, DateTime endTime);

    @Query(value = "select new com.fzy.admin.fp.goods.dto.GoodsOrderItemsDTO(sum (g.totalPrice)) from GoodsOrderItems g where g.goodsName=?1  and g.createTime between ?2 and ?3")
    GoodsOrderItemsDTO findByCountTotalPrice(String goodsName, DateTime startTime, DateTime endTime);

    @Query(value = "select new com.fzy.admin.fp.goods.dto.GoodsOrderItemsDTO(sum (g.totalPrice)) from GoodsOrderItems g ")
    GoodsOrderItemsDTO findByCountTotalPriceNum();

    List<GoodsOrderItems> findAllByGoodsIdAndCreateTimeBetween(String goodsName, Date startTime, Date endTime);

    List<GoodsOrderItems> findAllByGoodsNameAndCreateTimeBetween(String goodsName, Date startTime, Date endTime);

    List<GoodsOrderItems> findByGoodsName(String goodsName);
}
