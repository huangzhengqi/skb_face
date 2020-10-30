package com.fzy.admin.fp.distribution.article.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;

import java.util.List;

public interface ThirdSectionRepository extends BaseRepository<ThirdSection>  {
    List<ThirdSection> findAllByParentIdOrderByWeightDesc(String secondId);

    void deleteByParentId(String parentId);
}
