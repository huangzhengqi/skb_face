package com.fzy.admin.fp.log.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.log.repository.OperationLogRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.log.domain.OperationLog;
import com.fzy.admin.fp.log.repository.OperationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author Created by zk on 2019-06-13 14:47
 * @description
 */
@Slf4j
@Service
@Transactional
public class OperationLogService implements BaseService<OperationLog> {
    @Resource
    private OperationLogRepository operationLogRepository;

    @Override
    public BaseRepository<OperationLog> getRepository() {
        return operationLogRepository;
    }
}
