package com.fzy.admin.fp.member.coupon.controller;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lb
 * @date 2019/6/26 17:05
 * @Description 创建进入微信小程序的二维码
 */
@Component
public class  WXCodeUtil {

    private static final String ACCESS_TOKEN_URL_APP = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;


    public String doGet(String url) {

        System.out.println("-----------向微信请求小程序token-------------");
        System.out.println(url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Map<String, String> resultMap = JacksonUtil.toStringMap(result);
                resultMap.put("currentTime", String.valueOf(System.currentTimeMillis()));
                return resultMap.get("access_token");
            } else {
                throw new BaseException("获取token失败", Resp.Status.PARAM_ERROR.getCode());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取token
    public  String getAccessToken(String merchantId) {
        String token = null;
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(merchantId);
        if (merchantAppletConfig == null) {
            throw new BaseException("商户未配置小程序密钥", Resp.Status.PARAM_ERROR.getCode());
        }
        if (StringUtils.isEmpty(merchantAppletConfig.getAppKey())) {
            throw new BaseException("小程序appSecret未填写", Resp.Status.PARAM_ERROR.getCode());
        }
        if (StringUtils.isEmpty(merchantAppletConfig.getAppId())) {
            throw new BaseException("小程序appId未填写", Resp.Status.PARAM_ERROR.getCode());
        }
        String url = ACCESS_TOKEN_URL_APP.replace("APPID", merchantAppletConfig.getAppId()).replace("APPSECRET", merchantAppletConfig.getAppKey());
        try {
            token = this.doGet(url);
            if (token == null) {
                throw new BaseException("获取token失败", Resp.Status.PARAM_ERROR.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }


    //请求获取二维码buffer
    public String getImageBuffer(String couponId, String merchantId) {

        String token = this.getAccessToken(merchantId);
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", token);
        Map<String, Object> info = new HashMap<>();
        info.put("scene", couponId);//不能超过32位
        info.put("page", "pages/coupondetail/coupondetail");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String infos = JacksonUtil.toJson(info);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String image = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(infos, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
            String qrcode = RandomUtil.randomString(13);
            String path = "WorkDir/temp/lypay/qr/" + qrcode + ".jpg";
            System.out.println(path);
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                try {
                    fileParent.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            image = "qr/" + qrcode + ".jpg";
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

}
