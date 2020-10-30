package com.fzy.admin.fp.distribution.app.controller;

import com.alipay.api.AlipayApiException;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.service.AppDistMerchantZfbService;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoZfbVO;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 分销app-支付宝进件管理
 */
@RestController
@RequestMapping("/dist/app/merchant/zfb")
@Api(value="AppDistMerchantZfbController", tags={"分销app-支付宝进件管理"})
@Slf4j
public class AppDistMerchantZfbController extends BaseContent {

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    private AppDistMerchantZfbService appDistMerchantZfbService;

    @PostMapping("/into")
    @ApiOperation(value="支付宝进件", notes="支付宝进件")
    public Resp into(DistMchInfoZfbVO distMchInfoZfbVO){

        MchInfo mchInfo=mchInfoService.findOne(distMchInfoZfbVO.getId());
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }
        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        return appDistMerchantZfbService.into(topConfig,mchInfo,distMchInfoZfbVO);
    }

    @GetMapping("/detail")
    @ApiOperation(value="详情", notes="详情")
    public Resp<MchInfo> detail(String id) {

        MchInfo mchInfo=mchInfoService.findOne(id);
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        if (StringUtil.isNotEmpty(mchInfo.getBatchNo()) && !mchInfo.getZfbSuccess().equals(Integer.valueOf(4))) {
            try {
                //查看支付宝进件申请状态
                mchInfo=appDistMerchantZfbService.getZfbStatus(topConfig.getAliAppId(), topConfig.getAliPrivateKey(), topConfig.getAliPublicKey(), mchInfo);
                mchInfoService.update(mchInfo);
            } catch (AlipayApiException e) {
                e.printStackTrace();
                return Resp.success(mchInfo);
            }
        }
        return Resp.success(AppDistMerchantController.getMchInfo(mchInfo));
    }

    @GetMapping("/check")
    @ApiOperation(value="校验必填字段是否已填", notes="校验必填字段是否已填")
    public Resp check(String id){

        MchInfo mchInfo=mchInfoService.findOne(id);
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件不存在");
        }
        if (ParamUtil.isBlank(mchInfo.getSubjectType())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户类型未选择");
        }
        if(mchInfo.getSubjectType().equals("SUBJECT_TYPE_XIAOWEI")){
            return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝通道暂不支持小微商户进件");
        }
        if(ParamUtil.isBlank(mchInfo.getRepresentativeName())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "经营者/法人姓名不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getPhone())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "手机号不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getEmail())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "邮箱不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getBusinessLicensePhotoId())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getStorePhoto())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "店内照片不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getDoorPhoto())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "门头照片不能为空");
        }
        return Resp.success("校验通过");
    }
}
