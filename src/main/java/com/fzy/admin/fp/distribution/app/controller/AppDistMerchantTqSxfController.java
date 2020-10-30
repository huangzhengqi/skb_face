package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.service.AppDistMerchantTqSxfService;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoTqsxfVO;
import com.fzy.admin.fp.merchant.merchant.domain.*;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 分销app-天阙随行付进件管理
 */
@RestController
@RequestMapping("/dist/app/merchant/tqsxf")
@Api(value="AppDistMerchantTqSxfController", tags={"分销app-天阙随行付进件管理"})
@Slf4j
public class AppDistMerchantTqSxfController extends BaseContent {

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    private AppDistMerchantTqSxfService appDistMerchantTqSxfService;

    @PostMapping("/into")
    @ApiOperation(value="天阙随行付进件", notes="天阙随行付进件")
    public Resp into(DistMchInfoTqsxfVO distMchInfoTqsxfVO) {

        MchInfo mchInfo=mchInfoService.findOne(distMchInfoTqsxfVO.getId());
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }

        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqSxfMccId())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付经营类目不能为空");
        }
        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqsxfWxRate())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付微信费率不能为空");
        }
        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqsxfZfbRate())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付支付宝费率不能为空");
        }

        mchInfo.setTqsxfWxRate(distMchInfoTqsxfVO.getTqsxfWxRate());
        mchInfo.setTqsxfZfbRate(distMchInfoTqsxfVO.getTqsxfZfbRate());
        mchInfo.setTqSxfMccId(distMchInfoTqsxfVO.getTqSxfMccId());
        mchInfo.setTqSxfMccName(distMchInfoTqsxfVO.getTqSxfMccName());

        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            mchInfoService.update(mchInfo);
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        mchInfoService.update(mchInfo);
        return appDistMerchantTqSxfService.into(mchInfo, topConfig, distMchInfoTqsxfVO);
    }

    @GetMapping("/detail")
    @ApiOperation(value="详情", notes="详情")
    public Resp<MchInfo> detail(String id) {
        MchInfo mchInfo=mchInfoService.findOne(id);
        if(StringUtils.isEmpty(mchInfo)){
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }
        return appDistMerchantTqSxfService.detail(mchInfo);
    }

    /**
     * 查询支行行号接口代码
     */
    @ApiOperation(value="查询支行行号接口代码", notes="查询支行行号接口代码")
    @GetMapping("/query_bank")
    public Resp queryBank(String unitedBankName) {
        return Resp.success(appDistMerchantTqSxfService.queryBank(unitedBankName));
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
        //不等于小微需要校验营业执照
        if (!mchInfo.getSubjectType().equals("SUBJECT_TYPE_XIAOWEI")) {

            if (ParamUtil.isBlank(mchInfo.getBusinessLicensePhotoId())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "营业照片不能为空");
            }
            if (ParamUtil.isBlank(mchInfo.getLicense())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照注册号不能为空");
            }
            if (ParamUtil.isBlank(mchInfo.getMerchantName())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户全称不能为空");
            }
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
            return new Resp().error(Resp.Status.PARAM_ERROR, " 身份证号码不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getPhone())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "手机号不能为空");
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
        if( ParamUtil.isBlank(mchInfo.getBankCity())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "开户城市不能为空");
        }
        if (ParamUtil.isBlank(mchInfo.getBankName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "开户银行不能为空");
        }
        if(ParamUtil.isBlank(mchInfo.getBankCardNumber())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡卡面不能为空");
        }
//        if (mchInfo.getBankName().equals("其他银行")) {
//            if (ParamUtil.isBlank(mchInfo.getBankOutlet())) {
//                return new Resp().error(Resp.Status.PARAM_ERROR, "支行不能为空");
//            }
//        }
        if (ParamUtil.isBlank(mchInfo.getAccountNumber())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "银行账号不能为空");
        }
        return Resp.success("校验通过");
    }


    /**
     * 商户入驻修改
     */
    @ApiOperation(value="商户入驻修改 仅限于图片以及一些字段的修改", notes="商户入驻修改 仅限于图片以及一些字段的修改")
    @PostMapping("/updateMerchantInfo")
    public Resp updateMerchantInfo(DistMchInfoTqsxfVO distMchInfoTqsxfVO){

        MchInfo mchInfo=mchInfoService.findOne(distMchInfoTqsxfVO.getId());
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件内容不存在");
        }

        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqSxfMccId())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付经营类目不能为空");
        }
        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqsxfWxRate())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付微信费率不能为空");
        }
        if(ParamUtil.isBlank(distMchInfoTqsxfVO.getTqsxfZfbRate())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "天阙随行付支付宝费率不能为空");
        }

        mchInfo.setTqsxfWxRate(distMchInfoTqsxfVO.getTqsxfWxRate());
        mchInfo.setTqsxfZfbRate(distMchInfoTqsxfVO.getTqsxfZfbRate());
        mchInfo.setTqSxfMccId(distMchInfoTqsxfVO.getTqSxfMccId());
        mchInfo.setTqSxfMccName(distMchInfoTqsxfVO.getTqSxfMccName());
        mchInfoService.update(mchInfo);
        return appDistMerchantTqSxfService.updateMerchantInfo(mchInfo,distMchInfoTqsxfVO);
    }


//    @GetMapping(value = "/test_area")
//    public Resp testArea(String id){
//        MchInfo mchInfo=mchInfoService.findOne(id);
//        String[] split=mchInfo.getStoreAddress().split("/");
//        String areaId="";//区
//        String cityId="";//市
//        String provinceId="";//省
//
//        //先查区(可能存在多个地区)
//        List<TqSxfArea> tqSxfAreaList=tqSxfAreaRepository.findByAreaName(split[2]);
//        for (TqSxfArea tqSxfArea : tqSxfAreaList){
//            //地区
//            TqSxfArea area=tqSxfAreaRepository.findByAreaId(tqSxfArea.getAreaId());
//            areaId = area.getAreaId();
//            //查询市
//            TqSxfArea city=tqSxfAreaRepository.findByAreaId(area.getParentId());
//            //判断是否和传过来的市对应
//            if(city.getAreaName().equals(split[1])){
//                cityId = city.getAreaId();
//                TqSxfArea province=tqSxfAreaRepository.findByAreaId(city.getParentId());
//                provinceId = province.getAreaId();
//                break;
//            }
//        }
//
//        log.info("省",provinceId);
//        log.info("市",cityId);
//        log.info("区",areaId);
//        return Resp.success("测试地区");
//    }
}
