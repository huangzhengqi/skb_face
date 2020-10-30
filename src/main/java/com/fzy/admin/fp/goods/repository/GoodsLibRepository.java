package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.GoodsLib;

public interface GoodsLibRepository extends BaseRepository<GoodsLib> {
    GoodsLib findByCode(String paramString);
}
