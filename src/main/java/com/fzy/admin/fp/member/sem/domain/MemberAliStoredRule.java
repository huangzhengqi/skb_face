package com.fzy.admin.fp.member.sem.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name ="lysj_member_ali_stored_rule")
public class MemberAliStoredRule extends BaseEntity{

    @Getter
    public enum GiftType {
        MONEY(1, "送金额"),
        INTEGRAL(2, "送积分"),
        COUPONS(3, "送优惠券");
        private Integer code;
        private String status;
        GiftType(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Status {
        ENABLE(1, "启用"),
        DISABLE(2, "禁用");

        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty(value = "商户ID")
    private String merchantId;
    @ApiModelProperty(value = "储值金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal storedMoney;
    @ApiModelProperty(value = "赠送类型")
    private Integer giftType;
    @ApiModelProperty(value = "赠送金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal giftMoney;
    @ApiModelProperty(value = "卡券ID")
    private String couponId;
    @ApiModelProperty(value = "储值笔数")
    private Integer storedAmount;
    @ApiModelProperty(value = "启用状态")
    private Integer status;

}
