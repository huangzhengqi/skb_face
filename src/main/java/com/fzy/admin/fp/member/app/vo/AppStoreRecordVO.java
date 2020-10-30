package com.fzy.admin.fp.member.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:36 2019/6/12
 * @ Description:
 **/
@Data
public class AppStoreRecordVO {

    private String id;
    private String orderNumber; //交易订单号
    private BigDecimal tradingMoney;//交易金额
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    private Date createTime;  //创建时间
    private Integer payStatus;//支付状态
    private Integer payWay;//支付方式

    public AppStoreRecordVO(String id, String orderNumber, BigDecimal tradingMoney, Date createTime, Integer payStatus, Integer payWay) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.tradingMoney = tradingMoney;
        this.createTime = createTime;
        this.payStatus = payStatus;
        this.payWay = payWay;
    }
}
