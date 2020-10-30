package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:19 2019/7/1
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class SiteInfoService implements BaseService<SiteInfo> {

    @Resource
    private SiteInfoRepository siteInfoRepository;

    @Override
    public SiteInfoRepository getRepository() {
        return siteInfoRepository;
    }


    //根据域名查询对应的服务商信息
    public SiteInfo findByDomainName(String domainName) {

        return siteInfoRepository.findByDomainName(domainName);

    }
}
