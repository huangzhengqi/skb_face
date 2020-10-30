package com.fzy.admin.fp.advertise.device.dto;

import com.fzy.admin.fp.common.spring.pagination.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class AdvertiseDeviceListDTO extends PageVo {

    @ApiModelProperty(value = "计划标题")
    private String title;

    @ApiModelProperty(value = "状态 1待投放 2投放中 3已结束 4已作废 5已暂停")
    private Integer status;

    @ApiModelProperty("是否默认广告 1不是，2默认广告")
    private Integer type;
}
