package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.auth.dto.StatisticsConditionDTO;
import com.fzy.admin.fp.auth.service.StatisticsService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.auth.vo.StatisticsCountDataVo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.service.CommissionDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yy
 * @Date 2019-12-24 15:19:48
 * @Desp
 **/

@RestController
@RequestMapping("/dist/app/commission")
@Api(value = "AppCommissionController", tags = {"分销-app佣金统计"})
public class AppCommissionController {

    @Resource
    private StatisticsService statisticsService;

    @Resource
    private CommissionDayService commissionDayService;

    @GetMapping("/list")
    @ApiOperation(value = "佣金列表", notes = "佣金列表")
    public Resp<List<StatisticsCountDataVo>> getDayCountCommissionAmount(StatisticsConditionDTO condition, @UserId String userId){
        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());
        List<StatisticsCountDataVo> list = new ArrayList<>();
        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);
        for (DateTime dateTime : dateTimeList) {
            String tempDate = DateUtil.formatDate(dateTime);
            StatisticsCountDataVo cacheCountDataAndOrder = this.statisticsService.getAgentCacheCountDataAndOrderVoList(userId, tempDate, tempDate);
            list.add(cacheCountDataAndOrder);
        }
        if(list.size() <= 0){
            return Resp.success("当前用户无数据");
        }
        list.sort(Comparator.comparing(StatisticsCountDataVo::getSaveDay).reversed());
        return Resp.success(list);
    }

    @GetMapping("/total")
    @ApiOperation(value = "我的佣金", notes = "我的佣金")
    public Resp getCommissionAmount(@UserId String userId){
        Map<String,BigDecimal> result=new HashMap<>();
        try {
            Date dayByNum = DateUtils.getDayByNum(new Date(), -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String format = sdf.format(dayByNum);
            Date parse = sdf.parse(format);
            //昨天的佣金
            CommissionDay yesterday = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeAndType(userId, parse,1);
            Date dateByMonth = DateUtils.initDateByMonth();
            //当月的佣金
            List<CommissionDay> month = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(userId,dateByMonth , new Date(),1);
            //上个月的佣金
            List<CommissionDay> lastMonth = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(userId, DateUtils.getLastMonth(dateByMonth) , dateByMonth,1);
            List<CommissionDay> allCommission = commissionDayService.getRepository().findAllByCompanyIdAndType(userId, 1);
            BigDecimal totalCommission = allCommission.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal monthCommission = month.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal lastMonthCommission = lastMonth.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            result.put("yesterdayCommission",yesterday!=null?yesterday.getCommissionTotal():BigDecimal.ZERO);
            result.put("monthCommission",monthCommission);
            result.put("lastMonthCommission",lastMonthCommission);
            result.put("totalCommission",totalCommission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Resp.success(result);
    }

}
