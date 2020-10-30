package com.fzy.admin.fp.distribution.article.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.article.domain.Section;
import com.fzy.admin.fp.distribution.article.repository.SectionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-27 19:00:37
 * @Desp
 **/
@Service
public class SectionService implements BaseService<Section> {

    @Resource
    private SectionRepository sectionRepository;

    @Resource
    private ArticleService articleService;


    public SectionRepository getRepository() {
        return sectionRepository;
    }

    public void deleteAll(String id) {
        delete(id);
        articleService.getRepository().deleteBySectionId(id);
    }
}
