package com.fzy.admin.fp.member.sem.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberCreditsRuler;
import com.fzy.admin.fp.member.sem.service.MemberCreditsRulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "MemberCreditsRulerController",tags = {"支付宝会员储值积分规则"})
@RestController
@RequestMapping("/member/sem/creditsRuler")
public class MemberCreditsRulerController {

    @Resource
    private MemberCreditsRulerService memberCreditsRulerService;

    @ApiOperation(value = "查询会员储值积分规则",notes = "查询会员储值积分规则")
    @GetMapping("/findOne")
    public Resp findOne(@TokenInfo(property = "merchantId") String merchantId){
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        return memberCreditsRulerService.findByMerchantId(merchantId);
    }

    @ApiOperation(value = "保存会员储值积分规则",notes = "保存会员储值积分规则")
    @PostMapping("/save")
    public Resp save(@RequestBody MemberCreditsRuler memberCreditsRuler,@TokenInfo(property = "merchantId") String merchantId) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        memberCreditsRuler.setMerchantId(merchantId);
        return memberCreditsRulerService.saveCreditsRuler(memberCreditsRuler);
    }
}
