package com.fzy.admin.fp.member.sem.dto;


import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel
@Data
public class MemberCouponDTO {

    @ApiModelProperty(value = "优惠劵ID")
    private Integer id;

    @ApiModelProperty(value = "卡券名称")
    private String name;

    @ApiModelProperty(value = "活动开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date actTimeStart;

    @ApiModelProperty(value = "活动结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date actTimeEnd;

    @ApiModelProperty(value = "活动状态 1-未开始 2-进行中 3-已结束 默认值为 1")
    private Integer actStatus;

    private Integer delFlag;

}
