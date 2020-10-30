package com.fzy.admin.fp.auth.config;

import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.wechatopen.service.business.service.IServerCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huang
 * @create 2019-07-19 12:17
 **/
@Slf4j
@Component
public class ServerCheckServiceImpl implements IServerCheckService {

    @Resource
    private WxOpenConfigRepository wxOpenConfigRepository;

    @Override
    public String getTextByFilename(String filename) {
        WxOpenConfig wxOpenConfig = wxOpenConfigRepository.findByFilename(filename);
        if (wxOpenConfig != null) {
            return wxOpenConfig.getFilecontent();
        }
        return "";
    }
}
