package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:16 2019/7/1
 * @ Description:
 **/

public interface SiteInfoRepository extends BaseRepository<SiteInfo> {

    //根据域名查询网站信息
    SiteInfo findByDomainName(String domainName);

    //根据服务商id查询网站信息
    SiteInfo findByServiceProviderId(String serviceProviderId);
}
