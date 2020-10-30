package com.fzy.admin.fp.distribution.utils;

import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.schedule.model.OrderJob;
import com.fzy.admin.fp.schedule.utils.ScheduleUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yy
 * @Date 2020-1-9 09:29:45
 * @Desp 检测未支付与已支付的订单放置到quartz中
 **/
@Component
public class InitBean {

    @Resource
    private ShopOrderService shopOrderService;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @PostConstruct
    public void init(){
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ShopOrder.Status.NOPAY.getCode());
        statusList.add(ShopOrder.Status.DELIVERY.getCode());
        List<ShopOrder> noPayList = shopOrderService.getRepository().findAllByStatusInAndDelFlag(statusList, CommonConstant.NORMAL_FLAG);
        for(ShopOrder shopOrder:noPayList){
            Date removeTime=new Date();
            if(shopOrder.getStatus().equals(ShopOrder.Status.NOPAY.getCode())){
                removeTime = DateUtils.getDayByNum(shopOrder.getCreateTime(), 1);
                if(removeTime.getTime()<System.currentTimeMillis()){
                    shopOrder.setDelFlag(CommonConstant.DEL_FLAG);
                    shopOrderService.update(shopOrder);
                    continue;
                }
            }
            if(shopOrder.getStatus().equals(ShopOrder.Status.DELIVERY.getCode())&&shopOrder.getDeliveryTime()!=null){
                removeTime = DateUtils.getDayByNum(shopOrder.getDeliveryTime(), 7);
                if(removeTime.getTime()<System.currentTimeMillis()){
                    shopOrder.setStatus(ShopOrder.Status.OVER.getCode());
                    shopOrderService.update(shopOrder);
                    continue;
                }
            }
            OrderJob orderJob=new OrderJob();
            orderJob.setShopOrderId(shopOrder.getId());
            ScheduleUtils.createScheduleJob(scheduler,shopOrder.getId(),"OrderJob", DateUtils.getCron(removeTime),orderJob);
        }
    }
}
