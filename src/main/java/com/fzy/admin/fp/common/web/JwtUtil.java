package com.fzy.admin.fp.common.web;

import cn.hutool.core.codec.Base64;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;

/**
 * @author Created by zk on 2019-07-04 11:05
 * @description
 */
public class JwtUtil {

    public static String getPayloadProperty(String token, String property) {
        return getPayloadMap(token).get(property).toString();
    }

    public static Map<String, Object> getPayloadMap(String token) {
        if (ParamUtil.isBlank(token)) {
            throw new BaseException("缺失token,请重新登录", Resp.Status.TOKEN_ERROR.getCode());
        }
        String[] tokenParts = token.split("\\.");
        if (tokenParts.length != 3) {
            throw new BaseException("token格式错误,请重新登录", Resp.Status.TOKEN_ERROR.getCode());
        }
        String payloadStr = tokenParts[1];
        String payloadJson = Base64.decodeStr(payloadStr);
        return JacksonUtil.toObjectMap(payloadJson);
    }

    public static String copyToken(String token, int tokenExpiration, String key) {
        return copyToken(getPayloadMap(token), tokenExpiration, key);
    }

    /**
     * @author Created by zk on 2019/7/4 11:07
     * @Description 负载列表，token有效时间（分钟），秘钥
     */
    public static String copyToken(Map<String, Object> payloadMap, int tokenExpiration, String key) {
        final JWTCreator.Builder builder = JWT.create();
        for (String s : payloadMap.keySet()) {
            builder.withClaim(s, payloadMap.get(s).toString());
        }
        builder.withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000));
        return builder.sign(Algorithm.HMAC512(key));
    }

    public static DecodedJWT verifier(String token, String key) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(key)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new BaseException("token校验有误,请重新登录", Resp.Status.TOKEN_ERROR.getCode());
        }
        return jwt;
    }
}
