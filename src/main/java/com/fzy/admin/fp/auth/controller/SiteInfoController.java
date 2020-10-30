package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:51 2019/7/2
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/auth/company/site_info")
public class SiteInfoController extends BaseController<SiteInfo> {

    @Resource
    private SiteInfoService siteInfoService;

    @Override
    public SiteInfoService getService() {
        return siteInfoService;
    }

    /*
     * @author drj
     * @date 2019-07-02 9:57
     * @Description :查看详情
     */
    @GetMapping("/detail")
    public Resp detail(String serviceProviderId) {
        if (ParamUtil.isBlank(serviceProviderId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        SiteInfo siteInfo = siteInfoService.getRepository().findByServiceProviderId(serviceProviderId);
        return Resp.success(siteInfo);
    }

    /*
     * @author drj
     * @date 2019-07-02 10:03
     * @Description :录入网站资料
     */
    @GetMapping("/entry")
    public Resp entry(String serviceProviderId) {
        if (ParamUtil.isBlank(serviceProviderId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        SiteInfo siteInfo = siteInfoService.getRepository().findByServiceProviderId(serviceProviderId);
        //如果网站信息为空,创建一个
        if (ParamUtil.isBlank(siteInfo)) {
            siteInfo = new SiteInfo();
            siteInfo.setServiceProviderId(serviceProviderId);
            siteInfo.setDomainName("www.baidu.com");
            siteInfoService.save(siteInfo);
        }
        return Resp.success(siteInfo);
    }

    @GetMapping("/get_site_info")
    public Resp getSiteInfo(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        return Resp.success(siteInfoService.getRepository().findByServiceProviderId(companyId));
    }

    @Override
    public Resp update(SiteInfo entity) {
        SiteInfo siteInfo = siteInfoService.getRepository().findOne(entity.getId());
        if (!siteInfo.getDomainName().equals(entity.getDomainName())) {
            SiteInfo byDomainName = siteInfoService.findByDomainName(entity.getDomainName());
            if (byDomainName != null) {
                log.error("域名已存在->domainName:{}", entity.getDomainName());
                return new Resp().error(Resp.Status.PARAM_ERROR, "域名已存在，" + entity.getDomainName());
            }
        }

        return super.update(entity);
    }

}
