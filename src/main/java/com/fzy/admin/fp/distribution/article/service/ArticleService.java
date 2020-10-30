package com.fzy.admin.fp.distribution.article.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.article.domain.Article;
import com.fzy.admin.fp.distribution.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-6 10:34:56
 * @Desp
 **/
@Service
public class ArticleService implements BaseService<Article> {

    @Resource
    private ArticleRepository articleRepository;


    public ArticleRepository getRepository() {
        return articleRepository;
    }
}
