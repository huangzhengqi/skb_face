package com.fzy.admin.fp.member.sem.vo;

import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FaceAliMemberVO {

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

    @ApiModelProperty("支付宝会员等级")
    private MemberAliLevel memberAliLevel;
}
