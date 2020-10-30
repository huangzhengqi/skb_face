package com.fzy.admin.fp.distribution.article.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-28 09:09:46
 * @Desp
 **/
@Data
public class SectionDTO {

    @ApiModelProperty("排序")
    private Integer weight;//排序 值越高，越靠前

    @ApiModelProperty("状态0显示 1隐藏")
    private Integer status;//状态0显示 1隐藏

    @ApiModelProperty("0图文式 1左右式 2九宫格 3标题式")
    private Integer type;

    @NotBlank(message = "请输入标题")
    private String name;

    private String id;

}
