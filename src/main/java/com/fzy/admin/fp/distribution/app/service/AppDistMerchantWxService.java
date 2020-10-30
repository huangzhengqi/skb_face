package com.fzy.admin.fp.distribution.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.controller.AppDistMerchantController;
import com.fzy.admin.fp.distribution.app.enums.WxAccountType;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoWxVO;
import com.fzy.admin.fp.distribution.cert.PlainCertificateItem;
import com.fzy.admin.fp.distribution.utils.CertificateDownloader;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.WxCityNo;
import com.fzy.admin.fp.merchant.merchant.repository.MchInfoRepository;
import com.fzy.admin.fp.merchant.merchant.repository.WxCityNoRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.pay.pay.repository.TqSxfConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.WxConfigRepository;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author hzq
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AppDistMerchantWxService {

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private WxCityNoRepository wxCityNoRepository;

    @Resource
    private MerchantService merchantService;

    @Resource
    private MchInfoRepository mchInfoRepository;

    @Resource
    private WxConfigRepository wxConfigRepository;

    private static final String CertDownloadPath="https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/";

    @Value("${file.upload.path}")
    public String prefixPath;

    @Transactional
    public MchInfo downloadCertificate(String merchantId, String serialNo, String privateKeyFilePath, String wechatpaySerialNo, String param, MchInfo mchInfo) throws IOException {
        WechatPayHttpClientBuilder builder=WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, serialNo, PemUtil.loadPrivateKey(new FileInputStream(privateKeyFilePath)));
        builder.withValidator(response -> true);
        HttpPost httpGet=new HttpPost(CertDownloadPath);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Wechatpay-Serial", wechatpaySerialNo);
        httpGet.setHeader(org.apache.http.HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType() + "; charset=" + ContentType.APPLICATION_JSON.getCharset().name());
        httpGet.setEntity(new StringEntity(param, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient client=builder.build()) {
            CloseableHttpResponse response=client.execute(httpGet);
            int statusCode=response.getStatusLine().getStatusCode();
            String body=EntityUtils.toString(response.getEntity());

            log.info("微信返回参数{}", body);
            if (statusCode == 200) {
                JSONObject json=(JSONObject) JSONObject.parse(body);
                log.info("微信进件提交成功返回数据,{}", json.toString());
                mchInfo.setApplymentId(json.get("applyment_id").toString());
            } else {
                //JSON 字符串存储
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("code", statusCode);
                jsonObject.put("body", body);
                mchInfo.setWxMsg(jsonObject.toJSONString());
            }
        }
        return mchInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public Resp into(TopConfig topConfig, MchInfo mchInfo,DistMchInfoWxVO distMchInfoWxVO) {
        JSONObject jsonObject;
        //错误信息
        String msg=null;
        //特殊资质
        if (StringUtil.isNotEmpty(distMchInfoWxVO.getSpecialQualificationPhotoId())) {
            mchInfo.setSpecialQualificationPhotoId(distMchInfoWxVO.getSpecialQualificationPhotoId());
        }
        //补充材料
        if (StringUtil.isNotEmpty(distMchInfoWxVO.getBusinessAdditionPics())) {
            mchInfo.setBusinessAdditionPics(distMchInfoWxVO.getBusinessAdditionPics());
        }
        mchInfo.setQualificationType(distMchInfoWxVO.getQualificationType());
        mchInfo.setSettlementId(distMchInfoWxVO.getSettlementId());
        mchInfoRepository.saveAndFlush(mchInfo);
        try {
            FileInfoVo doorPhotoFileInfoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
            String doorPhotoPath=prefixPath + doorPhotoFileInfoVo.getPath();
            //微信特约商户（个体商户、企业）自动进件 2020-3-13 14:30:30 yy
            CertificateDownloader certificateDownloader=new CertificateDownloader();
            //得到商户证书的序列号
            String serialNo=AppDistMerchantController.getCertificate(topConfig.getWxApiCert()).getSerialNumber().toString(16);
            //下载微信公众号平台证书
            List<PlainCertificateItem> plainCerts=certificateDownloader.downloadCertificate(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), null, topConfig.getWxApiv3key());
            String payPath=null;
            if (plainCerts != null) {
                payPath=certificateDownloader.saveCertificate(plainCerts);
            }
            MyWxConfig myWxConfig=new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
            //微信证书内容
            JSONObject certificates=AppDistMerchantController.getCertificates(myWxConfig, topConfig);
            String wechatpaySerialNo=certificates.getString("serial_no");
            //请求体
            Map<String, Object> requestParam=new HashMap<>();
            //进件修改
            String businessCode=AppDistMerchantController.getMerchantMerInfoCode(topConfig, mchInfo);
            if (businessCode.equals("") || businessCode == null) {
                businessCode=UUID.randomUUID().toString().replace("-", "");
            }
            requestParam.put("business_code", businessCode);
            mchInfo.setBusinessCode(businessCode);
            //超级管理员信息
            Map<String, String> contactInfoParam=new HashMap<>();
            contactInfoParam.put("contact_name", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getRepresentativeName(), AppDistMerchantController.getCertificate(payPath)));
            contactInfoParam.put("contact_id_number", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getCertificateNum(), AppDistMerchantController.getCertificate(payPath)));
            contactInfoParam.put("mobile_phone", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getPhone(), AppDistMerchantController.getCertificate(payPath)));
            contactInfoParam.put("contact_email", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getEmail(), AppDistMerchantController.getCertificate(payPath)));
            requestParam.put("contact_info", contactInfoParam);
            //主体资料
            Map<String, Object> subjectInfoParam=new HashMap<>();
            subjectInfoParam.put("subject_type", mchInfo.getSubjectType());
            //主体资料-》营业执照
            Map<String, String> businessLicenseInfoParam=new HashMap<>();
            //营业执照
            FileInfoVo businessLicensePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
            String businessLicensePhotoPath=prefixPath + businessLicensePhotoVo.getPath();
            File businessLicensePhotoFile=new File(businessLicensePhotoPath);
            MultipartFile businessLicensePhotoMultipartFile=new MockMultipartFile("file", new FileInputStream(businessLicensePhotoFile));
            jsonObject = AppDistMerchantController.uploadFile(businessLicensePhotoMultipartFile, topConfig);
            if(jsonObject.get("return_code").toString().equals("FAIL")){
                return new Resp().error(Resp.Status.PARAM_ERROR,"营业执照" +jsonObject.get("return_msg").toString());
            }
            businessLicenseInfoParam.put("license_copy", jsonObject.get("media_id").toString());
            businessLicenseInfoParam.put("license_number", mchInfo.getLicense());
            businessLicenseInfoParam.put("merchant_name", mchInfo.getMerchantName());
            businessLicenseInfoParam.put("legal_person", mchInfo.getRepresentativeName());
            subjectInfoParam.put("business_license_info", businessLicenseInfoParam);
            //主体资料-》经营者法人信息
            Map<String, Object> identityInfoParam=new HashMap<>();
            identityInfoParam.put("id_doc_type", "IDENTIFICATION_TYPE_IDCARD");
            //主体资料-》经营者法人信息-》经营者/法人身份证件
            Map<String, String> idCardInfoParam=new HashMap<>();
            //身份证人像面
            FileInfoVo idCardCopyVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId());
            String idCardCopyPath=prefixPath + idCardCopyVo.getPath();
            File idCardCopyFile=new File(idCardCopyPath);
            MultipartFile idCardCopyMultipartFile=new MockMultipartFile("file", new FileInputStream(idCardCopyFile));
            jsonObject  = AppDistMerchantController.uploadFile(idCardCopyMultipartFile, topConfig);
            if(jsonObject.get("return_code").toString().equals("FAIL")){
                return new Resp().error(Resp.Status.PARAM_ERROR,"身份证人像面" +jsonObject.get("return_msg").toString());
            }
            idCardInfoParam.put("id_card_copy", jsonObject.get("media_id").toString());
            //身份证国徽面
            FileInfoVo idCardNationalVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId2());
            String idCardNationalPath=prefixPath + idCardNationalVo.getPath();
            File idCardNationalFile=new File(idCardNationalPath);
            MultipartFile idCardNationalMultipartFile=new MockMultipartFile("file", new FileInputStream(idCardNationalFile));
            jsonObject = AppDistMerchantController.uploadFile(idCardNationalMultipartFile, topConfig);
            if(jsonObject.get("return_code").toString().equals("FAIL")){
                return new Resp().error(Resp.Status.PARAM_ERROR,"身份证国徽面" +jsonObject.get("return_msg").toString());
            }
            idCardInfoParam.put("id_card_national", jsonObject.get("media_id").toString());
            idCardInfoParam.put("id_card_name", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getRepresentativeName(), AppDistMerchantController.getCertificate(payPath)));
            idCardInfoParam.put("id_card_number", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getCertificateNum(), AppDistMerchantController.getCertificate(payPath)));
            idCardInfoParam.put("card_period_begin", DateUtils.fmtDateToStr(mchInfo.getStartCertificateTime(), "yyyy-MM-dd"));
            //判断身份证是否是长期
            String fmtDateToStr=DateUtils.fmtDateToStr(mchInfo.getEndCertificateTime(), "yyyy-MM-dd");
            if (fmtDateToStr.equals("1970-01-01")) {
                idCardInfoParam.put("card_period_end", "长期");
            } else {
                idCardInfoParam.put("card_period_end", fmtDateToStr);
            }
            identityInfoParam.put("id_card_info", idCardInfoParam);
            identityInfoParam.put("owner", true);
            subjectInfoParam.put("identity_info", identityInfoParam);
            requestParam.put("subject_info", subjectInfoParam);
            //经营信息
            Map<String, Object> businessInfoParam=new HashMap<>();
            businessInfoParam.put("merchant_shortname", mchInfo.getShortName());
            businessInfoParam.put("service_phone", mchInfo.getCusServiceTel());
            //经营信息-》经营场景
            Map<String, Object> salesInfoParam=new HashMap<>();
            salesInfoParam.put("sales_scenes_type", new String[]{"SALES_SCENES_STORE"});
            //经营信息-》经营场景-》线下门店场景
            Map<String, Object> bizStoreInfoParam=new HashMap<>();
            bizStoreInfoParam.put("biz_store_name", mchInfo.getShortName());
            String[] split=mchInfo.getStoreAddress().split("/");
            WxCityNo byCity=wxCityNoRepository.findByCity(split[1]);
            if (byCity != null) {
                String bizAddressCode=byCity.getNo();
                bizStoreInfoParam.put("biz_address_code", bizAddressCode);
            } else {
                return new Resp().error(Resp.Status.PARAM_ERROR, "门店省市区请填写具体省市区，例广东省/深圳市/龙华区");
            }
            bizStoreInfoParam.put("biz_store_address", mchInfo.getRegisterAddress());
            //门店门头照片
            File doorPhotoFile=new File(doorPhotoPath);
            MultipartFile doorPhotoMultipartFile=new MockMultipartFile("file", new FileInputStream(doorPhotoFile));
            jsonObject = AppDistMerchantController.uploadFile(doorPhotoMultipartFile, topConfig);
            if(jsonObject.get("return_code").toString().equals("FAIL")){
                return new Resp().error(Resp.Status.PARAM_ERROR,"门店门头" +jsonObject.get("return_msg").toString());
            }
            bizStoreInfoParam.put("store_entrance_pic", new String[]{jsonObject.get("media_id").toString()});
            //店内环境照片
            FileInfoVo storePhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
            String storePhotoPath=prefixPath + storePhotoVo.getPath();
            File storePhotoFile=new File(storePhotoPath);
            MultipartFile storePhotoMultipartFile=new MockMultipartFile("file", new FileInputStream(storePhotoFile));
            jsonObject = AppDistMerchantController.uploadFile(storePhotoMultipartFile, topConfig);
            if(jsonObject.get("return_code").toString().equals("FAIL")){
                return new Resp().error(Resp.Status.PARAM_ERROR,"店内环境" +jsonObject.get("return_msg").toString());
            }
            bizStoreInfoParam.put("indoor_pic", new String[]{jsonObject.get("media_id").toString()});
            salesInfoParam.put("biz_store_info", bizStoreInfoParam);
            businessInfoParam.put("sales_info", salesInfoParam);
            requestParam.put("business_info", businessInfoParam);
            //结算规则
            Map<String, Object> settlementInfoParam=new HashMap<>();
            //费率结算规则id
            settlementInfoParam.put("settlement_id", mchInfo.getSettlementId());
            //所属行业
            settlementInfoParam.put("qualification_type", mchInfo.getQualificationType());
            //特殊资质图片
            if (StringUtil.isNotEmpty(mchInfo.getSpecialQualificationPhotoId())) {
                //微信接收图片数组
                String[] specialQualificationArray=new String[4];
                String[] splitArray=mchInfo.getSpecialQualificationPhotoId().split(",");
                for (int i=0; i < splitArray.length; i++) {
                    FileInfoVo specialQualificationPhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(splitArray[i]);
                    String specialQualificationPhotoPath=prefixPath + specialQualificationPhotoVo.getPath();
                    File specialQualificationPhotoFile=new File(specialQualificationPhotoPath);
                    MultipartFile specialQualificationPhotoMultipartFile=new MockMultipartFile("file", new FileInputStream(specialQualificationPhotoFile));
                    jsonObject  = AppDistMerchantController.uploadFile(specialQualificationPhotoMultipartFile, topConfig);
                    if(jsonObject.get("return_code").toString().equals("FAIL")){
                        return new Resp().error(Resp.Status.PARAM_ERROR,"特殊资质" +jsonObject.get("return_msg").toString());
                    }
                    specialQualificationArray[i]=jsonObject.get("media_id").toString();
                }
                settlementInfoParam.put("qualifications", specialQualificationArray);
            }
            requestParam.put("settlement_info", settlementInfoParam);
            Map<String, Object> bankAccountInfoParam=new HashMap<>();
            if (mchInfo.getAccountType().equals("对公账户")) {
                bankAccountInfoParam.put("bank_account_type", WxAccountType.BANK_ACCOUNT_TYPE_CORPORATE);
            } else {
                bankAccountInfoParam.put("bank_account_type", WxAccountType.BANK_ACCOUNT_TYPE_PERSONAL);
            }
            bankAccountInfoParam.put("account_name", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getAccountHolder(), AppDistMerchantController.getCertificate(payPath)));
            if (mchInfo.getBankName().equals("中国农业发展银行") || mchInfo.getBankName().equals("浙商银行") || mchInfo.getBankName().equals("渤海银行") ||
                    mchInfo.getBankName().equals("农村商业银行") || mchInfo.getBankName().equals("城市商业银行") || mchInfo.getBankName().equals("村镇银行") ||
                    mchInfo.getBankName().equals("其他银行907")) {
                mchInfo.setBankName("其他银行");
            }
            bankAccountInfoParam.put("account_bank", mchInfo.getBankName());
            String[] split1=mchInfo.getBankCity().split("/");
            WxCityNo bankCity=wxCityNoRepository.findByCity(split1[1]);
            if (bankCity != null) {
                String bankCityCode=bankCity.getNo();
                bankAccountInfoParam.put("bank_address_code", bankCityCode);
            } else {
                return new Resp().error(Resp.Status.PARAM_ERROR, " 开户银行城市请填写具体省市区，例广东省/深圳市/龙华区");
            }
            //如果填写其他银行必须填写支行
            if (mchInfo.getBankName().equals("其他银行")) {
                bankAccountInfoParam.put("bank_name", mchInfo.getBankOutlet());
            }
            bankAccountInfoParam.put("account_number", AppDistMerchantController.rsaEncryptOAEP(mchInfo.getAccountNumber(), AppDistMerchantController.getCertificate(payPath)));
            requestParam.put("bank_account_info", bankAccountInfoParam);
            //补充材料图片
            if (StringUtil.isNotEmpty(mchInfo.getBusinessAdditionPics())) {
                //微信接收图片数组
                String[] specialQualificationArray=new String[4];
                String[] splitArray=mchInfo.getBusinessAdditionPics().split(",");
                for (int i=0; i < splitArray.length; i++) {
                    FileInfoVo specialQualificationPhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(splitArray[i]);
                    String businessAdditionPicsPath=prefixPath + specialQualificationPhotoVo.getPath();
                    File specialQualificationPhotoFile=new File(businessAdditionPicsPath);
                    MultipartFile specialQualificationPhotoMultipartFile=new MockMultipartFile("file", new FileInputStream(specialQualificationPhotoFile));
                    jsonObject  = AppDistMerchantController.uploadFile(specialQualificationPhotoMultipartFile, topConfig);
                    if(jsonObject.get("return_code").toString().equals("FAIL")){
                        return new Resp().error(Resp.Status.PARAM_ERROR,"补充材料" +jsonObject.get("return_msg").toString());
                    }
                    specialQualificationArray[i]=jsonObject.get("media_id").toString();
                }
                Map<String, Object> additionInfoParam=new HashMap<>();
                additionInfoParam.put("business_addition_pics", specialQualificationArray);
                requestParam.put("addition_info", additionInfoParam);
            }
            JSONArray jArray=new JSONArray();
            jArray.add(requestParam);
            log.info("微信特约商户进件请求参数{}", requestParam.toString());
            String param=jArray.toString();
            param=param.substring(1, param.length() - 1);
            mchInfo=downloadCertificate(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), wechatpaySerialNo, param, mchInfo);
            mchInfoRepository.saveAndFlush(mchInfo);
            return Resp.success(mchInfo, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            log.info("报错异常类---------{}", e.getMessage());
            log.info("报错异常信息---------{}", e);
        }
        return new Resp().error(Resp.Status.PARAM_ERROR,msg);
    }

    @Transactional
    public Resp<MchInfo> detail(MchInfo mchInfo, TopConfig topConfig) {
        if (StringUtil.isNotEmpty(mchInfo.getApplymentId()) && !mchInfo.getWxSuccess().equals(Integer.valueOf(7))) {
            try {
                String serialNo=AppDistMerchantController.getCertificate(topConfig.getWxApiCert()).getSerialNumber().toString(16);
                //得到微信进件结果
                String body=CertificateDownloader.getApplyment(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), mchInfo.getApplymentId());
                JSONObject json=(JSONObject) JSONObject.parse(body);
                mchInfo.setWxMsg(body);
                String applymentState=json.getString("applyment_state"); //申请单状态

                if (applymentState.equals("APPLYMENT_STATE_EDITTING")) { //编辑中
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(1));
                } else if (applymentState.equals("APPLYMENT_STATE_AUDITING")) {  //审核中
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(2));
                } else if (applymentState.equals("APPLYMENT_STATE_REJECTED")) {  //已驳回
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(1));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(3));
                    //驳回原因
                    String audit_detail=json.getString("audit_detail");
                    log.info("audit_detail,{}", audit_detail);
                    JSONArray auditDdetailJson=(JSONArray) JSONArray.parse(audit_detail);
                    if (auditDdetailJson.size() > 0) {
                        mchInfo.setField(auditDdetailJson.getJSONObject(0).getString("field"));
                        mchInfo.setFieldName(auditDdetailJson.getJSONObject(0).getString("field_name"));
                        mchInfo.setRejectReason(auditDdetailJson.getJSONObject(0).getString("reject_reason"));
                    }
                } else if (applymentState.equals("APPLYMENT_STATE_TO_BE_CONFIRMED")) {  //待账户验证
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(4));
                } else if (applymentState.equals("APPLYMENT_STATE_TO_BE_SIGNED")) { //待签约
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(5));
                } else if (applymentState.equals("APPLYMENT_STATE_SIGNING")) {  //开通权限中
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(2));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(6));
                } else if (applymentState.equals("APPLYMENT_STATE_FINISHED")) {  //已完成
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(3));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(7));
                    mchInfo.setSubMchid(json.getString("sub_mchid"));//微信子商户号
                    //完成帮商户自动启用
                    Merchant merchant=merchantService.findOne(mchInfo.getMerchantId());
                    //如果禁用就启用
                    if (merchant.getStatus().equals(Integer.valueOf(2))) {
                        merchant.setStatus(Integer.valueOf(1));//启用
                        merchantService.update(merchant);
                    }

                    //商户支付参数配置
                    WxConfig wxConfig=wxConfigRepository.findByMerchantIdAndDelFlag(mchInfo.getMerchantId(), CommonConstant.NORMAL_FLAG);
                    if (wxConfig == null) {
                        WxConfig wxConfig1=new WxConfig();
                        wxConfig1.setMerchantId(mchInfo.getMerchantId());
                        wxConfig1.setSubMchId(json.getString("sub_mchid"));
                        wxConfigRepository.save(wxConfig1);
                    } else {
                        wxConfig.setSubMchId(json.getString("sub_mchid"));
                        wxConfigRepository.saveAndFlush(wxConfig);
                    }
                } else if (applymentState.equals("APPLYMENT_STATE_CANCELED")) { //已作废
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(1));
                    }
                } else {
                    //如果已激活状态就不修改状态
                    if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                        mchInfo.setStatus(Integer.valueOf(1));
                    }
                    mchInfo.setWxSuccess(Integer.valueOf(0));
                }
                mchInfo.setApplymentStateMsg(json.getString("applyment_state_msg"));
                mchInfo.setSignUrl(json.getString("sign_url")); //超级管理员签约链接
                mchInfoRepository.saveAndFlush(mchInfo);
            } catch (IOException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return Resp.success(AppDistMerchantController.getMchInfo(mchInfo));
    }
}
