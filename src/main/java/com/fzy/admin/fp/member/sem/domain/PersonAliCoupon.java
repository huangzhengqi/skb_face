package com.fzy.admin.fp.member.sem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
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
 * 支付宝个人卡券记录
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_person_coupon")
public class PersonAliCoupon extends BaseEntity {

    @Getter
    public enum Status {
        NO_USE(1, "未使用"),
        USE(2, "已使用"),
        INVALID(3, "已作废");
        private Integer code;
        private String message;

        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }


    @Getter
    public enum SynStatus {
        LIMIT(0, "该卡券不支持同步功能"),
        NO_SYN(1, "未同步"),
        SYN(2, "已同步");
        private Integer code;
        private String message;

        SynStatus(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @ApiModelProperty(value = "商户ID")
    private String merchantId;

    @ApiModelProperty(value = "卡券ID")
    private String couponId;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "卡券面值")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal money;

    @ApiModelProperty(value = "卡券图片ID")
    private String photoId;

    @ApiModelProperty(value = "卡券有效开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTimeStart;

    @ApiModelProperty(value = "卡券有效结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTimeEnd;

    @ApiModelProperty(value = "卡券说明")
    private String remark;

    @ApiModelProperty(value = "最低消费")
    private BigDecimal miniExpendLimit;

    @ApiModelProperty(value = "使用时段采用id-id拼接的形式")
    private String useTimeWeek;

    @ApiModelProperty(value = "使用时间")
    private String useTimeDay;

    @ApiModelProperty(value = "用户限领")
    private Integer claimUpperLimit;

    @ApiModelProperty(value = "门店ID逗号分隔")
    private String storeIds;

    @ApiModelProperty(value = "核销码")
    private String code;
    @ApiModelProperty(value = "卡券当前状态")
    private Integer status;

    @ApiModelProperty(value = "卡券同步状态 1-同步 2-不同步")
    private Integer synStatus;

    @ApiModelProperty(value = "过期提醒")
    private Integer remindTimes;

    @ApiModelProperty(value = "核销人ID")
    private String userId;

    @ApiModelProperty(value = "核销人姓名")
    private String userName;

    @ApiModelProperty(value = "核销日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;
}
