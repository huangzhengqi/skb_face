package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.service.AppDistMerchantWxService;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoWxVO;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hzq
 * @Description: 分销app-微信进件管理
 */
@RestController
@RequestMapping("/dist/app/merchant/wx")
@Api(value="AppDistMerchantWxController", tags={"分销app-微信进件管理"})
@Slf4j
public class AppDistMerchantWxController extends BaseContent {

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    private AppDistMerchantWxService appDistMerchantWxService;

    @PostMapping("/into")
    @ApiOperation(value="微信进件", notes="微信进件")
    public Resp into(DistMchInfoWxVO distMchInfoWxVO) {

        MchInfo mchInfo=mchInfoService.findOne(distMchInfoWxVO.getId());
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }
        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            mchInfoService.update(mchInfo);
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        return appDistMerchantWxService.into(topConfig, mchInfo,distMchInfoWxVO);
    }


    @GetMapping("/detail")
    @ApiOperation(value="微信进件详情", notes="微信进件详情")
    public Resp detail(String id) {

        MchInfo mchInfo=mchInfoService.findOne(id);
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        return appDistMerchantWxService.detail(mchInfo,topConfig);
    }

    @GetMapping("/check")
    @ApiOperation(value="校验必填字段是否已填", notes="校验必填字段是否已填")
    public Resp check(String id) {

        MchInfo mchInfo=mchInfoService.findOne(id);
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件不存在");
        }
        if (ParamUtil.isBlank(mchInfo.getSubjectType())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户类型未选择");
        }
        if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_XIAOWEI")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信通道暂不支持小微商户进件");
        }
        if (ParamUtil.isBlank(mchInfo.getEpresentativePhotoId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "身份证人像面不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getEpresentativePhotoId2())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "身份证国徽面不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getRepresentativeName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "经营者/法人姓名不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getCertificateNum())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "身份证号码不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getStartCertificateTime())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "身份证证件开始时间不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getEndCertificateTime())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "身份证证件结束时间不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getPhone())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "手机号不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getEmail())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "邮箱不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getBusinessLicensePhotoId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "营业照片不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getLicense())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照注册号不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getMerchantName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户全称不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getDoorPhoto())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "门头照不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getStorePhoto())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "店内照不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getShortName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户简称不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getStoreAddress())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "门店省市区不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getRegisterAddress())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "经营地址不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getCusServiceTel())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "客服电话不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getAccountHolder())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "开户名称不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getBankName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "开户银行不能为空");
        }
        if (mchInfo.getBankName().equals("其他银行")) {
            if (ParamUtil.isBlank(mchInfo.getBankOutlet())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "支行不能为空");
            }
        }
        if (ParamUtil.isBlank(mchInfo.getAccountNumber())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "银行账号不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getBankCity())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "开户城市不能为空");
        }
        return Resp.success("校验通过");
    }


}
