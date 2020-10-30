package com.fzy.admin.fp.auth.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by wtl on 2019-04-23 22:05
 * @description 微信开发平台服务商配置表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_wx_open_config")
public class WxOpenConfig extends CompanyBaseEntity {


    @ApiModelProperty(value = "appid")
    private String appId;

    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    @ApiModelProperty(value = "消息校验token")
    private String token;

    @ApiModelProperty(value = "消息加解key")
    private String msgKey;

    @ApiModelProperty(value = "额外参数 json格式")
    private String extra;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "文件内容")
    private String filecontent;


}
