package com.fzy.admin.fp.auth.controller;


import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.SystemConfig;
import com.fzy.admin.fp.common.repository.SystemConfigRepository;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.SystemConfig;
import com.fzy.admin.fp.common.repository.SystemConfigRepository;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zk
 * @description 微信开发平台服务商配置表数据处理层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@RestController
@RequestMapping("/auth/wx_open_config")
@Api(value = "WxOpenConfigController", tags = {"微信开发平台服务商配置"})
public class WxOpenConfigController {

    @Resource
    WxOpenConfigRepository wxOpenConfigRepository;

    @Resource
    SystemConfigRepository systemConfigRepository;

    @ApiOperation(value = "获取开放平台配置信息", notes = "获取开放平台配置信息")
    @GetMapping(value = "")
    public Resp<WxOpenConfig> getOpenConfig(String companyId) {
        WxOpenConfig wxOpenConfig = wxOpenConfigRepository.findByServiceProviderId(companyId);
        return Resp.success(wxOpenConfig);
    }


    @ApiOperation(value = "修改开放平台配置", notes = "修改开放平台配置")
    @PutMapping(value = "")
    public Resp<String> setOpenConfig(@RequestBody WxOpenConfig wxOpenConfig,
                                      @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        if (wxOpenConfig.getFilename().contains(".txt")) {
            wxOpenConfig.setFilename(wxOpenConfig.getFilename().replace(".txt", ""));
        }
        WxOpenConfig oldWxOpenConfig = wxOpenConfigRepository.findByServiceProviderId(serviceProviderId);
        if (oldWxOpenConfig != null) {
            wxOpenConfig.setId(oldWxOpenConfig.getId());
        }
        wxOpenConfig.setServiceProviderId(serviceProviderId);
        wxOpenConfigRepository.save(wxOpenConfig);
        return Resp.success("保存成功");
    }


    @ApiOperation(value = "获取小程序发布配置信息", notes = "获取小程序发布配置信息")
    @GetMapping(value = "/applet")
    public Resp<List<SystemConfig>> getMiniConfig(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        List<SystemConfig> systemConfigs = systemConfigRepository.findAllByCompanyId(serviceProviderId);
        if (systemConfigs == null || systemConfigs.size() == 0) {
            systemConfigs = initSystemConfig();
        }
        return Resp.success(systemConfigs);
    }

    @ApiOperation(value = "修改小程序发布配置", notes = "修改小程序发布配置")
    @PutMapping(value = "/applet")
    public Resp<String> setMiniConfig(@RequestBody List<SystemConfig> systemConfigList,
                                      @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        for (SystemConfig systemConfig : systemConfigList) {
            systemConfig.setCompanyId(serviceProviderId);
            systemConfigRepository.save(systemConfig);
        }
        return Resp.success("保存成功");
    }


    private List<SystemConfig> initSystemConfig() {
        List<SystemConfig> systemConfigList = new ArrayList<>();
        String[] configKey = new String[]{"minicode_template_id", "minicode_webviewdomain", "minicode_ext_json", "minicode_user_version",
                "minicode_user_desc", "minicode_item_list", "minicode_requestdomain", "minicode_wsrequestdomain", "minicode_uploaddomain", "minicode_downloaddomain"};
        for (String key : configKey) {
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setConfigKey(key);
            systemConfigList.add(systemConfig);
        }
        return systemConfigList;
    }
}