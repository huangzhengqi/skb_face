package com.fzy.admin.fp.order.order.dto;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Created by zk on 2019-05-08 20:46
 * @description 商户端流水列表条件DTO
 */
@Data
public class MerchantRunningAccountListConditionDTO {
    private String orderNumber;//订单编号

    private String userName;//收银员

    private Integer payWay;//支付类型

    private Integer status;//状态

    private String merchantId;//商户Id

    private String storeId;//门店Id

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_payTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_payTime;

    @ApiModelProperty(value = "支付方式")
    private Integer payType;

    public void setStart_payTime(Date start_payTime) {
        this.start_payTime = DateUtil.beginOfDay(start_payTime).toJdkDate();
    }

    public void setEnd_payTime(Date end_payTime) {
        this.end_payTime = DateUtil.endOfDay(end_payTime).toJdkDate();
    }
}
