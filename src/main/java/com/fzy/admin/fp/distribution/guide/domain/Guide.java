package com.fzy.admin.fp.distribution.guide.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2020-1-11 13:45:02
 * @Desp
 **/
@Data
@Entity
@Table(name = "lysj_dist_guide")
public class Guide extends CompanyBaseEntity{
    @ApiModelProperty("图片 多张图片用逗号隔开")
    private String img;
}
