package com.fzy.admin.fp.merchant.management.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
@Table(name = "lysj_merchant_permission")
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPermission extends BaseEntity {

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


    @Transient
    private List<MerchantPermission> children;//子菜单

}