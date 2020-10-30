package com.fzy.admin.fp.distribution.app.vo;

import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;


public interface DistAppWindowUserDetailsVO {

    @ApiModelProperty("公告id")
    String getId();

    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    String getTitle();

    @ApiModelProperty(value = "引导语")
    String getGuidance();

    @ApiModelProperty(value = "文章内容")
    String getContents();

    @ApiModelProperty("是否已读了 0未读 1已读")
    String getIsRead();
}
