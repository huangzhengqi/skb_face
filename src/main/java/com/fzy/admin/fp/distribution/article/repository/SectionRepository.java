package com.fzy.admin.fp.distribution.article.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.article.domain.Section;

import java.util.List;

public interface SectionRepository extends BaseRepository<Section> {
    List<Section> findAllByServiceProviderIdOrderByWeightDesc(String serviceProviderId);

    Section findByServiceProviderIdAndId(String serviceProviderId,String id);


}
