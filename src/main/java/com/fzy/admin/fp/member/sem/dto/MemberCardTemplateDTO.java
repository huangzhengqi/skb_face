package com.fzy.admin.fp.member.sem.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MemberCardTemplateDTO {

    @ApiModelProperty(value = "LOGO")
    private String logoId;

    @ApiModelProperty(value="LOGO地址")
    private String logoUrl;

    @ApiModelProperty(value = "背景图片")
    private String backgroundId;

    @ApiModelProperty(value = "背景图片地址")
    private String backgroundUrl;

    @ApiModelProperty(value = "支付宝创建模板ID")
    private String tempateId;

    @ApiModelProperty(value="背景颜色")
    private String color;

    @ApiModelProperty(value = "字体颜色")
    private String bgColor;

    @ApiModelProperty(value = "商户ID")
    private String merchantId;

    @ApiModelProperty(value = "使用期限")
    private Integer term;

    @ApiModelProperty(value = "会员卡名称")
    private String name;

    @ApiModelProperty(value = "商户联系电话")
    private String phone;

    @ApiModelProperty(value = "会员卡特权说明")
    private String privilegeExplain;

    @ApiModelProperty(value = "会员卡使用须知")
    private String tip;

    @ApiModelProperty(value = "会员卡领取方式")
    private Integer acceptWay;

    @ApiModelProperty(value = "赠送积分数量")
    private Integer presentScores;

    @ApiModelProperty(value = "赠送优惠卷")
    private String couponId;

    private String backgroudId;
}
