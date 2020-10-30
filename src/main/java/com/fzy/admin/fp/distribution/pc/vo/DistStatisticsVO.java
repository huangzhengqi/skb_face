package com.fzy.admin.fp.distribution.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-12-28 09:49:51
 * @Desp
 **/
@Data
public class DistStatisticsVO {
    @ApiModelProperty("新增用户")
    private Integer addUser;

    @ApiModelProperty("上一时段新增用户")
    private Integer oldAddUser;

    @ApiModelProperty("新增代理")
    private Integer addAgent;

    @ApiModelProperty("上一时段新增代理")
    private Integer oldAddAgent;

    @ApiModelProperty("累计代理")
    private Integer totalAgent;

    @ApiModelProperty("发展商户")
    private Integer addMerchant;

    @ApiModelProperty("上一时段发展商户")
    private Integer oldAddMerchant;

    @ApiModelProperty("激活设备")
    private Integer activateNum;

    @ApiModelProperty("上一时段激活设备")
    private Integer oldActivateNum;

}
