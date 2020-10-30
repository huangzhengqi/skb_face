package com.fzy.admin.fp.distribution.article.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-6 15:19:32
 * @Desp
 **/
@Data
public class ArticleSortDTO {

    @NotNull(message = "id不能为空")
    private String id;

    @NotBlank(message = "排序值不能为空")
    private Integer weight;
}
