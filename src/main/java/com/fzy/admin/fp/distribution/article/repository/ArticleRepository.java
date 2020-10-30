package com.fzy.admin.fp.distribution.article.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository extends BaseRepository<Article> {
    Article findByServiceProviderIdAndId(String serviceProviderId,String id);

    Integer countByServiceProviderIdAndWeightAndWeightNotAndSectionIdAndSecondIdAndThirdId(String serviceProviderId,Integer weight,Integer num,String sectionId,String secondId,String thirdId);

    Integer deleteBySectionId(String id);

    Integer deleteBySecondId(String id);

    Integer deleteByThirdId(String id);


}
