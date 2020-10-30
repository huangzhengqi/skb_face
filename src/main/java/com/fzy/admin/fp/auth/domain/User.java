package com.fzy.admin.fp.auth.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author zk
 * @description 用户表
 * @create 2018-07-25 14:55
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_user")
public class User extends CompanyBaseEntity {


    @Getter
    public enum Status {
        /**
         * 状态
         */
        ENABLE(1, "启用"),
        DISABLE(2, "禁用");

        private Integer code;
        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @Getter
    public enum SEX {
        /**
         * 性别
         */
        MAN(1, "男"),
        WOMEN(2, "女");

        private Integer code;
        private String status;

        SEX(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("用户名，登录账号，必填")
    private String username;

    @ApiModelProperty("密码 必填")
    private String password;

    @ApiModelProperty("联系电话 选用")
    private String phone;

    @ApiModelProperty("邮箱 选用")
    private String email;

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("员工状态")
    private Integer status;

    @ApiModelProperty("员工性别")
    private Integer sex;

    @ApiModelProperty("级别")
    @Transient
    private String level;

    @ApiModelProperty("公司名称")
    @Transient
    private String companyName;

    @ApiModelProperty("用户拥有的权限")
    @Transient
    private List<Permission> permissions;

    @ApiModelProperty("用户拥有的角色")
    @Transient
    private List<Role> roles;
}
