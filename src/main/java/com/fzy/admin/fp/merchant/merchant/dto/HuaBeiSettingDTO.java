package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hzq
 * @Date 2020/9/1 16:55
 * @Version 1.0
 * @description 花呗分期DTO
 */
@Data
public class HuaBeiSettingDTO {

    @ApiModelProperty("设备id")
    private String equipmentId;

    @ApiModelProperty("启用状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty("花呗利息代表卖家承担收费比例 仅支持传入 100,0两种，其他比例暂不支持，传入会报错")
    private String interest;
}
