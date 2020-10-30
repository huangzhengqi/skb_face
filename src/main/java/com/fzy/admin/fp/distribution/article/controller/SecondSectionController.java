package com.fzy.admin.fp.distribution.article.controller;

import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;
import com.fzy.admin.fp.distribution.article.dto.SecondSectionDTO;
import com.fzy.admin.fp.distribution.article.dto.SectionDTO;
import com.fzy.admin.fp.distribution.article.dto.ThirdSectionDTO;
import com.fzy.admin.fp.distribution.article.service.SecondSectionService;
import com.fzy.admin.fp.distribution.article.service.ThirdSectionService;
import com.fzy.admin.fp.distribution.article.vo.SecondSectionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yy
 * @Date 2020-2-10 16:34:56
 * @Desp
 **/

@RestController
@RequestMapping("/dist/pc/section/second")
@Api(value = "SecondSectionController", tags = {"分销-二级分类"})
public class SecondSectionController extends BaseContent {

    @Resource
    private SecondSectionService secondSectionService;

    @Resource
    private ThirdSectionService thirdSectionService;

    @GetMapping("/query")
    @ApiOperation(value = "查询二级分类", notes = "查询二级分类/左右式的二级分类带有子分类")
    public Resp query(String sectionId,Integer type){
        //type为1时是左右式,左右式需要查三级分类
        if(type==1){
            List<SecondSectionVO> secondSectionVOList=new ArrayList<>();
            List<SecondSection> secondSectionList = secondSectionService.getRepository().findAllBySectionIdAndTypeOrderByWeightDesc(sectionId,type);
            SecondSectionVO secondSectionVO=null;
            for (SecondSection secondSection:secondSectionList){
                secondSectionVO=new SecondSectionVO();
                secondSectionVO.setSecondSection(secondSection);
                List<ThirdSection> thirdSectionList = thirdSectionService.getRepository().findAllByParentIdOrderByWeightDesc(secondSection.getId());
                secondSectionVO.setThirdSection(thirdSectionList);
                secondSectionVOList.add(secondSectionVO);
            }
            return Resp.success(secondSectionVOList);
        }else{
            List<SecondSection> secondSectionList = secondSectionService.getRepository().findAllBySectionIdAndTypeOrderByWeightDesc(sectionId,type);
            return Resp.success(secondSectionList);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加二级分类", notes = "添加二级分类")
    public Resp insert(@Valid SecondSectionDTO secondSectionDTO){
        SecondSection secondSection=new SecondSection();
        secondSection.setIcon(secondSectionDTO.getIcon());
        secondSection.setName(secondSectionDTO.getName());
        secondSection.setType(secondSectionDTO.getType());
        secondSectionService.save(secondSection);
        return Resp.success("添加成功");
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改二级分类", notes = "修改二级分类")
    public Resp update(@Valid SecondSectionDTO secondSectionDTO){
        SecondSection secondSection = secondSectionService.findOne(secondSectionDTO.getId());
        if(secondSection==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请刷新页面");
        }
        secondSection.setIcon(secondSectionDTO.getIcon());
        secondSection.setName(secondSectionDTO.getName());
        secondSectionService.save(secondSection);
        return Resp.success("修改成功");
    }

    @PostMapping("/insert_third")
    @ApiOperation(value = "添加三级分类", notes = "添加三级分类")
    public Resp insertThird(@Valid ThirdSectionDTO thirdSectionDTO){
        ThirdSection thirdSection=new ThirdSection();
        thirdSection.setIcon(thirdSectionDTO.getIcon());
        thirdSection.setName(thirdSectionDTO.getName());
        thirdSection.setParentId(thirdSectionDTO.getParentId());
        thirdSectionService.save(thirdSection);
        return Resp.success("添加成功");
    }

    @PostMapping("/update_third")
    @ApiOperation(value = "修改三级分类", notes = "修改三级分类")
    public Resp updateThird(@Valid SecondSectionDTO secondSectionDTO){
        ThirdSection thirdSection = thirdSectionService.findOne(secondSectionDTO.getId());
        if(thirdSection==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请刷新页面");
        }
        thirdSection.setIcon(secondSectionDTO.getIcon());
        thirdSection.setName(secondSectionDTO.getName());
        thirdSectionService.save(thirdSection);
        return Resp.success("修改成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除二级分类", notes = "删除二级分类")
    public Resp delete(SecondSectionDTO sectionDTO){
        secondSectionService.deleteAll(sectionDTO.getId());
        return Resp.success("删除成功");
    }

    @PostMapping("/delete_third")
    @ApiOperation(value = "删除三级分类", notes = "删除三级分类")
    public Resp deleteThird(SecondSectionDTO sectionDTO){
        thirdSectionService.deleteAll(sectionDTO.getId());
        return Resp.success("删除成功");
    }

}
