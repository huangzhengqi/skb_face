package com.fzy.admin.fp.distribution.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-18 14:39:51
 * @Desp
 **/

@Data
public class DistUserVO {
    private String id;

    @ApiModelProperty("用户名")
    private String name;//用户名

    @ApiModelProperty("手机号")
    private String userName;//手机号

    @ApiModelProperty("城市")
    private String city;//城市

    @ApiModelProperty("邀请者姓名")
    private String inviteName;//邀请者姓名

    @ApiModelProperty("父级邀请码")
    private String parentNum;//父级邀请码

    @ApiModelProperty("邀请者等级")
    private String inviteGrade;//邀请者等级

    @ApiModelProperty("邀请者号码")
    private String invitePhone;//邀请者号码

    @ApiModelProperty("邀请码")
    private String inviteNum;//邀请码

    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间

    @ApiModelProperty("代理级别")
    private Integer grade;//代理等级

    @ApiModelProperty("加入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;//加入时间

    @ApiModelProperty("代理费")
    private Double fee;

    @ApiModelProperty("一级人数")
    private Integer firstNum;//一级人数

    @ApiModelProperty("二级人数")
    private Integer secondNum;//二级人数

    @ApiModelProperty("提成金额")
    private BigDecimal bonus;//提成金额

    @ApiModelProperty("已提现")
    private BigDecimal take;//已提现

    @ApiModelProperty("余额")
    private BigDecimal balance;//余额

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("状态 0启用 1禁用")
    private Integer status;

    @ApiModelProperty("邀请码")
    private String parentId;

    @ApiModelProperty("性别 1男 2女")
    private Integer sex;

    @ApiModelProperty("支付宝账号")
    private String aliNum;

    @ApiModelProperty("银行卡号")
    private String bankNum;

    @ApiModelProperty("银行卡姓名")
    private String bankName;

    @ApiModelProperty("支付宝姓名")
    private String aliName;

    @ApiModelProperty("微信号")
    private String wxNum;

    @ApiModelProperty("级别名称")
    private String gradeName;

    @ApiModelProperty("比例")
    private Integer rate;

    @ApiModelProperty("1级/2级代理")
    private Integer rank;

    @ApiModelProperty("邀请注册")
    private Integer inviteSize;

    @ApiModelProperty("我的团队")
    private Integer teamSize;

    @ApiModelProperty("代理人数")
    private Integer agentNum;

    @ApiModelProperty("团队人数")
    private Integer teamTotalNum;

    @ApiModelProperty("团队人数中的代理人数")
    private Integer teamAgentTotalNum;

    @ApiModelProperty("我的订单数")
    private Integer orderTotalNum;//我的订单数

    @ApiModelProperty("回复数量")
    private Integer feedback;//回复数量

    @ApiModelProperty("购买设备")
    private Integer buyNum;

    @ApiModelProperty("激活设备")
    private Integer activate;

    @ApiModelProperty("直邀人数")
    private Integer directNum;

    @ApiModelProperty("直邀人数中的代理人数")
    private Integer directAgentNum;

    @ApiModelProperty("间邀人数")
    private Integer indirectNum;

    @ApiModelProperty("昨日预估")
    private BigDecimal yesterdayCommission;

    @ApiModelProperty("本月预估")
    private BigDecimal monthCommission;

    @ApiModelProperty("累计佣金")
    private BigDecimal totalCommission;

    @ApiModelProperty("发展商户")
    private Integer merchantNum;

    @ApiModelProperty("团队激活设备")
    private Integer teamActivate;

    @ApiModelProperty("加入天数")
    private Long createNum;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("银行卡到账日期")
    private Integer bankDate;

    @ApiModelProperty("支付宝到账日期")
    private Integer aliDate;

    @ApiModelProperty("开户银行")
    private String depositBank;

    public DistUserVO() {
    }

    public DistUserVO(Integer status,String id, String name, String userName, String city, Date updateTime, Integer grade, BigDecimal bonus, BigDecimal take, BigDecimal balance, Integer activate, Integer buyNum ,Integer teamActivate) {
        this.status=status;
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.city = city;
        this.updateTime = updateTime;
        this.grade = grade;
        this.bonus = bonus;
        this.take = take;
        this.balance = balance;
        this.activate = activate;
        this.buyNum = buyNum;
        this.teamActivate=teamActivate;
    }

    public DistUserVO(String id,String wxNum,String aliNum,String bankNum,String bankName,String aliName,String inviteNum,Integer sex,String parentId, String name, String userName, String city, Date updateTime, Integer grade, String headImg,Date createTime,BigDecimal bonus, BigDecimal take, BigDecimal balance, Integer activate, Integer buyNum, Integer teamActivate) {
        this.id = id;
        this.wxNum=wxNum;
        this.aliNum=aliNum;
        this.bankNum=bankNum;
        this.bankName=bankName;
        this.aliName=aliName;
        this.inviteNum=inviteNum;
        this.sex=sex;
        this.parentId=parentId;
        this.name = name;
        this.userName = userName;
        this.city = city;
        this.updateTime = updateTime;
        this.grade = grade;
        this.bonus = bonus;
        this.take = take;
        this.balance = balance;
        this.headImg = headImg;
        this.createTime = createTime;
        this.activate = activate;
        this.buyNum = buyNum;
        this.teamActivate = teamActivate;
    }

    public DistUserVO(String name, String headImg) {
        this.name = name;
        this.headImg = headImg;
    }
}
