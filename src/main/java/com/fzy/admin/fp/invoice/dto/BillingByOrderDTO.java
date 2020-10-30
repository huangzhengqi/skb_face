package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.invoice.enmus.InvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("通过消费订单开票")
@Data
public class BillingByOrderDTO {
    @ApiModelProperty(value = "需要开票的订单列表", required = true)
    @NotNull
    private String[] orderIds;

    @ApiModelProperty(value = "提交开票申请的用户", required = true)
    @NotNull
    private String memberId;

    @ApiModelProperty(value = "开票的商户", required = true)
    @NotNull
    private String merchantId;

    @ApiModelProperty(value = "发票抬头[普通发票]", required = true)
    @NotNull
    private String title;

    @ApiModelProperty(value = "开票类型", required = true)
    @NotNull
    private InvoiceType invoiceType;

    @ApiModelProperty("单位名称")
    private String company;

    @ApiModelProperty("纳税人识别号")
    private String companyCode;

    @ApiModelProperty("注册地址")
    private String regAddr;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("移动电话")
    private String recMobilePhone;

    @ApiModelProperty("电子邮箱")
    private String recEmail;

    @ApiModelProperty("移动账户")
    private String regBaccount;

    @ApiModelProperty("移动姓名")
    private String recName;
}
