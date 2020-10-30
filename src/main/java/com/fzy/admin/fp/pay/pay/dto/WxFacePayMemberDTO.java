package com.fzy.admin.fp.pay.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxFacePayMemberDTO {



    @ApiModelProperty(value = "总金额")
    private BigDecimal totalFee;
    @ApiModelProperty(value = "openId")
    private String openId;




}
