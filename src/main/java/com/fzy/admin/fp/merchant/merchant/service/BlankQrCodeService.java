package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.merchant.merchant.repository.BlankQrCodeRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.BlankQrCode;
import com.fzy.admin.fp.merchant.merchant.repository.BlankQrCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:34 2019/4/30
 * @ Description: 空二维码服务层
 **/
@Slf4j
@Service
@Transactional
public class BlankQrCodeService implements BaseService<BlankQrCode> {

    @Resource
    private BlankQrCodeRepository blankQrCodeRepository;


    @Override
    public BlankQrCodeRepository getRepository() {
        return blankQrCodeRepository;
    }
}
