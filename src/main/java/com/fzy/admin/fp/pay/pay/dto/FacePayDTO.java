package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FacePayDTO {

    @ApiModelProperty("订单编号")
    private String orderNumber;
    @ApiModelProperty("微信分配的公众账号ID")
    private String appid;
    @ApiModelProperty("微信用户openId")
    private String openId;
    @ApiModelProperty("微信分配的子商户公众账号ID")
    private String subAppid;
    @ApiModelProperty("微信支付分配的商户号")
    private String mchId;
    @ApiModelProperty("微信支付分配的子商户号，开发者模式下必填")
    private String subMchId;
    @ApiModelProperty("商品或支付单简要描述：格式要求：门店品牌店-城市分店名-实际商品名称")
    private String body;
    @ApiModelProperty("金额，单位 分")
    private Integer totalFee;
    @ApiModelProperty("人脸凭证，与付款码二选一")
    private String faceCode;
    @ApiModelProperty("付款码，与人脸凭证二选一")
    private String wxAuthCode;
    @ApiModelProperty("终端ip")
    private String spbillCreateIp;
    @ApiModelProperty("设备id")
    private String equipmentId;

}
