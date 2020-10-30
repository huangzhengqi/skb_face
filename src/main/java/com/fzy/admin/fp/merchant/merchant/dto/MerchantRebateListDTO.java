package com.fzy.admin.fp.merchant.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Author hzq
 * @Date 2020/9/19 14:21
 * @Version 1.0
 * @description 商户返佣列表参数DTO
 */
@Data
public class MerchantRebateListDTO {

    @ApiModelProperty("商户名称/手机号")
    private String namePhone;

    @ApiModelProperty("设置返佣金 1不设置 2设置")
    private Integer rebateType;

    @ApiModelProperty("是否绑定 1未绑定 2已绑定")
    @Column(name="bind",columnDefinition="int default 1")
    private Integer bind;

    @ApiModelProperty("设置是否奖励 1不设置 2设置")
    private Integer rewardType;

    @ApiModelProperty("查询类型 1查询商户返佣 2查询商户奖励")
    private Integer type;

}
