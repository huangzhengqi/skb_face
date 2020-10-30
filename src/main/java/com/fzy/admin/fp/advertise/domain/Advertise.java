package com.fzy.admin.fp.advertise.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 广告管理
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_advertise")
public class Advertise extends CompanyBaseEntity {


    /**
     * 广告名称
     */
    @NotNull(message = "广告标题不能为空")
    @ApiModelProperty(value = "广告标题")
    private String title;

    /**
     * 广告图片Id
     */
    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "视频/图片")
    private Integer selectSt; //(0图片  1视频)

    /**
     * 广告图片跳转链接
     */
    @ApiModelProperty(value = "广告图片跳转链接")
    private String imageLink;


    /**
     * 广告类型 1商户 2客户
     */
    @NotNull(message = "投放对象不能为空")
    @ApiModelProperty(value = "广告类型 1商户 2客户 3付款设备")
    private Integer targetType;

    @Getter
    @AllArgsConstructor
    /**
     * 广告类型枚举 1商户 2客户
     */
    public enum TargetType {
        MERCHANT(1, "商户"),
        CUSTOM(2, "客户");
        private Integer code;
        private String message;
    }

    /**
     * 投放范围
     */
    @ApiModelProperty(value = "投放范围不能为空 1平台所有商户 2一级代理所有商户 3二级代理所有商户 " +
            "4指定商户 5指定城市 6客户小程序 7客户付完款页面 8启动页 9会员支付页 10支付成功页")
    @NotNull(message = "投放范围不能为空")
    private Integer targetRange;

    /**
     * 投放范围
     */
    @Getter
    @AllArgsConstructor
    public enum TargetRange {
        ALL_MERCHANT(1, "平台所有商户"),
        FIRST_AGNET(2, "一级代理所有商户"),
        SECOND_AGNET(3, "二级代理所有商户"),
        SOME_MERCHANT(4, "指定商户"),
        SOME_TITY(5, "指定城市"),
        APPLET(6, "客户小程序"),
        AFTER_PAY(7, "客户付完款页面");
        private Integer code;
        private String message;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    private Date beginTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;


    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

    @ApiModelProperty(value = "状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "是否跳转 1是 0不是")
    private Integer isJump;

    @ApiModelProperty(value= "商户类型")
    private String metType;

    @ApiModelProperty("是否默认广告 1默认广告 0不是")
    @Column(columnDefinition = "int(1) default 0")
    private Integer type;

    @ApiModelProperty("是否跳转 1是 0不是")
    @Column(columnDefinition = "int(1) default 0")
    private Integer adType;

    @ApiModelProperty("设备类型 1支付宝 2微信")
    private Integer deviceType;

    @ApiModelProperty("广告群组id")
    private String groupId;

    public Advertise(){

    }

    public Advertise(String title, String imageUrl, Integer selectSt, String imageLink, Integer targetType, Integer targetRange, Date beginTime, Date endTime, String companyId, Integer status, Integer isJump, String metType, Integer type, Integer adType,Integer deviceType) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.selectSt = selectSt;
        this.imageLink = imageLink;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.companyId = companyId;
        this.status = status;
        this.isJump = isJump;
        this.metType = metType;
        this.type = type;
        this.adType = adType;
        this.deviceType = deviceType;
    }
}
