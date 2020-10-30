package com.fzy.admin.fp.advertise.device.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class AdvertiseDeviceDTO {

    @ApiModelProperty("计划名称")
    private String name;

    @ApiModelProperty("群组id")
    private String groupId;

    @ApiModelProperty("商户ids")
    private String merchantIds;

    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

    @NotNull(message = "投放对象不能为空")
    @ApiModelProperty(value = "广告类型 1指定商户群 2全部商户 3指定商户")
    private Integer targetType;

    @ApiModelProperty("投放位置 0首屏  1收银页面  2支付成功页")
    private Integer targetRange;

    @ApiModelProperty(value = "状态 1待投放 2投放中 3已结束 4已作废 5已暂停")
    private Integer status;

    @ApiModelProperty(value = "投放中的广告状态 1暂停 2恢复")
    private Integer advertiseStatus;

    @ApiModelProperty("设备类型 0全部 1支付宝 2微信")
    private Integer deviceType;

    @ApiModelProperty("是否默认广告 1不是，2默认广告")
    @Column(columnDefinition = "int(1) default 0")
    private Integer type;

    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "视频/图片")
    private Integer selectSt; //(0图片  1视频)

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    private Date beginTime;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
