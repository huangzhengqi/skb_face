package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 天阙随行付地区对照表
 */
@Data
@Entity
@Table(name = "tq_sxf_area")
public class TqSxfArea extends BaseEntity {

    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("地区id")
    private String areaId;

    @ApiModelProperty("地区名称")
    private String areaName;
}
