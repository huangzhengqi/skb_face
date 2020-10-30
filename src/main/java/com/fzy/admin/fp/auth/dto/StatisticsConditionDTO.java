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
public class StatisticsConditionDTO {

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private int type = 1;
}
