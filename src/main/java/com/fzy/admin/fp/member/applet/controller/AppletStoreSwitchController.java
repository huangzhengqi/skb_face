package com.fzy.admin.fp.member.applet.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.service.StoredSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ：drj.
 * @Date ：Created in 15:14 2019/5/31
 * @Description: 小程序获取储值开关
 **/
@Slf4j
@RestController
@RequestMapping("/member/store_switch/applet")
@Api(value = "AppletStoreSwitchController", tags = "小程序获取储值开关")
public class AppletStoreSwitchController extends BaseContent {

    @Resource
    private StoredSwitchService storedSwitchService;

    @ApiOperation(value = "根据商户id查询储值开关状态", notes = "根据商户id查询储值开关状态")
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(@TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedSwitchService.findByMerchantId(merchantId).getStatus());
    }
}
