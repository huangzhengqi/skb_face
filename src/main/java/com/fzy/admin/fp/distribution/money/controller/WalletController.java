package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-18 17:44:10
 * @Desp 余额控制层
 **/
@RestController
@RequestMapping("/dist/wallet")
@Api(value = "UserController", tags = {"分销-钱包"})
public class WalletController extends BaseContent {
    @Resource
    private WalletService walletService;

    /**
     * 查询余额
     * @param userId
     * @return
     */
    @GetMapping("/find")
    @ApiOperation(value = "用户资料", notes = "余额查询")
    public Resp find (@UserId String userId){
        return Resp.success(walletService.getRepository().findByUserId(userId));
    }
}
