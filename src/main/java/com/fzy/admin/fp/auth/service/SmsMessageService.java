package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.repository.SmsMessageRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.auth.repository.SmsMessageRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:01 2019/6/4
 * @ Description: 短信配置服务
 **/
@Slf4j
@Service
@Transactional
public class SmsMessageService implements BaseService<SmsMessage> {

    @Resource
    private SmsMessageRepository smsMessageRepository;

    @Override
    public SmsMessageRepository getRepository() {
        return smsMessageRepository;
    }
}
