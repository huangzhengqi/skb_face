package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.GoodsImportRecord;
import com.fzy.admin.fp.goods.repository.GoodsImportRecordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsImportRecordService extends Object implements BaseService<GoodsImportRecord>
{
    @Resource
    private GoodsImportRecordRepository goodsImportRecordRepository;

    public BaseRepository<GoodsImportRecord> getRepository() { return this.goodsImportRecordRepository; }
}
