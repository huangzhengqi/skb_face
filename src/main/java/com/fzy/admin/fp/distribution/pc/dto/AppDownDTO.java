package com.fzy.admin.fp.distribution.pc.dto;

import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-16 09:46:06
 * @Desp
 **/
@Data
public class AppDownDTO {
    @NotNull(message = "下载链接不能为空")
    private String url;
}
