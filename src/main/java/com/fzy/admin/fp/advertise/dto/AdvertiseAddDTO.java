package com.fzy.admin.fp.advertise.dto;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 添加广告 入参
 */
@Data
public class AdvertiseAddDTO {

    @NotNull(message = "广告标题不能为空")
    @ApiModelProperty(value = "广告标题")
    private String title;

    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "视频/图片")
    private Integer selectSt; //(0图片  1视频)

    @ApiModelProperty(value = "广告图片跳转链接")
    private String imageLink;

    @NotNull(message = "投放对象不能为空")
    @ApiModelProperty(value = "广告类型 1商户 2客户 3付款设备")
    private Integer targetType;

    @NotNull(message = "投放范围不能为空")
    @ApiModelProperty("1平台所有商户 2一级代理所有商户 3二级代理所有商户 4指定商户 5指定城市（1-5,15,16 商户app首页广告）6客户小程序 7（小程序）客户付完款页面" +
            "8（设备）启动页 9(设备)收银页 10(设备)支付成功页 11(app)商户收款后广告 12(设备)非会员支付页 13收押金页成功 14退押金成功 15三级代理商所有商户 16服务商所有商户 17指定商户类型")
    private Integer targetRange;


    @ApiModelProperty("生效日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date beginTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;


    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "目标id")
    private String[] targetIds;

    @ApiModelProperty(value = "投放城市id")
    private String[] cityIds;

    @ApiModelProperty(value = "是否跳转 1是 0不是")
    private Integer isJump;

    @ApiModelProperty(value = "商户类型")
    private String[] metType;

    @ApiModelProperty("是否默认广告 1默认广告 0不是")
    @Column(columnDefinition = "int(1) default 0")
    private Integer type;

    @ApiModelProperty("是否跳转 1是 0不是")
    @Column(columnDefinition = "int(1) default 0")
    private Integer adType;

    @ApiModelProperty("付款设备 0全部 1支付宝 2微信")
    private Integer deviceType;

    public void setBeginTime(Date startTime) {
        this.beginTime = DateUtil.beginOfDay(startTime).toJdkDate();
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateUtil.endOfDay(endTime).toJdkDate();
    }

}
