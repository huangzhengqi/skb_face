package com.fzy.admin.fp.distribution.feedback.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author yy
 * @Date 2019-12-9 10:21:55
 * @Desp
 **/
@Data
public class AppFeedbackDTO {

    @NotBlank(message = "内容为空")
    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("图片")
    private String img;
}
