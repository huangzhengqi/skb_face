package com.fzy.admin.fp.merchant.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author fyz lzy
 * @create 2020/7/15 10:40
 * @Description: 分销商APP -我的商户
 */
@Data
@ApiModel(value = "我的商户")
public class MyMerchantDTO {

    @ApiModelProperty(value = "交易总额")
    private  BigDecimal totalPrice;

    @ApiModelProperty(value = "商户ID")
    private  String merchantId;

    @ApiModelProperty(value = "商户名称")
    private  String merchantName;

    @ApiModelProperty(value = "日期")
    private String createTime;

    @ApiModelProperty(value = "刷脸笔数")
    private  BigInteger faseNum;

}
