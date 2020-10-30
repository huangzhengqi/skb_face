package com.fzy.admin.fp.sdk.merchant.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wtl on 2019-04-30 10:50
 * @description 商户用户
 */
@Data
public class MerchantUserSelect {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名，登录账号")
    private String username;

    public MerchantUserSelect() {
    }

    public MerchantUserSelect(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
