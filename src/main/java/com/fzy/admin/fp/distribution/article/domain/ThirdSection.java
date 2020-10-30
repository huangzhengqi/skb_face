package com.fzy.admin.fp.distribution.article.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2020-2-10 16:24:54
 * @Desp 三级分类
 **/

@Data
@Entity
@Table(name="lysj_dist_third_section")
public class ThirdSection extends BaseEntity {
    private String parentId;//父id

    private String icon;//icon

    private Integer weight;
}
