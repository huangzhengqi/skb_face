package com.fzy.admin.fp.goods.service;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.goods.domain.GoodsLib;
import com.fzy.admin.fp.goods.repository.GoodsLibRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsLibService extends Object implements BaseService<GoodsLib>
{
    @Resource
    private GoodsLibRepository goodsLibRepository;

    public BaseRepository<GoodsLib> getRepository() { return this.goodsLibRepository; }




    public GoodsLib findByCode(String code) { return this.goodsLibRepository.findByCode(code); }
}
