package com.fzy.admin.fp.member.member.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-16 22:40
 * @description
 */
@Data
public class DateConditionDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;

    public void formatBeginAndEnd() {
        startTime = DateUtil.beginOfDay(startTime);
        endTime = DateUtil.endOfDay(endTime);
    }
}
