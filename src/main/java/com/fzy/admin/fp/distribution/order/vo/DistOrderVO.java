package com.fzy.admin.fp.distribution.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-26 16:45:50
 * @Desp
 **/
@Data
public class DistOrderVO {

    @ApiModelProperty("交易时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("级别")
    private Integer grade;

    @ApiModelProperty("代理费")
    private BigDecimal price;

    @ApiModelProperty("一级提成")
    private Integer firstCommissions;

    @ApiModelProperty("二级提成")
    private Integer secondCommissions;

}
