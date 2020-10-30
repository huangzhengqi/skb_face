package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxPayFaceAuthInfoVO {

    @ApiModelProperty(value = "错误码 成功：SUCCESS  ERROR/PARAM_ERROR/SYSTEMERROR")
    private String returnCode;

    @ApiModelProperty(value = "对错误码的描述")
    private String returnMsg;

    @ApiModelProperty(value = "SDK调用凭证")
    private String authInfo;

    @ApiModelProperty(value = "authinfo的有效时间")
    private Integer expiresIn;

}
