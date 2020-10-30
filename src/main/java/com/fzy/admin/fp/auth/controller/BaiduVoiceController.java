package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.BaiDuVoice;
import com.fzy.admin.fp.auth.service.BaiDuVoiceService;

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
 * @ Description: 百度语音控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/baidu_voice")
public class BaiduVoiceController extends BaseController<BaiDuVoice> {

    @Resource
    private BaiDuVoiceService baiDuVoiceService;

    @Override
    public BaiDuVoiceService getService() {
        return baiDuVoiceService;
    }

    @GetMapping("/get_info")
    public Resp listRewrite(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        List<BaiDuVoice> baiDuVoiceList = new ArrayList<>();
        BaiDuVoice baiDuVoice = baiDuVoiceService.getRepository().findByServiceProviderId(companyId);
        //若百度语音为空,则创建一个,配置信息为空字符串,防止调用的时候空指针
        if (ParamUtil.isBlank(baiDuVoice)) {
            baiDuVoice = new BaiDuVoice();
            baiDuVoice.setServiceProviderId(companyId);
            baiDuVoice.setApiKey("");
            baiDuVoice.setAppId("");
            baiDuVoice.setSecretKey("");
            baiDuVoiceService.save(baiDuVoice);
        }
        baiDuVoiceList.add(baiDuVoice);
        Map<String, Object> map = new HashMap<>();
        map.put("content", baiDuVoiceList);
        map.put("totalElements", 1);
        map.put("totalPages", 1);
        return Resp.success(map);
    }

}
