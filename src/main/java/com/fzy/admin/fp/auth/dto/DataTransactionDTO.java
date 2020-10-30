package com.fzy.admin.fp.auth.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-05 20:39
 * @description
 */
@Data
public class DataTransactionDTO {
    /**
     * 公司id
     */
    private String companyId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    public void setStartTime(Date startTime) {
        this.startTime = DateUtil.beginOfDay(startTime).toJdkDate();
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateUtil.endOfDay(endTime).toJdkDate();
    }
}
