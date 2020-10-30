package com.fzy.admin.fp.goods.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GoodsOrderDTO {


    @ApiModelProperty("门店名称或订单号")
    private String storeOrOrder;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("1会员卡 2支付宝 3微信")
    private Integer payType;

    @ApiModelProperty("行业分类 0超市  1自助点餐  2医药 3加油站 4景区 ")
    private Integer industryCategory;
}
