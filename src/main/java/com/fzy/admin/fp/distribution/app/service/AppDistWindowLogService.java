package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.app.domain.AgentRelation;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.distribution.app.repository.AgentRelationRepository;
import com.fzy.admin.fp.distribution.app.repository.AppDistWindowLogRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class AppDistWindowLogService implements BaseService<DistWindowLog> {

    @Resource
    private AppDistWindowLogRepository appDistWindowLogRepository;

    public AppDistWindowLogRepository getRepository() {
        return appDistWindowLogRepository;
    }

    @Transactional
    public void deleteByWindowId(String id) {
        appDistWindowLogRepository.deleteByWindowId(id);
    }
}
