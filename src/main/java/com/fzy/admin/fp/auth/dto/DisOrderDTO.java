package com.fzy.admin.fp.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author fyz123
 * @create 2020/7/24 15:59
 * @Description: 交易数据详情
 */
@Data
public class DisOrderDTO {

    @ApiModelProperty(value = "门店Id",required = true)
    private String storeId;

    @ApiModelProperty("订单号/收营员")
    private String orderName;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("支付方式")
    private String payWay;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("开始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
