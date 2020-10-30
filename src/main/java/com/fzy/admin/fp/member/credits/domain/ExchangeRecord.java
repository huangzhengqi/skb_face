package com.fzy.admin.fp.member.credits.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lb
 * @date 2019/5/16 17:31
 * @Description 礼品兑换记录
 */

@Entity
@Data
@Table(name = "lysj_member_exchange_record")
@EqualsAndHashCode(callSuper = true)
public class ExchangeRecord extends BaseEntity {

    @Getter
    public enum Status {
        NO_CHANGE(0, "未取货"),
        CHANGE(1, "已取货");
        private Integer code;
        private String message;

        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @Excel(name = "兑换时间", isImportField = "true_st", height = 20, width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private Date exchangeTime;//兑换时间

    @Excel(name = "商品名称", isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "商品名称未知")
    private String productName;//商品名称

    @Excel(name = "兑换所需积分", isImportField = "true_st", height = 20, width = 30)
    @NotNull(message = "商品消耗积分未知")
    private Integer conCredits;//消耗的积分

    @Excel(name = "提货码", isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "提货码未知")
    private String goodCodes;//提货码

    @Excel(name = "手机号", isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "手机号未知")
    private String phone;//手机号

    @Excel(name = "可用积分", isImportField = "true_st", height = 20, width = 30)
    @NotNull(message = "可用积分未知")
    private Integer avaCredits;//可用积分

    @Excel(name = "兑换状态", isImportField = "true_st", height = 20, width = 30, replace = {"未取货_0", "已取货_1"})
    @NotNull(message = "兑换状态未知")
    private Integer status;//兑换商品的状态

    @Excel(name = "操作人", isImportField = "true_st", height = 20, width = 30)
    @NotBlank(message = "操作人未知")
    private String operator;//操作人

    @NotBlank(message = "请填入商户Id")
    private String merchantId;//商户id

    private String productId;//商品ID

    private String memberId;//会员ID

    private String imageURL;//商品图片地址

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date start_exchangeTime;

    @Transient
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date end_exchangeTime;

    private BigDecimal productMoney;//商品价格

    private String remark;//记录

}
