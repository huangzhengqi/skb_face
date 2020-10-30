package com.fzy.admin.fp.member.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ：drj.
 * @Date ：Created in 21:19 2019/5/15
 * @Description: 卡券
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_coupon")
public class Coupon extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum RemindType {
        /**
         * 卡券过期提醒
         */
        EFFECTIVE(1, "有效"),
        UNEFFECTIVE(2, "无效");

        private Integer code;
        private String status;

    }


    @Getter
    @AllArgsConstructor
    public enum CouponSourceType {
        /**
         * 同步微信卡包
         */
        SYNC(1, "同步"),
        UNSYNC(2, "不同步");

        private Integer code;
        private String status;
    }

    @ApiModelProperty("商户id")
    private String merchantId;

    @Column(columnDefinition = "decimal(10,2)")
    @NotNull(message = "卡券面值不能为空")
    @ApiModelProperty("卡券面值")
    private BigDecimal money;

    @NotNull(message = "卡券图片id不能为空")
    @ApiModelProperty("卡券图片id")
    private String photoId;

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

    @NotNull(message = "兑换数量不能为空")
    @ApiModelProperty("兑换数量")
    private Integer changeInventory;

    @NotNull(message = "发放总量不能为空")
    @ApiModelProperty("发放总量")
    private Integer totalInventory;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("活动有效开始时间")
    private Date actTimeStart;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("活动有效结束时间")
    private Date actTimeEnd;

    @ApiModelProperty("卡券说明")
    private String remark;

    @NotNull(message = "最低消费不能为空")
    @Column(columnDefinition = "decimal(10,2)")
    @ApiModelProperty("最低消费")
    private BigDecimal miniExpendLimit;

    @NotNull(message = "使用时段不能为空")
    @ApiModelProperty("使用时段 采用id-id拼接的形式")
    private String useTimeWeek;

    @NotNull(message = "使用时间不能为空")
    @ApiModelProperty("使用时间")
    private String useTimeDay;

    @NotNull(message = "每位用户限领不能为空")
    @ApiModelProperty("每位用户限领")
    private Integer claimUpperLimit;

    @NotNull(message = "门店id不能为空")
    @ApiModelProperty("门店id 采用id-id拼接的形式")
    private String storeIds;

    @NotNull(message = "卡券过期提醒不能为空")
    @ApiModelProperty("卡券过期提醒 1:有效,2:无效")
    private Integer remindType;

    @NotNull(message = "同步微信卡包不能为空")
    @ApiModelProperty("同步微信卡包 1:同步,2:不同步")
    private Integer couponSourceType;

    @ApiModelProperty("卡券标题")
    private String title;

    @ApiModelProperty("卡券颜色")
    private String wxColor;

    @NotNull(message = "活动状态不能为空")
    @ApiModelProperty("活动状态 1未开始 2进行中 3已结束")
    private Integer actStatus;


    @NotNull(message = "是否人为中断活动不能为空")
    @ApiModelProperty("是否人为中断活动 0不是 1是")
    private Integer interrupt;

    @NotNull(message = "校验卡券有效期的种类不能为空")
    @ApiModelProperty("校验卡券有效期的种类 0有效天数决定 1卡券时间起止天决定")
    private Integer validType;

    @ApiModelProperty("推广图片")
    private String image;

    @ApiModelProperty("判断是否有新增会员卡而赠送的卡券 1不是2是绑卡3是储值 赠送卡券不属于活动范围")
    private Integer cardType;

    @ApiModelProperty("创建卡券活动有绑定微信时填充微信卡券id")
    private String cardId;

    @ApiModelProperty("卡券类型 1：微信  2：支付宝")
    private Integer type;

}