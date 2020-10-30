package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by zk on 2019-05-29 15:18
 * @description 商户小程序配置
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_applet_config")
public class MerchantAppletConfig extends BaseEntity {

    @NotBlank(message = "merchantId为空，请重试")
    @ApiModelProperty("商户id")
    private String merchantId;

    @NotBlank(message = "请填写appId")
    @ApiModelProperty("商户appId")
    private String appId;

    @NotBlank(message = "请填写appKey")
    @ApiModelProperty("商户appKey")
    private String appKey;

    @ApiModelProperty("小程序昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("原始id")
    private String userName;

    @ApiModelProperty("主体名称")
    private String principalName;

    @ApiModelProperty("小程序审核id")
    private String auditId;
}
