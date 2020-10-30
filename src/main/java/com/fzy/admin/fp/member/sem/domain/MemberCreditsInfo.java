package com.fzy.admin.fp.member.sem.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_credits_info")
public class MemberCreditsInfo extends BaseEntity {

    @Getter
    public enum Trade {
        CONSUM_SCORE(1, "积分商品兑换"),
        RECHARGE_GIFTS(2, "充值赠送积分"),
        CONSUM_CONSUM(3, "消费赠送积分"),
        REFUND(4, "退款"),
        CARD_GIVEN(6, "开卡赠送");
        private Integer code;
        private String message;

        Trade(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @ApiModelProperty(value = "会员卡号")
    @NotBlank(message = "请填入会员卡号")
    private String memberNum;

    @ApiModelProperty(value = "支付宝会员ID")
    private String buyerId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "交易类型")
    private String transactionType;


    @ApiModelProperty(value = "交易时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tradeTime;

    @ApiModelProperty(value = "交易积分正负代表增减")
    private Integer tradeScores;

    @ApiModelProperty(value = "交易后可用积分")
    private Integer avaCredits;

    @ApiModelProperty(value = "商户ID")
    @NotBlank(message = "请填入商户id")
    private String merchantId;

    @ApiModelProperty(value = "交易门店ID")
    private String storeId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "积分明细增减类型 1增加 2减少")
    private Integer tradeNum;

    @ApiModelProperty(value = "明细类型")
    private Integer infoType;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date start_tradeTime;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date end_tradeTime;

}
