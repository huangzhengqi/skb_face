package com.fzy.admin.fp.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
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
 * @Author ：drj.
 * @Date  ：Created in 16:06 2019/4/19
 * @Description:
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_company")
public class Company extends BaseEntity {

    @Getter
    public enum Type {
        /**
         * 角色类型
         */
        ADMIN(-1, "管理员"),
        PROVIDERS(1, "服务商"),
        OPERATOR(2, "一级代理商"),
        DISTRIBUTUTORS(3, "二级代理商"),
        THIRDAGENT(4, "三级代理商");

        private Integer code;
        private String status;

        Type(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Status {
        /**
         * 签约状态
         */
        UNENABLED(1, "未签约"),
        UNAUDIT(2, "待审核"),
        SIGNED(3, "已签约"),
        OVERDUE(4, "过期"),
        CANCELL(5, "禁用"),
        BACK(6, "驳回");

        private Integer code;
        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum CooperationLev {
        /**
         * 代理商类型
         */
        TOP(1, "顶级一级代理商"),
        COMMON(2, "普通一级代理商");

        private Integer code;
        private String status;

        CooperationLev(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("公司/个人")
    @NotBlank(message = "请输入公司名称")
    private String name;

    @ApiModelProperty("联系人")
    @NotBlank(message = "请输入联系人")
    private String contact;

    @ApiModelProperty("手机")
    @NotBlank(message = "请输入手机")
    private String phone;

    @ApiModelProperty("公司类型")
    private Integer type;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("合作级别")
    private Integer cooperationLev;

    @ApiModelProperty("省")
    @NotBlank(message = "请选择省份")
    private String province;

    @ApiModelProperty("市")
    @NotBlank(message = "请选择城市")
    private String city;

    @ApiModelProperty("分佣比例")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal payProrata;

    @ApiModelProperty(value = "支付宝分佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal zfbPayProrata = BigDecimal.ZERO;

    @ApiModelProperty(value = "微信分佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal wxPayProrata = BigDecimal.ZERO;

    @ApiModelProperty(value = "随行付分佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal sxfPayProrata = BigDecimal.ZERO;

    @ApiModelProperty(value = "富有分佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal fyPayProrata = BigDecimal.ZERO;

    /**
     * 07 13新增字段 ====
     */
    @ApiModelProperty(value = "天阙随行付分佣比例")
    @Column(columnDefinition = "decimal(10,4) default 0")
    private BigDecimal tqSxfPayProrata = BigDecimal.ZERO;

    @ApiModelProperty(value = "联系地址")
    @NotBlank(message = "请输入联系地址")
    private String address;

    @ApiModelProperty(value = "微信奖励")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal wechatReward;

    @ApiModelProperty(value = "支付宝奖励")
    @Column(columnDefinition = "decimal(10,4)")
    private BigDecimal alipayReward;

    @ApiModelProperty(value = "上级id null为服务商")
    private String parentId;

    @ApiModelProperty(value = "业务员id")
    private String managerId;

    @ApiModelProperty(value = "业务员")
    @Transient
    private String saleName;

    @ApiModelProperty(value = "到期时间")
    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCooperaTime;

    @ApiModelProperty(value = "上级名称")
    @Transient
    private String parentName;

    @Transient
    @ApiModelProperty(value = "是否直属下级 1是 0不是")
    private Integer isDirect;

    @ApiModelProperty(value = "约定格式：|服务商Id|一级代理商Id| 如：|1|2|")
    private String idPath;

    @ApiModelProperty("广告权限 0:关闭  1:开启")
    private Integer advertiseType;

}
