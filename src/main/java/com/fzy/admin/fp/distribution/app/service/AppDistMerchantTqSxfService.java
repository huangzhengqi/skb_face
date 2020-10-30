package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoTqsxfVO;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.merchant.domain.*;
import com.fzy.admin.fp.merchant.merchant.repository.*;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TqSxfConfigRepository;
import com.fzy.admin.fp.pay.pay.vo.TqSxfIncomeVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfQueryMerchantInfoVO;
import com.fzy.admin.fp.pay.pay.vo.UploadPictureVO;
import com.fzy.admin.fp.sdk.pay.feign.TqSxfPayServiceFeign;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AppDistMerchantTqSxfService {


    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private TqSxfCityNoRepository tqSxfCityNoRepository;

    @Resource
    private TqSxfAreaRepository tqSxfAreaRepository;

    @Resource
    private TqSxfConfigRepository tqSxfConfigRepository;

    @Resource
    private TqSxfBankRepository tqSxfBankRepository;

    @Resource
    private TqSxfPayServiceFeign tqSxfPayServiceFeign;

    @Resource
    private TqSxfWxBankRepository tqSxfWxBankRepository;

    @Resource
    private MchInfoRepository mchInfoRepository;

    @Resource
    private MerchantService merchantService;


    @Value("${file.upload.path}")
    public String prefixPath;

    /**
     * 天阙随行付首次进件
     * @param mchInfo
     * @param topConfig
     * @param distMchInfoTqsxfVO
     * @return
     */
    @Transactional
    public Resp into(MchInfo mchInfo, TopConfig topConfig, DistMchInfoTqsxfVO distMchInfoTqsxfVO) {

        TqSxfIncomeVO tqSxfIncomeVO= new TqSxfIncomeVO();

        try {
            Map<String, Object> params=new HashMap<>();
            //结算账户类型
            if (mchInfo.getAccountType().equals("对公账户")) {
                if (StringUtil.isBlank(distMchInfoTqsxfVO.getOpeningPermit())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "对公结算必须上传开户许可证");
                }
                //开户许可证
                FileInfoVo bankCardNumberVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(distMchInfoTqsxfVO.getOpeningPermit());
                String bankCardNumberPath=prefixPath + bankCardNumberVo.getPath();
                UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(bankCardNumberPath, "04");
                if(uploadPictureVO.getBizCode().equals("0001")){
                    return new Resp().error(Resp.Status.PARAM_ERROR, "开户许可证"+ uploadPictureVO.getBizMsg());
                }
                params.put("openingAccountLicensePic", uploadPictureVO.getPhotoUrl());
                mchInfo.setOpeningPermit(distMchInfoTqsxfVO.getOpeningPermit());

                if (StringUtils.isEmpty(mchInfo.getBankOutlet())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "对公结算必须填写开户支行名称");
                }
                TqSxfBank tqSxfBank=tqSxfBankRepository.findByUnitedBankName(mchInfo.getBankOutlet());
                if (StringUtils.isEmpty(tqSxfBank)) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "开户支行不存在，请输入完整的开户支行名称");
                }
                params.put("actTyp", "00");
                params.put("lbnkNo", tqSxfBank.getUnitedBankNo());//开户支行联行行号

            } else {
                FileInfoVo bankCardNumberVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBankCardNumber());
                if(bankCardNumberVo == null){
                    return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡正面照不能为空");
                }
                String bankCardNumberPath=prefixPath + bankCardNumberVo.getPath();
                UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(bankCardNumberPath, "05");
                if(uploadPictureVO.getBizCode().equals("0001")){
                    return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡正面照" + uploadPictureVO.getBizMsg());
                }
                params.put("bankCardPositivePic",uploadPictureVO.getPhotoUrl());
                mchInfo.setBankCardNumber(mchInfo.getBankCardNumber());
                params.put("actTyp", "01");
                params.put("stmManIdNo", mchInfo.getCertificateNum());//结算人证件号

                //对私结算时：18 家银行无需传入，如发卡行是村镇银行、城市商业银行、农村商业银行或其他银行则必须传入联行号
                if(mchInfo.getBankName().equals("村镇银行") || mchInfo.getBankName().equals("城市商业银行") || mchInfo.getBankName().equals("城市商业银行") || mchInfo.getBankName().equals("农村商业银行") || mchInfo.getBankName().equals("其他银行")){
                    TqSxfBank tqSxfBank=tqSxfBankRepository.findByUnitedBankName(mchInfo.getBankOutlet());
                    if (StringUtils.isEmpty(tqSxfBank)) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "开户支行不存在，请输入完整的开户支行名称");
                    }
                    params.put("lbnkNo", tqSxfBank.getUnitedBankNo());//开户支行联行行号
                }

            }


            params.put("mecDisNm", mchInfo.getShortName()); //商户简称
            params.put("mblNo", mchInfo.getPhone());//商户手机号
            params.put("operationalType", "01");//经营类型


            //资质类型
            //小微商户
            if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_XIAOWEI")) {
                params.put("haveLicenseNo", "01");
            }

            //个体户
            if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_INDIVIDUAL")) {
                params.put("haveLicenseNo", "02");
                params.put("cprRegNmCn", mchInfo.getMerchantName()); //营业执照注册名称 （对应微信的商户名称字段《merchant_name》）
                params.put("registCode", mchInfo.getLicense()); //营业执照注册号

                //营业执照照片
                FileInfoVo businessLicensePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
                String businessLicensePhotoPath=prefixPath + businessLicensePhotoVo.getPath();
                UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(businessLicensePhotoPath, "13");
                if(uploadPictureVO.getBizCode().equals("0001")){
                    return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片" + uploadPictureVO.getBizMsg());
                }
                params.put("licensePic",uploadPictureVO.getPhotoUrl());
            }

            //企业
            if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_ENTERPRISE")) {
                params.put("haveLicenseNo", "03");
                params.put("cprRegNmCn", mchInfo.getMerchantName()); //营业执照注册名称 （对应微信的商户名称字段《merchant_name》）
                params.put("registCode", mchInfo.getLicense()); //营业执照注册号
                params.put("licenseMatch", "00"); //是否三证合一  取值范围：00 是 01 否

                //营业执照照片
                FileInfoVo businessLicensePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
                String businessLicensePhotoPath=prefixPath + businessLicensePhotoVo.getPath();
                UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(businessLicensePhotoPath, "13");
                if(uploadPictureVO.getBizCode().equals("0001")){
                    return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片" + uploadPictureVO.getBizMsg());
                }
                params.put("licensePic",uploadPictureVO.getPhotoUrl());
            }

            params.put("mecTypeFlag", "00");//商户类型  00 普通单店商户（非连锁商户） 01 连锁总 02 连锁分 03 1+n 总 04 1+n 分

            //设置费率
            Map<String, String> wxQrcodeList=new HashMap<>(2);
            wxQrcodeList.put("rate", mchInfo.getTqsxfWxRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
            wxQrcodeList.put("rateType", "01");
            Map<String, String> aliQrcodeList=new HashMap<>(2);
            aliQrcodeList.put("rate", mchInfo.getTqsxfZfbRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
            aliQrcodeList.put("rateType", "02");
            Map<String, String> yinLianQrcodeList=new HashMap<>(2); // 银联单笔小于 1000
            yinLianQrcodeList.put("rate", "0.35");
            yinLianQrcodeList.put("rateType", "06");
            Map<String, String> yinLianQrcodeList2=new HashMap<>(2); // 银联单笔大于 1000
            yinLianQrcodeList2.put("rate", "0.61");
            yinLianQrcodeList2.put("rateType", "07");
            List<Map<String, String>> qrcodeList=new ArrayList<>();
            qrcodeList.add(wxQrcodeList);
            qrcodeList.add(aliQrcodeList);
            qrcodeList.add(yinLianQrcodeList);
            qrcodeList.add(yinLianQrcodeList2);
            params.put("qrcodeList", qrcodeList);

            params.put("cprRegAddr", mchInfo.getRegisterAddress()); //商户经营详细地址
            String[] split=mchInfo.getStoreAddress().split("/");
            String areaId="";//区
            String cityId="";//市
            String provinceId="";//省

            //先查区(可能存在多个地区)
            List<TqSxfArea> tqSxfAreaList=tqSxfAreaRepository.findByAreaName(split[2]);
            if(tqSxfAreaList.equals(null) || tqSxfAreaList.size() == 0){
                return new Resp().error(Resp.Status.PARAM_ERROR, "省市区地址错误");
            }
            for (TqSxfArea tqSxfArea : tqSxfAreaList) {
                //地区
                TqSxfArea area=tqSxfAreaRepository.findByAreaId(tqSxfArea.getAreaId());
                areaId=area.getAreaId();
                //查询市
                TqSxfArea city=tqSxfAreaRepository.findByAreaId(area.getParentId());
                //判断是否和传过来的市对应
                if (city.getAreaName().equals(split[1])) {
                    cityId=city.getAreaId();
                    TqSxfArea province=tqSxfAreaRepository.findByAreaId(city.getParentId());
                    provinceId=province.getAreaId();
                    break;
                }
            }
            params.put("regProvCd", provinceId); //商户经营地址省编码
            params.put("regCityCd", cityId); //商户经营地址市编码
            params.put("regDistCd", areaId); //商户经营地址地区编码

            params.put("mccCd", distMchInfoTqsxfVO.getTqSxfMccId()); //经营类目
            params.put("csTelNo", mchInfo.getCusServiceTel()); //客服电话
            params.put("identityName", mchInfo.getRepresentativeName()); //法人
            params.put("identityTyp", "00");//证件类型
            params.put("identityNo", mchInfo.getCertificateNum());//法人证件号
            params.put("actNm", mchInfo.getAccountHolder());//结算账户名
            params.put("actNo", mchInfo.getAccountNumber());//结算卡号

            TqSxfWxBank byTqSxfBank=tqSxfWxBankRepository.findByTqSxfBankName(mchInfo.getBankName());//开户银行
            params.put("depoBank", byTqSxfBank.getBankNo());
            String[] split1=mchInfo.getBankCity().split("/");
            TqSxfCityNo tqSxfCityNo=tqSxfCityNoRepository.findByCityName(split1[1]);//开户城市
            if(StringUtils.isEmpty(tqSxfCityNo)){
                return new Resp().error(Resp.Status.PARAM_ERROR, "开户城市搜索不到，请联系客服人员");
            }
            params.put("depoProvCd", tqSxfCityNo.getProvinceNo()); //开户省份
            params.put("depoCityCd", tqSxfCityNo.getCityNo());//开户城市

            //法人/商户负责人身份证正面照（人像面）
            FileInfoVo idCardCopyVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId());
            if(idCardCopyVo == null){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证正面照不能为空");
            }
            String idCardCopyPath=prefixPath + idCardCopyVo.getPath();
            UploadPictureVO uploadPictureVO02=tqSxfPayServiceFeign.photoUrl(idCardCopyPath, "02");
            if(uploadPictureVO02.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证正面照" + uploadPictureVO02.getBizMsg());
            }
            params.put("legalPersonidPositivePic",uploadPictureVO02.getPhotoUrl());

            //法人/商户负责人身份证反面照（国徽面）
            FileInfoVo idCardNationalVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId2());
            if(idCardNationalVo == null){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证反面照不能为空");
            }
            String idCardNationalPath=prefixPath + idCardNationalVo.getPath();
            UploadPictureVO uploadPictureVO03=tqSxfPayServiceFeign.photoUrl(idCardNationalPath, "03");
            if(uploadPictureVO03.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证反面照" + uploadPictureVO03.getBizMsg());
            }
            params.put("legalPersonidOppositePic", uploadPictureVO03.getPhotoUrl());

            //门头照片
            FileInfoVo doorPhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
            String doorPhotoPath=prefixPath + doorPhotoVo.getPath();
            UploadPictureVO uploadPictureVO10=tqSxfPayServiceFeign.photoUrl(doorPhotoPath, "10");
            if(uploadPictureVO10.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "门头照片" + uploadPictureVO10.getBizMsg());
            }
            params.put("storePic",uploadPictureVO10.getPhotoUrl());

            //真实商户内景图片
            FileInfoVo StorePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
            String StorePhotoPath=prefixPath + StorePhotoVo.getPath();
            UploadPictureVO uploadPictureVO11=tqSxfPayServiceFeign.photoUrl(StorePhotoPath, "11");
            if(uploadPictureVO11.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户内景照" + uploadPictureVO11.getBizMsg());
            }
            params.put("insideScenePic", uploadPictureVO11.getPhotoUrl());

            tqSxfIncomeVO=tqSxfPayServiceFeign.income(params, mchInfo.getMerchantId());
            mchInfo.setApplicationId(tqSxfIncomeVO.getApplicationId());

            if (tqSxfIncomeVO.getBizCode().equals("0000")) {
                mchInfo.setTqSxfMsg(tqSxfIncomeVO.getBizMsg());
            } else {
                mchInfo.setTqSxfSuccess(Integer.valueOf(0));
                mchInfo.setTqSxfMsg(tqSxfIncomeVO.getBizMsg());
            }
            mchInfoRepository.saveAndFlush(mchInfo);
            return Resp.success(tqSxfIncomeVO);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("异常信息---------------{}",e.getMessage());
        }
        return Resp.success(tqSxfIncomeVO);
    }


    /**
     * 天阙随行付查看详情
     * @param mchInfo
     * @return
     */
    @Transactional
    public Resp<MchInfo> detail(MchInfo mchInfo) {
        try {
            if (StringUtil.isNotEmpty(mchInfo.getApplicationId())) {
                Map<String, Object> params=new HashMap<>();
                params.put("applicationId", mchInfo.getApplicationId());
                TqSxfQueryMerchantInfoVO tqSxfQueryMerchantInfoVO=tqSxfPayServiceFeign.queryMerchantInfo(params, mchInfo.getMerchantId());
                log.info("返回的具体参数 ========= {}" , tqSxfQueryMerchantInfoVO.toString());
                //天阙随行付商编
                mchInfo.setTqSxfMno(tqSxfQueryMerchantInfoVO.getMno());
                //审核中
                if (tqSxfQueryMerchantInfoVO.getTaskStatus().equals("0")) {
                    mchInfo.setTqSxfSuccess(Integer.valueOf(4));
                    mchInfo.setTqSxfMsg(tqSxfQueryMerchantInfoVO.getBizMsg());
                    //如果返回的子商户号不为空
                    if(StringUtil.isNotBlank(tqSxfQueryMerchantInfoVO.getSubMchId())){
                        //添加商编到天阙随行付配置参数表
                        TqSxfConfig tqSxfConfig=tqSxfConfigRepository.findByMerchantIdAndDelFlag(mchInfo.getMerchantId(), Integer.valueOf(1));
                        if (tqSxfConfig == null) {
                            TqSxfConfig tqSxfConfig1=new TqSxfConfig();
                            tqSxfConfig1.setMerchantId(mchInfo.getMerchantId());
                            tqSxfConfig1.setMno(tqSxfQueryMerchantInfoVO.getMno());
                            tqSxfConfig1.setWxInterestRate(mchInfo.getTqsxfWxRate());
                            tqSxfConfig1.setAliInterestRate(mchInfo.getTqsxfZfbRate());
                            tqSxfConfig1.setSubMchId(tqSxfQueryMerchantInfoVO.getSubMchId());
                            tqSxfConfigRepository.save(tqSxfConfig1);
                        }
                    }
                    //判断是否已激活如果激活则不修改状态
                    if(!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))){
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                }
                //入驻通过
                if (tqSxfQueryMerchantInfoVO.getTaskStatus().equals("1")) {
                    mchInfo.setTqSxfSuccess(Integer.valueOf(1));
                    mchInfo.setTqSxfMsg("入驻成功,请完成微信商家授权");

                    //判断是否已激活如果激活则不修改状态
                    if(!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))){
                        mchInfo.setStatus(Integer.valueOf(3));
                    }

                    //完成帮商户自动启用
                    Merchant merchant=merchantService.findOne(mchInfo.getMerchantId());
                    //如果禁用就启用
                    if(merchant.getStatus().equals(Integer.valueOf(2))){
                        merchant.setStatus(Integer.valueOf(1));//启用
                        merchantService.update(merchant);
                    }

                    //添加商编到天阙随行付配置参数表
                    TqSxfConfig tqSxfConfig=tqSxfConfigRepository.findByMerchantIdAndDelFlag(mchInfo.getMerchantId(), Integer.valueOf(1));
                    if (tqSxfConfig == null) {
                        TqSxfConfig tqSxfConfig1=new TqSxfConfig();
                        tqSxfConfig1.setMerchantId(mchInfo.getMerchantId());
                        tqSxfConfig1.setMno(tqSxfQueryMerchantInfoVO.getMno());
                        tqSxfConfig1.setWxInterestRate(mchInfo.getTqsxfWxRate());
                        tqSxfConfig1.setAliInterestRate(mchInfo.getTqsxfZfbRate());
                        tqSxfConfig1.setSubMchId(tqSxfQueryMerchantInfoVO.getSubMchId());
                        tqSxfConfigRepository.save(tqSxfConfig1);
                    }else {
                        tqSxfConfig.setSubMchId(tqSxfQueryMerchantInfoVO.getSubMchId());
                        tqSxfConfigRepository.save(tqSxfConfig);
                        log.info("tqSxfConfig ============== {}", tqSxfConfig);
                    }
                }
                //入驻驳回
                if (tqSxfQueryMerchantInfoVO.getTaskStatus().equals("2")) {
                    mchInfo.setTqSxfSuccess(Integer.valueOf(2));
                    mchInfo.setTqSxfMsg(tqSxfQueryMerchantInfoVO.getBizMsg() + "，驳回原因 : " +tqSxfQueryMerchantInfoVO.getSuggestion());
                    //判断是否已激活如果激活则不修改状态
                    if(!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))){
                        mchInfo.setStatus(Integer.valueOf(2));
                    }

                }
                //入驻图片驳回
                if (tqSxfQueryMerchantInfoVO.getTaskStatus().equals("3")) {
                    mchInfo.setTqSxfSuccess(Integer.valueOf(3));
                    mchInfo.setTqSxfMsg(tqSxfQueryMerchantInfoVO.getBizMsg() + "，驳回原因 : " + tqSxfQueryMerchantInfoVO.getSuggestion());
                    //判断是否已激活如果激活则不修改状态
                    if(!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))){
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    //如果返回的子商户号不为空
                    if(StringUtil.isNotBlank(tqSxfQueryMerchantInfoVO.getSubMchId())){
                        //添加商编到天阙随行付配置参数表
                        TqSxfConfig tqSxfConfig=tqSxfConfigRepository.findByMerchantIdAndDelFlag(mchInfo.getMerchantId(), Integer.valueOf(1));
                        if (tqSxfConfig == null) {
                            TqSxfConfig tqSxfConfig1=new TqSxfConfig();
                            tqSxfConfig1.setMerchantId(mchInfo.getMerchantId());
                            tqSxfConfig1.setMno(tqSxfQueryMerchantInfoVO.getMno());
                            tqSxfConfig1.setWxInterestRate(mchInfo.getTqsxfWxRate());
                            tqSxfConfig1.setAliInterestRate(mchInfo.getTqsxfZfbRate());
                            tqSxfConfig1.setSubMchId(tqSxfQueryMerchantInfoVO.getSubMchId());
                            tqSxfConfigRepository.save(tqSxfConfig1);
                        }
                    }
                }
                mchInfoRepository.saveAndFlush(mchInfo);
            }
            return Resp.success(mchInfo, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("异常信息类---------------{}",e);
            log.info("异常信息---------------{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return Resp.success(mchInfo, "FAILURE");
    }

    public List queryBank(String unitedBankName) {
        return tqSxfBankRepository.findByUnitedBankNameLike(unitedBankName);
    }


    /**
     * 入驻修改
     * @param mchInfo
     * @param distMchInfoTqsxfVO
     * @return
     */
    @Transactional
    public Resp updateMerchantInfo(MchInfo mchInfo, DistMchInfoTqsxfVO distMchInfoTqsxfVO) {

        TqSxfIncomeVO tqSxfIncomeVO = new TqSxfIncomeVO();
        try {
            log.info("修改入驻的进件内容 ============= {}",mchInfo.toString());
            Map<String, Object> params=new HashMap<>();
            //查询状态是入驻驳回还是入驻图片驳回
            Map<String, Object> req=new HashMap<>();
            req.put("applicationId", mchInfo.getApplicationId());
            TqSxfQueryMerchantInfoVO tqSxfQueryMerchantInfoVO=tqSxfPayServiceFeign.queryMerchantInfo(req, mchInfo.getMerchantId());
            if (tqSxfQueryMerchantInfoVO.getBizCode().equals("0001")) {
                return new Resp().error(Resp.Status.PARAM_ERROR, tqSxfQueryMerchantInfoVO.getBizMsg());
            }
            if (!tqSxfQueryMerchantInfoVO.getTaskStatus().equals("3")) {

                params.put("operationalType", "01");//经营类型
                params.put("mecTypeFlag", "00");//商户类型
                params.put("identityName", mchInfo.getRepresentativeName()); //法人
                params.put("identityTyp", "00");//证件类型
                params.put("identityNo", mchInfo.getCertificateNum());//法人证件号
                //小微商户
                if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_XIAOWEI")) {
                    params.put("haveLicenseNo", "01");
                }
                //个体户
                if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_INDIVIDUAL")) {
                    params.put("haveLicenseNo", "02");
                    params.put("cprRegNmCn", mchInfo.getMerchantName()); //营业执照注册名称 （对应微信的商户名称字段《merchant_name》）
                    params.put("registCode", mchInfo.getLicense()); //营业执照注册号

                    //营业执照照片
                    FileInfoVo businessLicensePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
                    String businessLicensePhotoPath=prefixPath + businessLicensePhotoVo.getPath();
                    UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(businessLicensePhotoPath, "13");
                    if(uploadPictureVO.getBizCode().equals("0001")){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片" + uploadPictureVO.getBizMsg());
                    }
                    params.put("licensePic",uploadPictureVO.getPhotoUrl());
                }
                //企业
                if (mchInfo.getSubjectType().equals("SUBJECT_TYPE_ENTERPRISE")) {
                    params.put("haveLicenseNo", "03");
                    params.put("cprRegNmCn", mchInfo.getMerchantName()); //营业执照注册名称 （对应微信的商户名称字段《merchant_name》）
                    params.put("registCode", mchInfo.getLicense()); //营业执照注册号
                    params.put("licenseMatch", "00"); //是否三证合一  取值范围：00 是 01 否

                    //营业执照照片
                    FileInfoVo businessLicensePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
                    String businessLicensePhotoPath=prefixPath + businessLicensePhotoVo.getPath();
                    UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(businessLicensePhotoPath, "13");
                    if(uploadPictureVO.getBizCode().equals("0001")){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片" + uploadPictureVO.getBizMsg());
                    }
                    params.put("licensePic",uploadPictureVO.getPhotoUrl());
                }

                //结算账户类型
                if (mchInfo.getAccountType().equals("对公账户")) {
                    //开户许可证
                    FileInfoVo bankCardNumberVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getOpeningPermit());
                    if(bankCardNumberVo == null){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "开户许可证不能为空");
                    }
                    String bankCardNumberPath=prefixPath + bankCardNumberVo.getPath();
                    UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(bankCardNumberPath, "04");
                    if(uploadPictureVO.getBizCode().equals("0001")){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "开户许可证"+ uploadPictureVO.getBizMsg());
                    }
                    params.put("openingAccountLicensePic", uploadPictureVO.getPhotoUrl());


                    if (StringUtils.isEmpty(mchInfo.getBankOutlet())) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "对公结算必须填写开户支行名称");
                    }
                    TqSxfBank tqSxfBank=tqSxfBankRepository.findByUnitedBankName(mchInfo.getBankOutlet());
                    if (StringUtils.isEmpty(tqSxfBank)) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "开户支行不存在，请输入完整的开户支行名称");
                    }
                    params.put("actTyp", "00");
                    params.put("lbnkNo", tqSxfBank.getUnitedBankNo());//开户支行联行行号

                } else {
                    FileInfoVo bankCardNumberVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBankCardNumber());
                    log.info("bankCardNumberVo(银行卡正面照地址) =============== {}", bankCardNumberVo.toString());
                    if(bankCardNumberVo == null){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡正面照不能为空");
                    }
                    String bankCardNumberPath=prefixPath + bankCardNumberVo.getPath();
                    UploadPictureVO uploadPictureVO=tqSxfPayServiceFeign.photoUrl(bankCardNumberPath, "05");
                    if(uploadPictureVO.getBizCode().equals("0001")){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡正面照" + uploadPictureVO.getBizMsg());
                    }
                    params.put("bankCardPositivePic",uploadPictureVO.getPhotoUrl());

                    params.put("actTyp", "01");
                    params.put("stmManIdNo", mchInfo.getCertificateNum());//结算人证件号

                    //对私结算时：18 家银行无需传入，如发卡行是村镇银行、城市商业银行、农村商业银行或其他银行则必须传入联行号
                    if(mchInfo.getBankName().equals("村镇银行") || mchInfo.getBankName().equals("城市商业银行") || mchInfo.getBankName().equals("城市商业银行") || mchInfo.getBankName().equals("农村商业银行") || mchInfo.getBankName().equals("其他银行")){
                        TqSxfBank tqSxfBank=tqSxfBankRepository.findByUnitedBankName(mchInfo.getBankOutlet());
                        if (StringUtils.isEmpty(tqSxfBank)) {
                            return new Resp().error(Resp.Status.PARAM_ERROR, "开户支行不存在，请输入完整的开户支行名称");
                        }
                        params.put("lbnkNo", tqSxfBank.getUnitedBankNo());//开户支行联行行号
                    }
                }

                //设置费率
                Map<String, String> wxQrcodeList=new HashMap<>(2);
                wxQrcodeList.put("rate", mchInfo.getTqsxfWxRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
                wxQrcodeList.put("rateType", "01");
                Map<String, String> aliQrcodeList=new HashMap<>(2);
                aliQrcodeList.put("rate", mchInfo.getTqsxfZfbRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
                aliQrcodeList.put("rateType", "02");
                Map<String, String> yinLianQrcodeList=new HashMap<>(2); // 银联单笔小于 1000
                yinLianQrcodeList.put("rate", "0.35");
                yinLianQrcodeList.put("rateType", "06");
                Map<String, String> yinLianQrcodeList2=new HashMap<>(2); // 银联单笔大于 1000
                yinLianQrcodeList2.put("rate", "0.61");
                yinLianQrcodeList2.put("rateType", "07");
                List<Map<String, String>> qrcodeList=new ArrayList<>();
                qrcodeList.add(wxQrcodeList);
                qrcodeList.add(aliQrcodeList);
                qrcodeList.add(yinLianQrcodeList);
                qrcodeList.add(yinLianQrcodeList2);
                params.put("qrcodeList", qrcodeList);

                params.put("actNm", mchInfo.getAccountHolder());//结算账户名

            }


            //=============== 驳回的公共参数 ============================
            params.put("cprRegAddr", mchInfo.getRegisterAddress()); //商户经营详细地址
            String[] split1=mchInfo.getStoreAddress().split("/");
            String areaId="";//区
            String cityId="";//市
            String provinceId="";//省

            //先查区(可能存在多个地区)
            List<TqSxfArea> tqSxfAreaList=tqSxfAreaRepository.findByAreaName(split1[2]);
            if(tqSxfAreaList.equals(null) || tqSxfAreaList.size() == 0){
                return new Resp().error(Resp.Status.PARAM_ERROR, "省市区地址错误");
            }
            for (TqSxfArea tqSxfArea : tqSxfAreaList) {
                //地区
                TqSxfArea area=tqSxfAreaRepository.findByAreaId(tqSxfArea.getAreaId());
                areaId=area.getAreaId();
                //查询市
                TqSxfArea city=tqSxfAreaRepository.findByAreaId(area.getParentId());
                //判断是否和传过来的市对应
                if (city.getAreaName().equals(split1[1])) {
                    cityId=city.getAreaId();
                    TqSxfArea province=tqSxfAreaRepository.findByAreaId(city.getParentId());
                    provinceId=province.getAreaId();
                    break;
                }
            }
            params.put("regProvCd", provinceId); //商户经营地址省编码
            params.put("regCityCd", cityId); //商户经营地址市编码
            params.put("regDistCd", areaId); //商户经营地址地区编码
            params.put("mccCd", distMchInfoTqsxfVO.getTqSxfMccId()); //经营类目
            params.put("csTelNo", mchInfo.getCusServiceTel()); //客服电话


            params.put("mno", mchInfo.getTqSxfMno()); //商编
            params.put("mecDisNm", mchInfo.getShortName()); //商户简称
            params.put("mblNo", mchInfo.getPhone());//商户手机号
            params.put("actNo", mchInfo.getAccountNumber());//结算卡号

            TqSxfWxBank byTqSxfBank=tqSxfWxBankRepository.findByTqSxfBankName(mchInfo.getBankName());//开户银行
            params.put("depoBank", byTqSxfBank.getBankNo());
            String[] split=mchInfo.getBankCity().split("/");
            TqSxfCityNo tqSxfCityNo=tqSxfCityNoRepository.findByCityName(split[1]);//开户银行城市
            params.put("depoProvCd", tqSxfCityNo.getProvinceNo()); //开户省份
            params.put("depoCityCd", tqSxfCityNo.getCityNo());//开户城市

            //法人/商户负责人身份证正面照（人像面）
            FileInfoVo idCardCopyVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId());
            String idCardCopyPath=prefixPath + idCardCopyVo.getPath();
            UploadPictureVO uploadPictureVO02=tqSxfPayServiceFeign.photoUrl(idCardCopyPath, "02");
            if(uploadPictureVO02.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证正面照" + uploadPictureVO02.getBizMsg());
            }
            params.put("legalPersonidPositivePic",uploadPictureVO02.getPhotoUrl());

            //法人/商户负责人身份证反面照（国徽面）
            FileInfoVo idCardNationalVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId2());

            String idCardNationalPath=prefixPath + idCardNationalVo.getPath();
            UploadPictureVO uploadPictureVO03=tqSxfPayServiceFeign.photoUrl(idCardNationalPath, "03");
            if(uploadPictureVO03.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "法人/商户负责人身份证反面照" + uploadPictureVO03.getBizMsg());
            }
            params.put("legalPersonidOppositePic", uploadPictureVO03.getPhotoUrl());

            //门头照片
            FileInfoVo doorPhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
            String doorPhotoPath=prefixPath + doorPhotoVo.getPath();
            UploadPictureVO uploadPictureVO10=tqSxfPayServiceFeign.photoUrl(doorPhotoPath, "10");
            if(uploadPictureVO10.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "门头照片" + uploadPictureVO10.getBizMsg());
            }
            params.put("storePic",uploadPictureVO10.getPhotoUrl());

            //真实商户内景图片
            FileInfoVo StorePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
            String StorePhotoPath=prefixPath + StorePhotoVo.getPath();
            UploadPictureVO uploadPictureVO11=tqSxfPayServiceFeign.photoUrl(StorePhotoPath, "11");
            if(uploadPictureVO11.getBizCode().equals("0001")){
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户内景照" + uploadPictureVO11.getBizMsg());
            }
            params.put("insideScenePic", uploadPictureVO11.getPhotoUrl());

            tqSxfIncomeVO=tqSxfPayServiceFeign.updateMerchantInfo(params, mchInfo.getMerchantId());
            log.info("入驻修改返回来的参数 ------ " + tqSxfIncomeVO);
            if(StringUtil.isNotBlank(tqSxfIncomeVO.getApplicationId())){
                mchInfo.setApplicationId(tqSxfIncomeVO.getApplicationId());
            }
            if (tqSxfIncomeVO.getBizCode().equals("0000")) {
                mchInfo.setTqSxfMsg(tqSxfIncomeVO.getBizMsg());
            } else {
                mchInfo.setTqSxfSuccess(Integer.valueOf(0));
                mchInfo.setTqSxfMsg(tqSxfIncomeVO.getBizMsg());

            }
            mchInfoRepository.saveAndFlush(mchInfo);
            return Resp.success(tqSxfIncomeVO);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Resp.success(tqSxfIncomeVO);
    }
}
