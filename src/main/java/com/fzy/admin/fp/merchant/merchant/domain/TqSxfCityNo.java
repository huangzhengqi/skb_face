package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 天阙随行付省区编码表
 */
@Data
@Entity
@Table(name = "tq_sxf_city_no")
public class TqSxfCityNo extends BaseEntity {

    @ApiModelProperty("省编码")
    private String provinceNo;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityNo;

    @ApiModelProperty("市名称")
    private String cityName;
}
