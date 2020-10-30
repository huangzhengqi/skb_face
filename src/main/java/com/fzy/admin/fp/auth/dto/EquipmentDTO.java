package com.fzy.admin.fp.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EquipmentDTO {
    @ApiModelProperty("设备号/门店/商家/上级")
    private String keyWord;

    @ApiModelProperty("设备类型")
    private Integer type;
}
