package com.fzy.admin.fp.distribution.pc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-25 14:42:36
 * @Desp
 **/
@Data
public class SalesmanDTO {
    private String id;

    @ApiModelProperty("手机号/用户名")
    private String name;

    @ApiModelProperty("代理级别")
    private Integer type;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("等级")
    private Integer rank;


}
