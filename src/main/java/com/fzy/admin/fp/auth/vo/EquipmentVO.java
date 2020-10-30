package com.fzy.admin.fp.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("设备类型 1支付宝 2微信")
    private Integer deviceType;

    @ApiModelProperty("交易笔数")
    private long orderCount;

    @ApiModelProperty("所属门店")
    private String storeName;

    @ApiModelProperty("所属商家")
    private String merchantName;

    @ApiModelProperty("上级")
    private String companyName;

    @ApiModelProperty("设备编号")
    private String deviced;

    @ApiModelProperty("金额")
    private BigDecimal actPayPrice;

    @ApiModelProperty("收银模式 1收银 2收银+押金 3收银+结算 4收银+押金+结算")
    private Integer mode;

//    @ApiModelProperty(value = "类型0普通商户 1分销开出来的商户")
    //类型0普通商户 1分销开出来的商户
    private Integer type;
    //业务员Id
    private String managerId;

    public EquipmentVO(Integer mode, String id, String deviceId, long orderCount, String storeName, String merchantName, String companyName, Integer deviceType, BigDecimal actPayPrice) {
        this.mode = mode;
        this.id = id;
        this.deviced = deviceId;
        this.orderCount = orderCount;
        this.storeName = storeName;
        this.merchantName = merchantName;
        this.companyName = companyName;
        this.deviceType = deviceType;
        this.actPayPrice = actPayPrice;
    }

    public EquipmentVO(Integer mode, String id, String deviceId, String storeName, String merchantName, String companyName, Integer deviceType) {
        this.mode = mode;
        this.id = id;
        this.deviced = deviceId;
        this.storeName = storeName;
        this.merchantName = merchantName;
        this.companyName = companyName;
        this.deviceType = deviceType;
    }

    public EquipmentVO(Integer mode, String id, String deviceId, String storeName, String merchantName, Integer deviceType) {
        this.mode = mode;
        this.id = id;
        this.deviced = deviceId;
        this.storeName = storeName;
        this.merchantName = merchantName;
        this.deviceType = deviceType;
    }

    public EquipmentVO(String id, Integer deviceType,  String storeName, String merchantName,  String deviced, Integer mode, Integer type,String managerId) {
        this.id = id;
        this.deviceType = deviceType;
        this.storeName = storeName;
        this.merchantName = merchantName;
        this.deviced = deviced;
        this.mode = mode;
        this.type = type;
        this.managerId=managerId;
    }
}
