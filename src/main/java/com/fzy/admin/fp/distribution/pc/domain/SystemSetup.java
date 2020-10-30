package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author yy
 * @Date 2020-2-24 09:29:39
 * @Desp
 **/

@Data
@Entity
@Table(name = "lysj_system_setup")
public class SystemSetup extends CompanyBaseEntity {
    @ApiModelProperty("初级代理角色名称")
    @NotBlank(message = "初级代理名称不能为空")
    private String primaryName;

    @ApiModelProperty("中级代理名称")
    @NotBlank(message = "中级代理名称不能为空")
    private String operationName;

    @ApiModelProperty("晋升条件")
    @NotNull(message = "晋升条件不能为空")
    private Integer rise;

    @ApiModelProperty("提现条件")
    @NotNull(message = "提现条件不能为空")
    private Integer deposit;

    @ApiModelProperty("最低提现金额")
    @NotNull(message = "最低提现金额不能为空")
    private BigDecimal minMoney;

    @ApiModelProperty("银行卡到账日期")
    @NotBlank(message = "银行卡到账日期不能为空")
    private Integer bankDate;

    @ApiModelProperty("支付宝到账日期")
    @NotBlank(message = "支付宝到账日期不能为空")
    private Integer aliDate;

    @ApiModelProperty("支付宝提现方式 0自动提现 1手动提现")
    @NotNull(message = "支付宝提现方式不能为空")
    private Integer aliStatus;

    @ApiModelProperty("服务费收取")
    @NotNull(message = "服务费收取不能为空")
    private Double serviceCharge;

    @ApiModelProperty("中级代理直邀发展商户的流水佣金所得")
    @NotNull(message = "中级代理直邀发展商户的流水佣金所得不能为空")
    private Integer operationDirect;

    @ApiModelProperty("中级代理直邀代理发展商户的流水佣金分得")
    @NotNull(message = "中级代理直邀代理发展商户的流水佣金分得不能为空")
    private Integer operationOneLevel;

    @ApiModelProperty("中级代理间邀代理发展商户的流水佣金分得")
    @NotNull(message = "中级代理间邀代理发展商户的流水佣金分得不能为空")
    private Integer operationTwoLevel;

    @ApiModelProperty("中级代理三级代理发展商户的流水佣金分得")
    @NotNull(message = "中级代理三级代理发展商户的流水佣金分得不能为空")
    private Integer operationThreeLevel;

    @ApiModelProperty("中级代理其他代理发展商户的流水佣金分得")
    @NotNull(message = "中级代理其他代理发展商户的流水佣金分得不能为空")
    private Integer operationFourLevel;

    @ApiModelProperty("初级代理直邀发展商户的流水佣金所得")
    @NotNull(message = "初级代理直邀发展商户的流水佣金所得不能为空")
    private Integer direct;

    @ApiModelProperty("初级代理直邀代理发展商户的流水佣金分得")
    @NotNull(message = "初级代理直邀代理发展商户的流水佣金分得不能为空")
    private Integer oneLevel;

    @ApiModelProperty("初级代理间邀代理发展商户的流水佣金分得")
    @NotNull(message = "初级代理间邀代理发展商户的流水佣金分得不能为空")
    private Integer twoLevel;

    @ApiModelProperty("初级代理三级代理发展商户的流水佣金分得")
    @NotNull(message = "初级代理三级代理发展商户的流水佣金分得不能为空")
    private Integer threeLevel;

}
