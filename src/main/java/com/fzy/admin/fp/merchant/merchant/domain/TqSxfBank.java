package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 天阙随行付联行行号表
 */
@Data
@Entity
@Table(name = "tq_sxf_bank")
public class TqSxfBank extends BaseEntity {

    @ApiModelProperty("联行行号")
    private String unitedBankNo;

    @ApiModelProperty("联行名称")
    private String unitedBankName;

    @ApiModelProperty(value = "银行编码")
    private String bankNo;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "网联机构号")
    private String networkOrganizationNumber;

    @ApiModelProperty(value = "省编码")
    private String provinceNo;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市编码")
    private String cityNo;

    @ApiModelProperty(value = "市名称")
    private String cityName;

}
