package com.fzy.admin.fp.distribution.article.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.repository.SecondSectionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @Author yy
 * @Date 2020-2-10 16:31:58
 * @Desp
 **/
@Service
public class SecondSectionService implements BaseService<SecondSection> {

    @Resource
    private SecondSectionRepository secondSectionRepository;

    @Resource
    private ThirdSectionService thirdSectionService;

    @Resource
    private ArticleService articleService;

    public SecondSectionRepository getRepository() {
        return secondSectionRepository;
    }

    @Transactional
    public void deleteAll(String id) {
        delete(id);
        articleService.getRepository().deleteBySecondId(id);
        thirdSectionService.getRepository().deleteByParentId(id);
    }
}
