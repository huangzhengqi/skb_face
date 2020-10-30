package com.fzy.admin.fp.goods.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.goods.domain.GoodsImportRecord;

import java.util.List;

public interface GoodsImportRecordRepository extends BaseRepository<GoodsImportRecord> {

    List<GoodsImportRecord> findByImportId(String paramString);
}
