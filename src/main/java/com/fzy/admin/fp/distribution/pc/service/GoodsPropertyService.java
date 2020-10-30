package com.fzy.admin.fp.distribution.pc.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;
import com.fzy.admin.fp.distribution.pc.repository.GoodsPropertyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-30 14:03:39
 * @Desp
 **/
@Service
public class GoodsPropertyService implements BaseService<GoodsProperty> {
    @Resource
    private GoodsPropertyRepository goodsPropertyRepository;

    public GoodsPropertyRepository getRepository() {
        return goodsPropertyRepository;
    }
}
