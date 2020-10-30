package com.fzy.admin.fp.order.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-26 18:11:07
 * @Desp
 **/
@Data
public class CommissionDayVO {
    @ApiModelProperty(value = "结算日期")
    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    @ApiModelProperty(value = "代理用户")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "交易流水")
    private BigDecimal totalOrder;

    @ApiModelProperty(value = "佣金金额")
    private BigDecimal totalCommission;

    public CommissionDayVO(Date settleTime, String name, String phone, BigDecimal totalOrder, BigDecimal totalCommission) {
        this.settleTime = settleTime;
        this.name = name;
        this.phone = phone;
        this.totalOrder = totalOrder;
        this.totalCommission = totalCommission;
    }
}
