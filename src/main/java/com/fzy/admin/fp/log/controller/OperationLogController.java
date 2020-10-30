package com.fzy.admin.fp.log.controller;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.log.domain.OperationLog;
import com.fzy.admin.fp.log.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by zk on 2019-06-13 14:48
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/admin/log")
public class OperationLogController extends BaseController<OperationLog> {
    @Resource
    private OperationLogService operationLogService;

    @Override
    public BaseService<OperationLog> getService() {
        return operationLogService;
    }
}
