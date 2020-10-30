package com.fzy.admin.fp.member.sem.domain;


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

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_coupon")
public class MemberCoupon extends BaseEntity{

    @Getter
    @AllArgsConstructor
    public enum CouponSourceType {
        SYNC(1, "同步"),
        UNSYNC(2, "不同步");

        private Integer code;
        private String status;
    }

    @ApiModelProperty(value = "卡券面值")
    @Column(columnDefinition = "decimal(10,2)")
    @NotNull(message = "卡券面值不能为空")
    private BigDecimal money;

    @ApiModelProperty(value="卡券图片id")
    @NotNull(message = "卡券图片id不能为空")
    private String photoId;

    @ApiModelProperty(value="卡券图片路径")
    private String photoUrl;

    @ApiModelProperty(value="校验卡券有效期的种类0有效天数决定1卡券时间起止天决定")
    @NotNull(message = "校验卡券有效期的种类不能为空")
    private Integer validType;

    @ApiModelProperty(value = "卡券领取后有效期")
    private Integer claimedTime;

    @ApiModelProperty(value = "卡券有效开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validTimeStart;

    @ApiModelProperty(value = "卡券有效结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validTimeEnd;

    @ApiModelProperty(value = "发放数量类型 1-不限制  2-限制")
    private Integer sendType;


    @ApiModelProperty(value = "发放总量")
    @NotNull(message = "发放总量不能为空")
    private Integer totalInventory;

    @ApiModelProperty(value = "兑换数量")
    private Integer changeInventory;

    @ApiModelProperty(value = "活动有效开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actTimeStart;

    @ApiModelProperty(value = "活动有效结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actTimeEnd;

    @ApiModelProperty(value = "卡券说明")
    private String remark;

    @ApiModelProperty(value="最低消费")
    @NotNull(message = "最低消费不能为空")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal miniExpendLimit;

    @ApiModelProperty(value = "使用时段 采用id-id拼接的形式")
    @NotNull(message = "使用时段不能为空")
    private String useTimeWeek;

    @ApiModelProperty(value = "使用时间")
    private String useTimeDay;

    @ApiModelProperty(value = "用户限领数量")
    private Integer claimUpperLimit;

    @ApiModelProperty(value = "门店id 采用id-id拼接的形式")
    @NotNull(message = "门店id不能为空")
    private String storeIds;

    @ApiModelProperty(value = "同步支付宝卡包 1:同步,2:不同步")
    private Integer couponSourceType;

    @ApiModelProperty(value = "活动状态 1未开始2进行中3已结束")
    @NotNull(message = "活动状态不能为空")
    private Integer actStatus;

    @ApiModelProperty(value = "是否人为中断活动 0不是 1是")
    @NotNull(message = "是否人为中断活动不能为空")
    private Integer interrupt;

    @ApiModelProperty(value = "判断是否有新增会员卡而赠送的卡券 1不是2是绑卡3是储值 赠送卡券不属于活动范围")
    private Integer cardType;

    @ApiModelProperty(value = "创建卡券活动有绑定支付宝时填充支付宝模板id")
    private String templateId;

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

}
