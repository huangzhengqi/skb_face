package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随行付
 */
@Data
public class SxfPayFaceAuthInfoDTO {

    @ApiModelProperty(value = "门店编号， 由商户定义， 各门店唯一")
    private String storeId;
    @ApiModelProperty(value = "门店名称，由商户定义。（可用于展示）")
    private String store_name;
    @ApiModelProperty(value = "终端设备编号，由商户定义")
    private String deviceId;
    @ApiModelProperty(value = "初始化数据。由微信人脸SDK的接口返回")
    private String rawdata;
    @ApiModelProperty(value = "订单编号")
    private String ordNo;
}
