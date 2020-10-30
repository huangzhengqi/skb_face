package com.fzy.admin.fp.order.pc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-04-27 22:47
 * @description pc端订单查询条件
 */
@Data
public class OrderPcDto {

    private String id; // 订单id

    private String orderNumber;//订单编号
    private BigDecimal actPayPrice; //订单实付金额
    private Integer payWay;//支付方式
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;// 支付时间
    private Integer status;//订单状态

}
