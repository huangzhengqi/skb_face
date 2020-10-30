package com.fzy.admin.fp.distribution.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author hzq
 * @Date 2020-03-10 10:51:51
 * @Desp
 **/
@Data
public class DistWindowVO {

    @ApiModelProperty("生效日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date beginTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "引导语")
    private String guidance;

    @ApiModelProperty(value = "封面图片")
    private String imgUrl;

    @NotNull(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容")
    private String contents;

    @ApiModelProperty("用户id")
    private String userId;
}
