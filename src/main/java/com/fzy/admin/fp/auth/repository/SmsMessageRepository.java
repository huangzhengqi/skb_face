package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @author zk
 * @description : 短信配置
 * @create 2018-07-25 15:10:47
 **/
public interface SmsMessageRepository extends BaseRepository<SmsMessage> {

    SmsMessage findByServiceProviderId(String companyId);
}