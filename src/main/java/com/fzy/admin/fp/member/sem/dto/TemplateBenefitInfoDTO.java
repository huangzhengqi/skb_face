package com.fzy.admin.fp.member.sem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TemplateBenefitInfoDTO {

    /**
     * template_benefit_info 权益信息 (可选)
     */

    @ApiModelProperty(value = "权益描述")
    private String title;//必填

    @ApiModelProperty(value = "权益描述信息")
    private String[] benefit_desc;//必填

    @ApiModelProperty(value = "开始时间")
    private Date start_date;//必填

    @ApiModelProperty(value = "结束时间")
    private Date end_date;//必填

}
