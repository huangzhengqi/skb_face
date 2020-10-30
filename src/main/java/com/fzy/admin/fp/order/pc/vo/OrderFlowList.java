package com.fzy.admin.fp.order.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by wtl on 2019-05-04 22:57
 * @description 订单流水列表
 */
@Data
public class OrderFlowList {

    @ApiModelProperty(value = "支付方式")
    private String payWay;
    @ApiModelProperty(value = "支付金额")
    private String price;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "支付时间")
    private Date payTime;


}
