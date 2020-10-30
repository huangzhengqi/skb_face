package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.ChinaMapService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * --中国地图数据采集控制层
 * --版权所有-锋之云科技
 * --作者-曹拴拴
 * --时间-2019-12-06
 */
@Slf4j
@RestController
@RequestMapping("/auth/chinamap")
public class ChinaMapController extends BaseController<Company> {
    @Resource
    private ChinaMapService chinaMapService;

    @Override
    public ChinaMapService getService() {
        return chinaMapService;
    }
    /**
     * 查询地图数据
     * @param userId
     * @param showTpye 1是代理商，2是商户
     * @return
     */
    @GetMapping("/count_by_org")
    public Resp countByOrg(@UserId String userId,Integer showTpye) {
        return Resp.success(chinaMapService.countByOrg(userId, showTpye));
    }
}
