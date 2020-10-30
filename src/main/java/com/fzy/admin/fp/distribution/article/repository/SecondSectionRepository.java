package com.fzy.admin.fp.distribution.article.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.article.domain.SecondSection;

import java.util.List;

public interface SecondSectionRepository extends BaseRepository<SecondSection>  {
    List<SecondSection> findAllBySectionIdAndTypeOrderByWeightDesc(String sectionId,Integer type);
}
