package com.fzy.admin.fp;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOpenAgentConfirmRequest;
import com.alipay.api.request.AlipayOpenAgentCreateRequest;
import com.alipay.api.request.AlipayOpenAgentFacetofaceSignRequest;
import com.alipay.api.response.AlipayOpenAgentConfirmResponse;
import com.alipay.api.response.AlipayOpenAgentCreateResponse;
import com.alipay.api.response.AlipayOpenAgentFacetofaceSignResponse;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.distribution.app.controller.AppDistMerchantController;
import com.fzy.admin.fp.distribution.app.service.DistPayService;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.utils.CertificateDownloader;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.repository.WxCityNoRepository;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.CertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@ActiveProfiles("prod")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {LysjPayMasterApplication.class}
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Slf4j
public class fzy {
    @Resource
    private DistPayService distPayService;

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private DistUserService distUserService;

    private static final int RUNNER_COUNT = 100;

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    TopConfigRepository topConfigRepository;

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private WxCityNoRepository wxCityNoRepository;

    private static final String CertDownloadPath = "https://api.mch.weixin.qq.com/v3/certificates";

    public String prefixPath="WorkDir/temp/md5/";

    public String getBatchNo(String aliAppId,String aliPublicKey,String aliPrivateKey,String accountName,String contactName,String contactMobile,String contactEmail) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliAppId, aliPrivateKey, "json", "GBK", aliPublicKey, "RSA2");
        AlipayOpenAgentCreateRequest request = new AlipayOpenAgentCreateRequest();
        request.setBizContent("{" +
                "\"account\":\""+accountName+"\"," +
                "\"contact_info\":{" +
                "\"contact_name\":\""+contactName+"\"," +
                "\"contact_mobile\":\""+contactMobile+"\"," +
                "\"contact_email\":\""+contactEmail+"\"" +
                "    }," +
                "\"order_ticket\":\"\"" +
                "  }");
        AlipayOpenAgentCreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            return response.getBatchNo();
        } else {
            return null;
        }
    }

    /**
     * 2020-3-13 09:44:44 yy
     * 小微商户自动进件
     */
//    public void wxInto() throws Exception {
//        final String serviceId ="1236113777056485376";
//        MchInfo mchInfo = mchInfoService.findOne(serviceId);
//        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
//        FileInfoVo idCardCopyVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId());
//        if (idCardCopyVo == null) {
//        }
//        String idCardCopyPath = prefixPath + idCardCopyVo.getPath();
//        File idCardCopyFile = new File(idCardCopyPath);
//        MultipartFile idCardCopyMultipartFile = new MockMultipartFile("file", new FileInputStream(idCardCopyFile));
//        String idCardCopy = AppDistMerchantController.uploadFile(idCardCopyMultipartFile, topConfig);
//
//        FileInfoVo idCardNationalVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getEpresentativePhotoId2());
//        if (idCardNationalVo == null) {
//        }
//        String idCardNationalPath = prefixPath + idCardNationalVo.getPath();
//        File idCardNationalFile = new File(idCardNationalPath);
//        MultipartFile idCardNationalMultipartFile = new MockMultipartFile("file", new FileInputStream(idCardNationalFile));
//        String idCardNational = AppDistMerchantController.uploadFile(idCardNationalMultipartFile, topConfig);
//
//        FileInfoVo storeEntrancePicVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
//        if (storeEntrancePicVo == null) {
//        }
//        String storeEntrancePicPath = prefixPath+ storeEntrancePicVo.getPath();
//        File storeEntrancePicFile = new File(storeEntrancePicPath);
//        MultipartFile storeEntrancePicMultipartFile = new MockMultipartFile("file", new FileInputStream(storeEntrancePicFile));
//        String storeEntrancePicNational = AppDistMerchantController.uploadFile(storeEntrancePicMultipartFile, topConfig);
//
//        FileInfoVo storePhotoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
//        if (storePhotoVo == null) {
//        }
//        String storePhotoVoPath = prefixPath + storePhotoVo.getPath();
//        File storePhotoFile = new File(storePhotoVoPath);
//        MultipartFile storePhotoMultipartFile = new MockMultipartFile("file", new FileInputStream(storePhotoFile));
//        String storePhotoNational = AppDistMerchantController.uploadFile(storePhotoMultipartFile, topConfig);
//
//        MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
//        //微信证书内容
//        JSONObject certificates = AppDistMerchantController.getCertificates(myWxConfig, topConfig);
//        JSONObject encryptCertificate=certificates.getJSONObject("encrypt_certificate");
//        //微信证书解密内容
//        String content = AppDistMerchantController.decryptCertSN(encryptCertificate.getString("associated_data"), encryptCertificate.getString("nonce"), encryptCertificate.getString("ciphertext"), topConfig.getWxApiv3key());
//
//        Map<String, String> refundMap = new HashMap<String, String>();
//        refundMap.put("version", "3.0");
//        refundMap.put("cert_sn", certificates.getString("serial_no"));
//        refundMap.put("mch_id", topConfig.getWxMchId());
//        refundMap.put("nonce_str",UUID.randomUUID().toString().replace("-", ""));
//        refundMap.put("sign_type", "HMAC-SHA256");
//        //服务商自定义的商户唯一编号。每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
//        String businessCode = UUID.randomUUID().toString().replace("-", "");
//        refundMap.put("business_code", businessCode);
//        refundMap.put("id_card_copy", idCardCopy);
//        refundMap.put("id_card_national", idCardNational);
//        refundMap.put("id_card_name",AppDistMerchantController.rsaEncryptByCert(mchInfo.getAccountHolder(), content));
//        refundMap.put("id_card_number",AppDistMerchantController.rsaEncryptByCert(mchInfo.getAccountHolder(), content));
//        refundMap.put("id_card_valid_time","[\""+mchInfo.getStartCertificateTime()+"\",\""+mchInfo.getEndCertificateTime()+"\"]");
//        refundMap.put("account_name",AppDistMerchantController.rsaEncryptByCert(mchInfo.getAccountHolder(), content));
//        refundMap.put("account_bank",mchInfo.getBankName());
//        String bankAddressCode = wxCityNoRepository.findByCity(mchInfo.getBankCity()).getNo();
//        refundMap.put("bank_address_code",bankAddressCode);
//        refundMap.put("bank_name",mchInfo.getBankName());
//        refundMap.put("account_number",AppDistMerchantController.rsaEncryptByCert(mchInfo.getAccountNumber(), content));
//        refundMap.put("store_name",mchInfo.getMerchantName());
//        String registerAddress = wxCityNoRepository.findByCity(mchInfo.getStoreAddress()).getNo();
//        refundMap.put("store_address_code",registerAddress);
//        refundMap.put("store_street",mchInfo.getRegisterAddress());
//        refundMap.put("store_entrance_pic",storeEntrancePicNational);
//        refundMap.put("indoor_pic",storePhotoNational);
//        refundMap.put("merchant_shortname",mchInfo.getShortName());
//        refundMap.put("service_phone",mchInfo.getCusServiceTel());
//        refundMap.put("product_desc",mchInfo.getBusinessScope());
//        refundMap.put("rate",String.valueOf(mchInfo.getWxRate()));
//        refundMap.put("contact",AppDistMerchantController.rsaEncryptByCert(mchInfo.getAccountHolder(), content));
//        refundMap.put("contact_phone",AppDistMerchantController.rsaEncryptByCert(mchInfo.getPhone(), content));
//        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());
//        Map<String, String> serialNoMap = WxDepositUtils.micro(refundMap, myWxConfig);
//        String applymentId = serialNoMap.get("applyment_id");
//        mchInfo.setApplymentId(applymentId);
//    }

    private String downloadCertificate(String merchantId,String serialNo,String privateKeyFilePath,String wechatpaySerialNo,String param) throws IOException, GeneralSecurityException {
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, serialNo, PemUtil.loadPrivateKey(new FileInputStream(privateKeyFilePath)));
        builder.withValidator(response -> true);
        HttpPost httpGet = new HttpPost("https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/");
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Wechatpay-Serial", wechatpaySerialNo);
        httpGet.setHeader(org.apache.http.HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()+"; charset="+ContentType.APPLICATION_JSON.getCharset().name());
        httpGet.setEntity(new StringEntity(param,ContentType.APPLICATION_JSON));
        try (CloseableHttpClient client = builder.build()) {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                return body;
            } else {
                System.out.println("download failed,resp code=" + statusCode + ",body=" + body);
                throw new IOException("request failed");
            }
        }
    }

    public static String send(String url, String jsonObject,String encoding) throws IOException {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
//        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("User-Agent", "PostmanRuntime/7.23.0");
        httpPost.setHeader("Authorization", "WECHATPAY2-SHA256-RSA2048 mchid=\"1553555941\",serial_no=\"60048B0040A8A1803F3EE9098E16C45214C351C6\",nonce_str=\"1583917061077\",timestamp=\"1583917403\",signature=\"bF4vfTk0QNT5LPfExO48hRJ6+0Qf77g9mADmtC44VWgju+uCcfIsI9zy7YRyhE/DVvWJ/tDLxd5ISXGpsFGje2MBvwi83lqoqew86fqtxkNP/F9XjIcvClcDN7+BLoov1OklmXvxMGOjkWskd28u7FIM2VHTa5xRwTo14qWZaFmvErCFeD6F1NpGQ5JXyEiGhvom2PLWAEOq+oiXtL/yEv0h0ajz2EQy3iqXY/YRAMYqPGdz7tghlsTNzpn/98uN0rZczawn1qtH1aAMuY5gaxSC4YsRxKG9Iwafp4fAAocLHbmCr5PWtBBKGZeoecnpcndrhRJRPLmrQ92yWa8B+g==\"");


        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        org.apache.http.HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    public static X509Certificate getCertificate(String filename) throws IOException {
        InputStream fis = new FileInputStream(filename);
        try (BufferedInputStream bis = new BufferedInputStream(fis)) {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
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

    public static String test11(String jsonObject,String content) throws Exception {
        String toJson = "";
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials("1553555941", new PrivateKeySigner(getCertificate("C:\\Users\\Administrator\\Desktop\\服务商证书\\apiclient_cert.pem").getSerialNumber().toString(16), getPrivateKey("D:\\我的文档\\Documents\\Tencent Files\\2130125753\\FileRecv\\服务商证书\\apiclient_key.pem")));
        HttpPost request = new HttpPost("https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/");
        request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()+"; charset="+ContentType.APPLICATION_JSON.getCharset().name());
        request.setHeader(HttpHeaders.AUTHORIZATION,"WECHATPAY2-SHA256-RSA2048 mchid=\"1553555941\",serial_no=\"60048B0040A8A1803F3EE9098E16C45214C351C6\",nonce_str=\"1583917061077\",timestamp=\"1583920400\",signature=\"bF4vfTk0QNT5LPfExO48hRJ6+0Qf77g9mADmtC44VWgju+uCcfIsI9zy7YRyhE/DVvWJ/tDLxd5ISXGpsFGje2MBvwi83lqoqew86fqtxkNP/F9XjIcvClcDN7+BLoov1OklmXvxMGOjkWskd28u7FIM2VHTa5xRwTo14qWZaFmvErCFeD6F1NpGQ5JXyEiGhvom2PLWAEOq+oiXtL/yEv0h0ajz2EQy3iqXY/YRAMYqPGdz7tghlsTNzpn/98uN0rZczawn1qtH1aAMuY5gaxSC4YsRxKG9Iwafp4fAAocLHbmCr5PWtBBKGZeoecnpcndrhRJRPLmrQ92yWa8B+g==\"");
        request.setHeader("Wechatpay-Serial","6735068CD04B80E1F122237E50FF0E6F47D205DC");
        request.setHeader("Wechatpay-Nonce","1b24e83451a48c7f69556491f98c33c6");
        request.setHeader("Wechatpay-Signature","TcUyzjCctkMhc+x2DHunwbhkFJJ+MLwkSeK1VwzWeaDXAhKgL9LYTp/sbFEUoFRrQ6EdjgM1Hv6meHMPoGHDbSibLAFv2q5GfSVVwny0xOYq+rWbGBxzumTY6q/jNeLL2X73Jg+7iPCK+hblmR1dJQEsDoP6gfoL1eH2tkOYNt8dd6aEtMxdVJ0QgeiIIs/LHbT9zLp9YobD30ezTaLd+URT5U5NeobMC8/tJ4lNT/oJIQvvcKlP5UBo2Bs4MVOYaONpG09rtjAyiV9Wy4dmw29ifM9uxECVsS3lP+vJd3WzPdvzsWHlSPPiWO1QqBOFxsjVExXMIn4E/GyEoDXCaw==");
        request.setHeader("Wechatpay-Timestamp","1583922839");
        request.addHeader(HttpHeaders.USER_AGENT, "wxpay sdk java v1.0 " + "1553555941");
        request.addHeader(HttpHeaders.ACCEPT,ContentType.APPLICATION_JSON.getMimeType() );
        request.setEntity(new StringEntity(toJson,ContentType.APPLICATION_JSON));
        WechatPayHttpClientBuilder httpClientBuilder = WechatPayHttpClientBuilder.create() ;
        httpClientBuilder.withCredentials(wechatPay2Credentials);
        List<X509Certificate> list = new ArrayList<X509Certificate>(1);
        list.add(getCertificate("C:\\Users\\Administrator\\Desktop\\服务商证书\\apiclient_cert.pem"));
        httpClientBuilder.withValidator(new WechatPay2Validator(new CertificatesVerifier(list)));
        CloseableHttpClient httpClient = httpClientBuilder.build();
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity, Consts.UTF_8.name());
        return s;
    }




    public static PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    String schema = "WECHATPAY2-SHA256-RSA2048 ";
    HttpUrl httpurl = HttpUrl.parse("https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/");



    String getToken(String method, HttpUrl url, String body,String mchId,String nonceStr,String certificateSerialNo,PrivateKey privateKey) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes("utf-8"),privateKey);

        return "mchid=\"" + mchId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + certificateSerialNo + "\","
                + "signature=\"" + signature + "\"";
    }

    String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    String sign(byte[] message,PrivateKey privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }

    @Test
    public void zfbInto() throws Exception {
        final String serviceId ="1236113777056485376";
        MchInfo mchInfo = mchInfoService.findOne(serviceId);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        String batchNo = getBatchNo(topConfig.getAliAppId(),  topConfig.getAliPublicKey(), topConfig.getAliPrivateKey(),mchInfo.getAliAccountName(), mchInfo.getAccountHolder(), mchInfo.getPhone(), mchInfo.getEmail());
        //----------------支付宝自动进件start yy-2020-2-13 13:50:30-----------
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", topConfig.getAliAppId(), topConfig.getAliPrivateKey(), "json", "GBK", topConfig.getAliPublicKey(), "RSA2");
        AlipayOpenAgentFacetofaceSignRequest request = new AlipayOpenAgentFacetofaceSignRequest();
        request.setBatchNo(batchNo);
        //通过三级经营范围得到经营类目编号
        request.setMccCode("C_C07_5732");
//        FileInfoVo fileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getLicense());
//        String path = prefixPath + fileInfoVo.getPath();
//        FileItem SpecialLicensePic = new FileItem(path);
//        request.setSpecialLicensePic(SpecialLicensePic);//企业特殊资质照片
        FileInfoVo businessLicensePicFileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
        String businessLicensePicPath = prefixPath + businessLicensePicFileInfoVo.getPath();
        request.setRate(String.valueOf(mchInfo.getZfbRate()));
        request.setSignAndAuth(true);
        request.setBusinessLicenseNo(mchInfo.getLicense());
        FileItem BusinessLicensePic = new FileItem(businessLicensePicPath);
        request.setBusinessLicensePic(BusinessLicensePic);//营业执照图片
//        FileItem BusinessLicenseAuthPic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg"); 未知的错误码AE0511010010411
//        request.setBusinessLicenseAuthPic(BusinessLicenseAuthPic);//营业执照授权函图片
        //request.setLongTerm(true);
        //request.setDateLimitation("2017-11-11");
        FileInfoVo storeFileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getStorePhoto());
        String shopScenePicPath = prefixPath + storeFileInfoVo.getPath();
        FileItem ShopScenePic = new FileItem(shopScenePicPath);
        request.setShopScenePic(ShopScenePic);//内铺内景照片
        FileInfoVo doorPhotoFileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
        String doorPhotoPath = prefixPath + doorPhotoFileInfoVo.getPath();
        FileItem ShopSignBoardPic = new FileItem(doorPhotoPath);
        request.setShopSignBoardPic(ShopSignBoardPic);//门头照片
        AlipayOpenAgentFacetofaceSignResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            mchInfo.setBatchNo(batchNo);
            mchInfoService.update(mchInfo);
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败@"+response.getBody());
        }
    }


    public void uploadFile() throws Exception {
////        String serviceId="1171970665827614720";
////        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
////        MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
////        Map<String, String> refundMap = new HashMap<String, String>();
////        refundMap.put("mch_id", topConfig.getWxMchId());
////        refundMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
////        refundMap.put("sign_type", "HMAC-SHA256");
////        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());
////        Map<String, String> serialNoMap = WxDepositUtils.getSerialNo(refundMap, myWxConfig);
////
////        String certificates = serialNoMap.get("certificates");
////        JSONObject json = JSONObject.parseObject(certificates);
////        JSONObject data = json.getJSONArray("data").getJSONObject(0);
////        String serialNo=data.getString("serial_no");
////        System.out.println(serialNo);
//        /*List<String> b=new ArrayList<>();
//        b.add("1207920992691646464");
//        b.add("1209710809094901760");
//        b.add("1211893338074804224");
//        b.add("1211898813562515456");
//        b.add("1215806974065405952");
//        b.add("1227155609579401216");
//        List<Map> commission = distUserService.getCommission(b);
//        for(Map obj:commission){
//            BigDecimal s=new BigDecimal(obj.get("operationCommission").toString());
//            System.out.println(s.toPlainString());
//            System.out.println(obj.get("oneLevelCommission"));
//        }
//*/
////        List<DistUser> distUserVOList = distUserService.getRepository().rankList("1171970665827614720");
////        System.out.println("");
//       /* File file = new File("C:\\Users\\Administrator\\Desktop\\ff.png");
//        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));
//        String s = uploadFile(multipartFile);
//        System.out.println("####################"+s);*/
    }






   /* public void uploadFile() {
        String serviceId="1171970665827614720";
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/mch/uploadmedia");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.getMimeType());
        //CloseableHttpClient client = null;
        try {
            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());

            //client = HttpClients.custom().setSSLContext(getSSLContext("证书地址","商户号")).build();
            // 生成签名和图片md5加密
            FileItem fileItem=new FileItem("C:\\Users\\Administrator\\Desktop\\ff.png");
            String hash = DigestUtils.md5Hex(fileItem.getContent());
            Map<String, String> param = new HashMap<>(3);
            param.put("media_hash", hash);
            param.put("mch_id", topConfig.getWxMchId());
            param.put("sign_type", "HMAC-SHA256");
            WxDepositUtils.sign(param, topConfig.getWxAppKey());
            Map<String, String> refundResultMap = WxDepositUtils.upload(param, myWxConfig);

            for(Map.Entry<String, String> a:refundResultMap.entrySet()){
                System.out.println("#############################键是"+a.getKey()+",值是"+a.getValue());
            }
*//*
            // 配置post图片上传
            // 用uuid作为文件名，防止生成的临时文件重复
            excelFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            multipartFile.transferTo(excelFile);
            FileBody bin = new FileBody(excelFile, ContentType.create("image/jpg", Consts.UTF_8));
            String sign = WXPayUtil.generateSignature(param,  "api密钥", WXPayConstants.SignType.HMACSHA256);
            HttpEntity build = MultipartEntityBuilder.create().setCharset(Charset.forName("utf-8"))
                    .addTextBody("media_hash", hash)
                    .addTextBody("mch_id", "商户号")
                    .addTextBody("sign_type", "HMAC-SHA256")
                    .addTextBody("sign", sign)
                    .addPart("media", bin)
                    .build();
            httpPost.setEntity(build);
            HttpResponse httpResponse = client.execute(httpPost);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                Document document = DocumentHelper.parseText(responseEntity);
                if ("SUCCESS".equalsIgnoreCase(document.selectSingleNode("//return_code").getStringValue())) {
                    if ("SUCCESS".equalsIgnoreCase(document.selectSingleNode("//result_code").getStringValue())) {
                        return document.selectSingleNode("//media_id").getStringValue();
                    }
                }
                error = document.selectSingleNode("//err_code_de").getStringValue();
            }*//*
        } catch (Exception e) {
        }
    }*/
  /*  private static final String CIPHER_PROVIDER = "SunJCE";

    private static final String TRANSFORMATION_PKCS1Paddiing = "RSA/ECB/PKCS1Padding";

    private static final String CHAR_ENCODING = "UTF-8";//固定值，无须修改


    private static byte[] encryptPkcs1padding(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(TRANSFORMATION_PKCS1Paddiing, CIPHER_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    public static String rsaEncryptByCert(String Content, String certStr) throws Exception {
       X509Certificate certificate = X509Certificate.getInstance(certStr.getBytes());
       PublicKey publicKey = certificate.getPublicKey();
       return encodeBase64(encryptPkcs1padding(publicKey, Content.getBytes(CHAR_ENCODING)));
   }

    private static String encodeBase64(byte[] bytes) throws Exception {
        return Base64.getEncoder().encodeToString(bytes);
    }


    @Test
    public void test() throws Exception {
        String serviceId="1171970665827614720";
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
        JSONObject certificates = getCertificates(myWxConfig, topConfig);
        JSONObject encryptCertificate=certificates.getJSONObject("encrypt_certificate");
        String  s= decryptCertSN(encryptCertificate.getString("associated_data"), encryptCertificate.getString("nonce"), encryptCertificate.getString("ciphertext"), topConfig.getWxApiv3key());
        System.out.println(s);
        String encrypt = rsaEncryptByCert("我的身份证", s);
        log.info("身份证的密文了 {}",encrypt);

    }


    public JSONObject getCertificates(MyWxConfig myWxConfig,TopConfig topConfig) throws Exception {
        Map<String, String> refundMap = new HashMap<String, String>();
        refundMap.put("mch_id", topConfig.getWxMchId());
        refundMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        refundMap.put("sign_type", "HMAC-SHA256");
        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());
        Map<String, String> serialNoMap = WxDepositUtils.getSerialNo(refundMap, myWxConfig);
        String certificates = serialNoMap.get("certificates");
        JSONObject json = JSONObject.parseObject(certificates);
        return json.getJSONArray("data").getJSONObject(0);
    }

    public String decryptCertSN(String associatedData, String nonce, String cipherText, String apiv3Key) throws Exception{
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(apiv3Key.getBytes(), "AES");
        GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        cipher.updateAAD(associatedData.getBytes());
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }*/

    public static String rsaEncryptOAEP(String message, X509Certificate certificate)
            throws IllegalBlockSizeException, IOException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());

            byte[] data = message.getBytes("utf-8");
            byte[] cipherdata = cipher.doFinal(data);
            return Base64.getEncoder().encodeToString(cipherdata);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }



    @Test
    public void test(){
        MchInfo mchInfo = mchInfoService.findOne("1236113777056485376");
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        String serialNo = null;
        try {
            serialNo = getCertificate(topConfig.getWxApiCert()).getSerialNumber().toString(16);
            String body =  CertificateDownloader.getApplyment(topConfig.getWxMchId(), serialNo, topConfig.getWxApiKey(), mchInfo.getApplymentId());
            JSONObject json = (JSONObject) JSONObject.parse(body);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZfb(){
        MchInfo mchInfo = mchInfoService.findOne("1236113777056485376");
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        try {
            MchInfo mchInfo1 = AppDistMerchantController.getZfbStatus(topConfig.getAliAppId(), topConfig.getAliPrivateKey(), topConfig.getAliPublicKey(), mchInfo);
            System.out.println("@@@@@@@@"+ mchInfo1);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSumbit(){
        MchInfo mchInfo = mchInfoService.findOne("1236113777056485376");
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(mchInfo.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",topConfig.getAliAppId(),topConfig.getAliPrivateKey(),"json","GBK",topConfig.getAliPublicKey(),"RSA2");
        AlipayOpenAgentConfirmRequest alipayOpenAgentConfirmRequest = new AlipayOpenAgentConfirmRequest();
        alipayOpenAgentConfirmRequest.setBizContent("{" +
                "\"batch_no\":\"2020031618285131000069244\"" +
                "  }");
        AlipayOpenAgentConfirmResponse alipayOpenAgentConfirmResponse = null;
        try {
            alipayOpenAgentConfirmResponse = alipayClient.execute(alipayOpenAgentConfirmRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(alipayOpenAgentConfirmResponse.isSuccess()){
            //支付宝的事务编号：提交事务
            System.out.println("成功！！！！");
        }else{
            System.out.println("失败！！！！");
        }
    }

}
