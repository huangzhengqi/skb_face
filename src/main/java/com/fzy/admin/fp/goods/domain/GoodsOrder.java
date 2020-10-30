package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单表
 */

@Entity
@Table(name="goods_order")
@Data
public class GoodsOrder extends BaseEntity {

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单时间")
    private Date payTime;

    @ApiModelProperty("订单金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal orderPrice;

    @ApiModelProperty("实际支付金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal payPrice;

    @ApiModelProperty("（1微信  2支付宝  3银行卡  4会员卡  99未知）")
    private Integer payType;

    @ApiModelProperty("会员id")
    private String memberId;

    @ApiModelProperty("优惠券")
    private String couponName;

    @ApiModelProperty("优惠金额")
    private BigDecimal couponPrice;

    @ApiModelProperty("折扣")
    private String discountName;

    @ApiModelProperty("折扣金额")
    private BigDecimal discountPrice;

    @ApiModelProperty("满减")
    private String fullReductionName;

    @ApiModelProperty("满减金额")
    private BigDecimal fullReductionPrice;

    @ApiModelProperty("积分抵扣")
    private String integralName;

    @ApiModelProperty("积分抵扣金额")
    private BigDecimal integralPrice;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("(状态  1已下单  2已支付)")
    private Integer status;

    @ApiModelProperty("会员消费记录id")
    private String storeRecordId;

    @Column(columnDefinition="int(11) default 0")
    @ApiModelProperty("行业分类 0超市  1自助点餐  2医药 3加油站 4景区 ")
    private Integer industryCategory;

    @ApiModelProperty("使用方式  0默认  1外带 2堂食 3堂食餐号")
    @Column(columnDefinition="int(11) default 0")
    private Integer useType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("取号")
    private String takeNumber;

    @ApiModelProperty("操作员id")
    private String userId;

    @ApiModelProperty("核销")
    @Column(columnDefinition="int(11) default 0")
    private Integer destroy;
}
