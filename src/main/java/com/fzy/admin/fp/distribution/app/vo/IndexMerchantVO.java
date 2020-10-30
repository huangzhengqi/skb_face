package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2020-3-2 14:15:51
 * @Desp
 **/
@Data
public class IndexMerchantVO {
    @ApiModelProperty("商铺名称")
    private String name;

    @ApiModelProperty("佣金")
    private BigDecimal total;

    @ApiModelProperty("所属代理")
    private String manageName;

    @ApiModelProperty("交易金额")
    private String totalPrice;

    public Double getTotal() {
        return total==null?0:total.doubleValue();
    }

}
