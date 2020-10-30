package com.fzy.admin.fp.distribution.guide.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.guide.domain.Guide;
import com.fzy.admin.fp.distribution.guide.service.GuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-1-11 13:50:31
 * @Desp
 **/

@RestController
@RequestMapping("/dist/guide")
@Api(value = "GuideController", tags = {"分销-指引页"})
public class GuideController extends BaseContent {
    @Resource
    private GuideService guideService;


    @GetMapping("/set")
    @ApiOperation(value = "设置指引页图片", notes = "设置指引页图片")
    public Resp set(@RequestParam(name = "img")String img){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Guide oldGuide = guideService.getRepository().findByServiceProviderId(serviceId);
        if(oldGuide!=null){
            oldGuide.setImg(img);
            guideService.update(oldGuide);
            return Resp.success("保存成功");
        }
        oldGuide=new Guide();
        oldGuide.setImg(img);
        oldGuide.setServiceProviderId(serviceId);
        guideService.save(oldGuide);
        return Resp.success("保存成功");
    }

    @PostMapping("/query")
    @ApiOperation(value = "设置指引页图片", notes = "设置指引页图片")
    public Resp<Guide> query(){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(guideService.getRepository().findByServiceProviderId(serviceId));
    }
}
