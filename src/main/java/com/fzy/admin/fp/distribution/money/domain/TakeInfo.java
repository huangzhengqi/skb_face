package com.fzy.admin.fp.distribution.money.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2019-11-18 17:54:17
 * @Desp 提现申请
 **/
@Entity
@Data
@Table(name = "lysj_dist_take_info")
public class TakeInfo extends CompanyBaseEntity{


    private BigDecimal sum;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("0未打款 1已打款 2已驳回")
    private Integer status;

    @ApiModelProperty("提现方式 0银行卡 1支付宝")
    private Integer takeType;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("商户转账唯一订单号")
    private String outBizNo;

}
