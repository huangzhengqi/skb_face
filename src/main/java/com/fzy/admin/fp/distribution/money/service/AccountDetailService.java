package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.repository.AccountDetailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-18 10:10:05
 **/
@Service
public class AccountDetailService implements BaseService<AccountDetail> {
    @Resource
    private AccountDetailRepository accountDetailRepository;


    public AccountDetailRepository getRepository() {
        return accountDetailRepository;
    }
}
