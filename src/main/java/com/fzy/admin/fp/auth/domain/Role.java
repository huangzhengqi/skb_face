package com.fzy.admin.fp.auth.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
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
@Table(name = "lysj_auth_role")
public class Role extends BaseEntity {

    @Getter
    public enum Type {
        PROVIDERS(1, "服务商"),
        OPERATOR(2, "一级代理商"),
        DISTRIBUTUTORS(3, "二级代理商"),
        THIRDAGENT(4, "三级代理商");

        private Integer code;
        private String status;

        Type(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @Getter
    public enum Kind {
        MANAGER(1, "业务员"),
        CUSTOMER(2, "客服");


        private Integer code;
        private String status;

        Kind(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @Getter
    public enum LEVEL {
        TOP(1, "高级"),
        COMMON(2, "普通");

        private Integer code;
        private String status;

        LEVEL(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @NotBlank(message = "请填写角色名")
    private String name;//角色名

    private Boolean defaultRole;//是否为注册默认角色

    private Integer type;//公司类型

    private Integer kind;//角色类型

    private Integer level;// 员工等级

    @ApiModelProperty(value = "是否授权权限给下级 1是 0否")
    @Column(columnDefinition = "int(1) default 0")
    private Integer Authorized;

    @ApiModelProperty(value = "权限所属公司")
    @Column(columnDefinition = "varchar(255) default 0")
    private String companyId;

    @ApiModelProperty(value = "授权公司")
    @Column(columnDefinition = "varchar(255) default 0")
    private String authCompanyId;

    @ApiModelProperty(value = "源角色id")
    private String sourceRoleId;

    @Transient
    private List<Permission> permissions;//角色拥有的权限




}