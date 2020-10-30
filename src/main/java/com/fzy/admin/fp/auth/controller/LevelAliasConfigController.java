package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.LevelAliasConfig;
import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: huxiangqiang
 * @since: 2019/8/10
 */
@RequestMapping("/auth/level_alias")
@RestController
@Api(value = "LevelAliasConfigController", tags = {"层级别名配置"})
public class LevelAliasConfigController {
    @Resource
    LevelAliasConfigRepository levelAliasConfigRepository;

    @GetMapping("")
    @ApiOperation(value = "获取别名")
    public Resp<LevelAliasConfig> findOne(@TokenInfo(property = "companyId") String companyId) {
        LevelAliasConfig levelAliasConfig = levelAliasConfigRepository.findOne(companyId);
        return Resp.success(levelAliasConfig,"");
    }

    @PostMapping("")
    @ApiOperation(value = "设置别名")
    public Resp<String> setOne(LevelAliasConfig levelAliasConfig,@TokenInfo(property = "companyId") String companyId) {
        try {
            levelAliasConfig.setId(companyId);
            levelAliasConfigRepository.save(levelAliasConfig);
            return Resp.success("","设置成功");
        } catch (Exception e) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"设置失败");
        }

    }
}
