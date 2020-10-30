package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.app.domain.AgentRelation;
import com.fzy.admin.fp.distribution.app.repository.AgentRelationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-25 17:22:25
 * @Desp
 **/
@Service
public class AgentRelationService implements BaseService<AgentRelation> {
    @Resource
    private AgentRelationRepository agentRelationRepository;



    public AgentRelationRepository getRepository() {
        return agentRelationRepository;
    }
}
