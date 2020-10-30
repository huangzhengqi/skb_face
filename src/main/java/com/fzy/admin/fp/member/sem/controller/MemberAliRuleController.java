package com.fzy.admin.fp.member.sem.controller;


import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import com.fzy.admin.fp.member.sem.domain.MemberAliRule;
import com.fzy.admin.fp.member.sem.domain.MemberPayRule;
import com.fzy.admin.fp.member.sem.dto.MemberAliRuleDTO;
import com.fzy.admin.fp.member.sem.service.MemberAliLevelService;
import com.fzy.admin.fp.member.sem.service.MemberAliRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@Api(value = "MemberAliRuleController",tags = {"支付宝满减规则"})
@RestController
@RequestMapping("/member/sem/rule")
public class MemberAliRuleController {

    @Resource
    private MemberAliRuleService memberAliRuleService;

    @ApiOperation(value="满减规则列表",notes = "满减规则列表")
    @GetMapping("/list")
    public Resp findList(@TokenInfo(property = "merchantId") String merchantId, MemberAliRuleDTO memberAliRuleDTO, PageVo pageVo){
        if(null == merchantId) {
            return  new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        memberAliRuleDTO.setMerchantId(merchantId);
        return Resp.success(memberAliRuleService.list(memberAliRuleDTO,pageVo));
    }

    @ApiOperation(value="保存满减规则",notes = "保存满减规则")
    @PostMapping("/save")
    public Resp savePayRule(@RequestBody MemberAliRule memberAliRule, @TokenInfo(property = "merchantId") String merchantId) {
        if (null == merchantId) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        memberAliRule.setMerchantId(merchantId);
        return Resp.success(memberAliRuleService.save(memberAliRule));
    }

    @ApiOperation(value = "修改可用",notes = "修改可用")
    @PostMapping("/changeStatus")
    public Resp changeStatus(@RequestBody MemberAliRule memberAliRule) {
        if(null == memberAliRule.getId()){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        if(null == memberAliRule.getStatus()){
            return new Resp().error(Resp.Status.PARAM_ERROR,"状态参数为空");
        }
        MemberAliRule aliRule = memberAliRuleService.findOne(memberAliRule.getId());
        if(null == aliRule) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"满减规则不存在");
        }
        aliRule.setStatus(memberAliRule.getStatus());
        return Resp.success(memberAliRuleService.save(aliRule));
    }
}
