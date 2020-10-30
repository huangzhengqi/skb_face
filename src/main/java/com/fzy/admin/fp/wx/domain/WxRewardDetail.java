package com.fzy.admin.fp.wx.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @Author hzq
 * @Date 2020/10/9 15:15
 * @Version 1.0
 * @description 奖励微信红包明细表
 */
@Data
@Entity
@Table(name = "wx_reward_detail")
public class WxRewardDetail extends CompanyBaseEntity {

    @ApiModelProperty("商户订单号")
    private String mchBillno;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("公众账号appid")
    private String wxAppid;

    @ApiModelProperty("用户openid")
    private String reOpenid;

    @ApiModelProperty("微信单号")
    private String sendListid;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("奖励金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal rewardPrice;

    @ApiModelProperty("发放状态 0没发放 1已发放 2未绑定")
    private Integer returnType;

    @ApiModelProperty("红包状态 0未领取 1已领取 ")
    private Integer status;

    @ApiModelProperty("查询状态 0未查询 1已查询")
    private Integer findStatus;

    @ApiModelProperty("有效刷脸笔数")
    private Integer actFaceNum;

}
