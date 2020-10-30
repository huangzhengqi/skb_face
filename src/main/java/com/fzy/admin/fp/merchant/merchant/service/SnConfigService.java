package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.merchant.merchant.repository.SnConfigRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.SnConfig;
import com.fzy.admin.fp.merchant.merchant.repository.SnConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:35 2019/6/13
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class SnConfigService implements BaseService<SnConfig> {


    @Resource
    private SnConfigRepository snConfigRepository;


    @Override
    public SnConfigRepository getRepository() {
        return snConfigRepository;
    }
}
