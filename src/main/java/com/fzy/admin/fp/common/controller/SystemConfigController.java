package com.fzy.admin.fp.common.controller;

import com.fzy.admin.fp.common.domain.SystemConfig;
import com.fzy.admin.fp.common.repository.SystemConfigRepository;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  导入商品接口
 */
@RestController
@RequestMapping("/auth/systemconfig")
@Api(value = "SystemConfigController" ,tags = "系统配置")
@Slf4j
public class SystemConfigController {

    @Resource
    private SystemConfigRepository systemConfigRepository;




    @ApiOperation(value = "根据key获取配置", notes = "根据key获取配置")
    @GetMapping({""})
    public Resp<String> getPage(String key) {
        SystemConfig systemConfig = this.systemConfigRepository.findByConfigKey(key);
        if (systemConfig == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "配置不存在");
        }
        return Resp.success(systemConfig.getConfigValue());
    }
}
