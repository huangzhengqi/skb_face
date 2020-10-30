package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author hzq
 * @date 2020-05-12
 * @Description:   微信用户表
 */
@Entity
@Table(name = "lysj_dist_wx_user")
@Data
public class WxUser extends CompanyBaseEntity {


    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "微信昵称")
    private String nickname;

}
