package com.fzy.admin.fp.distribution.money.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-19 13:31:25
 * @Desp 提现申请记录
 **/
@Data
public class TakeInfoDetailVO {
    private String id;

    @ApiModelProperty("申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("代理级别 1一级 2二级 3三级 ")
    private Integer grade;

    @ApiModelProperty("提现金额")
    private BigDecimal sum;

    @ApiModelProperty("提现方式 0银行卡 1支付宝")
    private Integer takeType;

    @ApiModelProperty("姓名")
    private String accountName;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("0未打款 1已打款 2已驳回")
    private Integer status;

    @ApiModelProperty("账户余额")
    private BigDecimal balance;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("总金额")
    private BigDecimal totalFee;

    public TakeInfoDetailVO(BigDecimal totalFee){
        this.totalFee=totalFee;
    }

    public TakeInfoDetailVO(String id,String remark,Date updateTime,BigDecimal balance,Date createTime, String name, String phone, Integer grade, BigDecimal sum, Integer takeType, String accountName, String account, Integer status) {
        this.id=id;
        this.remark=remark;
        this.updateTime = updateTime;
        this.balance=balance;
        this.createTime = createTime;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.sum = sum;
        this.takeType = takeType;
        this.accountName = accountName;
        this.account = account;
        this.status = status;
    }
}
