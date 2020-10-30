package com.fzy.admin.fp.distribution.pc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-26 18:15:28
 * @Desp
 **/
@Data
public class StatisticsDTO {
    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    @ApiModelProperty("天数")
    private Integer num;

    @ApiModelProperty("级别 全部级别true 分级别false")
    private Boolean isAll;

    @ApiModelProperty(" 累计金额true 新增金额false")
    private Boolean isTotal;

    @ApiModelProperty("类型 0新增用户 1新增代理 2累计代理 3发展商户 4激活设备")
    private Integer type;
}
