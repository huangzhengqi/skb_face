package com.fzy.admin.fp.distribution.money.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-19 10:46:58
 * @Desp 提现
 **/
@Data
public class TakeInfoDTO {

    @ApiModelProperty("提现金额")
    private Double money;

    @ApiModelProperty("用户名称")
    @NotBlank(message = "账户姓名为空")
    private String name;

    @ApiModelProperty("账户")
    @NotBlank(message = "账户为空")
    private String account;

    @ApiModelProperty("提现的方式 0银行卡 1支付宝")
    private Integer type;

    @ApiModelProperty("提现状态 0待处理 1已打款 2已驳回")
    private String status;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("驳回信息")
    private String remark;

    private String id;

    private String serviceProviderId;

}
