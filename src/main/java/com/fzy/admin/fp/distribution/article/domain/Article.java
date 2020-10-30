package com.fzy.admin.fp.distribution.article.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-6 10:27:22
 * @Desp 文章
 **/
@Data
@Entity
@Table(name="lysj_dist_article")
public class Article extends CompanyBaseEntity{

    @Column(columnDefinition="TEXT")
    private String content;//咨询内容

    private String title;//标题

    private Integer weight;//排序 值越高，越靠前

    private Integer status;//状态0显示 1隐藏

    private String sectionId;//类型0刷脸资讯 1商户落地 2平台规则 3官方政策

    @ApiModelProperty("三级分类id")
    private String thirdId;

    @ApiModelProperty("二级分类id")
    private String secondId;

}
