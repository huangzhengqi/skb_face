package com.fzy.admin.fp.sdk.merchant.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyDataVO {
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "加入时间")
    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;

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

    private Integer type;// 公司类型
}
