package com.fzy.admin.fp.member.sem.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplateColumnInfoDTO {
    /**
     * column_info_list (必选)
     */

    @ApiModelProperty(value = "标准栏位BENEFIT_INFO")
    private String code;//必填

    @ApiModelProperty(value = "默认staticinfo")
    private String operate_type;

    @ApiModelProperty(value = "栏目标题")
    private String title;//必填

    @ApiModelProperty(value = "卡包详情界面商家电话传入")
    private String value;


}
