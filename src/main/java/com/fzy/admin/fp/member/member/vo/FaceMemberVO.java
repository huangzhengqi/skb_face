package com.fzy.admin.fp.member.member.vo;


import com.fzy.admin.fp.member.member.domain.MemberLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class FaceMemberVO {
    private String id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String head;

    @ApiModelProperty("储值余额")
    private BigDecimal balance;

    @ApiModelProperty("积分")
    private Integer scores;

    @ApiModelProperty("不可用余额")
    private BigDecimal freezeBalance;

    @ApiModelProperty("会员等级")
    private MemberLevel memberLevel;

}
