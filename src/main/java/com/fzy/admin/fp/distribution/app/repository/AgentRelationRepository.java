package com.fzy.admin.fp.distribution.app.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.app.domain.AgentRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentRelationRepository extends BaseRepository<AgentRelation> {
    AgentRelation findByChildIdAndType(String childId, Integer type);

    Page<AgentRelation> findAllByUserIdAndType(String userId, Integer type, Pageable pageable);
}
