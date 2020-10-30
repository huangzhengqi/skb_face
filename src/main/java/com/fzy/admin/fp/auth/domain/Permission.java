package com.fzy.admin.fp.auth.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.Range;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author zk
 * @description 权限表
 * @create 2018-07-25 15:32:54
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_permission")
public class Permission extends BaseEntity {

    @Range(min = "1", minEqual = true, max = "3", maxEqual = true, message = "层级取值范围为1-3")
    private Integer level;//层级

    private Integer type;//类型 0-页面 1具体操作

    @NotBlank(message = "请填写路径")
    private String path;//路径

    private String icon;//图标

    private String parentId;//父ID

    private String description;//描述

    private Integer sortOrder;//排序值

    private Integer visible;// 是否可见 1:可见,2:不可见


    public Permission() {
    }

    public Permission(Integer level, Integer type, String path, String icon, String parentId, String description, Integer sortOrder) {
        this.level = level;
        this.type = type;
        this.path = path;
        this.icon = icon;
        this.parentId = parentId;
        this.description = description;
        this.sortOrder = sortOrder;
    }

    @Transient
    private List<Permission> children;//子菜单

}