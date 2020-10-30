package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.merchant.merchant.domain.BlankQrCode;
import com.fzy.admin.fp.merchant.merchant.service.BlankQrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:35 2019/4/30
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/blank_qr_code")
public class BlankQrCodeController extends BaseController<BlankQrCode> {

    @Resource
    private BlankQrCodeService blankQrCodeService;

    @Override
    public BlankQrCodeService getService() {
        return blankQrCodeService;
    }


}
