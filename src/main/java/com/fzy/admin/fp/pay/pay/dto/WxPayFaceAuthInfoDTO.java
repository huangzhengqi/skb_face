package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxPayFaceAuthInfoDTO {


    @ApiModelProperty(value = "门店编号， 由商户定义， 各门店唯一")
    private String storeId;
    @ApiModelProperty(value = "门店名称，由商户定义。（可用于展示）")
    private String store_name;
    @ApiModelProperty(value = "终端设备编号，由商户定义")
    private String deviceId;
    @ApiModelProperty(value = "初始化数据。由微信人脸SDK的接口返回")
    private String rawdata;


}
