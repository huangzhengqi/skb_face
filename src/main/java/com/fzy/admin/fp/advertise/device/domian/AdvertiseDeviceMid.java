package com.fzy.admin.fp.advertise.device.domian;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备广告收银页商户关联表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_device_mid")
public class AdvertiseDeviceMid extends CompanyBaseEntity {

    @ApiModelProperty("群组id")
    private String groupId;

    @ApiModelProperty("广告设备id")
    private String advertiseDeviceId;

    @ApiModelProperty("商户id")
    private String merchantId;

    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

}
