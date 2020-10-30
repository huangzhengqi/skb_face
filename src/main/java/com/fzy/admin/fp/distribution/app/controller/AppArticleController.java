package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.article.domain.Article;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.domain.Section;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;
import com.fzy.admin.fp.distribution.article.dto.ArticleDTO;
import com.fzy.admin.fp.distribution.article.service.ArticleService;
import com.fzy.admin.fp.distribution.article.service.SecondSectionService;
import com.fzy.admin.fp.distribution.article.service.SectionService;
import com.fzy.admin.fp.distribution.article.service.ThirdSectionService;
import com.fzy.admin.fp.distribution.article.vo.ArticlePageVO;
import com.fzy.admin.fp.distribution.article.vo.ArticleVO;
import com.fzy.admin.fp.distribution.article.vo.SecondSectionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-6 16:27:00
 * @Desp
 **/

@RestController
@RequestMapping("/dist/app/article")
@Api(value = "AppArticleController", tags = {"分销-app资讯"})
public class AppArticleController extends BaseContent {

    @Resource
    private ArticleService articleService;

    @Resource
    private SectionService sectionService;

    @Resource
    private SecondSectionService secondSectionService;

    @Resource
    private ThirdSectionService thirdSectionService;

    @GetMapping("/query")
    @ApiOperation(value = "查询资讯", notes = "查询资讯")
    public Resp query(@TokenInfo(property="serviceProviderId")String serviceProviderId, ArticleDTO articleDTO, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<Article> specification = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                if(articleDTO.getType()==1){
                    predicates.add(cb.equal(root.get("thirdId"), articleDTO.getSectionId()));
                }else if(articleDTO.getType()==2){
                    predicates.add(cb.equal(root.get("secondId"), articleDTO.getSectionId()));
                }else{
                    predicates.add(cb.equal(root.get("sectionId"), articleDTO.getSectionId()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));
//                if(StringUtil.isNotEmpty(articleDTO.getContent())){
//                    List<Predicate> listPermission = new ArrayList<>();
//                    listPermission.add(cb.like(root.get("content"), articleDTO.getContent()+"%"));
//                    listPermission.add(cb.like(root.get("title"), articleDTO.getContent()+"%"));
//                    Predicate[] predicatesPermissionArr = new Predicate[listPermission.size()];
//                    Predicate predicatesPermission = cb.or(listPermission.toArray(predicatesPermissionArr));
//                    return query.where(Pre_And, predicatesPermission).getRestriction();
//                }
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<Article> all = articleService.findAll(specification, pageable);
        return Resp.success(all);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "文章详情", notes = "查询咨询")
    public Resp detail(@TokenInfo(property="serviceProviderId")String serviceProviderId, String id){
        Article article = articleService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        return Resp.success(article);
    }

    @GetMapping("/Section")
    @ApiOperation(value = "查询所有版块", notes = "查询所有版块")
    public Resp<List<Section>> queryAll(@TokenInfo(property="serviceProviderId")String serviceProviderId){
        List<Section> all = sectionService.getRepository().findAllByServiceProviderIdOrderByWeightDesc(serviceProviderId);
        return Resp.success(all);
    }

    @GetMapping("/child/section")
    @ApiOperation(value = "查询二级/三级分类", notes = "查询二级/三级分类")
    public Resp queryChildSection(String sectionId,Integer type){
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

}
