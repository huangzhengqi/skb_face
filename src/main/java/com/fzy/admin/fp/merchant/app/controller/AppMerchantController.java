package com.fzy.admin.fp.merchant.app.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.pc.service.PcMerchantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-05-01 23:13
 * @description app端商户模块接口
 */
@RestController
@RequestMapping("/merchant/management/app")
public class AppMerchantController extends BaseContent {

    @Resource
    private PcMerchantService pcMerchantService;

    /**
     * @author Created by wtl on 2019/5/1 23:08
     * @Description 修改密码
     */
    @PostMapping("/edit_password")
    public Resp editPassword(@UserId String userId, String password, String newPassword) {
        pcMerchantService.editPassword(userId, password, newPassword);
        // 修改密码
        return Resp.success("成功修改密码");
    }

    /**
     * @author Created by wtl on 2019/5/1 23:01
     * @Description 账户信息，根据登录账号的类型不同，门店下拉框不同内容
     * 商户登录有门店下拉框列表、店长和店员只有自己的账户信息
     */
    @GetMapping("/account_info")
    public Resp accountInfo(@UserId String userId) {
        return Resp.success(pcMerchantService.findAccountInfo(userId));
    }

}
