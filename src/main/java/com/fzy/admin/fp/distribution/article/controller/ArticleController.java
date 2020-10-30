package com.fzy.admin.fp.distribution.article.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.article.domain.Article;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.domain.Section;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;
import com.fzy.admin.fp.distribution.article.dto.ArticleDTO;
import com.fzy.admin.fp.distribution.article.dto.ArticleSortDTO;
import com.fzy.admin.fp.distribution.article.service.ArticleService;
import com.fzy.admin.fp.distribution.article.service.SecondSectionService;
import com.fzy.admin.fp.distribution.article.service.SectionService;
import com.fzy.admin.fp.distribution.article.service.ThirdSectionService;
import com.fzy.admin.fp.distribution.article.vo.ArticlePageVO;
import com.fzy.admin.fp.distribution.article.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-6 10:36:01
 * @Desp
 **/
@RestController
@RequestMapping("/dist/pc/article")
@Api(value="ArticleController", tags={"分销-资讯管理"})
public class ArticleController extends BaseContent {
    @Resource
    private ArticleService articleService;

    @Resource
    private SecondSectionService secondSectionService;

    @Resource
    private ThirdSectionService thirdSectionService;

    @Resource
    private SectionService sectionService;

    @PostMapping("/insert")
    @ApiOperation(value="添加资讯", notes="添加资讯")
    public Resp insert(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid ArticleDTO articleDTO) {
        Section section=sectionService.findOne(articleDTO.getSectionId());
        if (section.getType() == 1) {
            if(articleDTO.getSecondId() == null || articleDTO.getSecondId().equals("") ){
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择子级分类");
            }
            if (articleDTO.getThirdId() == null || articleDTO.getThirdId().equals("") ) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择子级分类");
            }
        }
        if (section.getType() == 2) {
            if (articleDTO.getSecondId() == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择子分类");
            }
        }
        Article article=new Article();
        BeanUtil.copyProperties(articleDTO, article);
        article.setStatus(0);
        article.setWeight(0);
        article.setServiceProviderId(serviceProviderId);
        articleService.save(article);
        return Resp.success("新增成功");
    }

    @PostMapping("/update")
    @ApiOperation(value="修改资讯", notes="修改资讯")
    public Resp update(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid ArticleDTO articleDTO) {
        Article article=articleService.findOne(articleDTO.getId());
        if (article == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        BeanUtil.copyProperties(articleDTO, article);
        article.setServiceProviderId(serviceProviderId);
        articleService.update(article);
        return Resp.success("修改成功");
    }

    @GetMapping("/detail")
    @ApiOperation(value="资讯详情", notes="资讯详情")
    public Resp detail(@TokenInfo(property="serviceProviderId") String serviceProviderId, String id) {
        return Resp.success(articleService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id));
    }

    @GetMapping("/query")
    @ApiOperation(value="查询资讯", notes="查询资讯")
    public Resp query(@TokenInfo(property="serviceProviderId") String serviceProviderId, ArticleDTO articleDTO, PageVo pageVo) {
        if (articleDTO.getSectionId() == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择需要查询的版块");
        }
        Pageable pageable=PageUtil.initPage(pageVo);
        Specification<Article> specification=new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.equal(root.get("sectionId"), articleDTO.getSectionId()));
                //日期查询
                if (articleDTO.getStartTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), articleDTO.getStartTime()));
                }
                if (articleDTO.getEndTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), articleDTO.getEndTime()));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                Predicate Pre_And=cb.and(predicates.toArray(pre));
                if (StringUtil.isNotEmpty(articleDTO.getContent())) {
                    List<Predicate> listPermission=new ArrayList<>();
                    listPermission.add(cb.like(root.get("content"), articleDTO.getContent() + "%"));
                    listPermission.add(cb.like(root.get("title"), articleDTO.getContent() + "%"));
                    Predicate[] predicatesPermissionArr=new Predicate[listPermission.size()];
                    Predicate predicatesPermission=cb.or(listPermission.toArray(predicatesPermissionArr));
                    return query.where(Pre_And, predicatesPermission).getRestriction();
                }
                return query.where(Pre_And).getRestriction();
            }
        };
        ArticlePageVO articlePageVO=new ArticlePageVO();
        Page<Article> all=articleService.findAll(specification, pageable);
        articlePageVO.setTotalElements(all.getTotalElements());
        articlePageVO.setTotalPages(all.getTotalPages());
        Section section=sectionService.findOne(articleDTO.getSectionId());
        if (section.getType() == 1) {
            List<ArticleVO> articleVOList=new ArrayList<>();
            for (Article article : all) {
                ArticleVO articleVO=new ArticleVO();
                BeanUtil.copyProperties(article, articleVO);
                SecondSection secondSection=secondSectionService.findOne(article.getSecondId());
                ThirdSection thirdSection=thirdSectionService.findOne(article.getThirdId());
                articleVO.setSecondName(secondSection.getName());
                articleVO.setThirdName(thirdSection.getName());
                articleVOList.add(articleVO);
            }
            articlePageVO.setArticleVOList(articleVOList);
            return Resp.success(articlePageVO);
        } else if (section.getType() == 2) {
            List<ArticleVO> articleVOList=new ArrayList<>();
            for (Article article : all) {
                ArticleVO articleVO=new ArticleVO();
                BeanUtil.copyProperties(article, articleVO);
                SecondSection secondSection=secondSectionService.findOne(article.getSecondId());
                articleVO.setSecondName(secondSection.getName());
                articleVOList.add(articleVO);
            }
            articlePageVO.setArticleVOList(articleVOList);
            return Resp.success(articlePageVO);
        }
        return Resp.success(all);
    }

    @PostMapping("/delete")
    @ApiOperation(value="删除资讯", notes="删除资讯")
    public Resp delete(@TokenInfo(property="serviceProviderId") String serviceProviderId, String id) {
        Article article=articleService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if (article == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        articleService.delete(id);
        return Resp.success("删除成功");
    }

    @PostMapping("/sort")
    @ApiOperation(value="排序", notes="排序")
    public Resp sort(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid ArticleSortDTO articleSortDTO) {
        Article article=articleService.getRepository().findByServiceProviderIdAndId(serviceProviderId, articleSortDTO.getId());
        if (article == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (articleService.getRepository().countByServiceProviderIdAndWeightAndWeightNotAndSectionIdAndSecondIdAndThirdId(serviceProviderId, articleSortDTO.getWeight(), 0, article.getSectionId(), article.getSecondId(), article.getThirdId()) != 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该排序数值已存在，不可重复！");
        }
        article.setWeight(articleSortDTO.getWeight());
        articleService.update(article);
        return Resp.success("设置成功");
    }

    @PostMapping("/show")
    @ApiOperation(value="显示/隐藏", notes="显示/隐藏")
    public Resp show(@TokenInfo(property="serviceProviderId") String serviceProviderId, String id) {
        Article article=articleService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if (article == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        article.setStatus(article.getStatus() == 0 ? 1 : 0);
        articleService.update(article);
        return Resp.success("设置成功");
    }

}
