package com.fzy.admin.fp.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: huxiangqiang
 * @since: 2019/8/2
 */
@Data
public class RoleListDTO {


    @ApiModelProperty(value = "公司id")
    private String id;

    @ApiModelProperty(value = "1选择 0未选择")
    private Integer status;


}
