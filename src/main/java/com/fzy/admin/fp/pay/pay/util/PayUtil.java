package com.fzy.admin.fp.pay.pay.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-04-25 14:39
 * @description 支付工具类
 */
@Slf4j
public class PayUtil {


    /**
     * @author Created by wtl on 2019/4/25 14:42
     * @Description 支付签名
     */
    public static String sign(Map<String, Object> paramMap, String appKey) {
        StringBuilder sb = createSignStr(paramMap);
        sb.append("key=").append(appKey);
        String sign = ParamUtil.md5(sb.toString()).toLowerCase();
        log.info("支付签名，{}", sign);
        return sign;
    }

    /**
     * @author Created by wtl on 2019/5/16 15:31
     * @Description 易融码签名算法
     */
    public static String yrmSign(Map<String, ?> paramMap, String appKey) {
        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            log.info(key + "{}" + paramMap.get(key));
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        sb.append("key=").append(appKey);
        log.info("sign-data,{}", sb.toString());
        String sign = ParamUtil.md5(sb.toString()).toUpperCase();
        log.info("支付签名，{}", sign);
        return sign;
    }

    /**
     * @author Created by wtl on 2019/5/30 18:31
     * @Description 统统收签名算法
     */
    public static String ttsSign(Map<String, Object> paramMap, String privateKey) {
        StringBuilder sb = createSignStr(paramMap);
        // 去掉末尾的符号
        String str = sb.toString();
        str = str.substring(0, str.lastIndexOf("&"));
        log.info("待签名数据，{}", str);
        byte[] data = str.getBytes();
        // 私钥签名
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);
        //签名
        byte[] signed = sign.sign(data);

        return Base64.encode(signed);
    }

    /**
     * @author Created by wtl on 2019/5/30 19:31
     * @Description 统统收签名校验
     */
    public static boolean ttsVerify(Map<String, Object> paramMap, String signature, String publicKey) {
        paramMap.remove("signature");
        // 签名数据转成treeMap排序
        Map<String, Object> treeMap = new TreeMap<>();
        for (String s : paramMap.keySet()) {
            treeMap.put(s, paramMap.get(s));
        }
        StringBuilder sb = createSignStr(treeMap);

        // 去掉末尾的符号
        String str = sb.toString();
        str = str.substring(0, str.lastIndexOf("&"));
        System.err.println("校验字符串：" + str);
        byte[] data = str.getBytes();
        //验证签名
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey);
        // 要验证的签名
        byte[] signed = Base64.decode(signature);
        boolean flag = sign.verify(data, signed);
        log.info("签名校验结果，{}", flag);
        return flag;
    }

    private static StringBuilder createSignStr(Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            log.info(key + "{}" + paramMap.get(key));
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return sb;
    }


}
