package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 * @Description: 满减优惠
 */

@Entity
@Table(name = "pay_rule")
@ApiModel
@Data
public class PayRule extends BaseEntity {

    @ApiModelProperty("充值赠送的金额 1可立即使用 2下次消费可使用")
    private Integer rechargeUse;

    @ApiModelProperty("充值赠送的金额 1可抵扣 0不可抵扣")
    private Integer integralUse;

    @ApiModelProperty("积分规则json格式 例子{'integralNum':'','deduction':'','deductionMax':''}")
    private String integralRule;

    @ApiModelProperty("折扣/优惠券/满减优惠是否同享  1仅试用满减 2仅使用优惠券 3两个一起享用")
    private Integer simultaneouslyUse;

    @ApiModelProperty("商户id")
    private String merchantId;
}