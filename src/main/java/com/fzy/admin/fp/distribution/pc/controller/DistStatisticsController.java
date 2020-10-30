package com.fzy.admin.fp.distribution.pc.controller;

import cn.hutool.core.date.DateField;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.pc.dto.StatisticsDTO;
import com.fzy.admin.fp.distribution.pc.vo.*;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.service.CommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yy
 * @Date 2019-11-26 18:06:46
 * @Desp 分销数据
 **/
@RestController
@RequestMapping("/dist/statistics")
@Api(value = "StatisticsController", tags = {"分销-分销数据"})
public class DistStatisticsController extends BaseContent {

    @Resource
    private DistUserService distUserService;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private CommissionService commissionService;

    @Resource
    private CompanyService companyService;

    @GetMapping("/data")
    @ApiOperation(value = "平台数据概况", notes = "平台数据概况")
    public Resp<DistStatisticsVO> detail(@TokenInfo(property = "companyId") String companyId,StatisticsDTO statisticsDTO){
        DistStatisticsVO distStatisticsVO=new DistStatisticsVO();
        Date dayByNum = DateUtils.getDayByNum(statisticsDTO.getStartTime(), -statisticsDTO.getNum());
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);

        //新增注册
        Integer reg = distUserService.getRepository().countByCreateTimeBetweenAndServiceProviderId(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),serviceId);
        distStatisticsVO.setAddUser(reg);
        //上一时段新增注册
        Integer oldReg = distUserService.getRepository().countByCreateTimeBetweenAndServiceProviderId(dayByNum, statisticsDTO.getStartTime(),serviceId);
        distStatisticsVO.setOldAddUser(oldReg);

        //新增注册
        Integer agentNum = distUserService.getRepository().countByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),0,serviceId);
        distStatisticsVO.setAddAgent(agentNum);
        //上一时段新增注册
        Integer oldAgentNum = distUserService.getRepository().countByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(dayByNum, statisticsDTO.getStartTime(),0,serviceId);
        distStatisticsVO.setOldAddAgent(oldAgentNum);
        //总代理数
        Integer totalAgent = distUserService.getRepository().countByGradeGreaterThanAndBecomeTimeLessThanEqualAndServiceProviderId(0,statisticsDTO.getEndTime(),serviceId);
        distStatisticsVO.setTotalAgent(totalAgent);


        //当前企业
        Company company = companyService.findOne(companyId);
        List<String> parentIds = new ArrayList<>();
        parentIds.add(company.getId());
        //获取当前企业下所有的商户
        List<String> merchantIds = companyService.findAllmerchantIds(company.getId());

        Integer activateNum = equipmentService.getRepository().countByStatusAndActivateTimeBetweenAndMerchantIdIn(1, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),merchantIds);
        distStatisticsVO.setActivateNum(activateNum);
        //上一时段激活
        Integer oldActivateNum = equipmentService.getRepository().countByStatusAndActivateTimeBetweenAndMerchantIdIn(1,dayByNum, statisticsDTO.getStartTime(),merchantIds);
        distStatisticsVO.setOldActivateNum(oldActivateNum);
        //统计发展商户
        Integer countMerchant = merchantService.getRepository().countByTypeAndCreateTimeBetweenAndServiceProviderId(1, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),serviceId);
        distStatisticsVO.setAddMerchant(countMerchant);
        Integer oldCountMerchant = merchantService.getRepository().countByTypeAndCreateTimeBetweenAndServiceProviderId(1, dayByNum, statisticsDTO.getStartTime(),serviceId);
        distStatisticsVO.setOldAddMerchant(oldCountMerchant);
        return Resp.success(distStatisticsVO);
    }


    @GetMapping("/data/trend")
    @ApiOperation(value = "新增数据趋势图", notes = "新增数据趋势图")
    public Resp<List<DistTrendVO>> dataTrend(StatisticsDTO statisticsDTO){
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        List<String> xData = DateUtils.dateRangeList(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        List<DistTrendVO> distTrendVOList=new ArrayList<>();
        if(statisticsDTO.getType()==0){
            List<DistUser> userList = distUserService.getRepository().findAllByCreateTimeBetweenAndServiceProviderId(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),serviceId);
            for (String dateTime : xData) {
                Integer num=0;
                DistTrendVO distTrendVO = new DistTrendVO();
                distTrendVO.setDateTime(dateTime);
                for (DistUser distUser : userList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (sdf.format(distUser.getCreateTime()).equals(dateTime)) {
                        ++num;
                    }
                }
                distTrendVO.setNum(num);
                distTrendVOList.add(distTrendVO);
            }
        }else if(statisticsDTO.getType()==1){
            List<DistUser> userList = distUserService.getRepository().findAllByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),0,serviceId);
            for (String dateTime : xData) {
                Integer num=0;
                DistTrendVO distTrendVO = new DistTrendVO();
                distTrendVO.setDateTime(dateTime);
                for (DistUser distUser : userList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (sdf.format(distUser.getCreateTime()).equals(dateTime)) {
                        ++num;
                    }
                }
                distTrendVO.setNum(num);
                distTrendVOList.add(distTrendVO);
            }
        }else if(statisticsDTO.getType()==2){
            List<DistUser> userList = distUserService.getRepository().findAllByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),0,serviceId);
            Integer num=0;
            for (String dateTime : xData) {
                DistTrendVO distTrendVO = new DistTrendVO();
                distTrendVO.setDateTime(dateTime);
                for (DistUser distUser : userList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (sdf.format(distUser.getCreateTime()).equals(dateTime)) {
                        ++num;
                    }
                }
                distTrendVO.setNum(num);
                distTrendVOList.add(distTrendVO);
            }
        }else if(statisticsDTO.getType()==3){
            List<Merchant> merchantList = merchantService.getRepository().findAllByTypeAndCreateTimeBetweenAndServiceProviderId(1,statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),serviceId);
            for (String dateTime : xData) {
                Integer num=0;
                DistTrendVO distTrendVO = new DistTrendVO();
                distTrendVO.setDateTime(dateTime);
                for (Merchant agentUser : merchantList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (sdf.format(agentUser.getCreateTime()).equals(dateTime)) {
                        ++num;
                    }
                }
                distTrendVO.setNum(num);
                distTrendVOList.add(distTrendVO);
            }
        }else if(statisticsDTO.getType()==4){
            List<Equipment> equipmentList = equipmentService.getRepository().findAllByStatusAndActivateTimeBetweenAndServiceProviderId(1,statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),serviceId);
            for (String dateTime : xData) {
                Integer num=0;
                DistTrendVO distTrendVO = new DistTrendVO();
                distTrendVO.setDateTime(dateTime);
                for (Equipment agentUser : equipmentList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (sdf.format(agentUser.getCreateTime()).equals(dateTime)) {
                        ++num;
                    }
                }
                distTrendVO.setNum(num);
                distTrendVOList.add(distTrendVO);
            }
        }
        return Resp.success(distTrendVOList);
    }

    @GetMapping("/commission")
    @ApiOperation(value = "佣金统计", notes = "佣金统计")
    public Resp<DistStatisticsCommissionVO> commission(StatisticsDTO statisticsDTO){
        //当前时段的数据
        List<Commission> commissionDaysList = commissionService.getRepository().findAllByCreateTimeBetweenAndTypeAndOrderStatus(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 1,2);
        BigDecimal orderPrice = commissionDaysList.stream().map(Commission::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal commission = commissionDaysList.stream().filter(s->s.getOemCommission()!=null).map(Commission::getOemCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal directCommission = commissionDaysList.stream().filter(s->s.getDirectCommission()!=null).map(Commission::getDirectCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal oneLevelCommission = commissionDaysList.stream().filter(s->s.getOneLevelCommission()!=null).map(Commission::getOneLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal twoLevelCommission = commissionDaysList.stream().filter(s->s.getTwoLevelCommission()!=null).map(Commission::getTwoLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal threeLevelCommission = commissionDaysList.stream().filter(s->s.getThreeLevelCommission()!=null).map(Commission::getThreeLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal operationCommission = commissionDaysList.stream().filter(s->s.getOperationCommission()!=null).map(Commission::getOperationCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expend=operationCommission.add(directCommission).add(oneLevelCommission).add(twoLevelCommission).add(threeLevelCommission);
        DistStatisticsCommissionVO distStatisticsCommissionVO=new DistStatisticsCommissionVO();
        distStatisticsCommissionVO.setCommission(commission);
        distStatisticsCommissionVO.setOrderPrice(orderPrice);
        distStatisticsCommissionVO.setExpend(expend);
        //上一时段的数据
        Date dayByNum = DateUtils.getDayByNum(statisticsDTO.getStartTime(), -statisticsDTO.getNum());
        List<Commission> lastCommissionDaysList = commissionService.getRepository().findAllByCreateTimeBetweenAndTypeAndOrderStatus(dayByNum,statisticsDTO.getStartTime(), 1,2);
        BigDecimal lastOrderPrice = lastCommissionDaysList.stream().map(Commission::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastCommission = lastCommissionDaysList.stream().filter(s->s.getOemCommission()!=null).map(Commission::getOemCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastDirectCommission = commissionDaysList.stream().filter(s->s.getDirectCommission()!=null).map(Commission::getDirectCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastOneLevelCommission = commissionDaysList.stream().filter(s->s.getOneLevelCommission()!=null).map(Commission::getOneLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastTwoLevelCommission = commissionDaysList.stream().filter(s->s.getTwoLevelCommission()!=null).map(Commission::getTwoLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastThreeLevelCommission = commissionDaysList.stream().filter(s->s.getThreeLevelCommission()!=null).map(Commission::getThreeLevelCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastOperationCommission = commissionDaysList.stream().filter(s->s.getOperationCommission()!=null).map(Commission::getOperationCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lastExpend=lastDirectCommission.add(lastOneLevelCommission).add(lastTwoLevelCommission).add(lastThreeLevelCommission).add(lastOperationCommission);
        distStatisticsCommissionVO.setLastCommission(lastCommission);
        distStatisticsCommissionVO.setLastOrderPrice(lastOrderPrice);
        distStatisticsCommissionVO.setLastExpend(lastExpend);
        return Resp.success(distStatisticsCommissionVO);
    }

    @GetMapping("/commission/trend")
    @ApiOperation(value = "佣金统计", notes = "佣金统计")
    public Resp<List<DistCommissionTrendVO>> commissionTrend(StatisticsDTO statisticsDTO){
        List<String> xData = DateUtils.dateRangeList(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        //当前时段的数据
        long startTime=System.currentTimeMillis();
        List<DistCommissionTrendVO> distList=new ArrayList<>();
        if(statisticsDTO.getIsTotal()) {
            for (String dateTime : xData) {
                DistCommissionTrendVO distCommissionTrendVO=new DistCommissionTrendVO();
                distCommissionTrendVO.setDateTime(dateTime);
                BigDecimal price = commissionService.commissionTrend(dateTime);
                distCommissionTrendVO.setPrice(price);
                distList.add(distCommissionTrendVO);
            }
        }else{
            for (String dateTime : xData) {
                DistCommissionTrendVO distCommissionTrendVO=new DistCommissionTrendVO();
                distCommissionTrendVO.setDateTime(dateTime);
                BigDecimal price = commissionService.commissionTrend(dateTime);
                distCommissionTrendVO.setPrice(price);
                distList.add(distCommissionTrendVO);
            }
        }
        long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-startTime)/1000;
        System.out.println("执行时间："+excTime+"s");
        return Resp.success(distList);
    }
    /*
    @Resource
    private DistUserService distUserService;

    @Resource
    private DistOrderService distOrderService;


    @GetMapping("/agent/count")
    @ApiOperation(value = "代理人数统计", notes = "代理人数统计")
    public Resp agentCount(@TokenInfo(property = "serviceProviderId") String serviceProviderId, StatisticsDTO statisticsDTO){
        Map<String,Object> result=new HashMap<>();
        Date dayByNum = DateUtils.getDayByNum(statisticsDTO.getStartTime(), -statisticsDTO.getNum());
        Integer total = distUserService.getRepository().countByServiceProviderIdAndGradeGreaterThanAndGradeLessThan(serviceProviderId, 0, 4);
        //新增注册
        Integer newReg = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetween(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime());
        //上一时段新增注册
        Integer oldReg = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetween(serviceProviderId,dayByNum, statisticsDTO.getStartTime());
        //新增代理
        Integer newAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 0, 4);
        //上一时段新增代理
        Integer oldAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId,dayByNum, statisticsDTO.getStartTime(), 0, 4);
        //一级代理
        Integer oneAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 0, 2);
        //二级代理
        Integer secondAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 1, 3);
        //三级代理
        Integer thirdAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 2, 4);
        //上一时段一级代理
        Integer oldOneAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId,dayByNum, statisticsDTO.getStartTime(), 0, 2);
        //上一时段二级代理
        Integer oldSecondAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId,dayByNum, statisticsDTO.getStartTime(), 1, 3);
        //上一时段三级代理
        Integer oldThirdAgent = distUserService.getRepository().countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, dayByNum, statisticsDTO.getStartTime(), 2, 4);
        result.put("total",total);//累计代理
        result.put("newReg",newReg);//新注册
        result.put("oldReg",oldReg);//上一时段注册
        result.put("newAgent",newAgent);//新增代理
        result.put("oldAgent",oldAgent);//上一时段新增代理
        result.put("oneAgent",oneAgent);//市代
        result.put("secondAgent",secondAgent);//省代
        result.put("thirdAgent",thirdAgent);//区代
        result.put("oldOneAgent",oldOneAgent);//上一时段市代
        result.put("oldSecondAgent",oldSecondAgent);//上一时段省代
        result.put("oldThirdAgent",oldThirdAgent);//上一时段区代
        return Resp.success(result);
    }

    @GetMapping("/fee/count")
    @ApiOperation(value = "代理收益统计", notes = "代理收益统计")
    public Resp feeCount(@TokenInfo(property = "serviceProviderId") String serviceProviderId, StatisticsDTO statisticsDTO){
        Map<String,Object> result=new HashMap<>();
        Date dayByNum = DateUtils.getDayByNum(statisticsDTO.getStartTime(), -statisticsDTO.getNum());
        List<DistOrder> distOrderList = distOrderService.getRepository().findAllByServiceProviderIdAndCreateTimeBetweenAndStatus(serviceProviderId,statisticsDTO.getStartTime(),statisticsDTO.getEndTime(),1);
        double oneFee = DistUtil.formatDouble(distOrderList.stream().filter(s -> s.getType() == 1).mapToDouble(s -> s.getPrice().doubleValue()).sum());
        double twoFee = DistUtil.formatDouble(distOrderList.stream().filter(s -> s.getType() == 2).mapToDouble(s -> s.getPrice().doubleValue()).sum());
        double threeFee = DistUtil.formatDouble(distOrderList.stream().filter(s -> s.getType() == 3).mapToDouble(s -> s.getPrice().doubleValue()).sum());

        double sum = DistUtil.formatDouble(distOrderList.stream().mapToDouble(s -> s.getPrice().doubleValue()).sum());
        distOrderList = distOrderList.stream().filter(s -> s.getFirstCommissions() != null).collect(Collectors.toList());
        double oneExpend = DistUtil.formatDouble(distOrderList.stream().mapToDouble(s -> (s.getFirstCommissions()*0.01) * s.getPrice().doubleValue()).sum());
        distOrderList = distOrderList.stream().filter(s -> s.getSecondCommissions() != null).collect(Collectors.toList());
        double twoExpend = DistUtil.formatDouble(distOrderList.stream().mapToDouble(s -> (s.getSecondCommissions()*0.01) * s.getPrice().doubleValue()).sum());

        List<DistOrder> oldDistOrderList = distOrderService.getRepository().findAllByServiceProviderIdAndCreateTimeBetweenAndStatus(serviceProviderId,dayByNum,statisticsDTO.getStartTime(),1);
        double oldSum = DistUtil.formatDouble(oldDistOrderList.stream().mapToDouble(s -> s.getPrice().doubleValue()).sum());
        oldDistOrderList = oldDistOrderList.stream().filter(s -> s.getFirstCommissions() != null).collect(Collectors.toList());
        double oldOneExpend = DistUtil.formatDouble(oldDistOrderList.stream().mapToDouble(s -> (s.getFirstCommissions()*0.01) * s.getPrice().doubleValue()).sum());
        oldDistOrderList = oldDistOrderList.stream().filter(s -> s.getSecondCommissions() != null).collect(Collectors.toList());
        double oldTwoExpend = DistUtil.formatDouble(oldDistOrderList.stream().mapToDouble(s -> (s.getSecondCommissions()*0.01) * s.getPrice().doubleValue()).sum());

        result.put("sum",sum);//代理费
        result.put("expend",oneExpend+twoExpend);//提成
        result.put("oldSum",oldSum);//上一时段代理费
        result.put("oldExpend",oldOneExpend+oldTwoExpend);//上一时段提成
        result.put("oneFee",oneFee);//市级代理
        result.put("twoFee",twoFee);//省级代理
        result.put("threeFee",threeFee);//区级代理
        return Resp.success(result);
    }

    @GetMapping("/agent/chart")
    @ApiOperation(value = "代理人数趋势", notes = "代理人数统计图")
    public Resp agentChart(@TokenInfo(property = "serviceProviderId") String serviceProviderId, StatisticsDTO statisticsDTO){
        List<String> xData = DateUtils.dateRangeList(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        List<DistUser> distUserList = distUserService.getRepository().findAllByServiceProviderIdAndPayTimeBetweenAndGradeGreaterThanAndGradeLessThan(serviceProviderId, statisticsDTO.getStartTime(), statisticsDTO.getEndTime(), 0, 4);
        List<StatisticsAgentVO> statisticsAgentVOList = new ArrayList<>();
        if(statisticsDTO.getIsTotal()){
            if(statisticsDTO.getIsAll()){
                Integer num=0;
                for (String dateTime : xData) {
                    StatisticsAgentVO statisticsAgentVO = new StatisticsAgentVO();
                    statisticsAgentVO.setDateTime(dateTime);
                    for (DistUser distUser : distUserList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distUser.getPayTime()).equals(dateTime)) {
                            ++num;
                        }
                    }
                    statisticsAgentVO.setCount(num);
                    statisticsAgentVOList.add(statisticsAgentVO);
                }
            }else{
                int oneAgent=0;
                int twoAgent=0;
                int threeAgent=0;
                for (String dateTime : xData) {
                    StatisticsAgentVO statisticsAgentVO = new StatisticsAgentVO();
                    statisticsAgentVO.setDateTime(dateTime);
                    for (DistUser distUser : distUserList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distUser.getPayTime()).equals(dateTime)) {
                            switch (distUser.getGrade()){
                                case 1:
                                    oneAgent++;
                                case 2:
                                    twoAgent++;
                                case 3:
                                    threeAgent++;
                            }
                        }
                    }
                    statisticsAgentVO.setOneAgent(oneAgent);
                    statisticsAgentVO.setTwoAgent(twoAgent);
                    statisticsAgentVO.setThreeAgent(threeAgent);
                    statisticsAgentVOList.add(statisticsAgentVO);
                }
            }
        }else{
            if(statisticsDTO.getIsAll()){
                for (String dateTime : xData) {
                    StatisticsAgentVO statisticsAgentVO = new StatisticsAgentVO();
                    statisticsAgentVO.setDateTime(dateTime);
                    Integer num=0;
                    for (DistUser distUser : distUserList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distUser.getPayTime()).equals(dateTime)) {
                            num++;
                        }
                    }
                    statisticsAgentVO.setCount(num);
                    statisticsAgentVOList.add(statisticsAgentVO);
                }
            }else{
                for (String dateTime : xData) {
                    StatisticsAgentVO statisticsAgentVO = new StatisticsAgentVO();
                    statisticsAgentVO.setDateTime(dateTime);
                    int oneAgent=0;
                    int twoAgent=0;
                    int threeAgent=0;
                    for (DistUser distUser : distUserList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distUser.getPayTime()).equals(dateTime)) {
                            switch (distUser.getGrade()){
                                case 1:
                                    oneAgent++;
                                case 2:
                                    twoAgent++;
                                case 3:
                                    threeAgent++;
                            }
                        }
                    }
                    statisticsAgentVO.setOneAgent(oneAgent);
                    statisticsAgentVO.setTwoAgent(twoAgent);
                    statisticsAgentVO.setThreeAgent(threeAgent);
                    statisticsAgentVOList.add(statisticsAgentVO);
                }
            }
        }
        return Resp.success(statisticsAgentVOList);
    }

    @GetMapping("/fee/chart")
    @ApiOperation(value = "代理费金额趋势", notes = "代理收益统计")
    public Resp feeChart(@TokenInfo(property = "serviceProviderId") String serviceProviderId, StatisticsDTO statisticsDTO){
        List<String> xData = DateUtils.dateRangeList(statisticsDTO.getStartTime(), statisticsDTO.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        List<DistOrder> distOrderList = distOrderService.getRepository().findAllByServiceProviderIdAndCreateTimeBetweenAndStatus(serviceProviderId,statisticsDTO.getStartTime(),statisticsDTO.getEndTime(),1);
        List<StatisticsFeeVO> statisticsFeeVOList = new ArrayList<>();
        if(statisticsDTO.getIsTotal()){
            if(statisticsDTO.getIsAll()){
                BigDecimal money = new BigDecimal(0);
                for (String dateTime : xData) {
                    StatisticsFeeVO statisticsFeeVO = new StatisticsFeeVO();
                    statisticsFeeVO.setDateTime(dateTime);
                    for (DistOrder distOrder : distOrderList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distOrder.getCreateTime()).equals(dateTime)) {
                            money = distOrder.getPrice().add(money);
                        }
                    }
                    statisticsFeeVO.setCount(money);
                    statisticsFeeVOList.add(statisticsFeeVO);
                }
            }else{
                BigDecimal oneFee = new BigDecimal(0);
                BigDecimal twoFee = new BigDecimal(0);
                BigDecimal threeFee = new BigDecimal(0);
                for (String dateTime : xData) {
                    StatisticsFeeVO statisticsFeeVO = new StatisticsFeeVO();
                    statisticsFeeVO.setDateTime(dateTime);
                    for (DistOrder distOrder : distOrderList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distOrder.getCreateTime()).equals(dateTime)) {
                            switch (distOrder.getType()){
                                case 1:
                                    oneFee = distOrder.getPrice().add(oneFee);
                                case 2:
                                    twoFee = distOrder.getPrice().add(twoFee);
                                case 3:
                                    threeFee = distOrder.getPrice().add(threeFee);
                            }
                        }
                    }
                    statisticsFeeVO.setOneFee(oneFee);
                    statisticsFeeVO.setTwoFee(twoFee);
                    statisticsFeeVO.setThreeFee(threeFee);
                    statisticsFeeVOList.add(statisticsFeeVO);
                }
            }
        }else{
            if(statisticsDTO.getIsAll()){
                for (String dateTime : xData) {
                    StatisticsFeeVO statisticsFeeVO = new StatisticsFeeVO();
                    statisticsFeeVO.setDateTime(dateTime);
                    BigDecimal money = new BigDecimal(0);
                    for (DistOrder distOrder : distOrderList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distOrder.getCreateTime()).equals(dateTime)) {
                            money = distOrder.getPrice().add(money);
                        }
                    }
                    statisticsFeeVO.setCount(money);
                    statisticsFeeVOList.add(statisticsFeeVO);
                }
            }else{
                for (String dateTime : xData) {
                    StatisticsFeeVO statisticsFeeVO = new StatisticsFeeVO();
                    statisticsFeeVO.setDateTime(dateTime);
                    BigDecimal oneFee = new BigDecimal(0);
                    BigDecimal twoFee = new BigDecimal(0);
                    BigDecimal threeFee = new BigDecimal(0);
                    for (DistOrder distOrder : distOrderList) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (sdf.format(distOrder.getCreateTime()).equals(dateTime)) {
                            switch (distOrder.getType()){
                                case 1:
                                    oneFee = distOrder.getPrice().add(oneFee);
                                case 2:
                                    twoFee = distOrder.getPrice().add(twoFee);
                                case 3:
                                    threeFee = distOrder.getPrice().add(threeFee);
                            }
                        }
                    }
                    statisticsFeeVO.setOneFee(oneFee);
                    statisticsFeeVO.setTwoFee(twoFee);
                    statisticsFeeVO.setThreeFee(threeFee);
                    statisticsFeeVOList.add(statisticsFeeVO);
                }
            }
        }
        return Resp.success(statisticsFeeVOList);
    }
*/
}
