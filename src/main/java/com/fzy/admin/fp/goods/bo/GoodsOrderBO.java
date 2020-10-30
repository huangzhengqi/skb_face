package com.fzy.admin.fp.goods.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单BO
 */

@Data
@Getter
@Setter
public class GoodsOrderBO {

    private String id;

    @ApiModelProperty("商品名称")
    private String storeName;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("支付时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty("订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal orderPrice;

    @ApiModelProperty("实际支付金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal payPrice;

    @ApiModelProperty("1会员卡 2支付宝 3微信")
    private Integer payType;

    public GoodsOrderBO() {
    }

    public GoodsOrderBO(String id, String storeName, String orderNo, Date payTime, BigDecimal orderPrice, BigDecimal payPrice, Integer payType) {
        this.id = id;
        this.storeName = storeName;
        this.orderNo = orderNo;
        this.payTime = payTime;
        this.orderPrice = orderPrice;
        this.payPrice = payPrice;
        this.payType = payType;
    }
}
