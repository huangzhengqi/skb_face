package com.fzy.admin.fp.member.credits.domain;

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

/**
 * @author lb
 * @date 2019/5/17 14:00
 * @Description 积分明细
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_credits_info")
public class CreditsInfo extends BaseEntity {

    @Getter
    public enum Trade {
        CONSUM_SCORE(1, "积分商品兑换"),
        RECHARGE_GIFTS(2, "充值赠送积分"),
        CONSUM_CONSUM(3, "消费赠送积分"),
        REFUND(4, "退款"),
        CARD_GIVEN(6, "开卡赠送"),
        CONSUM_EXPENSE(7,"消费费用"),
        MEMBER_UPDATE_GIVEN(8,"会员升级赠送");
        private Integer code;
        private String message;

        Trade(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @NotBlank(message = "请填入会员卡号")
    @ApiModelProperty("会员卡号")
    private String memberNum;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("交易时间")
    private Date tradeTime;

    @ApiModelProperty("交易积分正负代表增减")
    private Integer tradeScores;

    @ApiModelProperty("交易后可用积分")
    private Integer avaCredits;

    @NotBlank(message = "请填入商户id")
    @ApiModelProperty("商户Id")
    private String merchantId;

    @ApiModelProperty("交易门店id")
    private String storeId;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("积分明细增减类型 1增加 2减少")
    private Integer tradeNum;

    @ApiModelProperty("明细类型 trade")
    private Integer infoType;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("开始明细时间")
    private Date startTradeTime;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("结束明细时间")
    private Date endTradeTime;

}
