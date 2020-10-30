package com.fzy.admin.fp.schedule.quartz;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.schedule.model.OrderJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * @author : yy
 * @date  : 2019-12-3 17:49:55
 * @Description:  : 同步任务工厂
 * @version : 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {
    public static final String JOB_PARAM_KEY    = "jobParam";

    @Resource
    private ShopOrderService shopOrderService;

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SyncJobFactory.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SyncJobFactory execute");
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        OrderJob o = (OrderJob)mergedJobDataMap.get(JOB_PARAM_KEY);
        ShopOrder shopOrder = shopOrderService.findOne(o.getShopOrderId());
        if(shopOrder.getStatus()==0){
            shopOrder.setDelFlag( CommonConstant.DEL_FLAG);
            shopOrderService.update(shopOrder);
        }
    }
}
