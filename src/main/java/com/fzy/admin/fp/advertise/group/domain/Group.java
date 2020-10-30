package com.fzy.admin.fp.advertise.group.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 群组管理
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_group")
public class Group extends CompanyBaseEntity {

    @NotNull(message = "群组名称不能为空")
    @ApiModelProperty(value = "群组名称")
    private String groupName;

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("商户数量")
    private Integer merchantNumber;

    @ApiModelProperty("设备数量")
    private Integer deviceNumber;

    @ApiModelProperty("支付宝设备数量")
    private Integer aliDeviceNumber;

    @ApiModelProperty("微信设备数量")
    private Integer wxDeviceNumber;

    @ApiModelProperty("按区域")
    private String regionName;

    @ApiModelProperty("按代理")
    private String proxyName;

    @ApiModelProperty("按行业")
    private String industryName;

    @ApiModelProperty("按笔数")
    private String numberName;

    @ApiModelProperty("按流水")
    private String moneyName;

}
