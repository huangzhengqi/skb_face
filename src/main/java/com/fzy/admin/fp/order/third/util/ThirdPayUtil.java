package com.fzy.admin.fp.order.third.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-06-03 17:37
 * @description 第三方支付接口工具
 */
@Slf4j
public class ThirdPayUtil {

    /**
     * @author Created by wtl on 2019/6/3 19:55
     * @Description 对象参数转成TreeMap
     */
    public static Map<String, Object> createTreeMap(Object model) {
        JSONObject jsonObject = JSONUtil.parseObj(model);
        return new TreeMap<>(jsonObject);
    }


    /**
     * @author Created by wtl on 2019/6/3 19:50
     * @Description 参数签名
     */
    public static String sign(Map<String, Object> paramMap, String key) {
        StringBuilder sb = createSignStr(paramMap);
        sb.append("key=").append(key);
        log.info("待签名字符串，{}", sb.toString());
        String sign = ParamUtil.md5(sb.toString()).toLowerCase();
        log.info("支付签名，{}", sign);
        return sign;
    }

    /**
     * @param paramMap 支付请求参数
     * @param key      商户key
     * @param sign     签名
     * @author Created by wtl on 2019/6/3 19:55
     * @Description 参数校验
     */
    public static boolean verify(Map<String, Object> paramMap, String sign, String key) {
        if (ParamUtil.isBlank(sign)) {
            throw new BaseException("签名为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(key)) {
            throw new BaseException("商户key为空", Resp.Status.PARAM_ERROR.getCode());
        }
        log.info("key,{}", key);
        StringBuilder sb = createSignStr(paramMap);
        sb.append("key=").append(key);
        log.info("待签名字符串，{}", sb.toString());
        String signature = ParamUtil.md5(sb.toString()).toLowerCase();
        log.info("支付签名，{}", signature);
        boolean flag = signature.equals(sign);
        log.info("验签结果，{}", flag);
        return flag;
    }

    private static StringBuilder createSignStr(Map<String, Object> paramMap) {
        paramMap.remove("sign");
        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            log.info(key + "{}" + paramMap.get(key));
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return sb;
    }


}
