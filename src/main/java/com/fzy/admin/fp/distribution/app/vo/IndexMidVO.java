package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020-1-15 09:30:27
 * @Desp
 **/
@Data
public class IndexMidVO {
    @ApiModelProperty("团队人数")
    private Integer teamNum;

    @ApiModelProperty("代理人数")
    private Integer agentNum;

    @ApiModelProperty("发展商户")
    private Integer merchantNum;

    @ApiModelProperty("激活设备")
    private Integer activateNum;

    @ApiModelProperty("时间")
    private String Date;
}
