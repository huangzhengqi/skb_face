package com.fzy.admin.fp.distribution.order.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.order.repository.AfterSaleRepository;
import com.fzy.admin.fp.distribution.pc.domain.AfterSale;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class AfterSaleService implements BaseService<AfterSale> {

    @Resource
    private AfterSaleRepository afterSaleRepository;

    @Override
    public AfterSaleRepository getRepository() {
        return afterSaleRepository;
    }
}
