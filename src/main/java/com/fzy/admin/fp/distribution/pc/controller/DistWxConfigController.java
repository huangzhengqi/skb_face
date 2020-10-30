package com.fzy.admin.fp.distribution.pc.controller;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.pc.domain.DistWxConfig;
import com.fzy.admin.fp.distribution.pc.repository.DistWxConfigReposotory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fzy.admin.fp.common.validation.annotation.Valid;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dist/wxconfig")
@Api(value = "DistWxConfigController", tags = {"分销-微信参数"})
public class DistWxConfigController extends BaseContent{

    @Resource
    private DistWxConfigReposotory distWxConfigReposotory;

    @Resource
    private CompanyService companyService;

    /**
     * @Description 保存微信商户支付参数
     */
    @PostMapping("/save_wx_pay_config")
    @ApiOperation(value = "保存微信支付参数", notes = "保存微信支付参数")
    public Resp saveWxConfig(@Valid DistWxConfig model){
        if (ParamUtil.isBlank(model.getAppId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信appId不能为空");
        }
        if (ParamUtil.isBlank(model.getWxAppKey())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信API密钥不能为空");
        }
        if (ParamUtil.isBlank(model.getWxAppSecret())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信应用密钥不能为空");
        }
        if (ParamUtil.isBlank(model.getWxMchId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信商户ID不能为空");
        }
        if (ParamUtil.isBlank(model.getWxCertPath())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信证书不能为空");
        }

        String companyId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        if (ParamUtil.isBlank(companyId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "服务商不存在");
        }
        Company company = companyService.getRepository().findOne(companyId);
        if (ParamUtil.isBlank(company)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "服务商不存在");
        }
        model.setServiceProviderId(companyId);
        DistWxConfig distWxConfig = distWxConfigReposotory.save(model);
        return Resp.success(distWxConfig,"保存成功");
    }


    /**
     * @Description 查询微信商户支付参数
     */
    @GetMapping("/query_wx_pay_config")
    @ApiOperation(value = "查询微信支付参数", notes = "查询微信支付参数")
    public Resp<DistWxConfig> queryWxPayConfig(){
        String companyId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(distWxConfigReposotory.findByServiceProviderIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG));
    }

}
