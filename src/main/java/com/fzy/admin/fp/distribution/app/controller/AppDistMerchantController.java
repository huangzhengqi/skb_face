package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.internal.util.file.IOUtils;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.enums.WxAccountType;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoSpecialVO;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoVO;
import com.fzy.admin.fp.distribution.cert.PlainCertificateItem;
import com.fzy.admin.fp.distribution.utils.CertificateDownloader;
import com.fzy.admin.fp.distribution.utils.ImageByteUtil;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.WxCityNo;
import com.fzy.admin.fp.merchant.merchant.dto.MchInfoDTO;
import com.fzy.admin.fp.merchant.merchant.repository.WxCityNoRepository;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.util.WxDepositUtils;
import com.fzy.admin.fp.sdk.pay.feign.TqSxfPayServiceFeign;
import com.fzy.admin.fp.wx.util.XmlUtil;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.security.cert.X509Certificate;
import java.security.*;
import java.security.PublicKey;

import javax.crypto.Cipher;

import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.util.*;


import javax.annotation.Resource;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @Author yy
 * @Date 2019-12-26 09:46:18
 * @Desp
 **/

@RestController
@RequestMapping("/dist/app/merchant")
@Api(value="AppMerchantController", tags={"分销app-进件管理"})
@Slf4j
public class AppDistMerchantController extends BaseContent {
    @Resource
    private MerchantService merchantService;

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private WxCityNoRepository wxCityNoRepository;

    @Resource
    private TqSxfPayServiceFeign tqSxfPayServiceFeign;

    private static final String CertDownloadPath="https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/";


    @Value("${file.upload.path}")
    public String prefixPath;

    @GetMapping("/detail")
    @ApiOperation(value="详情", notes="详情")
    public Resp<MchInfo> detail(String id) {

        MchInfo mchInfo=mchInfoService.findOne(id);
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);

        //支付宝进件结果
//        if (mchInfo.getIsZfb() != null && mchInfo.getIsZfb() == 1 && StringUtil.isNotEmpty(mchInfo.getBatchNo()) && mchInfo.getZfbSuccess() == 0) {
        if (mchInfo.getIsZfb() != null && mchInfo.getIsZfb() == 1 && StringUtil.isNotEmpty(mchInfo.getBatchNo()) && mchInfo.getZfbSuccess() != 1 ) {
            try {
                //查看支付宝进件申请状态
                mchInfo=getZfbStatus(topConfig.getAliAppId(), topConfig.getAliPrivateKey(), topConfig.getAliPublicKey(), mchInfo);
                mchInfoService.update(mchInfo);
                log.info("支付宝审核成功");
            } catch (AlipayApiException e) {
                e.printStackTrace();
                return Resp.success(mchInfo);
            }
        }

        //微信进件结果
//        if (mchInfo.getIsWx() != null && mchInfo.getIsWx() == 1 && StringUtil.isNotEmpty(mchInfo.getApplymentId()) && mchInfo.getWxSuccess() == 0) {
        if (mchInfo.getIsWx() != null && mchInfo.getIsWx() == 1 && StringUtil.isNotEmpty(mchInfo.getApplymentId()) && mchInfo.getWxSuccess() != 1) {
            try {
                String serialNo=getCertificate(topConfig.getWxApiCert()).getSerialNumber().toString(16);
                log.info("serialNo ---------- " + serialNo);
                //得到微信进件结果
               String body=CertificateDownloader.getApplyment(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), mchInfo.getApplymentId());
                log.info("得到微信进件结果  -----  body" + body);
                //TODO 特殊字符串处理
                JSONObject json=(JSONObject) JSONObject.parse(body);
                mchInfo.setWxMsg(body);
                String applymentState=json.getString("applyment_state"); //申请单状态
                log.info("申请单状态 -------------------  " + applymentState);
                if (applymentState.equals("APPLYMENT_STATE_FINISHED")) {  //已完成
                    mchInfo.setWxSuccess(1);
                    mchInfo.setStatus(Integer.valueOf(3));
                } else if (applymentState.equals("APPLYMENT_STATE_AUDITING")) {  //审核中
                    mchInfo.setStatus(Integer.valueOf(2));
                    mchInfo.setWxSuccess(Integer.valueOf(2));
                } else if (applymentState.equals("APPLYMENT_STATE_TO_BE_CONFIRMED")) {  //待账户验证
                    mchInfo.setStatus(Integer.valueOf(2));
                    mchInfo.setWxSuccess(Integer.valueOf(2));
                } else if (applymentState.equals("APPLYMENT_STATE_REJECTED")) {  //已驳回
                    mchInfo.setStatus(Integer.valueOf(2));
                    //驳回原因
                    String audit_detail=json.getString("audit_detail");
                    log.info("audit_detail  -------------  " + audit_detail);
                    JSONArray auditDdetailJson=(JSONArray) JSONArray.parse(audit_detail);
                    if(auditDdetailJson.size()>0){
                        mchInfo.setField(auditDdetailJson.getJSONObject(0).getString("field"));
                        mchInfo.setFieldName(auditDdetailJson.getJSONObject(0).getString("field_name"));
                        mchInfo.setRejectReason(auditDdetailJson.getJSONObject(0).getString("reject_reason"));
//                        mchInfo.setWxMsg(auditDdetailJson.getJSONObject(0).getString("reject_reason"));
                    }
                } else if (applymentState.equals("APPLYMENT_STATE_CANCELED")) { //已作废
                    mchInfo.setStatus(Integer.valueOf(1));
                } else {
                    mchInfo.setStatus(Integer.valueOf(1));
                    mchInfo.setWxSuccess(Integer.valueOf(0));
                }
                mchInfo.setSignUrl(json.getString("sign_url")); //超级管理员签约链接
                mchInfoService.update(mchInfo);
            } catch (IOException e) {
                e.printStackTrace();
                return Resp.success(mchInfo);
            }
        }
        getMchInfo(mchInfo);
        return Resp.success(mchInfo);
    }

    /**
     * 进件结果字符串处理修改
     * @param mchInfo
     * @return
     */
    public static MchInfo getMchInfo(MchInfo mchInfo){
            //判断是否JSON 字符串,如果不是JSON 字符串不做处理
            if(!"".equals(mchInfo.getWxMsg()) && null != mchInfo.getWxMsg()){
                String wxMsg=mchInfo.getWxMsg();
                //判断JSON 格式
                if(wxMsg.contains("body")){
                    JSONObject json=(JSONObject) JSONObject.parse(wxMsg);
                    if(null !=json.getString("body") && ""!= json.get("body")){
                        JSONObject object=(JSONObject) JSONObject.parse(json.get("body").toString());
                        mchInfo.setWxMsg(object.get("message").toString());
                    }
                }
                if(wxMsg.contains("applyment_id")){
                    JSONObject json=(JSONObject) JSONObject.parse(wxMsg);
                    if(null != json.get("applyment_state_msg")){
                        mchInfo.setWxMsg(json.get("applyment_state_msg").toString());
                    }
                }
            }
            return  mchInfo;
    }

    /**
     * 查询进件状态申请单是否已撤销
     * @param topConfig
     * @param mchInfo
     * @return
     * @throws IOException
     */
    public static String getMerchantMerInfoCode(TopConfig topConfig, MchInfo mchInfo) throws IOException {
        if(StringUtil.isNotEmpty(mchInfo.getApplymentId())){
            String serialNo=getCertificate(topConfig.getWxApiCert()).getSerialNumber().toString(16);
            //得到微信进件结果
            String body=CertificateDownloader.getApplyment(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), mchInfo.getApplymentId());
            JSONObject json=(JSONObject) JSONObject.parse(body);
            String applymentState=json.getString("applyment_state"); //申请单状态
            if (applymentState.equals("APPLYMENT_STATE_CANCELED")) { //已作废
                return "";
            }
        }
        if(StringUtil.isNotEmpty(mchInfo.getBusinessCode())){
            return mchInfo.getBusinessCode();
        }
        return  "";
    }

    public static MchInfo getZfbStatus(String app_id, String private_key, String alipay_public_key, MchInfo mchInfo) throws AlipayApiException {
        AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, private_key, "json", "GBK", alipay_public_key, "RSA2");
        AlipayOpenAgentOrderQueryRequest request=new AlipayOpenAgentOrderQueryRequest();
        request.setBizContent("{" +
                "\"batch_no\":\"" + mchInfo.getBatchNo() + "\"" +
                "  }");
        AlipayOpenAgentOrderQueryResponse response=alipayClient.execute(request);
        log.info("支付审核返回参数（response）  ----  " + response.getBody());
        if (!response.getCode().equals("10000")) {
            mchInfo.setZfbMsg(response.getBody());
            return mchInfo;
        }
        //暂存，提交事务出现业务校验异常时，会暂存申请单信息，可以调用业务接口修正参数，并重新提交
        if (response.getOrderStatus().equals("MERCHANT_INFO_HOLD")) {
            mchInfo.setStatus(Integer.valueOf(1));
            mchInfo.setZfbMsg(response.getRejectReason());
            mchInfo.setZfbSuccess(Integer.valueOf(2));
        }
        //审核中，申请信息正在人工审核中
        if (response.getOrderStatus().equals("MERCHANT_AUDITING")) {
            mchInfo.setZfbMsg("审核中，申请信息正在人工审核中");
            mchInfo.setZfbSuccess(Integer.valueOf(2));
        }
        //待商户确认，申请信息审核通过，等待联系人确认签约或授权
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM")) {
            //confirm_url 只有申请单状态在MERCHANT_CONFIRM状态下，才会返回商户确认签约链接
            String confirmUrl=response.getConfirmUrl();
            mchInfo.setConfirmUrl(confirmUrl);
            mchInfo.setZfbMsg("待商户授权");
            mchInfo.setZfbSuccess(Integer.valueOf(2));
        }
        //商户确认成功，商户同意签约或授权
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM_SUCCESS")) {
            mchInfo.setZfbMsg("商户确认成功，商户同意签约或授权");
            mchInfo.setStatus(Integer.valueOf(3));
            mchInfo.setZfbSuccess(Integer.valueOf(1));
            mchInfo.setMerchantPid(response.getMerchantPid());
        }
        //商户超时未确认，如果商户受到确认信息15天内未确认，则需要重新提交申请信息
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM_TIME_OUT")) {
            mchInfo.setZfbSuccess(Integer.valueOf(2));
            mchInfo.setZfbMsg("商户超时未确认，如果商户受到确认信息15天内未确认，则需要重新提交申请信息");
        }
        //审核失败或商户拒绝，申请信息审核被驳回，或者商户选择拒绝签约或授权
        if (response.getOrderStatus().equals("MERCHANT_APPLY_ORDER_CANCELED")) {
            mchInfo.setZfbSuccess(Integer.valueOf(0));
            //失败原因
            mchInfo.setStatus(Integer.valueOf(1));
            mchInfo.setZfbMsg(response.getRejectReason());
        }
        return mchInfo;
    }


        //----------------微信小微商户自动进件start yy-2020-2-13 13:50:30-----------
       /* FileInfoVo idCardCopyVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId());
        if (idCardCopyVo == null) {
            return null;
        }
        String idCardCopyPath = prefixPath + "thumbnail/" + idCardCopyVo.getPath();
        File idCardCopyFile = new File(idCardCopyPath);
        MultipartFile idCardCopyMultipartFile = new MockMultipartFile("file", new FileInputStream(idCardCopyFile));
        String idCardCopy = uploadFile(idCardCopyMultipartFile, topConfig);

        FileInfoVo idCardNationalVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId2());
        if (idCardNationalVo == null) {
            return null;
        }
        String idCardNationalPath = prefixPath + "thumbnail/" + idCardNationalVo.getPath();
        File idCardNationalFile = new File(idCardNationalPath);
        MultipartFile idCardNationalMultipartFile = new MockMultipartFile("file", new FileInputStream(idCardNationalFile));
        String idCardNational = uploadFile(idCardNationalMultipartFile, topConfig);

        FileInfoVo storeEntrancePicVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
        if (storeEntrancePicVo == null) {
            return null;
        }
        String storeEntrancePicPath = prefixPath + "thumbnail/" + storeEntrancePicVo.getPath();
        File storeEntrancePicFile = new File(storeEntrancePicPath);
        MultipartFile storeEntrancePicMultipartFile = new MockMultipartFile("file", new FileInputStream(storeEntrancePicFile));
        String storeEntrancePicNational = uploadFile(storeEntrancePicMultipartFile, topConfig);

        FileInfoVo storePhotoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
        if (storePhotoVo == null) {
            return null;
        }
        String storePhotoVoPath = prefixPath + "thumbnail/" + storePhotoVo.getPath();
        File storePhotoFile = new File(storePhotoVoPath);
        MultipartFile storePhotoMultipartFile = new MockMultipartFile("file", new FileInputStream(storePhotoFile));
        String storePhotoNational = uploadFile(storePhotoMultipartFile, topConfig);

        MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
        //微信证书内容
        JSONObject certificates = getCertificates(myWxConfig, topConfig);
        //微信证书解密内容
        String content = decryptCertSN(certificates.getString("associated_data"), certificates.getString("nonce"), certificates.getString("ciphertext"), topConfig.getWxApiv3key());

        Map<String, String> refundMap = new HashMap<String, String>();
        refundMap.put("version", "3.0");
        refundMap.put("cert_sn", certificates.getString("serial_no"));
        refundMap.put("mch_id", topConfig.getWxMchId());
        refundMap.put("nonce_str",UUID.randomUUID().toString().replace("-", ""));
        refundMap.put("sign_type", "HMAC-SHA256");
        //服务商自定义的商户唯一编号。每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
        String businessCode = UUID.randomUUID().toString().replace("-", "");
        refundMap.put("business_code", businessCode);
        refundMap.put("id_card_copy", idCardCopy);
        refundMap.put("id_card_national", idCardNational);
        refundMap.put("id_card_name",rsaEncryptByCert(mchInfo.getAccountHolder(), content));
        refundMap.put("id_card_number",rsaEncryptByCert(mchInfo.getAccountHolder(), content));

        refundMap.put("id_card_valid_time","[\""+mchInfo.getStartCertificateTime()+"\",\""+mchInfo.getEndCertificateTime()+"\"]");
        refundMap.put("account_name",rsaEncryptByCert(mchInfo.getAccountHolder(), content));
        refundMap.put("account_bank",mchInfo.getBankName());
        String bankAddressCode = wxCityNoRepository.findByCity(mchInfo.getBankCity()).getNo();
        refundMap.put("bank_address_code",bankAddressCode);
        refundMap.put("bank_name",mchInfo.getBankName());
        refundMap.put("account_number",rsaEncryptByCert(mchInfo.getAccountNumber(), content));
        refundMap.put("store_name",mchInfo.getMerchantName());
        String registerAddress = wxCityNoRepository.findByCity(mchInfo.getRegisterAddress()).getNo();
        refundMap.put("store_address_code",registerAddress);
        refundMap.put("store_street",mchInfo.getRegisterAddress());
        refundMap.put("store_entrance_pic",storeEntrancePicNational);
        refundMap.put("indoor_pic",storePhotoNational);
        refundMap.put("merchant_shortname",mchInfo.getShortName());
        refundMap.put("service_phone",mchInfo.getCusServiceTel());
        refundMap.put("product_desc",mchInfo.getProductDesc());
        refundMap.put("rate",String.valueOf(mchInfo.getWxRate()));
        refundMap.put("contact",rsaEncryptByCert(mchInfo.getAccountHolder(), content));
        refundMap.put("contact_phone",rsaEncryptByCert(mchInfo.getPhone(), content));
        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());
        Map<String, String> serialNoMap = WxDepositUtils.micro(refundMap, myWxConfig);
        String applymentId = serialNoMap.get("applyment_id");
        mchInfo.setApplymentId(applymentId);

       */


    @PostMapping("/add")
    @ApiOperation(value="保存基本进件资料", notes="保存基本进件资料")
    public Resp addDistMchInfo(DistMchInfoVO distMchInfoVO) {

        //查询进件是否存在
        MchInfo mchInfo=mchInfoService.findOne(distMchInfoVO.getId());
        if (mchInfo == null) {
            return Resp.success("进件不存在");
        }
        BeanUtil.copyProperties(distMchInfoVO, mchInfo);
        MchInfo mchInfo1=mchInfoService.update(mchInfo);
        return Resp.success(mchInfo1, "保存成功");
    }

    @PostMapping("/add/special")
    @ApiOperation(value = "保存通道特殊资料",notes = "保存通道特殊资料")
    public Resp addSpecial(DistMchInfoSpecialVO distMchInfoSpecialVO){
        //查询进件是否存在
        MchInfo mchInfo=mchInfoService.findOne(distMchInfoSpecialVO.getId());
        if (mchInfo == null) {
            return Resp.success("进件不存在");
        }
        BeanUtil.copyProperties(distMchInfoSpecialVO, mchInfo);
        return Resp.success(mchInfoService.update(mchInfo), "保存成功");
    }

    @GetMapping("/query_mchinfo")
    @ApiOperation(value="查询进件", notes="查询进件")
    public Resp queryMchinfo(String id) {

        //查询进件是否存在
        MchInfo mchInfo=mchInfoService.findOne(id);
        if (mchInfo == null) {
            return Resp.success("进件不存在");
        }
        return Resp.success(mchInfo, "返回成功");
    }

    public static void downloadCertificate(String merchantId, String serialNo, String privateKeyFilePath, String wechatpaySerialNo, String param, MchInfo mchInfo) throws IOException, GeneralSecurityException {
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

            log.info("微信返回参数 ------------------ "  + body );
            if (statusCode == 200) {
                JSONObject json=(JSONObject) JSONObject.parse(body);
                log.info("微信进件提交成功返回数据 ----------   " + json.toString());
                mchInfo.setApplymentId(json.get("applyment_id").toString());
            } else {
                //JSON 字符串存储
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("code",statusCode);
                jsonObject.put("body",body);
                //mchInfo.setWxMsg("code=" + statusCode + ",body=" + body);
                mchInfo.setWxMsg(jsonObject.toJSONString());
            }
        }
    }

    public static String rsaEncryptOAEP(String message, java.security.cert.X509Certificate certificate)
            throws IllegalBlockSizeException, IOException {
        try {
            Cipher cipher=Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());

            byte[] data=message.getBytes("utf-8");
            byte[] cipherdata=cipher.doFinal(data);
            return Base64.getEncoder().encodeToString(cipherdata);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }

    public static java.security.cert.X509Certificate getCertificate(String filename) throws IOException {
        InputStream fis=new FileInputStream(filename);
        try (BufferedInputStream bis=new BufferedInputStream(fis)) {
            CertificateFactory cf=CertificateFactory.getInstance("X509");
            java.security.cert.X509Certificate cert=(java.security.cert.X509Certificate) cf.generateCertificate(bis);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书文件", e);
        }
    }

    @PostMapping("/save_rewrite")
    @ApiOperation(value="新增商户", notes="新增商户")
    public Resp saveRewrite(@Valid Merchant model, @TokenInfo(property=CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, @UserId String id) {
        model.setStatus(2);
        model.setServiceProviderId(serviceProviderId); //getAttribute：返回指定属性的属性值
        model.setManagerId(id);
        model.setType(1);
        return Resp.success(merchantService.saveMerchant(model, serviceProviderId, id));
    }

    @GetMapping("/find_by_fuwushang")
    @ApiOperation(value="服务商进件列表", notes="服务商进件列表 ")
    public Resp<Page<MchInfoVO>> findByFuwushangNew(MchInfoDTO mchInfoDTO, PageVo pageVo, @UserId String userId) {
        return Resp.success(mchInfoService.findByAgentMch(mchInfoDTO, pageVo, userId));
    }

    /**
     * 支付宝得到事务id
     *
     * @param aliAppId
     * @param aliPublicKey
     * @param aliPrivateKey
     * @param accountName
     * @param contactName
     * @param contactMobile
     * @param contactEmail
     * @return
     */
    public static String getBatchNo(String aliAppId, String aliPublicKey, String aliPrivateKey, String accountName, String contactName, String contactMobile, String contactEmail) {
        AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliAppId, aliPrivateKey, "json", "GBK", aliPublicKey, "RSA2");
        AlipayOpenAgentCreateRequest request=new AlipayOpenAgentCreateRequest();
        request.setBizContent("{" +
                "\"account\":\"" + accountName + "\"," +
                "\"contact_info\":{" +
                "\"contact_name\":\"" + contactName + "\"," +
                "\"contact_mobile\":\"" + contactMobile + "\"," +
                "\"contact_email\":\"" + contactEmail + "\"" +
                "    }," +
                "\"order_ticket\":\"\"" +
                "  }");
        AlipayOpenAgentCreateResponse response=null;
        try {
            response=alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            log.info("支付宝事务状态公共响应参数: {}",response.getBody());
            return response.getBatchNo();
        } else {
            return null;
        }
    }

    /**
     * yy 2020-2-12 15:32:33
     *
     * @param businessLevThree
     * @return 支付宝 商家经营类目
     */
    public static String getMccCode(String businessLevThree) {
        Map<String, String> mccCode=new HashMap<>();
        mccCode.put("出租车服务（TAXI）", "A_A01_4121");
        mccCode.put("出租船只", "A_A01_4457");
        mccCode.put("船舶、海运服务提供商", "A_A01_4468");
        mccCode.put("公共交通", "A_A01_4111");
        mccCode.put("急救服务", "A_A01_4119");
        mccCode.put("铁路货运", "A_A01_4011");
        mccCode.put("铁路客运", "A_A01_4112");
        mccCode.put("游轮及巡游航线服务", "A_A01_4411");
        mccCode.put("长途公路客运", "A_A01_4131");
        mccCode.put("航空公司", "A_A03_4511");
        mccCode.put("航空系统商", "A_A03_4514");
        mccCode.put("机场服务", "A_A03_4582");
        mccCode.put("机票代理人", "A_A03_4512");
        mccCode.put("机票平台", "A_A03_4513");
        mccCode.put("大型旅游景点", "A_A04_4733");
        mccCode.put("度假用别墅服务", "A_A04_7012");
        mccCode.put("国际货运代理和报关行", "A_A04_4723");
        mccCode.put("活动房车场及野营场所", "A_A04_7033");
        mccCode.put("路桥通行费", "A_A04_4784");
        mccCode.put("旅行社和旅游服务", "A_A04_4722");
        mccCode.put("未列入其他代码的运输服务", "A_A04_4789");
        mccCode.put("运动和娱乐露营", "A_A04_7032");
        mccCode.put("住宿服务（旅馆、酒店、汽车旅馆、度假村等）", "A_A04_7011");
        mccCode.put("公共仓储、集装整理", "A_A02_4225");
        mccCode.put("快递服务", "A_A02_4215");
        mccCode.put("物流货运服务", "A_A02_4214");
        mccCode.put("电器承包商", "S_S01_1731");
        mccCode.put("混凝土承包商", "S_S01_1771");
        mccCode.put("建筑材料承包商", "S_S01_1740");
        mccCode.put("金属产品承包商", "S_S01_1761");
        mccCode.put("景观美化与园艺服务", "S_S01_0780");
        mccCode.put("空调类承包商", "S_S01_17111");
        mccCode.put("木工承包商", "S_S01_1750");
        mccCode.put("农业合作与农具", "S_S01_0763");
        mccCode.put("其他工程承包商", "S_S01_1799");
        mccCode.put("兽医服务", "S_S01_0742");
        mccCode.put("不动产代理——房地产经纪", "S_S02_7013");
        mccCode.put("不动产管理－物业管理", "S_S02_6513");
        mccCode.put("房地产开发商", "S_S02_1520");
        mccCode.put("公共事业（电、气、水）", "S_S06_4900");
        mccCode.put("公共事业-电力缴费", "S_S06_4901");
        mccCode.put("公共事业-煤气缴费", "S_S06_4902");
        mccCode.put("公共事业-清洁服务缴费", "S_S06_4904");
        mccCode.put("公共事业-自来水缴费", "S_S06_4903");
        mccCode.put("保险代理", "S_S04_6301");
        mccCode.put("保险公司", "S_S04_6300");
        mccCode.put("典当行", "S_S04_5933");
        mccCode.put("电汇和汇票服务", "S_S04_4829");
        mccCode.put("个人资金借贷", "S_S04_6760");
        mccCode.put("贵金属投资", "S_S04_6050");
        mccCode.put("金融机构-其他服务", "S_S04_6012");
        mccCode.put("金融机构-商业银行服务", "S_S04_6010");
        mccCode.put("金融机构-自动现金服务", "S_S04_6011");
        mccCode.put("外币汇兑", "S_S04_6051");
        mccCode.put("证券期货基金", "S_S04_6211");
        mccCode.put("房车和娱乐车辆出租", "S_S10_7519");
        mccCode.put("卡车及拖车出租", "S_S10_7513");
        mccCode.put("轮胎翻新、维修", "S_S10_7534");
        mccCode.put("汽车出租", "S_S10_7512");
        mccCode.put("汽车改造等服务（非经销商）", "S_S10_7538");
        mccCode.put("汽车喷漆店", "S_S10_7535");
        mccCode.put("汽车维修、保养、美容装饰", "S_S10_7531");
        mccCode.put("停车服务", "S_S10_7523");
        mccCode.put("拖车服务", "S_S10_7549");
        mccCode.put("洗车", "S_S10_7542");
        mccCode.put("保安和监控服务", "S_S05_7393");
        mccCode.put("出版印刷服务", "S_S05_2741");
        mccCode.put("复印及绘图服务", "S_S05_7338");
        mccCode.put("公关和企业管理服务", "S_S05_7392");
        mccCode.put("广告服务", "S_S05_7311");
        mccCode.put("海上船只遇难救助", "S_S05_5935");
        mccCode.put("刻版排版服务", "S_S05_2791");
        mccCode.put("猎头、职业中介", "S_S05_7361");
        mccCode.put("灭虫及消毒服务", "S_S05_7342");
        mccCode.put("其他商业服务", "S_S05_7399");
        mccCode.put("清洁、保养及门卫服务", "S_S05_7349");
        mccCode.put("清洁抛光服务", "S_S05_2842");
        mccCode.put("商业摄影、设计、绘图服务", "S_S05_7333");
        mccCode.put("商业摄影摄像服务", "S_S05_7395");
        mccCode.put("设备、工具、家具和电器出租", "S_S05_7394");
        mccCode.put("文字处理/翻译速记", "S_S05_7339");
        mccCode.put("债务催收机构", "S_S05_7322");
        mccCode.put("征信和信用报告咨询服务", "S_S05_7321");
        mccCode.put("按摩服务", "S_S08_7297");
        mccCode.put("财务债务咨询", "S_S08_7276");
        mccCode.put("导购、经纪和拍卖服务", "S_S08_7278");
        mccCode.put("服装出租", "S_S08_7296");
        mccCode.put("干洗店", "S_S08_7216");
        mccCode.put("婚介服务", "S_S08_7273");
        mccCode.put("婚庆服务", "S_S08_7277");
        mccCode.put("家政服务", "S_S08_7295");
        mccCode.put("美容/美发服务", "S_S08_7230");
        mccCode.put("美容SPA和美体保健", "S_S08_7298");
        mccCode.put("其他生活服务", "S_S08_7299");
        mccCode.put("丧仪殡葬服务", "S_S08_7261");
        mccCode.put("摄影服务", "S_S08_7221");
        mccCode.put("室内清洁服务", "S_S08_7217");
        mccCode.put("洗衣服务", "S_S08_7210");
        mccCode.put("洗熨服务（自助洗衣服务）", "S_S08_7211");
        mccCode.put("鞋帽清洗", "S_S08_7251");
        mccCode.put("团购及折扣店", "S_S07_5310");
        mccCode.put("办公电器和小家电维修", "S_S09_7629");
        mccCode.put("电器维修", "S_S09_7622");
        mccCode.put("各类维修相关服务", "S_S09_7699");
        mccCode.put("焊接维修服务", "S_S09_7692");
        mccCode.put("家具维修、翻新", "S_S09_7641");
        mccCode.put("空调、制冷设备维修", "S_S09_7623");
        mccCode.put("手表、钟表和首饰维修店", "S_S09_7631");
        mccCode.put("保险直销（代扣）", "S_S03_5960");
        mccCode.put("电话接入直销", "S_S03_5967");
        mccCode.put("电话外呼直销", "S_S03_5966");
        mccCode.put("订阅订购服务", "S_S03_5968");
        mccCode.put("旅游相关服务直销", "S_S03_5962");
        mccCode.put("目录直销平台", "S_S03_5964");
        mccCode.put("上门直销（直销员）", "S_S03_5963");
        mccCode.put("直销", "S_S03_5969");
        mccCode.put("直销代理", "S_S03_5965");
        mccCode.put("超市（非平台类）", "C_C04_5411");
        mccCode.put("成人用品/避孕用品/情趣内衣", "C_C04_5914");
        mccCode.put("国外代购及免税店", "C_C04_5309");
        mccCode.put("会员制批量零售店", "C_C04_5300");
        mccCode.put("平台类综合商城", "C_C04_5311");
        mccCode.put("其他专业零售店", "C_C04_5999");
        mccCode.put("其他综合零售", "C_C04_5399");
        mccCode.put("烟花爆竹", "C_C04_5984");
        mccCode.put("油品燃料经销", "C_C04_5983");
        mccCode.put("杂货店", "C_C04_5331");
        mccCode.put("帐篷和遮阳篷商店", "C_C04_5998");
        mccCode.put("裁缝、修补、改衣制衣", "C_C06_5697");
        mccCode.put("高档时装及奢侈品", "C_C06_5691");
        mccCode.put("各类服装及饰物", "C_C06_5699");
        mccCode.put("行李箱包", "C_C06_5948");
        mccCode.put("假发等饰品", "C_C06_5698");
        mccCode.put("男性服饰", "C_C06_5611");
        mccCode.put("内衣/家居服", "C_C06_5651");
        mccCode.put("女性成衣", "C_C06_5621");
        mccCode.put("配饰商店", "C_C06_5631");
        mccCode.put("皮草皮具", "C_C06_5681");
        mccCode.put("鞋类", "C_C06_5661");
        mccCode.put("鞋类销售平台（批发商）", "C_C06_5139");
        mccCode.put("制服与商务正装定制", "C_C06_5137");
        mccCode.put("壁炉、屏风", "C_C03_5718");
        mccCode.put("玻璃、油漆涂料、墙纸", "C_C03_5231");
        mccCode.put("布料、缝纫用品和其他纺织品（批发商）", "C_C03_5131");
        mccCode.put("草坪和花园用品", "C_C03_5261");
        mccCode.put("窗帘、帷幕、室内装潢", "C_C03_5714");
        mccCode.put("大型仓储式家庭用品卖场", "C_C03_5200");
        mccCode.put("地板和地毯", "C_C03_5713");
        mccCode.put("各种家庭装饰专营", "C_C03_5719");
        mccCode.put("花木栽种用品、苗木和花卉（批发商）", "C_C03_5193");
        mccCode.put("家具/家庭摆设", "C_C03_5712");
        mccCode.put("家用纺织品", "C_C03_5949");
        mccCode.put("家用五金工具", "C_C03_5251");
        mccCode.put("木材与建材商店", "C_C03_5211");
        mccCode.put("未列入其他代码的建材（批发商）", "C_C03_5039");
        mccCode.put("油漆、清漆用品（批发商）", "C_C03_5198");
        mccCode.put("游泳、SPA、洗浴设备", "C_C03_5996");
        mccCode.put("化妆品", "C_C01_5977");
        mccCode.put("男士用品：剃须刀、烟酒具、瑞士军刀", "C_C01_5997");
        mccCode.put("钟表店", "C_C01_5944");
        mccCode.put("珠宝和金银饰品", "C_C01_5094");
        mccCode.put("母婴用品", "C_C02_5641");
        mccCode.put("玩具、游戏用品", "C_C02_5945");
        mccCode.put("家用电器", "C_C07_5722");
        mccCode.put("商用计算机及服务器", "C_C07_5045");
        mccCode.put("手机、通讯设备销售", "C_C07_4812");
        mccCode.put("数码产品及配件", "C_C07_5732");
        mccCode.put("专业摄影器材", "C_C07_5946");
        mccCode.put("报纸、杂志", "C_C09_5994");
        mccCode.put("书、期刊和报纸（批发商）", "C_C09_5192");
        mccCode.put("书籍", "C_C09_5942");
        mccCode.put("音像制品", "C_C09_5735");
        mccCode.put("宠物及宠物用品", "C_C10_5995");
        mccCode.put("瓷器、玻璃和水晶摆件", "C_C10_5950");
        mccCode.put("工艺美术用品", "C_C10_5970");
        mccCode.put("古玩复制品（赝品）", "C_C10_5937");
        mccCode.put("花店", "C_C10_5992");
        mccCode.put("家用电子游戏", "C_C10_7993");
        mccCode.put("旧商品店、二手商品店", "C_C10_5931");
        mccCode.put("乐器", "C_C10_5733");
        mccCode.put("礼品、卡片、纪念品", "C_C10_5947");
        mccCode.put("文物古董", "C_C10_5932");
        mccCode.put("艺术品和画廊", "C_C10_5971");
        mccCode.put("邮票/纪念币", "C_C10_5972");
        mccCode.put("宗教物品", "C_C10_5973");
        mccCode.put("保健品", "C_C05_5467");
        mccCode.put("餐厅、订餐服务", "C_C05_5812");
        mccCode.put("茶叶", "C_C05_5466");
        mccCode.put("酒吧、舞厅、夜总会", "C_C05_5813");
        mccCode.put("酒类", "C_C05_5921");
        mccCode.put("快餐店", "C_C05_5814");
        mccCode.put("面包糕点", "C_C05_5462");
        mccCode.put("其他食品零售", "C_C05_5499");
        mccCode.put("肉、禽、蛋及水产品", "C_C05_5422");
        mccCode.put("乳制品/冷饮", "C_C05_5451");
        mccCode.put("糖果及坚果商店", "C_C05_5441");
        mccCode.put("烟草/雪茄", "C_C05_5993");
        mccCode.put("宴会提供商", "C_C05_5811");
        mccCode.put("体育用品/器材", "C_C08_5941");
        mccCode.put("运动服饰", "C_C08_5655");
        mccCode.put("自行车及配件", "C_C08_5940");
        mccCode.put("彩票", "D_D03_7995");
        mccCode.put("电信运营商", "电信运营商");
        mccCode.put("付费电视", "D_D04_4899");
        mccCode.put("话费充值与缴费", "D_D04_4815");
        mccCode.put("网络电话、传真", "D_D04_4821");
        mccCode.put("SNS社交网站", "D_D05_7409");
        mccCode.put("互联网IDC服务", "D_D05_4816");
        mccCode.put("计算机软件", "D_D05_5734");
        mccCode.put("计算机维护和修理服务", "D_D05_7379");
        mccCode.put("门户网站", "D_D05_7374");
        mccCode.put("其他在线应用或综合类", "D_D05_7413");
        mccCode.put("软件系统商", "D_D05_7372");
        mccCode.put("视频点播", "D_D05_7414");
        mccCode.put("手机图铃", "D_D05_7412");
        mccCode.put("网络论坛", "D_D05_7373");
        mccCode.put("网站设计、推广", "D_D05_7408");
        mccCode.put("信息检索服务（信息平台）", "D_D05_7375");
        mccCode.put("休闲游戏", "D_D05_7410");
        mccCode.put("在线文学类", "D_D05_7411");
        mccCode.put("网络游戏点卡、渠道代理", "D_D02_7954");
        mccCode.put("网游运营商（含网页游戏）", "D_D02_7958");
        mccCode.put("网游周边服务、交易平台", "D_D02_7957");
        mccCode.put("游戏系统商", "D_D02_7956");
        mccCode.put("保龄球", "D_D01_7933");
        mccCode.put("电玩娱乐场所", "D_D01_7994");
        mccCode.put("电影院及电影票", "D_D01_7832");
        mccCode.put("动物园、水族馆", "D_D01_7998");
        mccCode.put("高尔夫球场", "D_D01_7992");
        mccCode.put("歌舞厅/夜店", "D_D01_7911");
        mccCode.put("健身和运动俱乐部", "D_D01_7997");
        mccCode.put("乐队和文艺表演", "D_D01_7929");
        mccCode.put("其他娱乐服务", "D_D01_7999");
        mccCode.put("体育场馆", "D_D01_7941");
        mccCode.put("演出票务服务", "D_D01_7922");
        mccCode.put("艺术创作服务", "D_D01_7829");
        mccCode.put("音像制品出租", "D_D01_7841");
        mccCode.put("游乐园、马戏团、嘉年华", "D_D01_7996");
        mccCode.put("展览和艺术场馆", "D_D01_7991");
        mccCode.put("桌球/桌游", "D_D01_7932");
        mccCode.put("大学与学院", "P_P02_8220");
        mccCode.put("儿童保育服务（含学前教育）", "P_P02_8351");
        mccCode.put("函授学校（成人教育）", "P_P02_8241");
        mccCode.put("其他学校和教育服务", "P_P02_8299");
        mccCode.put("商业和文秘学校", "P_P02_8244");
        mccCode.put("职业技能培训", "P_P02_8249");
        mccCode.put("中小学校", "P_P02_8211");
        mccCode.put("GCAS紧急服务（仅限Visa使用）", "P_P06_9702");
        mccCode.put("Visa信任服务", "P_P06_9701");
        mccCode.put("档案", "P_P06_9715");
        mccCode.put("电子档案", "P_P06_9752");
        mccCode.put("站内清算", "P_P06_9950");
        mccCode.put("慈善和社会公益服务", "P_P03_8398");
        mccCode.put("行业协会和专业社团", "P_P03_8641");
        mccCode.put("其他会员组织", "P_P03_8699");
        mccCode.put("汽车协会", "P_P03_8675");
        mccCode.put("政府机构", "P_P03_8651");
        mccCode.put("宗教组织", "P_P03_8661");
        mccCode.put("按摩医生", "P_P01_8041");
        mccCode.put("公立医院", "P_P01_8062");
        mccCode.put("护理和照料服务", "P_P01_8050");
        mccCode.put("其他医疗保健服务", "P_P01_8099");
        mccCode.put("社区医疗服务机构、诊所等", "P_P01_8011");
        mccCode.put("手足病医疗服务", "P_P01_8049");
        mccCode.put("牙科医生", "P_P01_8021");
        mccCode.put("眼镜店", "P_P01_8043");
        mccCode.put("眼科医疗服务", "P_P01_8042");
        mccCode.put("医学及牙科实验室", "P_P01_8071");
        mccCode.put("正骨医生", "P_P01_8031");
        mccCode.put("保释金", "P_P05_9223");
        mccCode.put("法庭费用", "P_P05_9211");
        mccCode.put("国家邮政", "P_P05_9402");
        mccCode.put("行政费用和罚款", "P_P05_9222");
        mccCode.put("社会保障服务", "P_P05_9399");
        mccCode.put("使领馆", "P_P05_9400");
        mccCode.put("税务、海关", "P_P05_9311");
        mccCode.put("政府采购", "P_P05_9405");
        mccCode.put("政府贷款", "P_P05_9411");
        mccCode.put("测试实验服务", "P_P04_8734");
        mccCode.put("法律咨询和律师事务所", "P_P04_8111");
        mccCode.put("会计、审计、财务服务", "P_P04_8931");
        mccCode.put("建筑、工程和测量服务", "P_P04_8911");
        mccCode.put("其他专业服务", "P_P04_8999");
        mccCode.put("装修、装潢、园艺", "P_P04_8912");
        mccCode.put("办公、影印及微缩摄影器材（批发商）", "B_B01_5044");
        mccCode.put("办公及商务家具（批发商）", "B_B01_5021");
        mccCode.put("打字设备、打印复印机、扫描仪", "B_B01_5978");
        mccCode.put("未列入其他代码的商用器材", "B_B01_5046");
        mccCode.put("文具、办公用品、复印纸和书写纸（批发商）", "B_B01_5111");
        mccCode.put("文具及办公用品", "B_B01_5943");
        mccCode.put("电气产品和设备", "B_B02_5065");
        mccCode.put("工业设备和制成品", "B_B02_5085");
        mccCode.put("管道及供暖设备", "B_B02_5074");
        mccCode.put("化工产品", "B_B02_5169");
        mccCode.put("金属产品和服务（批发商）", "B_B02_5051");
        mccCode.put("其他工业耐用品", "B_B02_5099");
        mccCode.put("其他工业原料和消耗品", "B_B02_5199");
        mccCode.put("石油及石油产品（批发商）", "B_B02_5172");
        mccCode.put("五金器材及用品（批发商）", "B_B02_5072");
        mccCode.put("船舶及配件销售", "B_B03_5551");
        mccCode.put("电动车及配件", "B_B03_5572");
        mccCode.put("二手车销售", "B_B03_5521");
        mccCode.put("飞机及配件、航道设施", "B_B03_5565");
        mccCode.put("轨道交通设备器材", "B_B03_5564");
        mccCode.put("活动房车销售商", "B_B03_5271");
        mccCode.put("机动车供应及零配件（批发商）", "B_B03_5013");
        mccCode.put("机动车综合经营", "B_B03_5599");
        mccCode.put("加油卡、加油服务", "B_B03_5542");
        mccCode.put("加油站、服务站", "B_B03_5541");
        mccCode.put("露营及旅行汽车", "B_B03_5592");
        mccCode.put("摩托车及配件", "B_B03_5571");
        mccCode.put("汽车零配件", "B_B03_5533");
        mccCode.put("汽车轮胎经销", "B_B03_5532");
        mccCode.put("汽车销售", "B_B03_5511");
        mccCode.put("拖车、篷车及娱乐用车", "B_B03_5561");
        mccCode.put("雪车", "B_B03_5598");
        mccCode.put("运输搬运设备、起重装卸设备", "B_B03_5566");
        mccCode.put("康复和身体辅助用品", "B_B04_5976");
        mccCode.put("药品、药品经营者（批发商）", "B_B04_5122");
        mccCode.put("药物", "B_B04_5912");
        mccCode.put("医疗器械", "B_B04_5047");
        mccCode.put("助听器", "B_B04_5975");
        mccCode.put("小贷公司", "S_S04_6060");
        mccCode.put("消费金融公司", "S_S04_6061");
        mccCode.put("汽车金融公司", "S_S04_6062");
        mccCode.put("融资租赁公司", "S_S04_6063");
        mccCode.put("金融租赁公司", "S_S04_6064");
        mccCode.put("信托公司", "S_S04_6065");
        mccCode.put("支付机构", "S_S04_6066");
        mccCode.put("融资担保公司", "S_S04_6067");
        mccCode.put("借贷消费平台", "S_S04_6068");
        return mccCode.get(businessLevThree);
    }

    public static SSLContext getSSLContext(String certPath, String mchId) {
        InputStream inputStream;
        try {
            inputStream=new FileInputStream(new File(certPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            KeyStore keystore=KeyStore.getInstance("PKCS12");
            char[] partnerId2charArray=mchId.toCharArray();
            keystore.load(inputStream, partnerId2charArray);
            return SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
        } catch (Exception var9) {
            throw new RuntimeException("证书文件有问题，请核实！", var9);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 删除临时文件
     *
     * @param files
     */
    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


    public static JSONObject uploadFile(MultipartFile multipartFile, TopConfig topConfig) {
        HttpPost httpPost=new HttpPost("https://api.mch.weixin.qq.com/secapi/mch/uploadmedia");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.getMimeType());
        CloseableHttpClient client=null;
        File excelFile=null;
        JSONObject jsonObject =null;
        try {
            client=HttpClients.custom().setSSLContext(getSSLContext(topConfig.getWxCertPath(), topConfig.getWxMchId())).build();
            // 生成签名和图片md5加密
            String hash=DigestUtils.md5Hex(multipartFile.getBytes());
            Map<String, String> param=new HashMap<>(3);
            param.put("media_hash", hash);
            param.put("mch_id", topConfig.getWxMchId());
            param.put("sign_type", "HMAC-SHA256");
            // 配置post图片上传
            String sign=WXPayUtil.generateSignature(param, topConfig.getWxAppKey(), WXPayConstants.SignType.HMACSHA256);
            // 用uuid作为文件名，防止生成的临时文件重复
            excelFile=File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            multipartFile.transferTo(excelFile);
            FileBody bin=new FileBody(excelFile, ContentType.create("image/jpg", Consts.UTF_8));
            HttpEntity build=MultipartEntityBuilder.create().setCharset(Charset.forName("utf-8"))
                    .addTextBody("media_hash", hash)
                    .addTextBody("mch_id", topConfig.getWxMchId())
                    .addTextBody("sign_type", "HMAC-SHA256")
                    .addTextBody("sign", sign)
                    .addPart("media", bin)
                    .build();
            httpPost.setEntity(build);
            HttpResponse httpResponse=client.execute(httpPost);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseEntity=EntityUtils.toString(httpResponse.getEntity());
                jsonObject = XmlUtil.xml2Json(responseEntity);
                return jsonObject;
            }
        } catch (Exception e) {
            log.error("微信图片上传异常 ， e={}", e);
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.warn("关闭资源httpclient失败 {}", e);
                }
            }
            if (excelFile != null) {
                deleteFile(excelFile);
            }
        }
        jsonObject.put("return_code","FAIL");
        jsonObject.put("return_msg","微信图片上传异常");
        return jsonObject;
    }

    /**
     * 2020-2-13 15:29:04 yy
     * 得到证书序列号，微信进件时必填
     *
     * @param myWxConfig
     * @param topConfig
     * @return
     * @throws Exception
     */
    public static JSONObject getCertificates(MyWxConfig myWxConfig, TopConfig topConfig) throws Exception {
        Map<String, String> refundMap=new HashMap<String, String>();
        refundMap.put("mch_id", topConfig.getWxMchId());
        refundMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        refundMap.put("sign_type", "HMAC-SHA256");
        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());
        Map<String, String> serialNoMap=WxDepositUtils.getSerialNo(refundMap, myWxConfig);
        String certificates=serialNoMap.get("certificates");
        JSONObject json=JSONObject.parseObject(certificates);
        return json.getJSONArray("data").getJSONObject(0);
    }

    public static String decryptCertSN(String associatedData, String nonce, String cipherText, String apiv3Key) throws Exception {
        final Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        SecretKeySpec key=new SecretKeySpec(apiv3Key.getBytes(), "AES");
        GCMParameterSpec spec=new GCMParameterSpec(128, nonce.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        cipher.updateAAD(associatedData.getBytes());
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }


    public static String rsaEncryptByCert(String Content, String certStr) throws Exception {
        X509Certificate certificate=X509Certificate.getInstance(certStr.getBytes());
        PublicKey publicKey=certificate.getPublicKey();
        return encodeBase64(encryptPkcs1padding(publicKey, Content.getBytes(CHAR_ENCODING)));
    }

    private static String encodeBase64(byte[] bytes) throws Exception {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static final String CIPHER_PROVIDER="SunJCE";

    private static final String TRANSFORMATION_PKCS1Paddiing="RSA/ECB/PKCS1Padding";

    private static final String CHAR_ENCODING="UTF-8";//固定值，无须修改


    private static byte[] encryptPkcs1padding(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci=Cipher.getInstance(TRANSFORMATION_PKCS1Paddiing, CIPHER_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }


    public static void main(String[] args) {
        String rootPath="E://";
        //创建文件
        File file=new File(rootPath + "temp_download");
        //判断文件是否存在，如果不存在，则创建此文件夹
        if (!file.exists()) {
            file.mkdir();
        }
        String name="图片压缩包下载";
        String fileName=name + new Date().getTime();
        String zipFileName=fileName + ".zip";
        File zipFile=null;


        String path=rootPath + "temp_download";

        //调用工具类获取图片
        byte[] data=ImageByteUtil.image2byte("C:\\Users\\Administrator\\Desktop\\bbbbbbbb.png");
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        if (data != null) {
            File imageFile=new File(path + File.separator + fileName + ".jpg");
            //创建输出流
            FileOutputStream outStream;
            try {
                outStream=new FileOutputStream(imageFile);
                //写入数据
                outStream.write(data);
                //关闭输出流
                outStream.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            //获取创建好的图片文件
            File imageFile=new File(path + "/" + fileName + ".jpg");
            //打成压缩包
            zipFile=new File(path + "/" + zipFileName);
            FileOutputStream zipFos=new FileOutputStream(zipFile);
            ArchiveOutputStream archOut=new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zipFos);
            if (archOut instanceof ZipArchiveOutputStream) {
                ZipArchiveOutputStream zos=(ZipArchiveOutputStream) archOut;
                ZipArchiveEntry zipEntry=new ZipArchiveEntry(imageFile, imageFile.getName());
                zos.putArchiveEntry(zipEntry);
                zos.write(ImageByteUtil.image2byte(path + "/" + fileName + ".jpg"));
                zos.closeArchiveEntry();
                zos.flush();
                zos.close();
            }


            // 压缩完删除txt文件
            if (imageFile.exists()) {
                imageFile.delete();
            }

            // 输出客户端结束后，删除压缩包
//            if (zipFile.exists()) {
//                zipFile.delete();
//            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ArchiveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 查询商户当面付产品的签约状态
     */
    @PostMapping("/into_ali_query")
    public Resp dmfQuery(String mchInfoId) throws AlipayApiException {

        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig=topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }

        MchInfo mchInfo=mchInfoService.findOne(mchInfoId);
        if (ParamUtil.isBlank(mchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前进件的信息不存在");
        }

        AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", topConfig.getAliAppId(), topConfig.getAliPrivateKey(), "json", "GBK", topConfig.getAliPublicKey(), "RSA2");
        AlipayOpenAgentSignstatusQueryRequest request=new AlipayOpenAgentSignstatusQueryRequest();
        request.setBizContent("{" +
                "\"pid\":" + mchInfo.getAliAccountName() + "," +
                "      \"product_codes\":[" +
                "        \"FACE_TO_FACE_PAYMENT\"" +
                "      ]" +
                "  }");
        AlipayOpenAgentSignstatusQueryResponse response=alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return Resp.success("支付宝回调信息:           " + response.getSubMsg());
    }
}
