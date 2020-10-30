package com.fzy.admin.fp.wx.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author hzq
 * @Date 2020/9/10 14:27
 * @Version 1.0
 * @description  返佣微信红包明细表
 */
@Data
@Entity
@Table(name = "wx_red_pack_detail")
public class WxRedPackDetail extends CompanyBaseEntity {

    @ApiModelProperty("商户订单号")
    private String mchBillno;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("公众账号appid")
    private String wxAppid;

    @ApiModelProperty("用户openid")
    private String reOpenid;

    @ApiModelProperty("返佣金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal totalAmount;

    @ApiModelProperty("微信单号")
    private String sendListid;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("交易金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal actPayPrice;

    @ApiModelProperty("实际营收")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal actualMoney;

    @ApiModelProperty(value="退款金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal refundPayPrice;

    @ApiModelProperty("发放状态 0没发放 1已发放 2未绑定")
    private Integer returnType;

    @ApiModelProperty("红包状态 0未领取 1已领取 ")
    private Integer status;

    @ApiModelProperty("查询状态 0未查询 1已查询")
    private Integer findStatus;

}
