package com.fzy.admin.fp.merchant.app.dto;

import com.fzy.admin.fp.advertise.domain.Advertise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoginInfoDTO {
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户类型 1：商户 2店长 3员工")
    private String userType;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("门店id")
    private String store_id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户登录账号/手机号")
    private String phone;

    @ApiModelProperty("商户名")
    private String merchantName;

    @ApiModelProperty("商户头像")
    private String merchantIcon;

    @ApiModelProperty("服务商id")
    private String serviceId;

    @ApiModelProperty("微信子商户商户id")
    private String wx_sub_mch_id;

    @ApiModelProperty("微信子商户appid")
    private String wx_sub_appid;

    @ApiModelProperty("微信服务商appid")
    private String wx_appid;

    @ApiModelProperty("微信服务商商户id")
    private String wx_mch_id;

    @ApiModelProperty("支付宝appId")
    private String ali_app_id;

    @ApiModelProperty("支付宝秘钥")
    private String ali_private_key;

    @ApiModelProperty("支付宝服务商 pid")
    private String ali_partner_id;

    @ApiModelProperty("支付宝商户 pid")
    private String ali_pid;

    private String domain;

    @ApiModelProperty("设备号")
    private String equipmentId;

    @ApiModelProperty("页面按钮 1收银 2收银+押金 3收银+结算 4收银+押金+结算")
    private Integer buttonType;

    @ApiModelProperty("客服电话")
    private String servicePhone;

    @ApiModelProperty("设备广告列表")
    private List<Advertise> advertiseList;

    @ApiModelProperty("广告类型")
    private Boolean advType;

    private String videoUrl;
}
