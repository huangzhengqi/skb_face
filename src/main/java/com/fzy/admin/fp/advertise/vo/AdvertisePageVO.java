package com.fzy.admin.fp.advertise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 广告管理
 */
@Data
public class AdvertisePageVO {
    //id, title, target_range, target_type, status, create_time

    @ApiModelProperty(value = "id")
    private String id;

    @CreatedDate
    @ApiModelProperty(value = "广告结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @CreatedDate
    @ApiModelProperty(value = "广告生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty(value = "广告标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "视频/图片")
    private Integer selectSt; //(0图片  1视频)

    @NotNull(message = "投放对象不能为空")
    @ApiModelProperty(value = "广告类型 1商户 2客户 3付款设备")
    private Integer targetType;

    @ApiModelProperty(value = "投放范围不能为空 1平台所有商户 2一级代理所有商户 3二级代理所有商户 " +
            "4指定商户 5指定城市 6客户小程序 7客户付完款页面 8启动页 9会员支付页 10支付成功页")
    private Integer targetRange;

    @ApiModelProperty(value = "状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "点击数")
    private Integer clickNum;

    @ApiModelProperty(value = "曝光数")
    private Integer exposureNum;

    @ApiModelProperty("是否默认广告 1默认广告 0不是")
    private Integer type;

    @ApiModelProperty("设备类型 1支付宝 2微信")
    private Integer deviceType;

    public AdvertisePageVO() {

    }

    public AdvertisePageVO(String id, String title,  Integer targetType, Integer targetRange,Integer status, Date beginTime, Date endTime,  String imageUrl, Integer selectSt, Integer type, Integer deviceType) {
        this.id = id;
        this.title = title;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.status = status;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
        this.selectSt = selectSt;
        this.type = type;
        this.deviceType = deviceType;
    }
}
