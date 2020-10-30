package com.fzy.admin.fp.push.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author hzq
 * @Date 2020/10/7 13:47
 * @Version 1.0
 * @description 个推client用户表
 */
@Data
@Entity
public class PushUserClient extends CompanyBaseEntity {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("用户token")
    private String token;

    @ApiModelProperty("用户clientid")
    private String clientid;

    @ApiModelProperty("用户appid")
    private String appid;

    @ApiModelProperty("用户appkey")
    private String appkey;

    @ApiModelProperty("客户端类型 1苹果 2安卓")
    private Integer clientType;

}
