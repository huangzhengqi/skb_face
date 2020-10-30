package com.fzy.admin.fp.distribution.pc.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;

import java.util.List;

public interface GoodsPropertyRepository extends BaseRepository<GoodsProperty> {

    void deleteByGoodsId(String s);
}
