package com.fzy.admin.fp.member.credits.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lb
 * @date 2019/5/14 17:32
 * @Description 积分活动商品实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_creditsproduct")
public class CreditsProduct extends BaseEntity {

    @Getter
    public enum Status {
        NO_START(0, "未开始"),
        START(1, "进行中"),
        END(2, "结束了");
        private Integer code;
        private String message;

        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @Getter
    public enum Interrupt {
        NO_INTERRUPT(0, "未终止"),
        INTERRUPT(1, "人为终止");
        private Integer code;
        private String message;

        Interrupt(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @NotBlank(message = "兑换开始时间必填")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date exchangeStart;//兑换开始时间

    @NotBlank(message = "兑换截止时间必填")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date exchangeEnd;// 兑换截止时间

    private String exchangeMessage;//兑换说明

    @NotBlank(message = "兑换所需要积分必填")
    private Integer credits;//兑换所需要积分

    @NotBlank(message = "商品名称必填")
    private String productName;//商品名称

    @NotNull(message = "商品价格不能为空")
    private BigDecimal productMoney;//商品价格

    @NotNull(message = "商品图片不能为空")
    private String imgUrl;//商品图片地址

    @NotNull(message = "商品数量不能为空")
    private Integer productNums;//总的商品数量

    @NotNull(message = "是否限制每位用户兑换数量必选")
    private Integer isLimited;//是否限制每位用户兑换数量  1限制 2不限制

    private Integer limitedNum;//限制用户兑换的数量

    private Integer exchangeNum;//已领取数量
    private Integer exchangedNum;//已兑换数量

    private Integer status;//兑换状态

    @NotNull(message = "中断状态为必填")
    private Integer interrupt;//是否人为中断活动 0不是 1是

    @NotBlank(message = "商户Id不能为空")
    private String merchantId;//商户Id

}
