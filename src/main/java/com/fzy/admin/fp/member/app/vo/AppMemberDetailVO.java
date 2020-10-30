package com.fzy.admin.fp.member.app.vo;

import lombok.Data;

/**
 * @author Created by zk on 2019-05-23 15:33
 * @description
 */
@Data
public class AppMemberDetailVO {
    private String id;
    private String nickname;
    private String phone;//电话
    private String head;//头像
    private String memberNum;//会员编号
}
