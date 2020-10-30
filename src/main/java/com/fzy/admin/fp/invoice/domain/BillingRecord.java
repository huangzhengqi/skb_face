package com.fzy.admin.fp.invoice.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.invoice.enmus.InvoiceStatus;
import com.fzy.admin.fp.invoice.enmus.InvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("电子发票开票记录")
@Entity
@Data
@Table(name = "lysj_billing_record")
public class BillingRecord extends BaseEntity {

    private static final long serialVersionUID = -3929653132861368234L;
    @ApiModelProperty("抬头类型")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @ApiModelProperty("发票抬头[普通发票].")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty("发票内容[普通发票].")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("单位名称")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("纳税人识别号")
    @Column(name = "company_code", length = 50)
    private String companyCode;

    @ApiModelProperty("注册地址")
    @Column(name = "reg_addr", length = 50)
    private String regAddr;

    @ApiModelProperty("注册电话")
    @Column(name = "reg_phone", length = 12)
    private String regPhone;

    @ApiModelProperty("开户银行")
    @Column(name = "reg_bname")
    private String regBname;

    @ApiModelProperty("银行账户")
    @Column(name = "reg_baccount", length = 30)
    private String regBaccount;

    @ApiModelProperty("收票人姓名")
    @Column(name = "rec_name", length = 20)
    private String recName;

    @ApiModelProperty("收票人手机号")
    @Column(name = "rec_mobile_phone", length = 15)
    private String recMobilePhone;

    @ApiModelProperty("收票人邮箱")
    @Column(name = "rec_e_mail", length = 64)
    private String recEmail;

    @ApiModelProperty("售票人省份")
    @Column(name = "rec_province", length = 30)
    private String recProvince;

    @ApiModelProperty("送票地址")
    @Column(name = "goto_addr", length = 50)
    private String gotoAddr;

    @ApiModelProperty("申请开票时间")
    @Column(name = "apply_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyTime;

    @ApiModelProperty("开票时间")
    @Column(name = "billing_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billingTime;

    @ApiModelProperty(value = "关联订单", notes = "json数组")
    @Column(name = "order_ids", nullable = false, length = 512)
    private String orderIds;

    @ApiModelProperty("开票用户")
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @ApiModelProperty("关联商户")
    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @ApiModelProperty(notes = "发票请求流水号", required = true)
    @Column(name = "invoice_sn", nullable = false)
    private String invoiceSn;

    @ApiModelProperty(value = "发票状态", notes = "发票状态 00：已开具的正数发票 01：已开具的负数发票 02：未开具发票的作废发票 03：已开具正数发票的作废发票 04：已开具负数发票的作废发票;")
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status")
    private InvoiceStatus invoiceStatus;

    @ApiModelProperty("税控设备编号")
    @Column(name = "taxControl_equipment_num", nullable = false)
    private String taxControlEquipmentNum;

    @ApiModelProperty("含计金额")
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @ApiModelProperty("含计税额")
    @Column(name = "total_tax", nullable = false)
    private BigDecimal totalTax;

    @ApiModelProperty(notes = "税率，小数点最多3位 ", required = true)
    @Column(name = "tax_rate", nullable = false)
    private BigDecimal taxRate;

    @ApiModelProperty("电子发票下载地址")
    @Column(name = "download_url")
    private String downloadUrl;
}
