package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-17 18:08:22
 * @Desp
 **/

@Data
@Entity
@Table(name = "lysj_dist_app_down")
public class NoteConfig extends CompanyBaseEntity{
    @ApiModelProperty("锋之云0 阿里云1")
    private Integer type=0;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("账号")
    private String fzyAccount;

    @ApiModelProperty("密码")
    private String fzyPassword;

    @ApiModelProperty("短信模板")
    private String templateCode;

    @ApiModelProperty("短信签名")
    private String signName;

    @ApiModelProperty("keyId")
    private String accessKeyId;

    @ApiModelProperty("accessKeySecret")
    private String accessKeySecret;
}
