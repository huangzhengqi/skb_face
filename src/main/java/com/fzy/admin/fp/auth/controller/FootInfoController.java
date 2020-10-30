package com.fzy.admin.fp.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.auth.domain.FootInfo;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.auth.dto.FootInfoDTO;
import com.fzy.admin.fp.auth.service.FootInfoService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-27 14:18:24
 * @Desp
 **/

@RestController
@RequestMapping("/auth/foot")
@Api(value = "FootInfoController", tags = "设备公司信息")
public class FootInfoController extends BaseContent {
    @Resource
    private FootInfoService footInfoService;


    @GetMapping("/query")
    @ApiOperation(value = "查询信息", notes = "设置信息")
    public Resp query(@TokenInfo(property="serviceProviderId") String serviceProviderId){
        FootInfo footInfo = footInfoService.getRepository().findByServiceProviderId(serviceProviderId);
        return Resp.success(footInfo);
    }

    @PostMapping("/set")
    @ApiOperation(value = "设置信息", notes = "设置信息")
    public Resp add(@TokenInfo(property="serviceProviderId") String serviceProviderId,FootInfoDTO footInfoDTO){
        FootInfo footInfo = footInfoService.getRepository().findByServiceProviderId(serviceProviderId);
        if(footInfo==null){
            footInfo=new FootInfo();
            BeanUtil.copyProperties(footInfoDTO,footInfo);
            footInfo.setServiceProviderId(serviceProviderId);
            footInfoService.save(footInfo);
            return Resp.success("新增成功");
        }
        BeanUtil.copyProperties(footInfoDTO,footInfo);
        footInfoService.update(footInfo);
        return Resp.success("修改成功");
    }




}
