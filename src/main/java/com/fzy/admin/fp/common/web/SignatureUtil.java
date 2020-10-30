package com.fzy.admin.fp.common.web;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SignatureUtil {

    /**
     * 对一个参数列表签名
     *
     * @param paramMap,参数列表
     * @param keyName,签名秘钥参数名
     * @param key,签名秘钥
     */
    public static void paramSign(Map<String, String> paramMap, String keyName, String key) {
        StringBuilder paramBuilder = new StringBuilder();
        paramMap.forEach((k, v) -> paramBuilder.append(k).append("=").append(v).append("&"));
        if (!"".equals(keyName)) {// 在参数后附带上签名秘钥
            paramBuilder.append(keyName).append("=").append(key);
        }
        String paramStr = paramBuilder.toString();
        log.debug("signature param string -> " + paramStr);
        String paramSignature = ParamUtil.md5(paramStr);
        log.debug("signature result string -> " + paramSignature);
        paramMap.put("signature", paramSignature);

    }


    // 将map转化为字符串参数
    public static String map2param(Map<String, String> paramMap) {
        StringBuilder paramBuilder = new StringBuilder();
        paramMap.forEach((k, v) -> {
            paramBuilder.append(k).append("=").append(v).append("&");
        });

        return paramBuilder.substring(0, paramBuilder.length() - 1);
    }

}
