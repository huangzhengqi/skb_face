package com.fzy.admin.fp.order.order.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-13 15:38
 * @description
 */
@Data
public class MerchantRunningAccountOverviewConditionDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date start_payTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date end_payTime;
    private String storeId;//门店Id
    private String userName;//收银员
    private String orderNumber;//订单号

    private String merchantId;//不需要前端传

    public void initDate() {
        start_payTime = DateUtil.beginOfDay(start_payTime).toJdkDate();
        end_payTime = DateUtil.endOfDay(end_payTime).toJdkDate();
    }
}
