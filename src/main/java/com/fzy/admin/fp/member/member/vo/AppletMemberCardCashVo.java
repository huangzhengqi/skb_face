package com.fzy.admin.fp.member.member.vo;

import lombok.Data;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:50 2019/6/11
 * @ Description:会员卡同步到微信卡包数据缓存
 **/
@Data
public class AppletMemberCardCashVo {

    private String cardId;
    private String apiTicket;
    private String nonceStr;
    private String timestamp;
    private String signature;
}
