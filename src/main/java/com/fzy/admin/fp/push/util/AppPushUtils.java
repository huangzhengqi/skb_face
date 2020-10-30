package com.fzy.admin.fp.push.util;

import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/9/29 16:15
 * @Version 1.0
 * @description
 */
public class AppPushUtils {
    /**
     * 设置透传模板
     * 1. appId
     * 2. appKey
     * 3. 要传送到客户端的 msg
     * 3.1 标题栏：key = title,
     * 3.2 通知栏内容： key = titleText,
     * 3.3 穿透内容：key = transText
     */
    public static TransmissionTemplate getNotifacationTemplate(String appId, String appKey, Map<String, String> msg, Integer platForm) {
        // 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用(通知模板)
//        NotificationTemplate template = new NotificationTemplate();
        //穿透模板
        TransmissionTemplate template = new TransmissionTemplate();
        // 设置appid，appkey
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 穿透消息设置为，1 强制启动应用
        template.setTransmissionType(1);
        // 设置穿透内容
        template.setTransmissionContent(msg.get("transText"));
        //安卓消息推送
        Notify notify = new Notify();
        if (platForm.equals(Integer.valueOf(1))) {
            // 设置
            notify.setTitle(msg.get("title"));
            notify.setContent(msg.get("titleText"));
        } else if (platForm.equals(Integer.valueOf(2))) {
            //ios消息推送
            template.setAPNInfo(getAPNPayload());
        }else {
            notify.setTitle(msg.get("title"));
            notify.setContent(msg.get("titleText"));
            template.setAPNInfo(getAPNPayload());
        }
        //设置第三方通知
        template.set3rdNotifyInfo(notify);
        return template;
    }

    public static APNPayload getAPNPayload() {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(0);
        //ios 12.0 以上可以使用 Dictionary 类型的 sound
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        payload.addCustomMsg("由客户自定义消息key", "由客户自定义消息value");
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
        //设置语音播报类型，int类型，0.不可用 1.播放body 2.播放自定义文本
        payload.setVoicePlayType(2);
        //设置语音播报内容，String类型，非必须参数，用户自定义播放内容，仅在voicePlayMessage=2时生效
        //注：当"定义类型"=2, "定义内容"为空时则忽略不播放
        payload.setVoicePlayMessage("定义内容");
        // 添加多媒体资源
        payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.pic)
                .setResUrl("资源文件地址")
                .setOnlyWifi(true));
        return payload;
    }

    public static Style0 getStyle0(Map<String, String> msg) {
        // 设置style
        Style0 style = new Style0();
        // 设置通知栏标题和内容
        style.setTitle(msg.get("title"));
        style.setText(msg.get("titleText"));
        // 设置通知，响铃、震动、可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        return style;
    }
}
