package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 云喇叭配置表
 *
 */
@Data
@Entity
@Table(name = "feiyu_config")
public class FeiyuConfig {

    @Id
    @Column(length = 128)
    @ApiModelProperty(value = "id")
    @GeneratedValue(generator = "snowFlakeIdStrategy")
    @GenericGenerator(name = "snowFlakeIdStrategy", strategy = CommonConstant.SNOW_FLAKE_ID_STRATEGY)
    private String id;

    @ApiModelProperty(value = "门店id'")
    private String storeId;

    @ApiModelProperty(value = "设备id'")
    private String deviceId;

    @ApiModelProperty(value = "状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "备注'")
    private String remark;

    @ApiModelProperty(value = "支付后广告 中文最长 15 字 其他字节 30 字节")
    private String advStr;

    @ApiModelProperty(value = "是否启用后缀 1启用 0禁用")
    @Column(columnDefinition = "int(1) default 1")
    private Integer isSuffix;

    @ApiModelProperty(value = "商户id'")
    private String merchantId;


}
