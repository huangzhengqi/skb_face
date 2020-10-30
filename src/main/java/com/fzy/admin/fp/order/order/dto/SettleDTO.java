package com.fzy.admin.fp.order.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Created by wtl on 2019-05-06 23:13
 * @description
 */
@Data
public class SettleDTO {

    @ApiModelProperty(value = "id")
    List<String> id;
    @ApiModelProperty(value = "结算url")
    String voucherurl;
}
