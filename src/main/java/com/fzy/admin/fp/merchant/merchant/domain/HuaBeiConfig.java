package com.fzy.admin.fp.merchant.merchant.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author hzq
 * @Date 2020/9/1 16:55
 * @Version 1.0
 * @description 花呗分期实体类
 */

@Data
@Entity
@Table(name = "hua_bei_config")
public class HuaBeiConfig extends BaseEntity {
    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("设备id")
    private String equipmentId;

    @ApiModelProperty("启用状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty("花呗利息代表卖家承担收费比例 仅支持传入 100、0 两种，其他比例暂不支持，传入会报错")
    private String interest;
}
