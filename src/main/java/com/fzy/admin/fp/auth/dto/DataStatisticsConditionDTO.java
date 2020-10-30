package com.fzy.admin.fp.auth.dto;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-05 20:39
 * @description
 */
@Data
public class DataStatisticsConditionDTO {


    @ApiModelProperty(value = "公司id")
    private String companyId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    public Date getEndTime() {
        return DateUtil.endOfDay(this.endTime);
    }

    @ApiModelProperty(value = "查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数 5佣金")
    private int type;

}
