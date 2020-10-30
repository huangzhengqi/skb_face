package com.fzy.admin.fp.member.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-23 15:07
 * @description
 */
@Data
public class AppMemberVO {
    private String id;
    private String phone;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    private Date lastPayDate;
    private String head;
    private String nickname;
}
