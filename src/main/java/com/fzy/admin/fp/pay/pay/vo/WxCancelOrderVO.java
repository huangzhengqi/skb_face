package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxCancelOrderVO {

    @ApiModelProperty(value = "错误码 SUCCESS/FAIL")
    private String returnCode;

    @ApiModelProperty(value = "SUCCESS/FAIL")
    private String resultCode;


    @ApiModelProperty(value = "是否需要继续调用撤销，Y-需要，N-不需要")
    private String reCall;


}
