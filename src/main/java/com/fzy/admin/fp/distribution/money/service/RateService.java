package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.Rate;
import com.fzy.admin.fp.distribution.money.repository.RateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-16 11:05:44
 **/
@Service
public class RateService implements BaseService<Rate> {
    @Resource
    private RateRepository rateRepository;


    public RateRepository getRepository() {
        return rateRepository;
    }
}

