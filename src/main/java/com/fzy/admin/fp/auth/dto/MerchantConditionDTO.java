package com.fzy.admin.fp.auth.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MerchantConditionDTO {

    private String companyId;
    private String merchantId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;
    private int type = 1;

    public void setStartTime(Date startTime) {
        this.startTime = DateUtil.beginOfDay(startTime).toJdkDate();
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateUtil.endOfDay(endTime).toJdkDate();
    }
}
