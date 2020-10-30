package com.fzy.admin.fp.push.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author hzq
 * @Date 2020/10/7 15:00
 * @Version 1.0
 * @description 推送用户表
 */
@Data
@Entity
@Table(name = "lysj_push_user")
public class PushUser extends CompanyBaseEntity {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("推送id")
    private String pushId;

    @ApiModelProperty("信息状态 1未查看 2已查看")
    private Integer type;
}
