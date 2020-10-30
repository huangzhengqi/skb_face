package com.fzy.admin.fp.member.sem.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliStoredRule;
import com.fzy.admin.fp.member.sem.service.MemberAliStoreRuleService;
import com.fzy.admin.fp.member.sem.service.StoredAliSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "支付宝会员储值规则",tags = {"支付宝会员储值规则"})
@RestController
@RequestMapping("/member/sem/storeRule")
public class MemberAliStoreRuleController{

    @Resource
    private StoredAliSwitchService storedAliSwitchService;

    @Resource
    private MemberAliStoreRuleService  memberAliStoreRuleService;

    @ApiOperation(value = "自定义储值开关状态",notes = "自定义储值开关状态")
    @PostMapping("/findSwitch")
    public Resp findSwitch(@TokenInfo(property = "merchantId") String merchantId,MemberAliStoredRule memberAliStoredRule){
        memberAliStoredRule.setMerchantId(merchantId);
        return storedAliSwitchService.findByMerchantId(memberAliStoredRule);
    }

    @ApiOperation(value = "保存储值规则",notes = "保存储值规则")
    @PostMapping("/save")
    public Resp save(@RequestBody MemberAliStoredRule memberAliStoredRule,@TokenInfo(property = "merchantId") String merchantId){
        memberAliStoredRule.setMerchantId(merchantId);
        return memberAliStoreRuleService.saveStoreRule(memberAliStoredRule);
    }

    @ApiOperation(value = "修改可用",notes = "修改可用")
    @PostMapping("/changeStatus")
    public Resp changeStatus(@RequestBody MemberAliStoredRule memberAliStoredRule){
        if(null == memberAliStoredRule.getId()){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        if(null == memberAliStoredRule.getStatus()){
            return new Resp().error(Resp.Status.PARAM_ERROR,"状态参数为空");
        }
        MemberAliStoredRule aliRule = memberAliStoreRuleService.findOne(memberAliStoredRule.getId());
        if(null == aliRule) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"储值规则不存在");
        }
        aliRule.setStatus(memberAliStoredRule.getStatus());
        return Resp.success(memberAliStoreRuleService.save(aliRule));
    }

    @ApiOperation(value = "储值规则列表",notes = "储值规则列表")
    @GetMapping("/list")
    public Resp findList(PageVo pageVo){
        return Resp.success(memberAliStoreRuleService.list(new MemberAliStoredRule() ,pageVo));
    }

}
