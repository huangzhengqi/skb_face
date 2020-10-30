package com.fzy.admin.fp.distribution.pc.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-2-24 10:26:24
 * @Desp
 **/

@RestController
@RequestMapping("/dist/system/setup")
@Api(value = "SystemSetupController", tags = {"分销-系统设置"})
public class SystemSetupController extends BaseContent {

    @Resource
    private SystemSetupService systemSetupService;

    @GetMapping("/get")
    @ApiOperation(value = "查询系统设置", notes = "查询系统设置")
    public Resp<SystemSetup> query(){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(systemSetupService.getRepository().findByServiceProviderId(serviceProviderId));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改系统设置", notes = "修改系统设置")
    public Resp update(@Valid SystemSetup systemSetup){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        SystemSetup systemSetupOld = systemSetupService.getRepository().findByServiceProviderId(serviceProviderId);
        if(systemSetupOld==null){
            systemSetup.setServiceProviderId(serviceProviderId);
            systemSetupService.save(systemSetup);
            return Resp.success("新增成功");
        }else{
            BeanUtil.copyProperties(systemSetup,systemSetupOld);
            systemSetupOld.setServiceProviderId(serviceProviderId);
            systemSetupService.save(systemSetupOld);
            return Resp.success("修改成功");
        }
    }


}
