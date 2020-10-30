package com.fzy.admin.fp.distribution.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 分销app广告入参
 */
@Data
public class AppAdvertiseDTO {

    @ApiModelProperty("广告id")
    private String id;

    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "广告图片跳转链接")
    private String imageLink;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @Temporal(TemporalType.DATE)
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty("生效时间")
    private Date beginTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @Temporal(TemporalType.DATE)
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "是否跳转 1是 0不是")
    private Integer isJump;

    @ApiModelProperty(value = "排序值")
    private Integer weight;//排序 值越高，越靠前

    @ApiModelProperty("投放位置 0启动页  1首页")
    private Integer targetRange;

}
