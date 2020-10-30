package com.fzy.admin.fp.distribution.article.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2020-2-10 15:44:45
 * @Desp 二级分类
 **/

@Data
@Entity
@Table(name="lysj_dist_child_section")
public class SecondSection extends BaseEntity {
    private String sectionId;//父id

    private String icon;//icon

    private Integer isParent;//是否还存在下级分类

    private Integer weight;//权重

    private Integer type;//1左右式 2九宫格

}
