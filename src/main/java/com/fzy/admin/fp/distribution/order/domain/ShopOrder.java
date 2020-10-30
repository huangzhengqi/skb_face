package com.fzy.admin.fp.distribution.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author  yy
 * @date   2019-12-2 16:23:27
 * @Description:  商城订单
 **/
@Data
@Entity
@Table(name="lysj_dist_shop_order")
public class ShopOrder extends CompanyBaseEntity {
    @Getter
    public enum Status {
        /**
         * 状态
         */
        NOPAY(0, "待支付"),
        PAY(1, "待发货"),
        DELIVERY(2, "已发货"),
        OVER(3, "已完成");

        private Integer code;
        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("用户id")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("待支付0 待发货1 已发货2 已完成3 待退款4 已退款5")
    @Column(columnDefinition = "int(1) default 0")
    private Integer status;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("余额0 支付宝1 微信2")
    @Column(columnDefinition = "int(1) default 0")
    private Integer payType;

    @ApiModelProperty("支付时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty("发货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    @ApiModelProperty("退款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    @ApiModelProperty("申请退款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyRefundTime;

    @ApiModelProperty("确认收货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    @ApiModelProperty("修改金额时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatePriceTime;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("收货地址")
    private String place;

    @ApiModelProperty("订单备注")
    private String remark;

    @ApiModelProperty("物流单号")
    private String trackNumber;

    @ApiModelProperty("订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal price;

    @ApiModelProperty("纪录")
    private String record;

    @ApiModelProperty("退款理由")
    private String refundReason;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("微信支付订单号")
    private String transactionId;

    @ApiModelProperty("用户")
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable=true)
    private DistUser distUser;

    @ApiModelProperty("商品")
    @OneToMany
    @JoinColumn(name = "shopOrderId", insertable = false, updatable = false, nullable=true)
    private List<OrderGoods> orderGoods = new ArrayList<>();
}
