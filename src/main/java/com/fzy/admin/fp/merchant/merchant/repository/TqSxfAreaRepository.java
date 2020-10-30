package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.TqSxfArea;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TqSxfAreaRepository extends BaseRepository<TqSxfArea> {

    List<TqSxfArea> findByAreaName(String city);

    TqSxfArea findByAreaId(String id);

    List<TqSxfArea> findByParentId(String id);
}
