package com.fzy.admin.fp.member.member.dto;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class WxMaSessionDTO implements Serializable {

    @ApiModelProperty("微信小程序openId")
    private String openId;

    @ApiModelProperty("微信开发平台unionId，仅在小程序有关联开发平台时该字段该有值")
    private String unionId;
}
