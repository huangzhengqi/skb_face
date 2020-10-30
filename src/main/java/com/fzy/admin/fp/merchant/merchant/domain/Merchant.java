package com.fzy.admin.fp.merchant.merchant.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.merchant.merchant.MerchantAppletConfigVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户控制层
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_merchant")
public class Merchant extends CompanyBaseEntity {


    public Merchant() {
    }

    @Getter
    public enum Status {
        GOODS(1, "启用"),
        GIFT(2, "禁用");

        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Type {
        NORMAL(0, "普通"),
        DIST(1, "分销");

        private Integer code;

        private String status;

        Type(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Excel(name = "商户名", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("商户名称")
    @NotBlank(message = "请输入商户名")
    private String name;

    @Excel(name = "联系人", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("联系人")
    @NotBlank(message = "请输入联系人")
    private String contact;

    @Excel(name = "手机", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("手机(商户账号暂时用手机号)")
    private String phone;

    @Excel(name = "邮箱", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("邮箱")
    @NotBlank(message = "请输入邮箱")
    private String email;

    @Excel(name = "地址", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("地址")
    @NotBlank(message = "请输入地址")
    private String address;

    @Excel(name = "省", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("省")
    @NotBlank(message = "请输入省")
    private String province;

    @ApiModelProperty("省名称")
    private String provinceName;

    @Excel(name = "市", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("市")
    @NotBlank(message = "请输入市")
    private String city;

    @ApiModelProperty("市名称")
    @Transient
    private String cityName;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("区名称")
    @Transient
    private String areaName;

    @Excel(name = "一级经营类别", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("一级经营类别")
    @NotBlank(message = "请输入一级经营类别")
    private String businessLevOne;

    @Excel(name = "二级经营类别", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("二级经营类别")
    @NotBlank(message = "请输入二级经营类别")
    private String businessLevTwo;

    @Excel(name = "三级经营类别", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("三级经营类别")
    @NotBlank(message = "请输入三级经营类别")
    private String businessLevThree;

    @Excel(name = "分佣比例", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty("分佣比例")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal payProrata;

    @ApiModelProperty("一级代理商或二级代理商id")
    private String companyId;

    @ApiModelProperty("业务员id")
    private String managerId;

    @ApiModelProperty("图片id")
    private String photoId;

    @ApiModelProperty("一级代理商或二级代理商名称")
    @Transient
    private String companyName;

    @ApiModelProperty("业务员名称")
    @Transient
    private String managerName;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("商户的key")
    private String appKey;

    @Transient
    private MerchantAppletConfigVO merchantAppletConfigVO;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCooperaTime;

    @Excel(name = "发票最低订单金额", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty(value = "发票最低订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal minElectronicInvoiceOrderPrice;

    @Excel(name = "发票最高订单金额", isImportField = "true_st", height = 20, width = 30)
    @ApiModelProperty(value = "发票最高订单金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal maxElectronicInvoiceOrderPrice;

    @ApiModelProperty(value = "类型0普通商户 1分销开出来的商户")
    @Column(columnDefinition = "int(1) default 0")
    private Integer type;

    @ApiModelProperty(value = "交易笔数")
    private Integer countNumber;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal money;

    @ApiModelProperty("设置返佣金 1不设置 2设置")
    @Column(name="rebateType",columnDefinition="int default 1")
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

    @ApiModelProperty("返佣限额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal limitRebate;

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("是否绑定 1未绑定 2已绑定")
    @Column(name="bind",columnDefinition="int default 1")
    private Integer bind;

    @ApiModelProperty("返佣费率")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal siteRate;

    @ApiModelProperty("返佣门槛 （笔数）")
    private Integer rebateNum;

    @ApiModelProperty("待结算金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal waitRebate;

    @Transient
    @ApiModelProperty(value = "是否是直属商户 1是 0不是")
    private Integer isDirect;

    @Transient
    @ApiModelProperty(value = "已返金额")
    private BigDecimal amountReturned;

    @Transient
    @ApiModelProperty(value = "实际领取")
    private BigDecimal actualCollection;

    @ApiModelProperty("设置是否奖励 1不设置 2设置")
    @Column(name="rewardType",columnDefinition="int default 1")
    private Integer rewardType;

    @ApiModelProperty(value="开始奖励日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="开始奖励日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date startRewardTime;

    @ApiModelProperty(value="结束奖励日期")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name="结束奖励日期", width=30.0D, isImportField="true", format="yyyy-MM-dd HH:mm:ss")
    private Date endRewardTime;

    @ApiModelProperty("奖励门槛 （笔数）")
    private Integer rewardNum;

    @ApiModelProperty(value="奖励金额")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal rewardPrice;

    @Transient
    @ApiModelProperty("奖励月数 ")
    private Integer rewardMonth;

}