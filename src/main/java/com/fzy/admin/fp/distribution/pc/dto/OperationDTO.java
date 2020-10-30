package com.fzy.admin.fp.distribution.pc.dto;

import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020-1-16 16:51:24
 * @Desp
 **/

@Data
public class OperationDTO {

    @NotNull(message = "请输入姓名")
    private String name;

    @NotNull(message = "请输入手机号")
    private String phone;
}
