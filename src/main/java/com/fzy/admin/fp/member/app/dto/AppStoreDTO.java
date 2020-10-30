package com.fzy.admin.fp.member.app.dto;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wtl on 2019-06-10 22:31
 * @description APP会员充值参数
 */
@Data
public class AppStoreDTO {

    @NotBlank(message = "会员ID为空")
    @ApiModelProperty(value = "会员id")
    private String memberId;

    @NotBlank(message = "储值规则ID为空")
    @ApiModelProperty(value = "储值规则id")
    private String storeRuleId;

    @NotBlank(message = "付款码为空")
    @ApiModelProperty(value = "付款码")
    private String authCode;

}
