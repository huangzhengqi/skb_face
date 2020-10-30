package com.fzy.admin.fp.common.controller;

import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.domain.Area;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.service.CityService;
import com.fzy.admin.fp.common.vo.ProvinceTree;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class VersionLogController {

    @Resource
    private ProvinceRepository provinceRepository;


    //@Value("${app.module.mainVersion}")
    private String mainVersion="1.1";
    //@Value("${app.module.buildVersion}")
    private String buildVersion="1.1";

    @GetMapping("/version")
    public String getVersion (){
        try {
            provinceRepository.findAll();
        } catch (Exception e) {
            return "Error:" + e.getMessage();
        }

        return mainVersion + "|" + buildVersion;
    }

}
