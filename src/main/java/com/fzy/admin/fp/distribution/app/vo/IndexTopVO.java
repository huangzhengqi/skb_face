package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2020-1-14 15:54:06
 * @Desp
 **/
@Data
public class IndexTopVO {
    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("等级")
    private Integer grade;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("本月佣金")
    private BigDecimal monthCommission;

    @ApiModelProperty("累积佣金")
    private BigDecimal totalCommission;

    @ApiModelProperty("团队人数")
    private Integer teamNum;

    @ApiModelProperty("代理人数")
    private Integer agentNum;

    @ApiModelProperty("商户人数")
    private Integer merchantNum;

    @ApiModelProperty("激活设备")
    private Integer activate;

}
