package com.fzy.admin.fp.member.sem.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberPayRule;
import com.fzy.admin.fp.member.sem.service.MemberPayRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Api(value = "MemberPayRuleController",tags = {"支付宝会员支付设置"})
@RestController
@RequestMapping("/member/sem/payRule")
@Slf4j
public class MemberPayRuleController {


    @Resource
    private MemberPayRuleService memberPayRuleService;


    @ApiOperation(value = "获取会员支付设置详情",notes = "获取会员支付设置详情")
    @GetMapping(value = "/findOne")
    public Resp findOne(@TokenInfo(property = "merchantId") String merchantId){
        if (null == merchantId) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        return memberPayRuleService.findByMerchantId(merchantId);
    }


    @ApiOperation(value="保存会员支付设置",notes = "保存会员支付设置")
    @PostMapping("/save")
    public Resp savePayRule(@RequestBody MemberPayRule memberPayRule,@TokenInfo(property = "merchantId") String merchantId) {
        if (null == merchantId) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        Resp<MemberPayRule> resp = memberPayRuleService.findByMerchantId(memberPayRule.getMerchantId());
        MemberPayRule payRule = resp.getObj();
        if(null != payRule) {
            memberPayRule.setId(payRule.getId());
        }
        memberPayRule.setMerchantId(merchantId);
        return Resp.success(memberPayRuleService.save(memberPayRule),"设置成功");
    }
}
