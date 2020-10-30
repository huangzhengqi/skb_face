package com.fzy.admin.fp.auth.dto;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-05 20:39
 * @description
 */
@Data
public class OrderCountDTO {

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    public Date getEndTime() {
        return DateUtil.endOfDay(this.endTime);
    }

}
