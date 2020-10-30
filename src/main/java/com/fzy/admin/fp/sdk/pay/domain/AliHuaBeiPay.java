package com.fzy.admin.fp.sdk.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hzq
 * @Date 2020/9/2 11:25
 * @Version 1.0
 * @description 业务扩展参数
 */
@Data
public class AliHuaBeiPay {

    @ApiModelProperty("使用花呗分期要进行的分期数")
    private String hb_fq_num;

    @ApiModelProperty("使用花呗分期需要卖家承担的手续费比例的百分值，传入100代表100%")
    private String hb_fq_seller_percent;

//    @ApiModelProperty("系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID")
//    private String sys_service_provider_id;

}
