package com.fzy.admin.fp.member.sem.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberCardTemplate;
import com.fzy.admin.fp.member.sem.dto.MemberCardTemplateDTO;
import com.fzy.admin.fp.member.sem.service.MemberCardTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;



@Slf4j
@RestController
@RequestMapping("/member/sem/cardTemplate")
@Api(value = "MemberCardTemplateController", tags = {"支付宝会员卡模板相关"})
public class MemberCardTemplateController {

    @Resource
    private MemberCardTemplateService memberCardTemplateService;

    @PostMapping("/detail")
    @ApiOperation(value = "获取模板详情",notes = "获取模板详情")
    public Resp<MemberCardTemplate> detail(String id){
        if(null == id) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        return Resp.success(memberCardTemplateService.findOne(id),"获取成功");
    }


    @PostMapping("/max")
    @ApiOperation(value="获取最新模板",notes = "获取最新模板")
    public Resp<MemberCardTemplate> max(@TokenInfo(property = "merchantId") String merchantId){
        if(null == merchantId) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }

        log.info("--------------------------------------------{}",merchantId);
        return Resp.success(memberCardTemplateService.findMax(merchantId),"获取成功");
    }


    @PostMapping("/save")
    @ApiOperation(value="保存模板",notes = "保存模板")
    public Resp<MemberCardTemplate> save(@RequestBody MemberCardTemplateDTO memberCardTemplateDTO){
        if(null == memberCardTemplateDTO) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        return memberCardTemplateService.save(memberCardTemplateDTO);
    }

    @PostMapping("/update")
    @ApiOperation(value="更新模板",notes = "更新模板")
    public Resp<MemberCardTemplate> update(@RequestBody MemberCardTemplateDTO memberCardTemplateDTO) {
        if(null == memberCardTemplateDTO) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        return memberCardTemplateService.update(memberCardTemplateDTO);
    }

    @PostMapping("/setTemplateForm")
    @ApiOperation(value="配置会员卡开卡表单",notes = "配置会员卡开卡表单")
    public Resp setTemplateForm(@TokenInfo(property = "merchantId") String merchantId,String templateId){
        return memberCardTemplateService.setTemplateForm(templateId,merchantId);
    }


    @PostMapping("/applyActivateUrl")
    @ApiOperation(value="获取发卡连接",notes = "获取发卡连接")
    public Resp<Map<String,Object>> applyActivateUrl(@TokenInfo(property = "merchantId") String merchantId,String templateId){
        return Resp.success(memberCardTemplateService.applyActivateUrl(templateId,merchantId),"获取成功");
    }
}
