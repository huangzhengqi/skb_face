package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.repository.CostsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yy
 * @Date 2019年11月15日14:38:42
 */
@Service
public class CostsService implements BaseService<Costs> {
    @Resource
    private CostsRepository costsRepository;


    public CostsRepository getRepository() {
        return costsRepository;
    }
}
