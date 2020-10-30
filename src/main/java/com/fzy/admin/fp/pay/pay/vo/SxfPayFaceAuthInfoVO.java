package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Getter
@Setter
public class SxfPayFaceAuthInfoVO {

    @ApiModelProperty(value="错误码 成功：SUCCESS  ERROR/PARAM_ERROR/SYSTEMERROR")
    private String returnCode;

    @ApiModelProperty(value="对错误码的描述")
    private String returnMsg;

    @ApiModelProperty(value="子商户号")
    private String subMchId;

    @ApiModelProperty(value="SDK调用凭证")
    private String authInfo;

    @ApiModelProperty(value="authinfo的有效时间")
    private Integer expiresIn;

    @ApiModelProperty(value="subAppid")
    private String subAppid;

    @ApiModelProperty(value="subOpednId")
    private String subOpednId;

    public SxfPayFaceAuthInfoVO(String returnCode, String returnMsg, String authInfo, Integer expiresIn) {
        this.returnCode=returnCode;
        this.returnMsg=returnMsg;
        this.authInfo=authInfo;
        this.expiresIn=expiresIn;
    }

    public SxfPayFaceAuthInfoVO(String returnCode, String returnMsg, String subMchId, String authInfo, Integer expiresIn) {
        this.returnCode=returnCode;
        this.returnMsg=returnMsg;
        this.subMchId=subMchId;
        this.authInfo=authInfo;
        this.expiresIn=expiresIn;
    }

    public SxfPayFaceAuthInfoVO(String subOpednId) {
        this.subOpednId=subOpednId;
    }
}
