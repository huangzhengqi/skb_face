package com.fzy.admin.fp.distribution.pc.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.pc.domain.NoteConfig;
import com.fzy.admin.fp.distribution.pc.service.NoteConfigService;
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
 * @Date 2019-12-17 18:22:15
 * @Desp 短信模板配置
 **/
@RestController
@RequestMapping("/dist/note")
@Api(value = "NoteConfigController", tags = {"分销-短信配置"})
public class NoteConfigController extends BaseContent{

    @Resource
    private NoteConfigService noteConfigService;

    @Resource
    private HttpServletRequest request;

    @GetMapping(value = "/query")
    @ApiOperation(value = "查询配置", notes = "查询配置")
    public Resp query(){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(noteConfigService.getRepository().findByServiceProviderId(serviceProviderId));
    }

    @PostMapping(value = "/set")
    @ApiOperation(value = "添加/修改配置", notes = "添加/修改配置")
    public Resp set(NoteConfig noteConfigOld){
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        NoteConfig noteConfig = noteConfigService.getRepository().findByServiceProviderId(serviceProviderId);
        if(noteConfig==null){
            noteConfigOld.setServiceProviderId(serviceProviderId);
            noteConfigService.save(noteConfigOld);
            return Resp.success("新增成功");
        }
        noteConfigOld.setId(noteConfig.getId());
        noteConfigOld.setServiceProviderId(serviceProviderId);
        noteConfigService.update(noteConfigOld);
        return Resp.success("修改成功");
    }
}
