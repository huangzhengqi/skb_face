package com.fzy.admin.fp.distribution.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-26 10:24:22
 * @Desp
 **/
@Data
public class TeamDTO {
    @ApiModelProperty("业务员")
    private String salesman;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty("团队人数")
    private Integer teamSize;

    @ApiModelProperty("团长")
    private String name;

    @ApiModelProperty("2级人数")
    private Integer secondNum;

    @ApiModelProperty("3级人数")
    private Integer thirdNum;

    @ApiModelProperty("4级人数")
    private Integer fourthNum;

    @ApiModelProperty("5级人数")
    private Integer fifthNum;

    @ApiModelProperty("6级人数")
    private Integer sixthNum;

    @ApiModelProperty("头像")
    private String headImg;

}
