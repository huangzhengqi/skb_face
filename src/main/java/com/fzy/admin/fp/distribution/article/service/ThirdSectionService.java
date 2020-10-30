package com.fzy.admin.fp.distribution.article.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;
import com.fzy.admin.fp.distribution.article.repository.ThirdSectionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @Author yy
 * @Date 2020-2-10 16:32:09
 * @Desp
 **/
@Service
public class ThirdSectionService implements BaseService<ThirdSection> {
    @Resource
    private ThirdSectionRepository thirdSectionRepository;

    @Resource
    private ArticleService articleService;

    public ThirdSectionRepository getRepository() {
        return thirdSectionRepository;
    }

    @Transactional
    public void deleteAll(String id) {
        delete(id);
        articleService.getRepository().deleteByThirdId(id);
    }
}
