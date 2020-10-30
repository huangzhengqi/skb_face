package com.fzy.admin.fp.advertise.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description
 */
@Data
public class AdvertiseClickDTO {

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "广告id")
    private String advertiseId;

    @ApiModelProperty(value = "6客户小程序 7客户付完款页面 8（设备）启动页 9（设备）会员支付页 10（设备）支付成功页 11app")
    private Integer fromRange;


}
