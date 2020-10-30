package com.fzy.admin.fp.wx.util;

import com.fzy.admin.fp.wx.domain.TextMessage;

import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/8/27 20:03
 * @Version 1.0
 * @description 消息处理工具类
 */
public class MessageUtil {

    /**
     * 消息类型--事件
     */
    public static final String MSGTYPE_EVENT = "event";

    /**
     * 消息事件类型--订阅事件
     */
    public static final String MESSAGE_SUBSCIBE = "subscribe";

    /**
     * 消息事件类型--取消订阅事件
     */
    public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";

    /**
     * 消息类型--文本消息
     */
    public static final String MESSAGE_TEXT = "text";

    /**
     * 消息类型--卡券通过审核
     */
    public static final String MESSAGE_CARD_PASS = "card_pass_check";

    /**
     * 消息类型--卡券未通过审核
     */
    public static final String MESSAGE_CARD_NOT_PASS = "card_not_pass_check";

    /**
     * 消息类型--用户领取卡券
     */
    public static final String MESSAGE_USER_GET_CAR = "user_get_card";


    /**
     * 组装文本消息
     * @param toUserName
     * @param fromUserName
     * @param content
     * @param map
     * @return
     */
    public static String textMsg(String toUserName, String fromUserName, String content, Map<String,String> map){
        TextMessage text = new TextMessage();
        //注意这里的toUserName 是刚才接收xml中的FromUserName
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(Long.valueOf(map.get("CreateTime")));
        text.setContent(content);
        text.setMsgId(Long.valueOf(System.currentTimeMillis()).toString());
        return XmlUtil.textMsgToxml(text);
    }
}
