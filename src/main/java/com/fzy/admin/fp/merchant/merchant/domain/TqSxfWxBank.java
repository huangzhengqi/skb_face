package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 天阙随行付微信关联银行表
 */
@Data
@Entity
@Table(name = "tq_sxf_wx_bank")
public class TqSxfWxBank extends BaseEntity {

    @ApiModelProperty("天阙随行付银行名称")
    private String tqSxfBankName;

    @ApiModelProperty("银行编号")
    private String BankNo;

    @ApiModelProperty("微信银行名称")
    private String wxBankName;


}
