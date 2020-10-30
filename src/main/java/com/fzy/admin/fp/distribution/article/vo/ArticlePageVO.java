package com.fzy.admin.fp.distribution.article.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author yy
 * @Date 2020-2-27 09:43:15
 * @Desp
 **/
@Data
public class ArticlePageVO {
    private List<ArticleVO> articleVOList;

    private Long totalElements;

    private Integer totalPages;
}
