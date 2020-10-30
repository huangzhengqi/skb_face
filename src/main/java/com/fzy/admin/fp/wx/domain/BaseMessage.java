package com.fzy.admin.fp.wx.domain;

import lombok.Data;

/**
 * @Author hzq
 * @Date 2020/8/27 20:01
 * @Version 1.0
 * @description
 */
@Data
public class BaseMessage {

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private String MsgType;

}
