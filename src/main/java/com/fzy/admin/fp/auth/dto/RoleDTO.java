package com.fzy.admin.fp.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: huxiangqiang
 * @since: 2019/8/2
 */
@Data
public class RoleDTO {


    @ApiModelProperty(value = "角色id")
    private String id;

    @ApiModelProperty(value = "选择列表")
    List<RoleListDTO> list;
}
