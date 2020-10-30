package com.fzy.admin.fp.advertise.device.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 计划列表返回参数
 */
@Data
public class AdvertiseDevicePageVO {

    @ApiModelProperty(value="id")
    private String id;

    @ApiModelProperty(value="广告标题")
    private String title;

    @ApiModelProperty(value="图片地址")
    private String imageUrl;

    @ApiModelProperty("设备类型0全部 1支付宝 2微信")
    private Integer deviceType;

    @CreatedDate
    @ApiModelProperty(value="广告生效时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @CreatedDate
    @ApiModelProperty(value="广告结束时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value="0启动页 1支付页 2支付成功页")
    private Integer targetRange;

    @NotNull(message="投放对象不能为空")
    @ApiModelProperty(value="广告类型 1指定商户群 2全部商户 3指定商户")
    private Integer targetType;

    @ApiModelProperty(value="曝光数")
    private Integer exposureNum;

    @ApiModelProperty(value="点击数")
    private Integer clickNum;

    @ApiModelProperty(value="状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value="视频/图片")
    private Integer selectSt; //(0图片  1视频)

    @ApiModelProperty("群组id")
    private String gruopId;

    public AdvertiseDevicePageVO() {

    }

    public AdvertiseDevicePageVO(String id, String title, String imageUrl, Integer deviceType, Date beginTime, Date endTime, Integer targetRange, Integer targetType, Integer status) {
        this.id=id;
        this.title=title;
        this.imageUrl=imageUrl;
        this.deviceType=deviceType;
        this.beginTime=beginTime;
        this.endTime=endTime;
        this.targetRange=targetRange;
        this.targetType=targetType;
        this.status=status;
    }

    public AdvertiseDevicePageVO(String id, String title, String imageUrl, Integer deviceType, Date beginTime, Date endTime, Integer targetRange, Integer targetType, Integer status,String gruopId ) {
        this.id=id;
        this.title=title;
        this.imageUrl=imageUrl;
        this.deviceType=deviceType;
        this.beginTime=beginTime;
        this.endTime=endTime;
        this.targetRange=targetRange;
        this.targetType=targetType;
        this.status=status;
        this.gruopId=gruopId;
    }
}
