package com.fzy.admin.fp.member.member.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;

/**
 * @author Created by wtl on 2019-06-26 16:49
 * @description H5注册会员DTO
 */
@Data
public class WebRegisterDTO {

    private String userId; // 收银员id，H5页面没有token无法获取商户id，除非卡包使用卡券附带

    private String merchantId; // 商户id

    @NotBlank(message = "手机号码不能为空")
    private String phone; // 手机号码

/*    @NotBlank(message = "验证码不能为空")
    private String code; // 验证码*/

    @NotBlank(message = "公众号openid不能为空")
    private String openId; // 公众号openid


}
