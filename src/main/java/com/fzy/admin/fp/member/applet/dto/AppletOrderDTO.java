package com.fzy.admin.fp.member.applet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class AppletOrderDTO {
    private String i;
    private String hid;

    public void setI(String i) {
        this.i = i;
    }

    @ApiModelProperty("扫码订单订单编号")
    private String order_id;

    @ApiModelProperty("金额 单位分")
    private BigDecimal total;

    @ApiModelProperty(" openid")
    private String openid;


}
