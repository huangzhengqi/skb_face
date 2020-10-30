package com.fzy.admin.fp.distribution.money.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.repository.WalletRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-16 18:11:09
 **/
@Service
public class WalletService implements BaseService<Wallet> {
    @Resource
    private WalletRepository walletRepository;

    @Override
    public WalletRepository getRepository() {
        return walletRepository;
    }
}
