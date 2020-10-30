package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.RateStandards;
import com.fzy.admin.fp.distribution.money.repository.RateStandardsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-1-10 16:12:40
 * @Desp
 **/
@Service
public class RateStandardsService implements BaseService<RateStandards> {
    @Resource
    private RateStandardsRepository rateStandardsRepository;


    public RateStandardsRepository getRepository() {
        return rateStandardsRepository;
    }
}
