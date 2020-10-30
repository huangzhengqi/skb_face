package com.fzy.admin.fp.distribution.article.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-27 18:54:52
 * @Desp
 **/

@Data
@Entity
@Table(name="lysj_dist_section")
public class Section extends CompanyBaseEntity {

    @ApiModelProperty("排序")
    private Integer weight;

    @ApiModelProperty("状态0显示 1隐藏")
    private Integer status;

    @ApiModelProperty("0图文式 1左右式 2九宫格 3标题式")
    private Integer type;
}

