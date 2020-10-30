package com.fzy.admin.fp.init;

import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 域名拦截及验证
 *
 * @author llw
 * @date 2019.07.01
 */
@Slf4j
@Component
public class InitData {


    @Autowired
    SiteInfoService siteInfoService;


    @Autowired
    CompanyService companyService;


}
