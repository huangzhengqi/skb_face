package com.fzy.admin.fp.distribution.pc.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.pc.domain.AppDown;
import com.fzy.admin.fp.distribution.pc.dto.AppDownDTO;
import com.fzy.admin.fp.distribution.pc.service.AppDownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author yy
 * @Date 2019-12-16 09:40:49
 * @Desp
 **/
@RestController
@RequestMapping("/dist/pc/down")
@Api(value = "AppDownController", tags = {"分销-后台设置app下载链接"})
public class AppDownController extends BaseContent{
    @Resource
    private AppDownService appDownService;


    @Resource
    private HttpServletRequest request;

    @GetMapping(value = "/query")
    @ApiOperation(value = "查询链接", notes = "查询链接")
    public Resp query(){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success( appDownService.getRepository().findByServiceProviderId(serviceProviderId));
    }

    @PostMapping(value = "/set")
    @ApiOperation(value = "添加/修改链接", notes = "添加/修改链接")
    public Resp set(@Valid AppDownDTO appDownDTO){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        AppDown appDown = appDownService.getRepository().findByServiceProviderId(serviceProviderId);
        if(appDown==null){
            appDown=new AppDown();
            appDown.setUrl(appDownDTO.getUrl());
            appDown.setServiceProviderId(serviceProviderId);
            appDownService.save(appDown);
            return Resp.success("新增成功");
        }
        appDown.setUrl(appDownDTO.getUrl());
        appDownService.update(appDown);
        return Resp.success("修改成功");
    }

}
