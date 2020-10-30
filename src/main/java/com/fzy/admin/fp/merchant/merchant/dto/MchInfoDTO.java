package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:29 2019/6/24
 * @ Description:
 **/
@Data
public class MchInfoDTO {


    @ApiModelProperty(value = "代理商企业名称")
    private String companyName;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;


}
