package com.fzy.admin.fp.advertise.device.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 设备曝光/点击
 */
@Data
public class AdvertiseDeviceViewListVO {

    @ApiModelProperty(value = "广告标题")
    private String title;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "生效时间")
    private Date createTime;

    public AdvertiseDeviceViewListVO() {

    }

    public AdvertiseDeviceViewListVO(String title, Date createTime) {
        this.title = title;
        this.createTime = createTime;
    }
}
