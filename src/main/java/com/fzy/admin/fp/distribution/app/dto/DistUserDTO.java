package com.fzy.admin.fp.distribution.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.Pattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@Data
public class DistUserDTO {
    private String serviceProviderId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("姓名")
    @NotBlank(message = "请填写姓名")
    @Pattern(pattern = "([\\u4e00-\\u9fa5a-zA-Z]{2,7})", message = "用户名应为2-7位长度,英文字母加中文")
    private String name;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("微信号")
    private String wxNum;

    @ApiModelProperty("银行卡")
    private String bankNum;

    @ApiModelProperty("支付宝")
    private String aliNum;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("加入时间")
    private String payTime;

    @ApiModelProperty("邀请码")
    private String newInviteNum;

    @ApiModelProperty("银行卡姓名")
    private String bankName;

    @ApiModelProperty("支付宝姓名")
    private String aliName;

    @ApiModelProperty("状态 0启用 1禁用")
    private String status;

    @ApiModelProperty("等级 0游客 1普通代理 2运营中心")
    private String grade;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("0银行卡 1支付宝")
    private Integer type;

    @ApiModelProperty("开户银行")
    private String depositBank;

}
