package com.fzy.admin.fp.member.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.Length;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author hzq
 * @Date 2020/8/25 17:02
 * @Version 1.0
 * @description  会员卡DTO
 */
@Data
public class MemberCardDTO {

    @ApiModelProperty("商家自定义会员卡背景图")
    private String backgroundPicUrl;

    @ApiModelProperty("会员卡名称")
    private String title;

    @ApiModelProperty("券颜色")
    private String color;

    @ApiModelProperty("卡券使用提醒，字数上限为16个汉字")
    private String notice;

    @ApiModelProperty("卡券使用说明，字数上限为1024个汉字")
    private String description;

    @ApiModelProperty("卡券库存的数量，不支持填写0，上限为100000000")
    private Integer quantity;

    @ApiModelProperty("校验卡券有效期的种类 0有效天数决定 1卡券时间起止天决定")
    private Integer validType;

    @ApiModelProperty("卡券领取后有效期")
    private Integer claimedTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("卡券有效开始时间")
    private Date validTimeStart;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("卡券有效结束时间")
    private Date validTimeEnd;

    @ApiModelProperty("会员卡特权说明,限制1024汉字")
    private String prerogative;
}
