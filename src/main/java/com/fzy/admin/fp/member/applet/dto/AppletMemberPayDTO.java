package com.fzy.admin.fp.member.applet.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-18 18:34
 * @description 会员小程序储值参数
 */
@Data
public class AppletMemberPayDTO {

    @ApiModelProperty(value = "储值规则id")
    private String storeRuleId;

    @ApiModelProperty(value = "自定义储值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "openId")
    private String openId;
}
