package com.fzy.admin.fp.pay.pay.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class WxFacePayVO {

    @ApiModelProperty(value = "错误码 成功：SUCCESS  ERROR/PARAM_ERROR/SYSTEMERROR")
    private String returnCode;

    @ApiModelProperty(value = "对错误码的描述")
    private String returnMsg;

    @ApiModelProperty(value = "SUCCESS：支付成功 FAIL：根据 err_code 的指引决定下一步操作。")
    private String resultCode;

    @ApiModelProperty(value = "会员信息")
    private Member member;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;


}
