package com.fzy.admin.fp.distribution.article.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.article.domain.Section;
import com.fzy.admin.fp.distribution.article.dto.SectionDTO;
import com.fzy.admin.fp.distribution.article.service.SectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-28 09:03:21
 * @Desp
 **/

@RestController
@RequestMapping("/dist/pc/section")
@Api(value = "SectionController", tags = {"分销-版块管理"})
public class SectionController extends BaseContent{
    @Resource
    private SectionService sectionService;

    @GetMapping("/get")
    @ApiOperation(value = "查询所有版块", notes = "查询所有版块")
    public Resp<List<Section>> queryAll(){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        List<Section> all = sectionService.getRepository().findAllByServiceProviderIdOrderByWeightDesc(serviceId);
        return Resp.success(all);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "添加版块", notes = "添加版块")
    public Resp insert(@Valid SectionDTO sectionDTO){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Section section=new Section();
        BeanUtil.copyProperties(sectionDTO,section);
        section.setServiceProviderId(serviceId);
        sectionService.save(section);
        return Resp.success("添加成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除版块", notes = "删除版块")
    public Resp delete(SectionDTO sectionDTO){
        sectionService.deleteAll(sectionDTO.getId());
        return Resp.success("删除成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改版块", notes = "修改版块")
    public Resp update(@Valid SectionDTO sectionDTO){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Section oldSection = sectionService.getRepository().findByServiceProviderIdAndId(serviceId, sectionDTO.getId());
        if(oldSection==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请刷新页面");
        }
        BeanUtil.copyProperties(sectionDTO,oldSection);
        sectionService.update(oldSection);
        return Resp.success("修改成功");
    }

    @PostMapping("/show")
    @ApiOperation(value = "显示/隐藏", notes = "显示/隐藏")
    public Resp show(String id){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Section oldSection = sectionService.getRepository().findByServiceProviderIdAndId(serviceId, id);
        if(oldSection==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请刷新页面");
        }
        oldSection.setStatus(oldSection.getStatus()==0?1:0);
        sectionService.update(oldSection);
        return Resp.success("设置成功");
    }
}
