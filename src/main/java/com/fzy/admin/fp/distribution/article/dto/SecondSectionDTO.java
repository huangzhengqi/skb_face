package com.fzy.admin.fp.distribution.article.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020-2-11 09:41:40
 * @Desp
 **/
@Data
public class SecondSectionDTO {
    @ApiModelProperty("分类名称")
    @NotBlank(message = "请输入分类名称")
    private String name;

    @ApiModelProperty("icon")
    @NotBlank(message = "请上传icon")
    private String icon;


    @ApiModelProperty("1左右式 2九宫格")
    @NotNull(message = "请选择板式")
    private Integer type;

    private String id;
}
