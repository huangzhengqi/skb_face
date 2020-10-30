package com.fzy.admin.fp.distribution.app.vo;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.Data;

@Data
public class WindowStateVo {

    @NotNull(message = "id不能为空")
    private String id;

    @NotBlank(message = "状态值不能为空")
    private Integer status;

}
