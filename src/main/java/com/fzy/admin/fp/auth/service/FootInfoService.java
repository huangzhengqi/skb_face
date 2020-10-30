package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.domain.FootInfo;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.FootInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-27 14:17:18
 * @Desp
 **/
@Service
public class FootInfoService implements BaseService<FootInfo> {
    @Resource
    private FootInfoRepository footInfoRepository;

    public FootInfoRepository getRepository() {
        return footInfoRepository;
    }
}
