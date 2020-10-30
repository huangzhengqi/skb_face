package com.fzy.admin.fp.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2019-11-27 14:23:07
 * @Desp
 **/
@Data
public class FootInfoDTO {
    @ApiModelProperty("公司名称")
    private String name;

    @ApiModelProperty("联系电话")
    private String phone;
}
