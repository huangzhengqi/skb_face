package com.fzy.admin.fp.sdk.merchant.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * @author Created by wtl on 2019-04-30 15:54
 * @description 商户模块用户
 */
@Data
public class MerchantUserDTO {

    @Getter
    public enum UserType {
        /**
         * 用户类型
         */
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
        /**
         * 用户状态
         */
        ENABLE(1, "启用"),
        DISABLE(2, "注销");
        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户名，登录账号，必填")
    private String username;

    @ApiModelProperty("密码 必填")
    private String password;

    @ApiModelProperty("用户类型，1：商户；2：店长；3：员工")
    private String userType;

    @ApiModelProperty("状态，1：男；2：女")
    private Integer sex;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("appKey")
    private String appKey;

    @ApiModelProperty("服务商ID")
    private String serviceProviderId;


}
