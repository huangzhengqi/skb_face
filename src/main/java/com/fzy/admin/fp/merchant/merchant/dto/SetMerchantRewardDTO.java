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
 * @Date 2020/10/14 17:08
 * @Version 1.0
 * @description 设置奖励参数
 */
@Data
public class SetMerchantRewardDTO {

    @ApiModelProperty("设置是否奖励 1不设置 2设置")
    private Integer rewardType;

    @ApiModelProperty(value="开始奖励日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="开始奖励日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date startRewardTime;

    @ApiModelProperty(value="结束奖励日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="结束奖励日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date endRewardTime;

    @ApiModelProperty("奖励门槛 （笔数）")
    private Integer rewardNum;

    @ApiModelProperty(value="奖励金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal rewardPrice;
}
