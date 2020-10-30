package com.fzy.admin.fp.distribution.pc.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.domain.AppDown;
import com.fzy.admin.fp.distribution.pc.repository.AppDownRepository;
import com.fzy.admin.fp.distribution.pc.repository.GoodsPropertyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-16 09:38:25
 * @Desp
 **/

@Service
public class AppDownService implements BaseService<AppDown> {
    @Resource
    private AppDownRepository appDownRepository;

    public AppDownRepository getRepository() {
        return appDownRepository;
    }
}
