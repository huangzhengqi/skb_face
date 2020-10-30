package com.fzy.admin.fp.distribution.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-4 17:43:31
 * @Desp
 **/
@Data
public class ShopOrderDTO {
    @ApiModelProperty("待支付0 待发货1 已发货2 已完成3")
    private Integer status;

    @ApiModelProperty("已支付0 未支付1")
    private Integer type;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("用户id")
    private String userId;
}
