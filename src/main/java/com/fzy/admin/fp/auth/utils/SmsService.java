package com.fzy.admin.fp.auth.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.auth.service.SmsMessageService;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.auth.domain.SmsMessage;
import com.fzy.admin.fp.auth.service.SmsMessageService;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 19:57 2019/4/29
 * @ Description:
 **/
@Slf4j
@Service
public class SmsService {

    @Resource
    private SmsMessageService smsMessageService;


    //发送创建用户成功的短信信息
    public boolean sendSmsInfo(String name, String phone, String password) {

        //TODO ID写死为1
        SmsMessage smsMessage = smsMessageService.findOne("1");
        if (ParamUtil.isBlank(smsMessage)) {
            return false;
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", smsMessage.getAccessKeyId(), smsMessage.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        // 2.创建API请求并设置参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 手机号码，国际7205028003的也可以
        request.putQueryParameter("PhoneNumbers", phone); // 0017205028003
        // 短信签名，阿里短信控制台获取，签名中英文通用，中文：鲸鲲，英文：xhandout
        request.putQueryParameter("SignName", smsMessage.getSignName());
        // 模板ID，国内"SMS_156845143"和国际"SMS_157070113"的模板不通用
        request.putQueryParameter("TemplateCode", smsMessage.getSmsInfoTemplateCode());
        // 验证码，模板内的{code}
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("account", phone);
        map.put("password", password);
        String jsonContent = JacksonUtil.toJson(map);
        // 模板参数，json数据格式
        request.putQueryParameter("TemplateParam", jsonContent);
        /*try {
            // 3.发起请求并处理应答或异常
            CommonResponse response = client.getCommonResponse(request);
            // 返回的json数据
            String responseJson = response.getData();
            JSONObject jsonObject = JSONUtil.parseObj(responseJson);
            // 返回的code除了ok都是false
            String result = (String) jsonObject.get("Code");
            if ("ok".equalsIgnoreCase(result)) {
                return true;
            }
            log.error("phone:{},name:{},password:{},jsonContent:{}", phone, password, jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return true;
    }

    //发送短信验证码接口
    public boolean sendSms(String phone, String code) {

        //TODO ID写死为1
        SmsMessage smsMessage = smsMessageService.findOne("1");

        DefaultProfile profile = DefaultProfile.getProfile("default", smsMessage.getAccessKeyId(), smsMessage.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        // 2.创建API请求并设置参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 手机号码，国际7205028003的也可以
        request.putQueryParameter("PhoneNumbers", phone); // 0017205028003
        // 短信签名，阿里短信控制台获取，签名中英文通用，中文：鲸鲲，英文：xhandout
        request.putQueryParameter("SignName", smsMessage.getSignName());
        // 模板ID，国内"SMS_156845143"和国际"SMS_157070113"的模板不通用
        request.putQueryParameter("TemplateCode", smsMessage.getSmsTemplateCode());
        // 验证码，模板内的{code}
        // 验证码，模板内的{code}
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        String jsonContent = JacksonUtil.toJson(map);
        // 模板参数，json数据格式
        request.putQueryParameter("TemplateParam", jsonContent);
        try {
            // 3.发起请求并处理应答或异常
            CommonResponse response = client.getCommonResponse(request);
            // 返回的json数据
            String responseJson = response.getData();
            JSONObject jsonObject = JSONUtil.parseObj(responseJson);
            // 返回的code除了ok都是false
            String result = (String) jsonObject.get("Code");
            log.info("smsSend result：{}", result);
            if ("ok".equalsIgnoreCase(result)) {
                return true;
            }
            log.error("phone:{},jsonContent:{}", phone, jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
