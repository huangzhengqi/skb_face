package com.fzy.admin.fp.distribution.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-6 11:25:00
 * @Desp
 **/
@Data
public class ArticleDTO {

    private String id;

    @ApiModelProperty("咨询内容")
    @NotBlank(message = "请输入内容")
    private String content;

    @ApiModelProperty("标题")
    @NotBlank(message = "请输入标题")
    private String title;

    @ApiModelProperty("排序 值越高，越靠前")
    private Integer weight;

    @ApiModelProperty("状态0显示 1隐藏")
    private Integer status;

    @NotBlank(message = "请输入选择所属版块")
    private String sectionId;

    @ApiModelProperty("三级分类id")
    private String thirdId;

    @ApiModelProperty("二级分类id")
    private String secondId;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;


    @ApiModelProperty("分类级别 1 一级类别 2二级类别 3三级类别")
    private Integer type;
}
