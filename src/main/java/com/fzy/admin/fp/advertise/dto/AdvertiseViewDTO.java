package com.fzy.admin.fp.advertise.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description
 */
@Data
public class AdvertiseViewDTO {

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "5商户app首页广告 6客户小程序 7（小程序）客户付完款页面 8（设备）启动页 9(设备)会员支付页 10（设备）支付成功页 11app收款后广告 12（设备）非会员支付页 13（设备）收退押金页")
    private Integer fromRange;

    @ApiModelProperty("行业")
    private Integer industryCategory;

    private Integer status;

    @ApiModelProperty("设备类型 1支付宝 2微信")
    private Integer deviceType;
}
