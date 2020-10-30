package com.fzy.admin.fp.advertise.device.domian;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  广告设备投放曝光目标表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_device_view_log")
public class AdvertiseDeviceViewLog extends BaseEntity {

    @ApiModelProperty("广告设备id")
    private String advertiseDeviceId;

    @ApiModelProperty(value = "类型 1曝光 2点击")
    private Integer status;

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "1首页 2收银页 3支付成功页")
    private Integer targetRange;
}
