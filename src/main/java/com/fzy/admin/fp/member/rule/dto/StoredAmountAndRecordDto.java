package com.fzy.admin.fp.member.rule.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author ：drj.
 * @Date  ：Created in 11:37 2019/5/20
 * @Description:  储值查询Dto
 **/
@Data
public class StoredAmountAndRecordDto {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("交易类型")
    private Integer tradeType;

    @ApiModelProperty("来源")
    private Integer source;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("支付方式")
    private Integer payWay;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_createTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_createTime;
}
