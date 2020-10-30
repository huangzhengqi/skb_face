package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.RateStandards;
import com.fzy.admin.fp.distribution.money.service.RateStandardsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-1-10 16:13:46
 * @Desp
 **/
@RequestMapping("/dist/rate_standards")
@RestController
@Api(value = "RateStandardsController", tags = {"分销-三级分销费率设置"})
public class RateStandardsController extends BaseContent {
    @Resource
    private RateStandardsService rateStandardsService;


    @GetMapping(value = "/query")
    @ApiOperation(value = "查询", notes = "查询")
    public Resp<RateStandards> query(){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(rateStandardsService.getRepository().findByServiceProviderId(serviceId));
    }

    @PostMapping(value = "/set")
    @ApiOperation(value = "修改", notes = "修改")
    public Resp set(RateStandards rateStandards){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        RateStandards standards = rateStandardsService.getRepository().findByServiceProviderId(serviceId);
        if(standards!=null){
            rateStandards.setServiceProviderId(standards.getServiceProviderId());
            rateStandards.setId(standards.getId());
            rateStandardsService.update(standards);
            return Resp.success("保存成功");
        }
        rateStandards.setServiceProviderId(serviceId);
        rateStandardsService.save(rateStandards);
        return Resp.success("保存成功");
    }
}
