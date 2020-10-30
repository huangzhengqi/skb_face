package com.fzy.admin.fp.pay.pay.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Created by wtl on 2019-04-22 11:18
 * @description 微信支付配置
 */
@Slf4j
public class MyWxConfig implements WXPayConfig {

    private byte[] certData;

    private String appid;
    private String mch_id;
    private String key;

    public MyWxConfig(String appid, String mch_id, String key, String certPath) throws Exception {
        this.appid = appid;
        this.mch_id = mch_id;
        this.key = key;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }


    @Override
    public String getAppID() {
        return appid;
    }

    @Override
    public String getMchID() {
        return mch_id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;

    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
