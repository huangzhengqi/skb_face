package com.fzy.admin.fp.distribution.utils;


import com.fzy.admin.fp.distribution.cert.CertificateItem;
import com.fzy.admin.fp.distribution.cert.CertificateList;
import com.fzy.admin.fp.distribution.cert.PlainCertificateItem;
import com.wechat.pay.contrib.apache.httpclient.Validator;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.CertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chen
 * @date: 2019/7/24
 **/

public class CertificateDownloader{
/*
    private String apiV3key = "f75e3e8da4f04ef882901799b4ce1234";

    private String merchantId= "1553555941";

    private String privateKeyFilePath = "C:\\Users\\Administrator\\Desktop\\服务商证书\\apiclient_key.pem";

    private String serialNo= "60048B0040A8A1803F3EE9098E16C45214C351C6";

    private String outputFilePath= "C:\\Users\\Administrator\\Desktop\\服务商证书";

    private String wechatpayCertificatePath;
*/
    private static final String CertDownloadPath = "https://api.mch.weixin.qq.com/v3/certificates";

    private static final String applyment4subPath = "https://api.mch.weixin.qq.com/v3/applyment4sub/applyment/applyment_id/";

    public String prefixPath="WorkDir/temp/md5/";

    public List<PlainCertificateItem> downloadCertificate(String merchantId, String serialNo, String privateKeyFilePath, String wechatpayCertificatePath, String apiV3key) throws IOException, GeneralSecurityException {
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, serialNo, PemUtil.loadPrivateKey(new FileInputStream(privateKeyFilePath)));

        if (wechatpayCertificatePath == null) {
            //不做验签
            builder.withValidator(response -> true);
        } else {
            List<X509Certificate> certs = new ArrayList<>();
            certs.add(PemUtil.loadCertificate(new FileInputStream(wechatpayCertificatePath)));
            builder.withWechatpay(certs);
        }

        HttpGet httpGet = new HttpGet(CertDownloadPath);
        httpGet.addHeader("Accept", "application/json");

        try (CloseableHttpClient client = builder.build()) {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            String body = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                System.out.println("body:" + body);
                CertificateList certList = JsonUtils.convertJsonToCertList(body);
                return decryptAndValidate(certList, response, apiV3key);
            } else {
                System.out.println("download failed,resp code=" + statusCode + ",body=" + body);
                throw new IOException("request failed");
            }
        }
    }

    private List<PlainCertificateItem> decryptAndValidate(CertificateList certList, CloseableHttpResponse response,String apiV3key) throws GeneralSecurityException, IOException {
        List<PlainCertificateItem> plainCerts = new ArrayList<>();
        List<X509Certificate> x509Certs = new ArrayList<>();
        AesUtil decryptor = new AesUtil(apiV3key.getBytes(StandardCharsets.UTF_8));
        for (CertificateItem item : certList.getCerts()) {
            PlainCertificateItem plainCert = new PlainCertificateItem(
                    item.getSerialNo(), item.getEffectiveTime(), item.getExpireTime(),
                    decryptor.decryptToString(
                            item.getEncryptCertificate().getAssociatedData().getBytes(StandardCharsets.UTF_8),
                            item.getEncryptCertificate().getNonce().getBytes(StandardCharsets.UTF_8),
                            item.getEncryptCertificate().getCiphertext()));
            System.out.println(plainCert.getPlainCertificate());

            ByteArrayInputStream inputStream = new ByteArrayInputStream(plainCert.getPlainCertificate().getBytes(StandardCharsets.UTF_8));
            X509Certificate x509Cert = PemUtil.loadCertificate(inputStream);
            plainCerts.add(plainCert);
            x509Certs.add(x509Cert);
        }
        //从下载的证书中，获取对报文签名的私钥所对应的证书，并进行验签来验证证书准确
        Verifier verifier = new CertificatesVerifier(x509Certs);
        Validator validator = new WechatPay2Validator(verifier);
        boolean isCorrectCert = validator.validate(response);
        System.out.println(isCorrectCert ? "=== validate success ===" : "=== validate failed ===");
        return isCorrectCert ? plainCerts : null;
    }

    public String saveCertificate(List<PlainCertificateItem> cert) throws IOException {
        File file = new File(prefixPath);
        file.mkdirs();
        PlainCertificateItem item = cert.get(0);
            String outputAbsoluteFilename = file.getAbsolutePath() + File.separator + "wechatpay_" + item.getSerialNo() + ".pem";
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputAbsoluteFilename), StandardCharsets.UTF_8))) {
                writer.write(item.getPlainCertificate());
            }
        return outputAbsoluteFilename;
    }

    public static String getApplyment(String merchantId, String serialNo, String privateKeyFilePath, String applyment_id) throws IOException {
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(merchantId, serialNo, PemUtil.loadPrivateKey(new FileInputStream(privateKeyFilePath)));
        builder.withValidator(response -> true);
        String url=applyment4subPath+applyment_id;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");
        httpGet.setHeader(org.apache.http.HttpHeaders.CONTENT_TYPE, "application/json");
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
}