package com.fzy.admin.fp.member.rule.controller;

import com.fzy.admin.fp.member.rule.service.StoredSwitchService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredSwitch;
import com.fzy.admin.fp.member.rule.service.StoredSwitchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:45 2019/5/14
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/member/stored_switch")
public class StoredSwitchController extends BaseController<StoredSwitch> {

    @Resource
    private StoredSwitchService storedSwitchService;

    @Override
    public StoredSwitchService getService() {
        return storedSwitchService;
    }

    //根据商户id查询储值规则
    @GetMapping("/find_by_merchantId")
    public Resp findByMerchantId(@TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedSwitchService.findByMerchantId(merchantId));
    }
}
