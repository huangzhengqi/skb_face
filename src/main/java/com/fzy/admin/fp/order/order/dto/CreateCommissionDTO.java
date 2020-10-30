package com.fzy.admin.fp.order.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-06 23:13
 * @description
 */
@Data
public class CreateCommissionDTO {

    @ApiModelProperty(value = "公司id/商户id")
    String id;
    @ApiModelProperty(value = "1一级代理 2二级代理 3三级代理 4贴牌商 5全局")
    Integer type;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
