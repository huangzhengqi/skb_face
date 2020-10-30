package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.repository.YunHornRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.YunHorn;
import com.fzy.admin.fp.auth.repository.YunHornRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:34 2019/7/1
 * @ Description: 云喇叭服务层
 **/
@Slf4j
@Service
@Transactional
public class YunHornService implements BaseService<YunHorn> {

    @Resource
    private YunHornRepository yunHornRepository;

    @Override
    public YunHornRepository getRepository() {
        return yunHornRepository;
    }
}
