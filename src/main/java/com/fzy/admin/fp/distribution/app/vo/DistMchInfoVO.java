package com.fzy.admin.fp.distribution.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

//普通分销进件vo
@Data
public class DistMchInfoVO {

    @ApiModelProperty(value = "进件id")
    private String id;

    @ApiModelProperty(value = "经营者身份证人像面")
    private String epresentativePhotoId;

    @ApiModelProperty(value = "经营者身份证国徽面")
    private String epresentativePhotoId2;

    @ApiModelProperty(value = "经营者/法人姓名")
    private String representativeName;

    @ApiModelProperty(value = "身份证号码")
    private String certificateNum;

    @ApiModelProperty(value = "身份证开始有效期间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startCertificateTime;

    @ApiModelProperty(value = "身份证结束证件期限")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endCertificateTime;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "营业执照照片")
    private String businessLicensePhotoId;

    @ApiModelProperty(value = "商户类型 小微（SUBJECT_TYPE_XIAOWEI） 个体工商户（SUBJECT_TYPE_INDIVIDUAL） 企业（SUBJECT_TYPE_ENTERPRISE） 下拉框选项  ")
    private String subjectType;

    @ApiModelProperty(value = "营业执照注册号")
    private String license;

    @ApiModelProperty(value = "商户全称")
    private String merchantName;

    @ApiModelProperty(value = "门头照片")
    private String doorPhoto;

    @ApiModelProperty(value = "店内照片")
    private String storePhoto;

    @ApiModelProperty(value = "商户简称")
    private String shortName;

    @ApiModelProperty(value = "门店省市区")
    private String storeAddress;

    @ApiModelProperty(value = "经营地址")
    private String registerAddress;

    @ApiModelProperty(value = "客服电话")
    private String cusServiceTel;

    @ApiModelProperty("银行卡卡号面")
    private String bankCardNumber;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "开户名称")
    private String accountHolder;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String accountNumber;

    @ApiModelProperty(value = "开户银行城市 xx省/xx市/xx区")
    private String bankCity;

    @ApiModelProperty(value = "开户支行")
    private String bankOutlet;

    @ApiModelProperty(value = "联系人姓名")
    private String contact;
}
