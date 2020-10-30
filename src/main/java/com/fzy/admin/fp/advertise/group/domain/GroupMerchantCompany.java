package com.fzy.admin.fp.advertise.group.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 群组商户公司映射表
 */
@Data
@Entity
@Table(name = "lysj_group_merchant_company")
public class GroupMerchantCompany extends CompanyBaseEntity {

    @ApiModelProperty("群组id")
    private String groupId;

    @ApiModelProperty("商户id")
    private String merchantId;

    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

}
