package com.fzy.admin.fp.schedule.utils;

import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.schedule.model.OrderJob;
import com.fzy.admin.fp.schedule.quartz.SyncJobFactory;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author yy
 * @Date 2019-12-4 15:08:03
 * @Desp
 **/
public class ScheduleUtils {

    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(SyncJobFactory.class);

    /**
     * 创建定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param param the param
     */
    public static void createScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, Object param) {
        //同步或异步
        Class<? extends Job> jobClass = SyncJobFactory.class;

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();

        String jobTrigger = trigger.getKey().getName();

        OrderJob orderJob = (OrderJob)param;
        orderJob.setJobTrigger(jobTrigger);
        //放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(SyncJobFactory.JOB_PARAM_KEY, orderJob);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("创建定时任务失败", e);
            throw new BaseException("创建定时任务失败");
        }
    }

    /**
     * 删除定时任务
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
            throw new BaseException("删除定时任务失败");
        }
    }

}
