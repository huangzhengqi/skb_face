package com.fzy.admin.fp.distribution.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;



public interface DistAppWindowUserListVO {

    @ApiModelProperty("公告id")
    String getId();

    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    String getTitle();

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    String getBeginTime();

    @ApiModelProperty("是否已读了 0未读 1已读")
    String getIsRead();

}
