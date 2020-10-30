package com.fzy.admin.fp.invoice.sign;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import com.fzy.admin.fp.invoice.feign.FamilyAuthenticationClient;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.resp.AccessTokenBO;
import feign.RequestTemplate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthenticationSignHelper {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationSignHelper.class);

    @Value("${facepay.invoice.appId}")
    private String appId;

    @Value("${facepay.invoice.appSecret}")
    private String appSecret;

    @Autowired
    private FamilyAuthenticationClient familyAuthenticationClient;

    private static final String TOKEN_KEY = "ACCESS_TOKEN";

    private static final String FORMATTER_PATTERN = "yyyy-MM-dd hh:mm:ss";

    private static long TOKEN_EXPIRE_TIME = 0L;

    private static String ACCESS_TOKEN = null;











    public void setHeaders(RequestTemplate requestTemplate) {
        String body = new String(requestTemplate.request().body(), StandardCharsets.UTF_8);
        String dateFormatText = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        requestTemplate.header("Date", new String[] { dateFormatText });
        requestTemplate.header("AppId", new String[] { this.appId });
        requestTemplate.header("Content-MD5", new String[] { getSignature(dateFormatText, body) });
    }





    private void refreshToken() {
        FamilyResp<AccessTokenBO> resp = this.familyAuthenticationClient.getAccessToken(this.appId, this.appSecret);
        AccessTokenBO respBody = (AccessTokenBO)resp.getBody();
        if (!resp.isSuccess()) {
            throw new RuntimeException(String.format("刷新数族token失败", new Object[] { resp }));
        }
        ACCESS_TOKEN = respBody.getAccessToken();

        TOKEN_EXPIRE_TIME = System.currentTimeMillis() + (respBody.getExpires().intValue() * 1000) - 1200000L;
    }











    private String getSignature(String date, String body) {
        String accessToken = ACCESS_TOKEN;
        if (System.currentTimeMillis() > TOKEN_EXPIRE_TIME)
        {
            synchronized ("ACCESS_TOKEN") {
                if (System.currentTimeMillis() > TOKEN_EXPIRE_TIME)
                {
                    refreshToken();
                }
            }
        }
        if (accessToken == null) {
            accessToken = ACCESS_TOKEN;
        }

        return Base64.encode(DigestUtil.md5Hex(body + date + accessToken).getBytes());
    }
}
