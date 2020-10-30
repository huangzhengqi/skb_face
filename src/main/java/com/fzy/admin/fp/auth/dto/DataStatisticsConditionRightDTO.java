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
public class DataStatisticsConditionRightDTO {

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

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 1金额 0数量
     */
    private Integer type;

    public Date getEndTime() {
        return DateUtil.endOfDay(this.endTime);
    }


}
