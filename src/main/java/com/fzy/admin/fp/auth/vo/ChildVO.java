package com.fzy.admin.fp.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ author ：drj.
 * @ Date  ：Created in 16:06 2019/4/19
 * @ Description:
 **/
@Data
public class ChildVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty("是否选择 1选择 0不选中")
    private Integer status;

}
