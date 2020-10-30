package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.auth.service.SmsMessageService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:09 2019/6/4
 * @ Description: 短信模板消息控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/sms_message")
public class SmsMessageController extends BaseController<SmsMessage> {

    @Resource
    private SmsMessageService smsMessageService;

    @Override
    public SmsMessageService getService() {
        return smsMessageService;
    }


    @GetMapping("/get_info")
    public Resp listRewrite(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        List<SmsMessage> smsMessages = new ArrayList<>();
        SmsMessage smsMessage = smsMessageService.getRepository().findByServiceProviderId(companyId);
        //若短信模板为空,则创建一个
        if (ParamUtil.isBlank(smsMessage)) {
            smsMessage = new SmsMessage();
            smsMessage.setServiceProviderId(companyId);
            smsMessageService.save(smsMessage);
        }
        smsMessages.add(smsMessage);
        Map<String, Object> map = new HashMap<>();
        map.put("content", smsMessages);
        map.put("totalElements", 1);
        map.put("totalPages", 1);
        return Resp.success(map);
    }

}
