package com.fzy.admin.fp.distribution.shop.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.shop.domain.ShopCart;
import org.hibernate.sql.Delete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ShopCartRepository extends BaseRepository<ShopCart> {
    ShopCart findByUserIdAndGoodsIdAndProperty(String userId,String goodsId,String property);

    ShopCart findByUserIdAndGoodsId(String userId,String goodsId);

    List<ShopCart> findAllByUserId(String userId);

    @Transactional
    void deleteByUserId(String userId);


    @Transactional
    void deleteByGoodsId(String id);

    @Query("select new com.fzy.admin.fp.distribution.shop.vo.ShopCartVO(a.name,a.price,b.num,b.property,a.img,a.id) from com.fzy.admin.fp.distribution.shop.domain.DistGoods a, com.fzy.admin.fp.distribution.shop.domain.ShopCart b where a.id = b.goodsId and b.userId=?1")
    List<ShopCart> getPage(String userId);
}
