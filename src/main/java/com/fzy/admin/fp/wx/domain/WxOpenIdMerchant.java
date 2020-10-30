package com.fzy.admin.fp.wx.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author hzq
 * @Date 2020/9/9 14:04
 * @Version 1.0
 * @description  微信openId 关联商户表
 */
@Data
@Entity
@Table(name = "wx_openid_merchant")
public class WxOpenIdMerchant extends CompanyBaseEntity {

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("商户电话")
    private String phone;

    @ApiModelProperty("设置返佣金 1不设置 2设置")
    @Column(nullable=false,name="rebateType",columnDefinition="int default 1")
    private Integer rebateType;

    @ApiModelProperty(value="开始返佣日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="开始返佣日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date startRebateTime;

    @ApiModelProperty(value="结束返佣日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="结束返佣日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date endRebateTime;

    @ApiModelProperty("累积金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal cumulationRebate;

    @ApiModelProperty("限制金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal limitRebate;



}
