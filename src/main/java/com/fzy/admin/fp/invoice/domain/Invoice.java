package com.fzy.admin.fp.invoice.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.invoice.enmus.InvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@ApiModel("用户发票抬头相关信息")
@Entity
@Data
@Table(name = "lysj_invoice")
public class Invoice extends BaseEntity {
    private static final long serialVersionUID = -5627944347344235334L;
    @ApiModelProperty("会员ID")
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @ApiModelProperty("抬头类型")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @ApiModelProperty("发票抬头[普通发票].")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty("单位名称")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("纳税人识别号")
    @Column(name = "company_code", length = 50)
    private String companyCode;

    @ApiModelProperty("注册地址")
    @Column(name = "reg_addr", length = 50)
    private String regAddr;

    private String recEmail;

    private String regPhone;

    private String recMobilePhone;

    private String recName;

    private String regBaccount;

    private String regBname;

    private String recProvince;

    private String gotoAddr;
}
