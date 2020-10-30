package com.fzy.admin.fp.push.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.push.domain.PushUser;
import com.fzy.admin.fp.push.domain.PushUserClient;
import com.fzy.admin.fp.push.repository.PushUserRepository;
import com.fzy.admin.fp.push.util.AppPushUtils;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/10/7 18:23
 * @Version 1.0
 * @description
 */
@Slf4j
@Transactional(rollbackOn = Exception.class)
@Service
public class PushUserServiceImpl implements BaseService<PushUser> {

    @Resource
    private PushUserRepository pushUserRepository;
    @Override
    public PushUserRepository getRepository() {
        return pushUserRepository;
    }
}
