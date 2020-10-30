package com.fzy.admin.fp.merchant.app.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: huxiangqiang
 * @since: 2019/7/31
 */
@Data
public class WeiqingCallBackDTO {
    private String transactionId;
    private String account;
    private BigDecimal totalPrice;
    private String remarks;
    private BigDecimal actPayPrice;
    private String openid;
    private Date payTime;
}
