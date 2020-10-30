package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.GoodsShopCart;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface GoodsShopCartRepository extends BaseRepository<GoodsShopCart> {

    GoodsShopCart findByUserIdAndGoodsId(String userId, String GoodsId);

    @Query("select new com.fzy.admin.fp.goods.vo.GoodsShopCartVO(a.goodsName,a.goodsPrice,b.num,a.goodsPic,a.id) from com.fzy.admin.fp.goods.domain.Goods a, com.fzy.admin.fp.goods.domain.GoodsShopCart b where a.id = b.goodsId and b.userId=?1")
    List<GoodsShopCart> getPage(String userId);

    @Transactional
    void deleteByUserId(String userId);

    List<GoodsShopCart> findAllByUserId(String userId);

    @Transactional
    void deleteByGoodsId(String goodsId);
}
