package com.fzy.admin.fp.pay.pay.util;

import com.alipay.api.internal.util.StringUtils;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author: huxiangqiang
 * @since: 2019/7/23
 */
public class SignUtils {
    public String getTimestamp() {
        //生成时间戳

        long timestampLong = System.currentTimeMillis();

        String timestampStr = String.valueOf(timestampLong);

        return timestampStr;
    }

    //类似微信接口的签名生成方法
    public static String createSign(Map<String, String> params, String privateKey) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().trim();
            if (!StringUtils.isEmpty(value)) {
                sb.append("&").append(key).append("=").append(value);
            }

        }
        String stringA = sb.toString().replaceFirst("&", "");

        String stringSignTemp = stringA + "&" + "key=" + privateKey;
        //将签名使用MD5加密并全部字母变为大写
        String signValue = ParamUtil.md5(stringSignTemp).toUpperCase();
        return signValue;
    }
}
