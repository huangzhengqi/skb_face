package com.fzy.admin.fp.merchant.management.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.*;
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
@Table(name = "lysj_merchant_user")
public class MerchantUser extends CompanyBaseEntity {

    @Getter
    public enum UserType {
        MERCHANT("1", "商户"),
        MANAGER("2", "店长"),
        EMPLOYEES("3", "员工");
        private String code;

        private String status;

        UserType(String code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Status {
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
        MAN(1, "男"),
        WOMEN(2, "女");

        private Integer code;
        private String status;

        SEX(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }


    @NotBlank(message = "用户名不能为空")
    @Pattern(pattern = "[a-zA-Z0-9_]{5,17}", message = "用户名应为英文、数字、下划线组成的5-17位字符")
    private String username;//用户名，登录账号，必填

    //    @Pattern(pattern = "[a-zA-Z0-9_]{6,15}", message = "密码应为英文、数字、下划线组成的6-15位字符")
    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    private String password;//密码 必填

    @NotBlank(message = "手机号码不能为空")
    private String phone; // 手机号码

    @Transient
    private List<MerchantPermission> permissions;//用户拥有的权限

    @Transient
    private List<MerchantRole> roles;//用户拥有的角色

    private String userType; // 用户类型，1：商户；2：店长；3：员工

    private Integer status; // 状态，1：启用；2：注销

    private Integer sex; // 性别，1：男；2：女

    private String merchantId; // 商户id

    //private String storeId; // 门店id，为空代表该员工为商户
    
    private String storeId; // 门店id

    private String photoId;//图像id

    @Transient
    private String storeName; // 门店名称

}
