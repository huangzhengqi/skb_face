package com.fzy.admin.fp.order.app.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-06-10 15:07
 * @description
 */
@Data
public class MerchantAppStatisticsConditionDTO {
    private String storeId;//门店Id,为空则查所有门店
    private String userId;//员工Id,为空则查所有员工
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date start_payTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date end_payTime;
    private String merchantId;//不需要前端传

    public void formatBeginAndEnd() {
        start_payTime = DateUtil.beginOfDay(start_payTime);
        end_payTime = DateUtil.endOfDay(end_payTime);
    }
}
