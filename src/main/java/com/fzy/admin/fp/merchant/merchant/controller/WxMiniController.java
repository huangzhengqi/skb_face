package com.fzy.admin.fp.merchant.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.SystemConfig;
import com.fzy.admin.fp.common.repository.SystemConfigRepository;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.wechatopen.gateway.config.WxOpenConfiguration;
import com.fzy.wechatopen.gateway.dto.wechat.mini.code.CodeCommitDTO;
import com.fzy.wechatopen.gateway.dto.wechat.mini.code.OnekeyUploadCodeDTO;
import com.fzy.wechatopen.gateway.dto.wechat.mini.code.SubmitAuditMessageDTO;
import com.fzy.wechatopen.service.business.service.IWechatOpenService;
import com.fzy.wechatopen.service.utils.WxOpenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.ma.WxOpenMaCategory;
import me.chanjar.weixin.open.bean.ma.WxOpenMaSubmitAudit;
import me.chanjar.weixin.open.bean.result.WxOpenMaCategoryListResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaSubmitAuditResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author: hhd
 * @since: 2019/7/23
 */
@RestController
@RequestMapping("/merchant/wx_mini")
@Api(value = "WxMiniController", tags = {"小程序配置"})
public class WxMiniController {

    @Resource
    private MerchantAppletConfigRepository appletConfigRepository;

    @Resource
    private WxOpenConfigRepository wxOpenConfigRepository;

    @Autowired
    private IWechatOpenService wechatOpenService;

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private SiteInfoRepository siteInfoRepository;

    /**
     * 小程序发布
     *
     * @return
     */
    @GetMapping("/release/{id}")
    @ApiOperation(value = "发布小程序", notes = "发布小程序")
    public Resp<String> releaseWxMini(@ApiParam(name = "id", value = "小程序id")
                                      @PathVariable("id") String id,
                                      @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) throws WxErrorException {
        MerchantAppletConfig merchantAppletConfig = appletConfigRepository.findByMerchantId(id);
        if (merchantAppletConfig == null || StringUtils.isBlank(merchantAppletConfig.getAppId())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "小程序未授权");
        }
        WxOpenConfig wxOpenConfig = wxOpenConfigRepository.findByServiceProviderId(serviceProviderId);
        if (wxOpenConfig == null) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "服务商未配置");
        }
        SiteInfo siteInfo = siteInfoRepository.findByServiceProviderId(serviceProviderId);
        WxOpenConfiguration.setDomain(siteInfo.getDomainName());
        SystemConfig template = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_template_id", serviceProviderId);
        SystemConfig extJson = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_ext_json", serviceProviderId);
        SystemConfig userVersion = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_user_version", serviceProviderId);
        SystemConfig userDesc = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_user_desc", serviceProviderId);
        SystemConfig itemList = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_item_list", serviceProviderId);
        SystemConfig requestdomain = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_requestdomain", serviceProviderId);
        SystemConfig wsrequestdomain = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_wsrequestdomain", serviceProviderId);
        SystemConfig uploaddomain = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_uploaddomain", serviceProviderId);
        SystemConfig downloaddomain = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_downloaddomain", serviceProviderId);
        SystemConfig webviewdomain = systemConfigRepository.findByConfigKeyAndCompanyId("minicode_webviewdomain", serviceProviderId);
        List<String> requestdomainList = Arrays.asList(requestdomain.getConfigValue().split(","));
        List<String> wsrequestdomainList = Arrays.asList(wsrequestdomain.getConfigValue().split(","));
        List<String> uploaddomainList = Arrays.asList(uploaddomain.getConfigValue().split(","));
        List<String> downloaddomainList = Arrays.asList(downloaddomain.getConfigValue().split(","));
        List<String> webviewdomainList = Arrays.asList(webviewdomain.getConfigValue().split(","));
        OnekeyUploadCodeDTO onekeyUploadCode = new OnekeyUploadCodeDTO();
        onekeyUploadCode.setAppid(merchantAppletConfig.getAppId());
        onekeyUploadCode.setRequestdomain(requestdomainList);
        onekeyUploadCode.setWsrequestdomain(wsrequestdomainList);
        onekeyUploadCode.setUploaddomain(uploaddomainList);
        onekeyUploadCode.setDownloaddomain(downloaddomainList);
        onekeyUploadCode.setWebviewdomain(webviewdomainList);
        CodeCommitDTO codeCommitDTO = new CodeCommitDTO();
        codeCommitDTO.setAppid(merchantAppletConfig.getAppId());
        codeCommitDTO.setTemplateId(Long.valueOf(template.getConfigValue()));
        codeCommitDTO.setUserDesc(userDesc.getConfigValue());
        codeCommitDTO.setUserVersion(userVersion.getConfigValue());
        codeCommitDTO.setExtJson(extJson.getConfigValue());
        onekeyUploadCode.setCodeCommit(codeCommitDTO);
        SubmitAuditMessageDTO submitAuditMessageDTO = new SubmitAuditMessageDTO();
        submitAuditMessageDTO.setAppid(merchantAppletConfig.getAppId());
        submitAuditMessageDTO.setItemList(JSON.parseArray(itemList.getConfigValue(), WxOpenMaSubmitAudit.class));
        WxOpenMaCategoryListResult wxOpenMaCategoryListResult = WxOpenUtils.getWxOpenMaService(merchantAppletConfig.getAppId()).getCategoryList();
        if (wxOpenMaCategoryListResult.getCategoryList() == null) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "小程序类目信息未配置");
        }
        List<WxOpenMaCategory> wxOpenMaCategories = wxOpenMaCategoryListResult.getCategoryList();
        WxOpenMaCategory wxOpenMaCategory = wxOpenMaCategories.get(0);
        List<WxOpenMaSubmitAudit> submitAuditList = submitAuditMessageDTO.getItemList();
        WxOpenMaSubmitAudit wxOpenMaSubmitAudit = submitAuditList.get(0);
        wxOpenMaSubmitAudit.setFirstClass(wxOpenMaCategory.getFirstClass());
        wxOpenMaSubmitAudit.setFirstId(wxOpenMaCategory.getFirstId());
        wxOpenMaSubmitAudit.setSecondClass(wxOpenMaCategory.getSecondClass());
        wxOpenMaSubmitAudit.setSecondId(wxOpenMaCategory.getSecondId());
        onekeyUploadCode.setSubmitAuditMessage(submitAuditMessageDTO);
        try {
            WxOpenMaSubmitAuditResult auditResult = wechatOpenService.oneKeyUploadCode(onekeyUploadCode);
            merchantAppletConfig.setAuditId(auditResult.getAuditId().toString());
        } catch (Exception e) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
        appletConfigRepository.save(merchantAppletConfig);
        return Resp.success("小程序发布成功");
    }


}
