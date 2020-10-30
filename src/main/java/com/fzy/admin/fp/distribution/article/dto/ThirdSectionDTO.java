package com.fzy.admin.fp.distribution.article.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020年2月11日10:12:40
 * @Desp
 **/
@Data
public class ThirdSectionDTO {

    @ApiModelProperty("分类名称")
    @NotBlank(message = "请输入分类名称")
    private String name;

    @ApiModelProperty("icon")
    @NotBlank(message = "请上传icon")
    private String icon;

    @ApiModelProperty
    @NotBlank(message = "请输入父级id")
    private String parentId;

    private String id;
}
