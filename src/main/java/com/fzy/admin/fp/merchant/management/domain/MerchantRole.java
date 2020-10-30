package com.fzy.admin.fp.merchant.management.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author zk
 * @description 角色表
 * @create 2018-07-25 15:10:47
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_role")
public class MerchantRole extends BaseEntity {

    @NotBlank(message = "请填写角色名")
    private String name;//角色名

    private Boolean defaultRole;//是否为注册默认角色

    @Transient
    private List<MerchantPermission> permissions;//角色拥有的权限

    private String storeId; // 门店id

    private Integer roleType; // 角色类型


    @Getter
    public enum RoleType {
        MANAGER(1, "店长"),
        OTHER(2, "其他");

        private Integer code;

        private String status;

        RoleType(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }
}