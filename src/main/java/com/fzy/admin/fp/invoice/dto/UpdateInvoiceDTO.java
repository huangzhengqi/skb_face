package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.invoice.enmus.InvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("更新发票抬头相关信息")
@Data
public class UpdateInvoiceDTO {
    @ApiModelProperty(value = "发票抬头信息Id", required = true)
    @NotNull
    private String id;
    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull
    private String memberId;
    @ApiModelProperty(value = "发票抬头[普通发票]", required = true)
    @NotNull
    private String title;
    @ApiModelProperty("抬头类型")
    private InvoiceType type;
    @ApiModelProperty("单位名称")
    private String company;
    @ApiModelProperty("纳税人识别号")
    private String companyCode;
    @ApiModelProperty("注册地址")
    private String regAddr;
    @ApiModelProperty("注册电话")
    private String regPhone;
    @ApiModelProperty("收票人邮箱")
    private String recEmail;
    @ApiModelProperty("开户银行")
    private String regBname;
    @ApiModelProperty("银行账户")
    private String regBaccount;
    @ApiModelProperty("收票人姓名")
    private String recName;
    @ApiModelProperty("收票人手机号")
    private String recMobilePhone;
    @ApiModelProperty("收票人省份")
    private String recProvince;
    @ApiModelProperty("送票地址")
    private String gotoAddr;
}
