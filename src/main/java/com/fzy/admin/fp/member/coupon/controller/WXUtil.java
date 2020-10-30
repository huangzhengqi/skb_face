package com.fzy.admin.fp.member.coupon.controller;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.member.dto.MemberCardDTO;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.spring.web.json.Json;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author lb
 * @date 2019/6/1 14:16
 * @Description 微信卡包工具类
 */
@Slf4j
public class WXUtil {

    //private static final String APPID="wx3703bd27d5d4d848";//"wx3703bd27d5d4d848"
    //private static final String APPSECRET="f58dcd6e014c179b80ee69ac80f669f8";//"f58dcd6e014c179b80ee69ac80f669f8"
    /**
     * 获取token的url
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 存access_token的信息集合
     */
    private static Map<String, String> ACCESS_TOKENS = null;
    /**
     * access_token信息
     */
    private static String ACCESS_TOKEN = null;
    /**
     * 票码信息
     */
    private static Map<String, String> TICKETS = null;

    private final static String ERRCODE = "errcode";
    private final static String ERROR = "error";
    private final static String ERRMSG = "errmsg";

    public static Map<String, String> doGet(String url) {
        System.out.println("-----------向微信请求token-------------");
        System.out.println(url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println("微信响应数据===" + result);
                ACCESS_TOKENS = JacksonUtil.toStringMap(result);
                ACCESS_TOKENS.put("currentTime", String.valueOf(System.currentTimeMillis()));
                ACCESS_TOKEN = ACCESS_TOKENS.get("access_token");
            }
            System.out.println("获取到的AccessToken------->>>" + ACCESS_TOKEN);
            return ACCESS_TOKENS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ACCESS_TOKENS;
    }

    //获取token
    public static String getAccessToken(String appId, String appSecret) {
        //如果token有值没过期不发送请求
        System.out.println("----------获取token中-----------");
        if (ACCESS_TOKEN != null) {
            if ((Long.valueOf(ACCESS_TOKENS.get("currentTime")) + 7000000) > System.currentTimeMillis()) {
                System.out.println("未过期不发送");
                return ACCESS_TOKEN;
            }
        }
        String url = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        try {
            doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ACCESS_TOKEN;
    }

    /**
     * 上传卡券logo得到返回集合里有url
     *
     * @param pathId
     * @return
     */
    public static String upLoadImage(String pathId) {
        String imageURL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
        imageURL = imageURL.replace("ACCESS_TOKEN", ACCESS_TOKEN);
        File file2 = new File(pathId);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(imageURL);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", file2);
        multipartEntityBuilder.addTextBody("comment", "this is comment");
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        String result = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity1 = httpResponse.getEntity();
            if (httpEntity1 != null) {
                JSONObject parse = (JSONObject) JSONObject.parse(EntityUtils.toString(httpEntity1));
                result = parse.get("url").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建代金券
     *
     * @param merchant
     * @param coupon
     * @param merchantName
     * @param logoUrl
     * @return
     */
    public static Map<String, String> createCoupon(Merchant merchant, Coupon coupon, String merchantName, String logoUrl) {

        String url = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", ACCESS_TOKEN);
        //date_info模块5
        Map<String, Object> dateInfo = new HashMap<>();
        //卡券生效类型
        if (coupon.getValidType() == 0) {
            dateInfo.put("type", "DATE_TYPE_FIX_TERM");
            dateInfo.put("fixed_term", coupon.getClaimedTime());
            dateInfo.put("fixed_begin_term", 0);
        } else {
            dateInfo.put("type", "DATE_TYPE_FIX_TIME_RANGE");
            Date date = coupon.getValidTimeStart();
            Date date1 = coupon.getValidTimeEnd();
            Long start = date.getTime() / 1000;
            Long end = date1.getTime() / 1000;
            System.out.println(start + "----" + end);
            dateInfo.put("begin_timestamp", start);
            dateInfo.put("end_timestamp", end);
        }

        //sku模块5
        Map<String, Object> sku = new HashMap<>();
        sku.put("quantity", coupon.getTotalInventory());
        //base_info摸块4
        Map<String, Object> baseInfo = new HashMap<>();
        baseInfo.put("logo_url", logoUrl);
        baseInfo.put("brand_name", merchantName);
        baseInfo.put("code_type", "CODE_TYPE_QRCODE");
        baseInfo.put("title", coupon.getTitle());
        baseInfo.put("color", coupon.getWxColor());
        baseInfo.put("notice", "使用时向服务员出示此券");
        baseInfo.put("service_phone", merchant.getPhone());
        baseInfo.put("description", coupon.getRemark());
        baseInfo.put("date_info", dateInfo);
        baseInfo.put("sku", sku);
        baseInfo.put("get_limit", coupon.getClaimUpperLimit());
        baseInfo.put("bind_openid", false);
        baseInfo.put("can_share", true);
        baseInfo.put("can_give_friend", true);
        //自定义code码
        baseInfo.put("use_custom_code", true);
        baseInfo.put("center_title", "立即使用");
        //cash模块3
        Map<String, Object> cash = new HashMap<>();
        cash.put("base_info", baseInfo);
        int least = coupon.getMiniExpendLimit().multiply(new BigDecimal(100)).intValue();
        int reduce = coupon.getMoney().multiply(new BigDecimal(100)).intValue();
        cash.put("least_cost", least);
        cash.put("reduce_cost", reduce);
        //card模块2  第二层
        Map<String, Object> card = new HashMap<>();
        //代金券
        card.put("card_type", "CASH");
        card.put("cash", cash);
        //初始模块1
        Map<String, Object> map = new HashMap<>();
        map.put("card", card);
        String cardJson = JacksonUtil.toJson(map);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        Map<String, String> cardId = null;
        try {
            httpPost.setEntity(new StringEntity(cardJson, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            log.info("微信代金券返回值：================> {}", result);
            cardId = JacksonUtil.toStringMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardId;
    }


    //获取API_Ticket
    public static String getApiTicket(String appId, String appSecret) {

        String api_ticket = null;
        if (TICKETS == null) {
            doGetTicket(appId, appSecret);
            api_ticket = TICKETS.get("ticket");
        }
        if (TICKETS != null) {
            if ((Long.valueOf(TICKETS.get("currentTime")) + 7000000) < System.currentTimeMillis()) {
                doGetTicket(appId, appSecret);
            }
            api_ticket = TICKETS.get("ticket");
        }

        return api_ticket;
    }

    //发送请求 存活时间7200
    public static void doGetTicket(String appId, String appSecret) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
        ACCESS_TOKEN = getAccessToken(appId, appSecret);//过期判断
        url = url.replace("ACCESS_TOKEN", ACCESS_TOKEN);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                TICKETS = JacksonUtil.toStringMap(result);
                TICKETS.put("currentTime", String.valueOf(System.currentTimeMillis()));
                System.out.println("TICKETS:------->" + TICKETS);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException("获取TICKETS失败", Resp.Status.PARAM_ERROR.getCode());
        }
    }

    //签名自定义code码五要素
    public static String getSignature(String code, String timestamp, String api_ticket, String nonce_str, String card_id) {

        String[] strings = new String[]{code, timestamp, api_ticket, nonce_str, card_id};
        Arrays.sort(strings);
        StringBuffer sb = new StringBuffer();
        for (String string : strings) {
            sb.append(string);
        }
        String sign = sb.toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] b = sign.getBytes();
            md.update(b);
            byte[] b2 = md.digest();
            int len = b2.length;
            String str = "0123456789abcdef";
            char[] ch = str.toCharArray();
            char[] chs = new char[len * 2];
            for (int i = 0, k = 0; i < len; i++) {
                byte b3 = b2[i];
                chs[k++] = ch[b3 >>> 4 & 0xf];
                chs[k++] = ch[b3 & 0xf];
            }
            String signature = new String(chs);
            System.out.println(signature);
            return signature;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "未获得签名";
    }

    //核销接口预处理
    public static String checkStatus(String cardId, String code, String appId, String appSecret) {

        ACCESS_TOKEN = getAccessToken(appId, appSecret);//过期判断
        String checkURL = "https://api.weixin.qq.com/card/code/get?access_token=TOKEN";
        checkURL = checkURL.replace("TOKEN", ACCESS_TOKEN);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(checkURL);
        Map<String, Object> map = new HashMap<>();
        map.put("card_id", cardId);
        map.put("code", code);
        map.put("check_consume", true);
        String maps = JacksonUtil.toJson(map);
        try {
            httpPost.setEntity(new StringEntity(maps));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println("----->>>>>" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //核销卡券
    public static String destoryCoupon(String cardId, String code, String appId, String appSecret) {
        ACCESS_TOKEN = getAccessToken(appId, appSecret);
        String deatoryURL = "https://api.weixin.qq.com/card/code/consume?access_token=TOKEN";
        deatoryURL = deatoryURL.replace("TOKEN", ACCESS_TOKEN);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(deatoryURL);
        Map<String, Object> map = new HashMap<>();
        map.put("card_id", cardId);
        map.put("code", code);
        String maps = JacksonUtil.toJson(map);
        try {
            httpPost.setEntity(new StringEntity(maps));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println("---->>>>>" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //解码接口
    public static String getDecode(String encryptCode, String appId, String appSecret) {
        ACCESS_TOKEN = getAccessToken(appId, appSecret);
        String decodeURL = "https://api.weixin.qq.com/card/code/decrypt?access_token=TOKEN";
        decodeURL = decodeURL.replace("TOKEN", ACCESS_TOKEN);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(decodeURL);
        Map<String, Object> map = new HashMap<>();
        map.put("encrypt_code", encryptCode);
        String maps = JacksonUtil.toJson(map);
        try {
            httpPost.setEntity(new StringEntity(maps));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println("---->>>>" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //模板消息调用
    public static String sendMessage(String appId, String appSecret, String openId, String templateId, String couponName
            , String code, String time) {
        ACCESS_TOKEN = getAccessToken(appId, appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", ACCESS_TOKEN);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        Map<String, Object> map = new HashMap<>();
        map.put("touser", openId);
        map.put("template_id", templateId);
        Map<String, Object> data = new HashMap<>();
        Map<String, String> value1 = new HashMap<>();
        value1.put("value", "您有卡券即将过期");
        Map<String, String> value2 = new HashMap<>();
        value2.put("value", couponName);
        Map<String, String> value3 = new HashMap<>();
        value3.put("value", code);
        Map<String, String> value4 = new HashMap<>();
        value4.put("value", time);
        data.put("first", value1);
        data.put("keyword1", value2);
        data.put("keyword2", value3);
        data.put("keyword3", value4);
        map.put("data", data);
        String maps = JacksonUtil.toJson(map);
        System.out.println("----map集合的内容----->>>>>>>" + maps);
        try {
            httpPost.setEntity(new StringEntity(maps));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println("----->>>>>" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改优惠券库存接口
     *
     * @param type
     * @param number
     * @param cardId
     * @return
     */
    public static String cardModifystock(Integer type, Integer number, String cardId) {
        String url = "https://api.weixin.qq.com/card/modifystock?access_token=TOKEN";
        url = url.replace("TOKEN", ACCESS_TOKEN);

        Map<String, Object> map = new HashMap<>();
        map.put("card_id", cardId);
        if (type.equals(Integer.valueOf(2))) {
            map.put("increase_stock_value", number);
        } else {
            map.put("reduce_stock_value", number);
        }
        String json = JacksonUtil.toJson(map);

        String result = HttpUtil.post(url, json);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        if (resultMap.get("errcode").equals(Integer.valueOf(0)) && resultMap.get("errmsg").equals("ok")) {
            return "SUCCESS";
        }
        return "FAILURE";
    }

    /**
     * 创建会员卡
     *
     * @param token
     * @param name
     * @param accessToken
     * @param logoUrl
     * @param backgroundPicUrl
     * @param memberCardDTO
     * @return
     */
    public static String memberCardCreate(String token, String name, String accessToken, String logoUrl, String backgroundPicUrl, MemberCardDTO memberCardDTO) {
        Map<String, Object> dateInfoMap = new HashMap<>();
        if (memberCardDTO.getValidType() == 0) {
            dateInfoMap.put("type", "DATE_TYPE_FIX_TERM");
            dateInfoMap.put("fixed_term", memberCardDTO.getClaimedTime());
            dateInfoMap.put("fixed_begin_term", 0);
        } else {
            dateInfoMap.put("type", "DATE_TYPE_FIX_TIME_RANGE");
            Date date = memberCardDTO.getValidTimeStart();
            Date date1 = memberCardDTO.getValidTimeEnd();
            Long start = date.getTime() / 1000;
            Long end = date1.getTime() / 1000;
            System.out.println(start + "===================" + end);
            dateInfoMap.put("begin_timestamp", start);
            dateInfoMap.put("end_timestamp", end);
        }
        String prefixUrl = "/pages/index/index";
        String appUserName = accessToken + "@app";
        Map<String, Object> baseInfoMap = new TreeMap<String, Object>();
        baseInfoMap.put("logo_url", logoUrl);
        baseInfoMap.put("code_type", "CODE_TYPE_QRCODE");
        baseInfoMap.put("brand_name", name);
        baseInfoMap.put("title", memberCardDTO.getTitle());
        baseInfoMap.put("color", memberCardDTO.getColor());
        baseInfoMap.put("notice", memberCardDTO.getNotice());
        baseInfoMap.put("description", memberCardDTO.getDescription());
        Map<String, Object> skuMap = new TreeMap<String, Object>();
        skuMap.put("quantity", memberCardDTO.getQuantity());
        baseInfoMap.put("sku", skuMap);
        baseInfoMap.put("date_info", dateInfoMap);
        baseInfoMap.put("get_limit", Integer.valueOf(1));
        baseInfoMap.put("use_custom_code", Boolean.valueOf(false));
        baseInfoMap.put("can_give_friend", Boolean.valueOf(true));
        //自定义跳转外链的入口名字 暂时写死
        baseInfoMap.put("custom_url_name", "刷客宝");
        //自定义跳转的URL
        baseInfoMap.put("custom_url", prefixUrl);
        //自定义使用入口跳转小程序的user_name，格式为原始id+@app
        baseInfoMap.put("custom_app_brand_user_name", appUserName);
        //自定义使用入口小程序页面地址
        baseInfoMap.put("custom_app_brand_pass", prefixUrl);
        //显示在入口右侧的提示语
        baseInfoMap.put("custom_url_sub_title", "刷客宝会员");
        //填写true为用户点击进入会员卡时推送事件，默认为false。详情见 进入会员卡事件推送
        baseInfoMap.put("need_push_on_view", Boolean.valueOf(true));
        Map<String, Object> memberCardMap = new TreeMap<String, Object>();
        memberCardMap.put("background_pic_url", backgroundPicUrl);
        memberCardMap.put("base_info", baseInfoMap);
        memberCardMap.put("prerogative", memberCardDTO.getPrerogative());
        //设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见 自动激活
//        memberCardMap.put("auto_activate", Boolean.valueOf(true));
        //设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。填入该字段后仍需调用接口设置开卡项方可生效，详情见 一键开卡 。
        memberCardMap.put("wx_activate", Boolean.valueOf(true));
        //显示积分，填写true或false，如填写true，积分相关字段均为必 填 若设置为true则后续不可以被关闭。
        memberCardMap.put("supply_bonus", Boolean.valueOf(true));
        //设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段。
        memberCardMap.put("bonus_url", prefixUrl);
        //是否支持储值，填写true或false。如填写true，储值相关字段均为必 填 若设置为true则后续不可以被关闭。该字段须开通储值功能后方可使用， 详情见： 获取特殊权限
        memberCardMap.put("supply_balance", Boolean.valueOf(false));
        //设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段。
        memberCardMap.put("balance_url", prefixUrl);
        memberCardMap.put("balance_rules", "储值说明");
        //自定义会员信息类目，会员卡激活后显示,包含name_type (name) 和url字段
        Map<String, Object> customField1Map = new TreeMap<String, Object>();
        customField1Map.put("name_type", "FIELD_NAME_TYPE_LEVEL");
        customField1Map.put("name", "等级");
        customField1Map.put("url", prefixUrl);
        memberCardMap.put("custom_field1", customField1Map);
        //自定义会员信息类目，会员卡激活后显示，包含name_type(name)和url字段
        Map<String, Object> customField2Map = new TreeMap<String, Object>();
        customField2Map.put("name_type", "FIELD_NAME_TYPE_COUPON");
        customField2Map.put("name", "优惠券");
        customField2Map.put("url", prefixUrl);
        memberCardMap.put("custom_field2", customField2Map);
        Map<String, Object> cardMap = new TreeMap<String, Object>();
        cardMap.put("card_type", "MEMBER_CARD");
        cardMap.put("member_card", memberCardMap);
        Map<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.put("card", cardMap);
        String json = JacksonUtil.toJson(treeMap);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/create?access_token=" + token, json);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        if (resultMap.get("errcode").equals(Integer.valueOf(0)) && resultMap.get("errmsg").equals("ok")) {
            return resultMap.get("card_id").toString();
        }
        return "error";
    }

    public static JSONObject detailMemberCard(String accessToken, String cardId) {
        Map map = new HashMap(2);
        map.put("card_id", cardId);
        String json = JacksonUtil.toJson(map);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/get?access_token=" + accessToken, json);
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        if (resultJson.get(ERRCODE).equals(Integer.valueOf(0)) && resultJson.get(ERRMSG).toString().equals("ok")) {
            JSONObject cardJson = (JSONObject) resultJson.get("card");
            JSONObject memberCardJson = (JSONObject) cardJson.get("member_card");
            JSONObject baseInfoJson = (JSONObject) memberCardJson.get("base_info");
            resultJson.put("status",baseInfoJson.get("status").toString());
            resultJson.put("card_type",cardJson.get("card_type").toString());
            return resultJson;
        }
        return resultJson;
    }

    public static JSONObject cardQrcodeCreate(String accessToken, String cardId) {
       Map cardMap = new HashMap();
       cardMap.put("card_id",cardId);
       cardMap.put("is_unique_code",Boolean.valueOf(false));
       cardMap.put("outer_str","12b");
       Map actionInfoMap = new HashMap(1);
       actionInfoMap.put("card",cardMap);
       Map map = new HashMap();
       map.put("action_name","QR_CARD");
       map.put("action_info",actionInfoMap);
       String json = JacksonUtil.toJson(map);
       String result = HttpUtil.post("https://api.weixin.qq.com/card/qrcode/create?access_token=" + accessToken, json);
       JSONObject resultJson = (JSONObject) JSONObject.parse(result);
       if (resultJson.get(ERRCODE).equals(Integer.valueOf(0)) && resultJson.get(ERRMSG).toString().equals("ok")) {
            return resultJson;
      }
    return resultJson;
    }

    public static JSONObject testwhitelistSet(String accessToken, String openId) {

        Map map = new HashMap(5);
        ArrayList openidList = new ArrayList();
        openidList.add(openId);
        map.put("openid",openidList);
        String json = JacksonUtil.toJson(map);
        log.info("json =============== {}",json);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/testwhitelist/set?access_token=" + accessToken, json);
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        if (resultJson.get(ERRCODE).equals(Integer.valueOf(0)) && resultJson.get(ERRMSG).toString().equals("ok")) {
            return resultJson;
        }
        return resultJson;
    }
}
