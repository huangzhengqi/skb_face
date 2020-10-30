package com.fzy.admin.fp.distribution.app.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@Entity
@Data
@Table(name = "lysj_dist_user")
public class DistUser extends CompanyBaseEntity {

    @ApiModelProperty("账号")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邀请码")
    private String inviteNum;

    @ApiModelProperty("上级id")
    private String parentId;

    @ApiModelProperty("交易日期")
    private Date dealDate;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("状态 0启用 1禁用")
    @Column(columnDefinition = "int(1) default 0")
    private Integer status;

    @ApiModelProperty("禁用时间")
    private Date disableTime;

    @ApiModelProperty("付费时间")
    private Date payTime;

    @ApiModelProperty("微信号")
    private String wxNum;

    @ApiModelProperty("支付宝账号")
    private String aliNum;

    @ApiModelProperty("银行卡号")
    private String bankNum;

    @ApiModelProperty("银行卡姓名")
    private String bankName;

    @ApiModelProperty("支付宝姓名")
    private String aliName;

    @ApiModelProperty("等级 0游客 1普通代理 2运营中心")
    private Integer grade;

    @ApiModelProperty("级别")
    @Column(columnDefinition = "int(1) default 0")
    private Integer level;

    @ApiModelProperty("发展商户人数")
    private Integer merchantNum=0;

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("最新成为代理的时间")
    private Date becomeTime;

    @ApiModelProperty("购买设备")
    private Integer buyNum=0;

    @ApiModelProperty("激活设备")
    private Integer activate=0;

    @ApiModelProperty("团队激活设备")
    private Integer teamActivate=0;

    @ApiModelProperty("直邀人数")
    private Integer directNum=0;

    @ApiModelProperty("零级Id")
    private String zeroLevelId;

    @ApiModelProperty("一级Id")
    private String oneLevelId;

    @ApiModelProperty("二级id")
    private String twoLevelId;

    @ApiModelProperty("三级id")
    private String threeLevelId;

    @ApiModelProperty("开户银行")
    private String depositBank;

    @Getter
    public enum Status {
        /**
         * 状态
         */
        ENABLE(1, "启用"),
        DISABLE(2, "禁用");

        private Integer code;
        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }
}
