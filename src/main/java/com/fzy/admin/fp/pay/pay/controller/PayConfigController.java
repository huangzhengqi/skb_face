package com.fzy.admin.fp.pay.pay.controller;


import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.domain.*;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.service.*;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-05 20:36
 * @description 支付配置接口
 */
@RestController
@RequestMapping("/pay/pay_config")
@Api(value = "PayConfigController", tags = "支付配置接口")
@Slf4j
public class PayConfigController extends BaseContent {

    @Resource
    private CompanyService companyService;

    @Resource
    private AliPayService aliPayService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private HybPayService hybPayService;

    @Resource
    private YrmPayService yrmPayService;

    @Resource
    private HsfPayService hsfPayService;

    @Resource
    private SxfPayService sxfPayService;

    @Resource
    private FyPayService fyPayService;

    @Resource
    private TqSxfPayService tqSxfPayService;

    @Resource
    private MerchantBusinessService merchantBusinessService;

    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private MchInfoService mchInfoService;

    /**
     * @author Created by wtl on 2019/6/11 21:06
     * @Description 获取服务商通用的支付参数
     */
    @GetMapping("/find_top_pay_config")
    @ApiOperation(value = "获取服务商通用的支付参数", notes = "获取服务商通用的支付参数")
    public Resp<TopConfig> findTopPayConfig() {
        String companyId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(topConfigRepository.findByServiceProviderIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG));
    }

    /**
     * @author Created by wtl on 2019/6/11 21:09
     * @Description 保存服务商支付参数
     */
    @PostMapping("/save_top_pay_config")
    @ApiOperation(value = "保存服务商支付参数", notes = "保存服务商支付参数")
    public Resp saveTopPayConfig(@Valid TopConfig model, Integer payWay) {
        switch (payWay) {
            case 1:
                if (ParamUtil.isBlank(model.getWxAppId())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信appId不能为空");
                }
                if (ParamUtil.isBlank(model.getWxAppKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信API密钥不能为空");
                }
                if (ParamUtil.isBlank(model.getWxApiv3key())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信apiV3密钥不能为空");
                }
                if (ParamUtil.isBlank(model.getWxAppSecret())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信应用密钥不能为空");
                }
                if (ParamUtil.isBlank(model.getWxMchId())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信服务商ID不能为空");
                }
                if (ParamUtil.isBlank(model.getWxCertPath())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信证书不能为空");
                }
                if (ParamUtil.isBlank(model.getWxRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信利率不能为空");
                }
                if (model.getWxRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "微信利率要大于0");
                }


                break;
            case 2:
                if (ParamUtil.isBlank(model.getAliPublicKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝平台公钥不能为空");
                }
                if (ParamUtil.isBlank(model.getAliPrivateKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝应用私钥不能为空");
                }
                if (ParamUtil.isBlank(model.getAliAppId())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝应用ID不能为空");
                }
                if (ParamUtil.isBlank(model.getZfbRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝利率不能为空");
                }
                if (model.getZfbRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝利率要大于0");
                }
                break;
            case 6:
                if (ParamUtil.isBlank(model.getFyInsCd())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友机构编码不能为空");
                }
                if (ParamUtil.isBlank(model.getFyInsPrivateKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友私钥不能为空");
                }
                if (ParamUtil.isBlank(model.getFyPreOrder())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友订单编码不能为空");
                }
                if (ParamUtil.isBlank(model.getFyWxRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友微信利率不能为空");
                }
                if (ParamUtil.isBlank(model.getFyAliRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友支付宝利率不能为空");
                }
                if (model.getFyWxRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友微信利率要大于0");
                }
                if (model.getFyAliRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "富友支付宝利率要大于0");
                }
                break;
            case 7:
                if (ParamUtil.isBlank(model.getSxfOrgId())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付机构编码不能为空");
                }
                if (ParamUtil.isBlank(model.getSxfPrivateKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付私钥不能为空");
                }

                if (ParamUtil.isBlank(model.getSxfWxRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付微信利率不能为空");
                }
                if (ParamUtil.isBlank(model.getSxfAliRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付宝利率不能为空");
                }
                if (model.getSxfWxRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付微信利率要大于0");
                }
                if (model.getSxfAliRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付宝利率要大于0");
                }
                break;
            case 12:
                if (ParamUtil.isBlank(model.getTqSxfOrgId())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付机构编码不能为空");
                }
                if (ParamUtil.isBlank(model.getTqSxfPrivateKey())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付私钥不能为空");
                }
                if (ParamUtil.isBlank(model.getTqSxfWxRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付微信利率不能为空");
                }
                if (ParamUtil.isBlank(model.getTqSxfAliRate())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付宝利率不能为空");
                }
                if (model.getTqSxfAliRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "天阀随行付支付宝利率要大于0");
                }
                if (model.getTqSxfWxRate().compareTo(BigDecimal.ZERO) <= 0) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "随行付微信利率要大于0");
                }
                    //天阀随行付私钥
                    model.setTqSxfPrivateKey(model.getTqSxfPrivateKey().replace(" ",""));
                    model.setTqSxfOrgId(model.getTqSxfOrgId().replace(" ",""));
                 break;
            default:
                return new Resp().error(Resp.Status.PARAM_ERROR, "支付方式异常");
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
        model.setSxfPrivateKey(model.getSxfPrivateKey().replace(" ", ""));
        model.setFyInsPrivateKey(model.getFyInsPrivateKey().replace(" ", ""));

        topConfigRepository.save(model);
        return Resp.success("服务商支付参数配置成功");
    }


    /**
     * @param merchantId 商户id
     * @author Created by wtl on 2019/5/5 20:36
     * @Description 根据选择支付方式获取商户的支付配置
     */
    @GetMapping("/find_config")
    public Resp findPayConfig(String merchantId) {
        Map<String, Object> map = new HashMap<>();
        WxConfig wxConfig = wxPayService.getWxConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("wx", wxConfig);

        AliConfig aliConfig = aliPayService.getAliConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("ali", aliConfig);

        HybConfig hybConfig = hybPayService.getHybConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("hyb", hybConfig);

        YrmConfig yrmConfig = yrmPayService.getYrmConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("yrm", yrmConfig);

        HsfConfig hsfConfig = hsfPayService.getHsfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("hsf", hsfConfig);

        SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("sxf", sxfConfig);

        FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("fy", fyConfig);

        TqSxfConfig tqSxfConfig=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        map.put("tqsxf", tqSxfConfig);

        return Resp.success(map);
    }

    /**
     * @author Created by wtl on 2019/5/5 20:44
     * @Description 添加或修改支付配置
     */
    @PostMapping("/save")
    public Resp save(String merchantId, Integer payWay, String payConfig) {
        if (payWay == 1) {
            WxConfig wxConfig = JacksonUtil.toObj(payConfig, WxConfig.class);
            if (ParamUtil.isBlank(wxConfig) || ParamUtil.isBlank(wxConfig.getInterestRate()) || ParamUtil.isBlank(wxConfig.getSubMchId())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            // 校验利率
            checkRate(merchantId, wxConfig.getInterestRate());
            wxConfig.setMerchantId(merchantId);
            wxPayService.getWxConfigRepository().save(wxConfig);
            //修改同步到进件表
            MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
            if (mchInfo != null) {
                mchInfo.setWxRate(wxConfig.getInterestRate());
                mchInfoService.save(mchInfo);
            }
        }
        if (payWay == 2) {
            AliConfig aliConfig = JacksonUtil.toObj(payConfig, AliConfig.class);
            if (ParamUtil.isBlank(aliConfig) || ParamUtil.isBlank(aliConfig.getInterestRate()) || ParamUtil.isBlank(aliConfig.getAppAuthToken())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, aliConfig.getInterestRate());
            aliConfig.setMerchantId(merchantId);
            aliPayService.getAliConfigRepository().save(aliConfig);
            //修改同步到进件表
            MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
            if (mchInfo != null) {
                mchInfo.setZfbRate(aliConfig.getInterestRate());
                mchInfoService.save(mchInfo);
            }
        }
        if (payWay == 3) {
            HybConfig hybConfig = JacksonUtil.toObj(payConfig, HybConfig.class);
            if (ParamUtil.isBlank(hybConfig) || ParamUtil.isBlank(hybConfig.getAppId()) || ParamUtil.isBlank(hybConfig.getAppKey())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, hybConfig.getWxInterestRate(), hybConfig.getAliInterestRate());
            hybConfig.setMerchantId(merchantId);
            hybPayService.getHybConfigRepository().save(hybConfig);

        }
        if (payWay == 4) {
            YrmConfig yrmConfig = JacksonUtil.toObj(payConfig, YrmConfig.class);
            if (ParamUtil.isBlank(yrmConfig) || ParamUtil.isBlank(yrmConfig.getMid()) || ParamUtil.isBlank(yrmConfig.getAppKey())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, yrmConfig.getWxInterestRate(), yrmConfig.getAliInterestRate());
            yrmConfig.setMerchantId(merchantId);
            yrmPayService.getYrmConfigRepository().save(yrmConfig);
        }
        if (payWay == 5) {
            HsfConfig hsfConfig = JacksonUtil.toObj(payConfig, HsfConfig.class);
            if (ParamUtil.isBlank(hsfConfig) || ParamUtil.isBlank(hsfConfig.getShopId()) || ParamUtil.isBlank(hsfConfig.getAppKey())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, hsfConfig.getWxInterestRate(), hsfConfig.getAliInterestRate());
            hsfConfig.setMerchantId(merchantId);
            hsfPayService.getHsfConfigRepository().save(hsfConfig);
        }
        if (PayChannelConstant.Channel.SXF.getCode().equals(payWay)) {
            SxfConfig sxfConfig = JacksonUtil.toObj(payConfig, SxfConfig.class);
            if (ParamUtil.isBlank(sxfConfig) || ParamUtil.isBlank(sxfConfig.getMno())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, sxfConfig.getWxInterestRate(), sxfConfig.getAliInterestRate());
            sxfConfig.setMerchantId(merchantId);
            sxfConfig.setSystemType(0);
            SxfConfig sxfConfigBeforeSave = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);

            SxfConfig SxfConfig=sxfPayService.getSxfConfigRepository().save(sxfConfig);
            try {
                PayRes payRes = sxfPayService.qrcodeProductSetup(sxfConfig.getWxInterestRate(), sxfConfig.getAliInterestRate(), merchantId);
                if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
                    if (sxfConfigBeforeSave == null) {
                        sxfPayService.getSxfConfigRepository().delete(SxfConfig);
                    }
                    return new Resp().error(Resp.Status.PARAM_ERROR, payRes.getMsg());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (sxfConfigBeforeSave == null) {
                    sxfPayService.getSxfConfigRepository().delete(sxfConfig);
                } else {
                    sxfConfig.setAliInterestRate(sxfConfigBeforeSave.getAliInterestRate());
                    sxfConfig.setWxInterestRate(sxfConfigBeforeSave.getWxInterestRate());
                    sxfConfig.setMno(sxfConfigBeforeSave.getMno());
                    sxfPayService.getSxfConfigRepository().save(sxfConfig);
                }
                return new Resp().error(Resp.Status.PARAM_ERROR, "费率设置失败，请填写正确的商户编码");
            }

            //修改同步到进件表
            MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
            if (mchInfo != null) {
                mchInfo.setSxfWxRate(sxfConfig.getWxInterestRate());
                mchInfo.setSxfZfbRate(sxfConfig.getAliInterestRate());
                mchInfoService.save(mchInfo);
            }

        }
        if (PayChannelConstant.Channel.FY.getCode().equals(payWay)) {
            FyConfig fyConfig = JacksonUtil.toObj(payConfig, FyConfig.class);
            if (ParamUtil.isBlank(fyConfig) || ParamUtil.isBlank(fyConfig.getMchntCd())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, fyConfig.getWxInterestRate(), fyConfig.getAliInterestRate());
            fyConfig.setMerchantId(merchantId);
            log.info("fyParam===" + fyConfig);
            fyPayService.getFyConfigRepository().save(fyConfig);
            //修改同步到进件表
            MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
            if (mchInfo != null) {
                mchInfo.setFyWxRate(fyConfig.getWxInterestRate());
                mchInfo.setFyZfbRate(fyConfig.getAliInterestRate());
                mchInfoService.save(mchInfo);
            }
        }

        if(PayChannelConstant.Channel.TQSXF.getCode().equals(payWay)){
            TqSxfConfig tqSxfConfig=JacksonUtil.toObj(payConfig, TqSxfConfig.class);
            if (ParamUtil.isBlank(tqSxfConfig) || ParamUtil.isBlank(tqSxfConfig.getMno())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "参数请填写完整");
            }
            checkRate(merchantId, tqSxfConfig.getWxInterestRate(), tqSxfConfig.getAliInterestRate());
            tqSxfConfig.setMerchantId(merchantId);
            TqSxfConfig TqSxfConfigBeforeSave=tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);

            TqSxfConfig TqSxfConfig=tqSxfPayService.getSxfConfigRepository().save(tqSxfConfig);

            try {
                PayRes payRes = tqSxfPayService.qrcodeProductSetup(tqSxfConfig.getWxInterestRate(), tqSxfConfig.getAliInterestRate(), merchantId);
                if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
                    if (TqSxfConfigBeforeSave == null) {
                        tqSxfPayService.getSxfConfigRepository().delete(TqSxfConfig);
                    }
                    return new Resp().error(Resp.Status.PARAM_ERROR, payRes.getMsg());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (TqSxfConfigBeforeSave == null) {
                    tqSxfPayService.getSxfConfigRepository().delete(TqSxfConfig);
                } else {
                    tqSxfConfig.setAliInterestRate(TqSxfConfigBeforeSave.getAliInterestRate());
                    tqSxfConfig.setWxInterestRate(TqSxfConfigBeforeSave.getWxInterestRate());
                    tqSxfConfig.setMno(TqSxfConfigBeforeSave.getMno());
                    tqSxfPayService.getSxfConfigRepository().save(tqSxfConfig);
                }
                return new Resp().error(Resp.Status.PARAM_ERROR, "费率设置失败，请填写正确的商户编码");
            }

            //修改同步到进件表
            MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
            if (mchInfo != null) {
                mchInfo.setTqsxfWxRate(tqSxfConfig.getWxInterestRate());
                mchInfo.setTqsxfZfbRate(tqSxfConfig.getAliInterestRate());
                mchInfoService.save(mchInfo);
            }
        }
        return Resp.success("保存成功");
    }

    /**
     * @author Created by wtl on 2019/6/12 0:23
     * @Description 校验利率
     */
    private void checkRate(String merchantId, BigDecimal rate) {
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("利率要大于0", Resp.Status.PARAM_ERROR.getCode());
        }
        if (rate.scale() > 4) {
            throw new BaseException("利率只能保留4位小数", Resp.Status.PARAM_ERROR.getCode());
        }
        // 获取当选择商户的佣金比例
        MerchantVO merchantVO = merchantBusinessService.findByMerchantId(merchantId);
        /*if (rate.compareTo(merchantVO.getPayProrata()) > 0) {
            throw new BaseException("利率要小于等于商户的手续费率:" + merchantVO.getPayProrata(), Resp.Status.PARAM_ERROR.getCode());
        }*/
    }

    private void checkRate(String merchantId, BigDecimal rate, BigDecimal r) {
        if (rate.compareTo(BigDecimal.ZERO) <= 0 || r.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("利率要大于0", Resp.Status.PARAM_ERROR.getCode());
        }
        if (rate.scale() > 4 || r.scale() > 4) {
            throw new BaseException("利率只能保留4位小数", Resp.Status.PARAM_ERROR.getCode());
        }
        // 获取当选择商户的佣金比例
        /*MerchantVO merchantVO = merchantBusinessService.findByMerchantId(merchantId);
        if (rate.compareTo(merchantVO.getPayProrata()) > 0 || r.compareTo(merchantVO.getPayProrata()) > 0) {
            throw new BaseException("利率要小于等于商户的手续费率:" + merchantVO.getPayProrata(), Resp.Status.PARAM_ERROR.getCode());
        }*/
    }

}
