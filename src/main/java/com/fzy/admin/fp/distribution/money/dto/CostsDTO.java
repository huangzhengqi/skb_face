package com.fzy.admin.fp.distribution.money.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * 代理商提成分配表
 * @Author yy
 * @Date 2019-11-15 14:47:04
 **/
@Data
public class CostsDTO {
    private String id;

    @ApiModelProperty("价格")
    private BigDecimal price;//价格

    @ApiModelProperty("一级提成")
    private Integer firstCommissions;//一级提成

    @ApiModelProperty("二级提成")
    private Integer secondCommissions;//二级提成

    @ApiModelProperty("备注")
    private String remark;//备注

    @ApiModelProperty("代理id")
    private String serviceProviderId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图片")
    private String introduce;
}
