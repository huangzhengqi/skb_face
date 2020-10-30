package com.fzy.admin.fp.distribution.pc.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.repository.SystemSetupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-2-24 10:37:57
 * @Desp
 **/
@Service
public class SystemSetupService implements BaseService<SystemSetup> {

    @Resource
    private SystemSetupRepository systemSetupRepository;

    public SystemSetupRepository getRepository() {
        return systemSetupRepository;
    }
}
