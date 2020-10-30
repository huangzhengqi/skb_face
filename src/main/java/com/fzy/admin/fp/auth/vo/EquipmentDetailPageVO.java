package com.fzy.admin.fp.auth.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class EquipmentDetailPageVO implements Serializable {


    //    o.id,o.orderNumber, s.name,u.name,o.totalPrice,o.actPayPrice,o.disCountPrice,o.status,o.payWay,o.payChannel,o.createTime,o.payType
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("订单号")
    @Excel(name="订单号", width=30.0D, isImportField="true")
    private String orderNum;

    @ApiModelProperty("门店")
    @Excel(name="门店", width=30.0D, isImportField="true")
    private String storeName;

    @ApiModelProperty("收银员")
    @Excel(name="收银员", width=30.0D, isImportField="true")
    private String staffName;

    @ApiModelProperty("订单金额")
    @Excel(name="订单金额", width=30.0D, isImportField="true")
    private BigDecimal orderMoney;


    @ApiModelProperty("实际付款")
    @Excel(name="实际付款", width=30.0D, isImportField="true")
    private BigDecimal actualPayMoney;

    @ApiModelProperty("优惠")
    @Excel(name="优惠", width=30.0D, isImportField="true")
    private BigDecimal discountMoney;

    @ApiModelProperty("订单状态")
    @Excel(name="订单状态", width=30.0D, isImportField="true")
    private Integer status;

    @ApiModelProperty("支付方式")
    @Excel(name="支付方式", width=30.0D, isImportField="true")
    private Integer payWay;

    @ApiModelProperty("支付通道")
    @Excel(name="支付通道", width=30.0D, isImportField="true")
    private Integer paychannel;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="创建时间", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("支付类型")
    @Excel(name="支付类型", width=30.0D, isImportField="true")
    private Integer payType;

//    public EquipmentDetailPageVO(String id, String orderNum, String storeName, String staffName, BigDecimal orderMoney, BigDecimal actualPayMoney, BigDecimal discountMoney, Integer status, Integer payWay, Integer paychannel, Date createTime, Integer payType) {
//        this.id=id;
//        this.orderNum=orderNum;
//        this.staffName=staffName;
//        this.storeName=storeName;
//        this.orderMoney=orderMoney;
//        this.createTime=createTime;
//        this.actualPayMoney=actualPayMoney;
//        this.discountMoney=discountMoney;
//        switch (status.intValue()) {
//
//            case 1:
//                this.status="未支付";
//                break;
//            case 2:
//                this.status="支付成功";
//                break;
//            case 3:
//                this.status="支付失败";
//                break;
//            case 4:
//                this.status="已撤销";
//                break;
//            case 5:
//                this.status="全额退款";
//                break;
//            case 6:
//                this.status="部分退款";
//                break;
//            default:
//                this.status="未知";
//                break;
//        }
//
//        switch (payWay.intValue()) {
//            case 1:
//                this.payWay="微信";
//                break;
//            case 2:
//                this.payWay="支付宝";
//                break;
//            case 3:
//                this.payWay="银行卡";
//                break;
//            case 4:
//                this.payWay="会员卡";
//                break;
//            default:
//                this.payWay="未知";
//                break;
//        }
//
//        switch (paychannel.intValue()) {
//            case 1:
//                this.paychannel="官方";
//                break;
//            case 6:
//                this.paychannel="富有";
//                break;
//            case 7:
//                this.paychannel="随行付";
//                break;
//            case 12:
//                this.paychannel="天阙随行付";
//                break;
//            default:
//                this.payWay="未知";
//                break;
//        }
//        switch (payType.intValue()) {
//            case 1:
//                this.payType="扫码";
//                return;
//            case 3:
//                this.payType="刷脸";
//                return;
//        }
//        this.payType="未知";
//    }


    public EquipmentDetailPageVO() {
    }

    public EquipmentDetailPageVO(String id, String orderNum, String storeName, String staffName, BigDecimal orderMoney, BigDecimal actualPayMoney, BigDecimal discountMoney, Integer status, Integer payWay, Integer paychannel, Date createTime, Integer payType) {
        this.id=id;
        this.orderNum=orderNum;
        this.storeName=storeName;
        this.staffName=staffName;
        this.orderMoney=orderMoney;
        this.actualPayMoney=actualPayMoney;
        this.discountMoney=discountMoney;
        this.status=status;
        this.payWay=payWay;
        this.paychannel=paychannel;
        this.createTime=createTime;
        this.payType=payType;
    }
}
