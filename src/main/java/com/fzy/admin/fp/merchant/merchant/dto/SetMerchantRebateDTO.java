package com.fzy.admin.fp.merchant.merchant.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author hzq
 * @Date 2020/9/19 17:25
 * @Version 1.0
 * @description 设置返佣参数
 */
@Data
public class SetMerchantRebateDTO {

    @ApiModelProperty("设置返佣金 1不设置 2设置")
    @Column(name="rebateType",columnDefinition="int default 1")
    private Integer rebateType;

    @ApiModelProperty(value="开始返佣日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startRebateTime;

    @ApiModelProperty(value="结束返佣日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endRebateTime;

    @ApiModelProperty("返佣费率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal siteRate;

    @ApiModelProperty("返佣限额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal limitRebate;

    @ApiModelProperty("返佣门槛 （笔数）")
    private Integer rebateNum;

}
