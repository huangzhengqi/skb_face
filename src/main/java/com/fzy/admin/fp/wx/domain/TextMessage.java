package com.fzy.admin.fp.wx.domain;

import lombok.Data;

/**
 * @Author hzq
 * @Date 2020/8/27 20:01
 * @Version 1.0
 * @description
 */
@Data
public class TextMessage extends BaseMessage {

    private String Content;

    private String MsgId;
}
