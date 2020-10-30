package com.fzy.admin.fp.member.rule.domain;

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

/**
 * @Author ：drj.
 * @Date  ：Created in 15:54 2019/5/14
 * @Description: 储值规则表
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_stored_rule")
public class StoredRule extends BaseEntity {

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

    @ApiModelProperty("商户id")
    private String merchantId;

    @NotBlank(message = "请填写储值规则名称")
    @ApiModelProperty("储值规则名称")
    private String name;

    @Column(columnDefinition = "decimal(10,2)")
    @ApiModelProperty("储值金额")
    private BigDecimal storedMoney;

    @ApiModelProperty("赠送类型")
    private Integer giftType;

    @Column(columnDefinition = "decimal(10,2)")
    @ApiModelProperty("赠送数额")
    private BigDecimal giftMoney;

    @ApiModelProperty("卡券id")
    private String couponId;

    @ApiModelProperty("储值笔数")
    private Integer storedAmount;

    @ApiModelProperty("启用状态")
    private Integer status;

    @ApiModelProperty("卡券赠送金额")
    @Transient
    private BigDecimal couponMoney;

    @ApiModelProperty("冗余字段，小程序支付下单后将选中的储值规则类转成json保存到订单快照中")
    @Transient
    private String memberId;


}
