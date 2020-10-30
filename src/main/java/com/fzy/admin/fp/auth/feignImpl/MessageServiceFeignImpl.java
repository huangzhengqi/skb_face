package com.fzy.admin.fp.auth.feignImpl;

import com.fzy.admin.fp.auth.utils.MailService;
import com.fzy.admin.fp.auth.utils.SmsService;
import com.fzy.admin.fp.sdk.auth.feign.MessageServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by zk on 2019-03-19 11:00
 * @description
 */
@Slf4j
@Service
public class MessageServiceFeignImpl implements MessageServiceFeign {
    @Resource
    private MailService mailService;

    @Resource
    private SmsService smsService;

    @Override
    public boolean sendSms(String phone, String code) {
        return smsService.sendSms(phone, code);
    }

    @Override
    public boolean sendSmsInfo(String name, String phone, String password) {
        return smsService.sendSmsInfo(name, phone, password);
    }


}
