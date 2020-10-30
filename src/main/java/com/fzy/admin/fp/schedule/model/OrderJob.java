package com.fzy.admin.fp.schedule.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-3 17:59:35
 * @Desp 订单的任务调度器
 **/
@Data
public class OrderJob {

    private String shopOrderId;

    /** 触发器 */
    private String jobTrigger;
}
