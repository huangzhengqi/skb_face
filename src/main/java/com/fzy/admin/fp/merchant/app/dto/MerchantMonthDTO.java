package com.fzy.admin.fp.merchant.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author fyz lzy
 * @create 2020/7/16 11:41
 * @Description: 我的商户月交易数据
 */
@ApiModel(value = "月交易数据")
@Data
public class MerchantMonthDTO {

    @ApiModelProperty(value = "交易总额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "交易笔数")
    private BigInteger totalNum;

    @ApiModelProperty(value = "刷脸笔数大于等于的二")
    private BigInteger faseNum;

    @ApiModelProperty(value = "刷脸笔数")
    private BigInteger fase2Num;

    @ApiModelProperty(value = "刷脸交易")
    private BigDecimal facePrice;

    @ApiModelProperty(value = "日期")
    private String monthDate;

}
