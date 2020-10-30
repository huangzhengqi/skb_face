package com.fzy.admin.fp.distribution.app.vo;

import com.fzy.admin.fp.order.order.domain.CommissionDay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author yy
 * @Date 2020-1-15 10:53:58
 * @Desp
 **/
@Data
public class IndexCommissionVO {

    @ApiModelProperty("交易笔数")
    private Integer dealNum;

    @ApiModelProperty("交易金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("我的佣金")
    private BigDecimal commission;

    private List<CommissionDay> commissionDayList;
}
