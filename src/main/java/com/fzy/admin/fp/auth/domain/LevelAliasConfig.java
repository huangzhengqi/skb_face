package com.fzy.admin.fp.auth.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 级别名称配置表
 */
@Data
@Entity
@Table(name = "level_alias_config")
public class LevelAliasConfig {

    @Id
    @Column(length = 128)
    @ApiModelProperty(value = "唯一标识")
    private String id;

    @ApiModelProperty(value = "贴牌商别名")
    private String oemName;

    @ApiModelProperty(value = "一级代理商别名")
    private String firstName;

    @ApiModelProperty(value = "二级代理商别名")
    private String secondName;

    @ApiModelProperty(value = "三级代理商别名")
    private String thirdName;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Integer status;


}
