package com.fzy.admin.fp.ali.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ali_pay_info")
public class AliPayInfo extends CompanyBaseEntity {

    @ApiModelProperty(value = "支付宝账户")
    private String aliName;

    @ApiModelProperty(value = "邮箱")
    private String aliAccountNumber;

    private Integer type;

    @ApiModelProperty("用户id")
    private String userId;
}
