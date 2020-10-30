package com.fzy.admin.fp.merchant.pc.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.pc.service.PcMerchantService;
import com.fzy.admin.fp.merchant.pc.vo.AccountInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-05-01 23:13
 * @description pc端商户模块接口
 */
@RestController
@RequestMapping("/merchant/management/pc")
@Api(value = "PcMerchantController", tags = "pc端商户模块接口")
public class PcMerchantController extends BaseContent {

    @Resource
    private PcMerchantService pcMerchantService;

    /**
     * @author Created by wtl on 2019/5/1 23:08
     * @Description 修改密码
     */
    @PostMapping("/edit_password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public Resp editPassword(@UserId String userId,
                             @RequestParam(value = "password") @ApiParam(value = "旧密码") String password,
                             @RequestParam(value = "newPassword") @ApiParam(value = "新密码") String newPassword) {
        pcMerchantService.editPassword(userId, password, newPassword);
        return Resp.success("成功修改密码");
    }

    /**
     * @author Created by wtl on 2019/5/1 23:01
     * @Description 账户信息，根据登录账号的类型不同，门店下拉框不同内容
     * 商户登录有门店下拉框列表、店长和店员只有自己的账户信息
     */
    @GetMapping("/account_info")
    @ApiOperation(value = "商户模块账户信息", notes = "商户模块账户信息")
    public Resp<AccountInfo> accountInfo(@UserId String userId) {
        return Resp.success(pcMerchantService.findAccountInfo(userId));
    }
}
