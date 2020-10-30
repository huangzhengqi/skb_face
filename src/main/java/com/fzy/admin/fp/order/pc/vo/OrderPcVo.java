package com.fzy.admin.fp.order.pc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-04-27 22:04
 * @description 订单pc端返回数据
 */
@Data
public class OrderPcVo {

    private String id; // 订单id

    private String orderNumber;//订单编号
    private BigDecimal actPayPrice = BigDecimal.ZERO; //订单实付金额
    private BigDecimal refundPrice = BigDecimal.ZERO;// 退款金额
    private Integer payWay;//支付方式
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;// 创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;// 支付时间
    private Integer status;//订单状态

    public OrderPcVo() {

    }

    public OrderPcVo(String id, String orderNumber, BigDecimal actPayPrice, Integer payWay, Date createTime, Date payTime, Integer status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.actPayPrice = actPayPrice;
        this.payWay = payWay;
        this.createTime = createTime;
        this.payTime = payTime;
        this.status = status;
    }

    public OrderPcVo(String id, String orderNumber, BigDecimal actPayPrice, BigDecimal refundPrice, Integer payWay, Date createTime, Date payTime, Integer status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.actPayPrice = actPayPrice;
        this.refundPrice = refundPrice;
        this.payWay = payWay;
        this.createTime = createTime;
        this.payTime = payTime;
        this.status = status;
    }
}
