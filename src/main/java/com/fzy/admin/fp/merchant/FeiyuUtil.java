package com.fzy.admin.fp.merchant;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author: huxiangqiang
 * @since: 2019/8/9
 */
@Slf4j
public class FeiyuUtil {

    public static final String URL = "https://open.gzfyit.com/iot-cloud/v1/third/send";
    public static final String APPSECRET = "f9436818a31b478e91a0f5abd29d8eed";
    public static final String APPKEY = "25451076";


    /**
     * 推送语音到对应设备
     *
     * @param device    设备id
     * @param price     金额 单位 分
     * @param priceType 收款类型 0通用 1支付宝 2微信
     */
    public static void sendVoice(String device, String price, String priceType) {
        try {
            String str = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            String type = "1";
            Map<String, String> map = new HashMap<>();
            map.put("appKey", APPKEY);
            map.put("device", device);
            map.put("str", str);
            map.put("type", type);
            map.put("price", price);
            map.put("priceType", priceType);

            String sign = sign(map, APPSECRET);
            map.put("sign", sign);
            System.out.println(map);
            String result = HttpRequest.post(URL).body(JSONUtil.toJsonStr(map), "application/json").execute().body();
            log.info("云喇叭推送：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("云喇叭推送：失败");
        }
    }

    /**
     * 定义语音 当前设备不支持
     * @param device
     * @param context
     */
    public static void sendVoice(String device, String context) {
        try {
            String str = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            String type = "3";
            Map<String, String> map = new HashMap<>();
            map.put("appKey", APPKEY);
            map.put("device", device);
            map.put("str", str);
            map.put("type", type);
            map.put("context", context);

            String sign = sign(map, APPSECRET);
            map.put("sign", sign);
            System.out.println(map);
            String result = HttpRequest.post(URL).body(JSONUtil.toJsonStr(map), "application/json").execute().body();
            log.info("云喇叭推送：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("云喇叭推送：失败");
        }
    }


    /**
     * 生成签名
     *
     * @param map
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> map, String secret) {
        List<String> keys = new ArrayList<>(map.keySet());
        //字典排序
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        //拼接字符串
        for (String key : keys) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        String signString = sb.toString();
        //去除最后一个'&'
        signString = StringUtils.substring(signString, 0, signString.length() - 1);
        //MD5 加密并转成大写
        return DigestUtils.md5Hex(signString + secret).toUpperCase();
    }
}
