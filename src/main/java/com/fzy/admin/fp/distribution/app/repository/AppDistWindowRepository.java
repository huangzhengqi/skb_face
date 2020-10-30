package com.fzy.admin.fp.distribution.app.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppDistWindowRepository extends BaseRepository<DistWindow> {

    List<DistWindow> findByServiceProviderId(String serviceProviderId);

    DistWindow findByServiceProviderIdAndId(String serviceProviderId, String id);

    Integer countByServiceProviderIdAndWeight(String serviceProviderId, Integer weight);

    List<DistWindow> findByServiceProviderIdAndStatusAndType(String serviceProviderId,Integer status,Integer type);
}
