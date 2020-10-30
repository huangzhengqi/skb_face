package com.fzy.admin.fp.distribution.order.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.order.repository.WxUserRepository;
import com.fzy.admin.fp.distribution.pc.domain.WxUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class WxUserService implements BaseService<WxUser> {

    @Resource
    private WxUserRepository wxUserRepository;

    @Override
    public WxUserRepository getRepository() {
        return wxUserRepository;
    }

}
