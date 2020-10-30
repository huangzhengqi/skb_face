package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.SalesmanRate;
import com.fzy.admin.fp.distribution.money.repository.SalesmanRateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-18 11:15:50
 * @Desp 业务员费率设置
 **/
@Service
public class SalesmanRateService implements BaseService<SalesmanRate>{
    @Resource
    private SalesmanRateRepository salesmanRateRepository;

    @Override
    public SalesmanRateRepository getRepository() {
        return salesmanRateRepository;
    }
}
