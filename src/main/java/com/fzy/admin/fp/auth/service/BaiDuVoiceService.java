package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.repository.BaiDuVoiceRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.BaiDuVoice;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.auth.repository.BaiDuVoiceRepository;
import com.fzy.admin.fp.auth.repository.SmsMessageRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:34 2019/7/1
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class BaiDuVoiceService implements BaseService<BaiDuVoice> {

    @Resource
    private BaiDuVoiceRepository baiDuVoiceRepository;

    @Override
    public BaiDuVoiceRepository getRepository() {
        return baiDuVoiceRepository;
    }
}
