package com.fzy.admin.fp.auth.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.auth.domain.StatisticsCountData;
import com.fzy.admin.fp.auth.dto.MerchantConditionDTO;
import com.fzy.admin.fp.auth.service.StatisticsService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.MerchantCountDetailConditionDTO;
import com.fzy.admin.fp.auth.dto.StatisticsConditionDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.auth.vo.*;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.order.feign.OrderServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-04-29 16:21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/auth/running_account")
public class StatisticsController extends BaseContent {

    @Resource
    private CompanyService companyService;
    @Resource
    private OrderServiceFeign orderServiceFeign;
    @Resource
    private MerchantBusinessService merchantBusinessService;
    @Resource
    private UserService userService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private StatisticsService statisticsService;

    private Map<String, Company> companyCacheMap = new HashMap<>();

    /**
     * @author Created by zk on 2019/5/7 21:29
     * @Description 首页第一板块统计
     */
    @GetMapping("/home_page_statistics")
    public Resp homePageStatistics(@UserId String userId, @TokenInfo(property = "level") Integer level, String companyId) {
        int userLevel = ParamUtil.isBlank(level) ? Role.LEVEL.COMMON.getCode() : level;
        Company company = companyService.findOne(companyId);
        //找出所有下级，服务商:找出所有一级代理商 一级代理商：找出所有二级代理商 二级代理商：无
        List<String> companyIds = companyService.findChildrenById(company.getId(), userLevel, userId);
        List<Object> dataList = new ArrayList<>();
        Date today = new Date();
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            //如果是服务商
            //找出所有该服务商下的一级代理商
//            List<Company> agents = companyService.findAll(companyIds);
            //如果是服务商，则传递空列表，查询所有商户
            final List<Company> childrenByServiceProviderId = companyService.findChildrenByServiceProviderId(company.getId());
            final Map<Integer, List<Company>> collect = childrenByServiceProviderId.parallelStream()
                    //筛选出已被签约的运营商
                    .filter(c -> c.getDelFlag().equals(CommonConstant.NORMAL_FLAG)
                            && Company.Status.SIGNED.getCode().equals(c.getStatus()))
                    .collect(Collectors.groupingBy(Company::getType));
            //服务商下属所有公司
            companyIds = childrenByServiceProviderId.parallelStream().map(Company::getId)
                    .collect(Collectors.toList());
            //服务商下属所有公司下属商户
            List<MerchantVO> merchantVOList = merchantBusinessService.findByCompanyIds(companyIds.toArray(new String[companyIds.size()]))
                    .parallelStream()
                    .filter(merchantVO -> merchantVO.getDelFlag().equals(CommonConstant.NORMAL_FLAG)
                            && Merchant.Status.GOODS.getCode().equals(merchantVO.getStatus()))
                    .collect(Collectors.toList());
            List<Company> agents = collect.get(Company.Type.OPERATOR.getCode());
            if (agents == null) {
                agents = new ArrayList<>();
            }
            dataList.add(agents.size());//1.一级代理商总数
            dataList.add(agents.stream()
                    .filter(agent -> DateUtil.isSameDay(agent.getCreateTime(), today))
                    .count());//2.今日新增一级代理商
            dataList.add(merchantVOList.size());//3.商户总数
            dataList.add(merchantVOList.stream()
                    .filter(merchantVO -> DateUtil.isSameDay(merchantVO.getCreateTime(), today))
                    .count());//4.今日新增商户
        } else if (Company.Type.OPERATOR.getCode().equals(company.getType())) {
            //如果是一级代理商
            dataList.add(companyIds.size());//1.二级代理商总数
            //将自己添加进公司列表，方便找出直属商户
            companyIds.add(company.getId());
            //获取该一级代理商下所有商户
            List<MerchantVO> merchantVOList = merchantBusinessService
                    .findByCompanyIds(companyIds.toArray(new String[0]));
            String[] merchantIds = filterMerchant(merchantVOList, userLevel, company.getId(), userId);
            dataList.add(merchantIds.length);//2.商户总数
            //获取今日订单
            List<OrderVo> orderVoList = orderServiceFeign.findByMerchantIdsAsc(merchantIds,
                    DateUtil.beginOfDay(today).toString(), DateUtil.endOfDay(today).toString());
            dataList.add(orderVoList.size());//3.今日订单数
            BigDecimal todayTotalPrice = BigDecimal.ZERO;
            for (OrderVo orderVo : orderVoList) {
                todayTotalPrice = todayTotalPrice.add(orderVo.getActPayPrice().subtract(orderVo.getRefundPayPrice()));
            }
            dataList.add(todayTotalPrice.stripTrailingZeros().toPlainString());//4.今日流水
        } else if (Company.Type.DISTRIBUTUTORS.getCode().equals(company.getType())) {
            //如果是二级代理商
            //将自己添加进公司列表，找出直属商户
            companyIds.add(company.getId());
            //获取该一级代理商下所有商户
            List<MerchantVO> merchantVOList = merchantBusinessService
                    .findByCompanyIds(companyIds.toArray(new String[0]));
            String[] merchantIds = filterMerchant(merchantVOList, userLevel, company.getId(), userId);
            dataList.add(merchantIds.length);//1.商户总数
            //获取今日订单
            List<OrderVo> orderVoList = orderServiceFeign.findByMerchantIdsAsc(merchantIds,
                    DateUtil.beginOfDay(today).toString(), DateUtil.endOfDay(today).toString());
            dataList.add(orderVoList.size());//2.今日订单数
            BigDecimal todayTotalPrice = BigDecimal.ZERO;
            for (OrderVo orderVo : orderVoList) {
                todayTotalPrice = todayTotalPrice.add(orderVo.getActPayPrice().subtract(orderVo.getRefundPayPrice()));
            }
            dataList.add(todayTotalPrice.stripTrailingZeros().toPlainString());//3.今日流水
            dataList.add(orderVoList.stream()
                    .filter(orderVo ->
                            OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus())
                                    || OrderVo.Status.REFUNDTOTAL.getCode().equals(orderVo.getStatus()))
                    .count());//4.今日退款数

        } else {
            for (int i = 0; i < 4; i++) {
                dataList.add(0);
            }
        }
        return Resp.success(dataList);
    }

    /**
     * @author Created by zk on 2019/5/8 16:39
     * @Description 业务员发展占比图
     */
    @GetMapping("/home_page_staff_develop")
    public Resp homePageStaffDevelop(@TokenInfo(property = "level") Integer level, String companyId) {
        int userLevel = ParamUtil.isBlank(level) ? Role.LEVEL.COMMON.getCode() : level;
        if (Role.LEVEL.COMMON.getCode().equals(userLevel)) {
            return Resp.success("");
        }
        Company company = companyService.findOne(companyId);
        List<User> staffs = userService.getRepository().findByCompanyId(company.getId());
        Map<String, String> staffIdNameMap = staffs.stream().collect(Collectors.toMap(User::getId, User::getName));
        Map<String, Long> staffIdCountMap;
        List<PieChartDataVO> content = new ArrayList<>();
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            //服务商，获取所有一级代理商
            List<Company> agentList = companyService.getRepository().findByParentId(company.getId());
            staffIdCountMap = agentList.stream()
                    .collect(Collectors.groupingBy(Company::getManagerId, Collectors.counting()));
        } else if (Company.Type.OPERATOR.getCode().equals(company.getType())
                || Company.Type.DISTRIBUTUTORS.getCode().equals(company.getType())) {
            List<MerchantVO> merchantVOList = merchantBusinessService.findByCompanyIds(new String[]{company.getId()});
            staffIdCountMap = merchantVOList.stream()
                    .collect(Collectors.groupingBy(MerchantVO::getManagerId, Collectors.counting()));
        } else {
            return Resp.success("");
        }
        staffIdNameMap.forEach((id, name) -> {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setName(name);
            Long count = staffIdCountMap.get(id);
            pieChartDataVO.setValue(count == null ? 0 : count.intValue());
            content.add(pieChartDataVO);
        });
        return Resp.success(content);
    }

    /**
     * @author Created by zk on 2019/5/7 22:11
     * @Description 首页商户统计列表
     */
    @GetMapping("/home_page_merchant_chart")
    public Resp homePageMerchantChart(@UserId String userId, @TokenInfo(property = "level") Integer level, String companyId) {
        int userLevel = ParamUtil.isBlank(level) ? Role.LEVEL.COMMON.getCode() : level;
        Company company = companyService.findOne(companyId);
        //找出所有下级，服务商:找出所有一级代理商 一级代理商：找出所有二级代理商 二级代理商：无
        List<String> companyIds = companyService.findChildrenById(company.getId(), userLevel, userId);
        List<MerchantVO> merchantVOList;
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            //如果是服务商
            final List<Company> childrenByServiceProviderId = companyService.findChildrenByServiceProviderId(company.getId());
            companyIds = childrenByServiceProviderId.parallelStream().map(Company::getId).collect(Collectors.toList());
            merchantVOList = merchantBusinessService.findByCompanyIds(companyIds.toArray(new String[0]));
        } else if (Company.Type.OPERATOR.getCode().equals(company.getType())
                || Company.Type.DISTRIBUTUTORS.getCode().equals(company.getType())) {
            companyIds.add(company.getId());
            merchantVOList = merchantBusinessService.findByCompanyIds(companyIds.toArray(new String[0]));
        } else {
            return Resp.success("");
        }
        Date today = DateUtil.endOfDay(new Date());
        Date lastYear = DateUtil.beginOfDay(DateUtil.offsetMonth(today, -12));//去年的今天
        List<String> xData = dateRangeList(lastYear, today,
                DateField.MONTH, "yyyy-MM");
        //当月新增集合
        Map<String, List<MerchantVO>> collect = merchantVOList.stream().collect(Collectors.groupingBy(o -> DateUtil.format(o.getCreateTime(), "yyyy-MM")));
        List<Long> yData1 = new ArrayList<>(xData.size());
        //全部
        //获取截止去年的今天所在月份的所有商户 如今天2019-5-7 则获取2018-5-31号前所有的商户
        List<Long> yData2 = new ArrayList<>(xData.size());
        yData2.add(merchantVOList.stream()
                .filter(merchantVO ->
                        merchantVO.getCreateTime().compareTo(DateUtil.endOfMonth(lastYear)) <= 0)
                .count());
        Long merchantBeginCount = yData2.get(0);
        for (int i = 0; i < xData.size(); i++) {
            String dateTime = xData.get(i);
            List<MerchantVO> merchantVOS = collect.get(dateTime);
            Long merchantCount = ParamUtil.isBlank(merchantVOS) ? 0L : merchantVOS.size();
            if (i != 0) {
                //由于最早日期的全部商户已经统计，所以只记录并叠加之后的商户数量
                merchantBeginCount = merchantBeginCount + merchantCount;
                yData2.add(merchantBeginCount);
            }
            yData1.add(merchantCount);
        }
        Map<String, List> dataMap = new HashMap<>();
        dataMap.put("xData", xData);
        dataMap.put("total", yData2);
        dataMap.put("month", yData1);
        return Resp.success(dataMap);
    }


    /**
     * @author Created by zk on 2019/4/29 22:11
     * @Description 系统后台流水概览第一层统计
     * 1 查询下属商户  2 查询下属二级代理商的下属商户
     */
    @GetMapping({"/count_data"})
    public Resp runningAccount(@UserId String userId, @TokenInfo(property = "level") Integer level, StatisticsConditionDTO condition) {
        int userLevel = (ParamUtil.isBlank(level) ? Role.LEVEL.COMMON.getCode() : level).intValue();


        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());


        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);


        CountDataVO countDataVOMain = new CountDataVO();

        List<OrderVo> orderVoList = new ArrayList<OrderVo>();
        for (DateTime dateTime : dateTimeList) {


            String tempDate = DateUtil.formatDate(dateTime);



            String today = DateUtil.today();


            if (tempDate.equals(today)) {


                Company company = (Company)this.companyService.findOne(condition.getCompanyId());
                Map<String, Object> map = this.statisticsService.getToday(company);


                CountDataVO countDataVOTempOther = (CountDataVO)map.get("countData");


                this.statisticsService.setData(countDataVOMain, countDataVOTempOther);


                List<OrderVo> listTemp = (List)map.get("orderVoList");

                orderVoList.addAll(listTemp);

                continue;
            }


            String countDataKey = condition.getCompanyId() + "_" + tempDate + "_countData";

            CountDataVO countDataVOTemp = this.statisticsService.getObj(countDataKey, CountDataVO.class);

            log.info("countDataController缓存数据{}   ", countDataVOTemp);

            if (countDataVOTemp == null || countDataVOTemp.getOrderNum().intValue() == 0) {

                StatisticsCountData statisticsCountData = this.statisticsService.getStatisticsCountDataBy(condition.getCompanyId(), tempDate);



                if (statisticsCountData == null) {

                    Company company = (Company)this.companyService.findOne(condition.getCompanyId());
                    this.statisticsService.setCacheCountDataAndOrderVoList(company, tempDate, tempDate);

                    statisticsCountData = this.statisticsService.getStatisticsCountDataBy(condition.getCompanyId(), tempDate);
                }
                countDataVOTemp = new CountDataVO();

                BeanUtils.copyProperties(statisticsCountData, countDataVOTemp);

                this.statisticsService.setObjWithTime(countDataKey, countDataVOTemp, 86400);
            }



            this.statisticsService.setData(countDataVOMain, countDataVOTemp);
        }


        for (DateTime dateTime : dateTimeList) {


            String tempDate = DateUtil.formatDate(dateTime);


            String today = DateUtil.today();


            if (tempDate.equals(today)) {
                continue;
            }



            String orderKey = condition.getCompanyId() + "_" + tempDate + "_orderVoList";
            List<OrderVo> tempOrderVoList = this.statisticsService.getArr(orderKey, OrderVo.class);

            log.info("tempOrderVoController缓存数据{}", tempOrderVoList);

            if (tempOrderVoList != null && tempOrderVoList.size() > 0) {
                orderVoList.addAll(tempOrderVoList);
            }
        }



        AuthConstant.COMPANY_STATISTICS_CACHE.put(userId + "_" + condition.getType(), orderVoList);
        return Resp.success(countDataVOMain);
    }

    private String[] filterMerchant(List<MerchantVO> merchantVOs, int userLevel, String companyId, String userId) {
        return merchantVOs.stream()
                //第二次过滤
                .filter(merchantVO -> {
                    if (Role.LEVEL.TOP.getCode().equals(userLevel)) {
                        return true;
                    }
                    //如果该商户为该公司直属并且属于该业务员
                    if (companyId.equals(merchantVO.getCompanyId()) && userId.equals(merchantVO.getManagerId())) {
                        return true;
                    }
                    return false;
                })
                .map(MerchantVO::getId)
                .toArray(String[]::new);
    }

    @GetMapping({"/order_line_chart_all"})
    public Resp orderLineChartAll(@UserId String userId, StatisticsConditionDTO condition) {
        List<OrderVo> orderVos = (List)AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + condition.getType());
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }



        List<String> xData = dateRangeList(condition.getStartTime(), condition.getEndTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");

        Map<String, TotalPriceAndNumVO> dataMap = new LinkedHashMap<String, TotalPriceAndNumVO>();
        for (String data : xData) {
            dataMap.put(data, new TotalPriceAndNumVO());
        }
        for (OrderVo orderVo : orderVos) {
            TotalPriceAndNumVO totalPriceAndNumVO = (TotalPriceAndNumVO)dataMap.get(DateUtil.format(orderVo.getPayTime(), "yyyy-MM-dd"));
            if (totalPriceAndNumVO != null) {
                totalPriceAndNumVO.setNum(Integer.valueOf(totalPriceAndNumVO.getNum().intValue() + 1));
                totalPriceAndNumVO.setTotalPrice(totalPriceAndNumVO.getTotalPrice().add(orderVo.getTotalPrice()));
            }
        }
        List<String> yData = new ArrayList<String>(xData.size());
        List<String> num = new ArrayList<String>(xData.size());
        for (String s : dataMap.keySet()) {
            TotalPriceAndNumVO totalPriceAndNumVO = (TotalPriceAndNumVO)dataMap.get(s);
            yData.add(totalPriceAndNumVO.getTotalPrice().stripTrailingZeros().toPlainString());
            num.add(totalPriceAndNumVO.getNum().toString());
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }

    @GetMapping("/refund_line_chart_all")
    public Resp refundLineChartAll(@UserId String userId, StatisticsConditionDTO condition) {
        List<OrderVo> orderVos = AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + condition.getType());
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }
//        if (DateUtil.betweenDay(condition.getStartTime(), condition.getEndTime(), true) < 6) {
//            return Resp.success(new ChartDataVO());
//        }
        List<String> xData = dateRangeList(condition.getStartTime(), condition.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        Map<String, TotalPriceAndNumVO> dataMap = new LinkedHashMap<>();
        for (String data : xData) {
            dataMap.put(data, new TotalPriceAndNumVO());
        }
        for (OrderVo orderVo : orderVos) {
            if (orderVo.getStatus().equals(OrderVo.Status.REFUNDPART.getCode())
                    || orderVo.getStatus().equals(OrderVo.Status.REFUNDTOTAL.getCode())) {
                TotalPriceAndNumVO totalPriceAndNumVO = dataMap.get(DateUtil.format(orderVo.getRefundTime(), "yyyy-MM-dd"));
                if (totalPriceAndNumVO != null) {
                    totalPriceAndNumVO.setNum(totalPriceAndNumVO.getNum() + 1);
                    totalPriceAndNumVO.setTotalPrice(totalPriceAndNumVO.getTotalPrice().add(orderVo.getRefundPayPrice()));
                }
            }
        }
        List<String> yData = new ArrayList<>(xData.size());
        List<String> num = new ArrayList<>(xData.size());
        for (String s : dataMap.keySet()) {
            TotalPriceAndNumVO totalPriceAndNumVO = dataMap.get(s);
            yData.add(totalPriceAndNumVO.getTotalPrice().stripTrailingZeros().toPlainString());
            num.add(totalPriceAndNumVO.getNum().toString());
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }

    @GetMapping({"/commission_line_chart_all"})
    public Resp commissionLineChartAll(@UserId String userId, StatisticsConditionDTO condition) {
        List<OrderVo> orderVos = (List)AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + condition.getType());
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }



        List<String> xData = dateRangeList(condition.getStartTime(), condition.getEndTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");

        Map<String, TotalPriceAndNumVO> dataMap = new LinkedHashMap<String, TotalPriceAndNumVO>();
        for (String data : xData) {
            dataMap.put(data, new TotalPriceAndNumVO());
        }
        for (OrderVo orderVo : orderVos) {
            TotalPriceAndNumVO totalPriceAndNumVO = (TotalPriceAndNumVO)dataMap.get(DateUtil.format(orderVo.getPayTime(), "yyyy-MM-dd"));
            if (totalPriceAndNumVO != null) {
                totalPriceAndNumVO.setNum(Integer.valueOf(totalPriceAndNumVO.getNum().intValue() + 1));
                totalPriceAndNumVO.setTotalPrice(totalPriceAndNumVO.getTotalPrice().add(orderVo.getCommissionAmount()));
            }
        }
        List<String> yData = new ArrayList<String>(xData.size());
        List<String> num = new ArrayList<String>(xData.size());
        for (String s : dataMap.keySet()) {
            TotalPriceAndNumVO totalPriceAndNumVO = (TotalPriceAndNumVO)dataMap.get(s);
            yData.add(totalPriceAndNumVO.getTotalPrice().stripTrailingZeros().toPlainString());
            num.add(totalPriceAndNumVO.getNum().toString());
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }


    /**
     * @author Created by zk on 2019/5/1 16:49
     * @Description 支付方式饼图数据
     */
    @GetMapping("/pay_type_pie_chart_all")
    public Resp payTypePieChartAll(@UserId String userId, int type) {
        List<OrderVo> orderVos = AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + type);
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }
        Map<Integer, PieChartDataVO> pieChartDataVOMap = new HashMap<>();
        for (OrderVo.PayWay payWay : OrderVo.PayWay.values()) {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setType(payWay.getCode().toString());
            pieChartDataVO.setName(payWay.getStatus());
            pieChartDataVOMap.put(payWay.getCode(), pieChartDataVO);
        }
        for (OrderVo orderVo : orderVos) {
            PieChartDataVO pieChartDataVO = pieChartDataVOMap.get(orderVo.getPayWay());
            if (pieChartDataVO == null) {
                continue;
            }
            pieChartDataVO.setValue(pieChartDataVO.getValue() + 1);
        }
        List<PieChartDataVO> pieChartDataVOList = new ArrayList<>();
        for (Integer key : pieChartDataVOMap.keySet()) {
            pieChartDataVOList.add(pieChartDataVOMap.get(key));
        }
        return Resp.success(pieChartDataVOList);
    }

    /**
     * @author Created by zk on 2019/5/1 16:50
     * @Description 终端比例饼图数据
     */
    @GetMapping("/terminal_ratio_pie_chart_all")
    public Resp terminalRatioPieChartAll(@UserId String userId, int type) {
        List<OrderVo> orderVos = AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + type);
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }
        Map<Integer, PieChartDataVO> pieChartDataVOMap = new HashMap<>();
        for (OrderVo.PayClient payClient : OrderVo.PayClient.values()) {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setType(payClient.getCode().toString());
            pieChartDataVO.setName(payClient.getStatus());
            pieChartDataVOMap.put(payClient.getCode(), pieChartDataVO);
        }
        for (OrderVo orderVo : orderVos) {
            PieChartDataVO pieChartDataVO = pieChartDataVOMap.get(orderVo.getPayClient());
            if (pieChartDataVO == null) {
                continue;
            }
            pieChartDataVO.setValue(pieChartDataVO.getValue() + 1);
        }
        List<PieChartDataVO> pieChartDataVOList = new ArrayList<>();
        for (Integer key : pieChartDataVOMap.keySet()) {
            pieChartDataVOList.add(pieChartDataVOMap.get(key));
        }
        return Resp.success(pieChartDataVOList);
    }

    @GetMapping("/merchant_count_list")
    public Resp merchantCountList(@UserId String userId, int type, int pageNum, int pageSize) {
        List<OrderVo> orderVos = AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + type);
        if (ParamUtil.isBlank(orderVos)) {
            return Resp.success("");
        }
        Map<String, MerchantCountVO> merchantCountVOMap = new LinkedHashMap<>();
        for (OrderVo orderVo : orderVos) {
            MerchantCountVO merchantCountVO = merchantCountVOMap.get(orderVo.getMerchantId());
            if (merchantCountVO == null) {
                merchantCountVO = new MerchantCountVO();
                merchantCountVO.setAgentName(orderVo.getAgentName());
                merchantCountVO.setSubAgentName(orderVo.getSubAgentName());
                merchantCountVO.setMerchantName(orderVo.getMerchantName());
                merchantCountVO.setMerchantId(orderVo.getMerchantId());
                merchantCountVOMap.put(orderVo.getMerchantId(), merchantCountVO);
            }
            //订单金额
            merchantCountVO.setOrderAmount(merchantCountVO.getOrderAmount().add(orderVo.getTotalPrice()));
            //退款金额
            merchantCountVO.setRefundAmount(merchantCountVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
            //有效交易基数
            merchantCountVO.setValidDealAmount(merchantCountVO.getValidDealAmount().add(orderVo.getTotalPrice().subtract(orderVo.getRefundPayPrice())));
            //用户实付
            merchantCountVO.setCustomerPaidAmount(merchantCountVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));
            //优惠金额
            merchantCountVO.setDiscountAmount(merchantCountVO.getDiscountAmount().add(orderVo.getDisCountPrice()));
        }
        List<MerchantCountVO> merchantCountVOList = new ArrayList<>(merchantCountVOMap.size());
        for (String s : merchantCountVOMap.keySet()) {
            merchantCountVOList.add(merchantCountVOMap.get(s));
        }
        merchantCountVOList = merchantCountVOList.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("totalPage", merchantCountVOMap.size() % pageSize == 0 ? merchantCountVOMap.size() / pageSize : merchantCountVOMap.size() / pageSize + 1);
        dataMap.put("totalElement", merchantCountVOMap.size());
        dataMap.put("data", merchantCountVOList);
        return Resp.success(dataMap);
    }

    @GetMapping("/export/merchant")
    public Resp merchantCountListExcel(String userId, int type, int pageNum, int pageSize){
        List<OrderVo> orderVos = AuthConstant.COMPANY_STATISTICS_CACHE.get(userId + "_" + type);
        if (ParamUtil.isBlank(orderVos)) {
            List<MerchantCountVO> merchantCountVOList = new ArrayList<>();
            EasyPoiUtil.exportExcel(merchantCountVOList, "流水明细", "流水", MerchantCountVO.class, "流水明细信息表.xls", response);
            return Resp.success("导出成功");
        }
        Map<String, MerchantCountVO> merchantCountVOMap = new LinkedHashMap<>();
        for (OrderVo orderVo : orderVos) {
            MerchantCountVO merchantCountVO = merchantCountVOMap.get(orderVo.getMerchantId());
            if (merchantCountVO == null) {
                merchantCountVO = new MerchantCountVO();
                merchantCountVO.setAgentName(orderVo.getAgentName());
                merchantCountVO.setSubAgentName(orderVo.getSubAgentName());
                merchantCountVO.setMerchantName(orderVo.getMerchantName());
                merchantCountVO.setMerchantId(orderVo.getMerchantId());
                merchantCountVOMap.put(orderVo.getMerchantId(), merchantCountVO);
            }
            //订单金额
            merchantCountVO.setOrderAmount(merchantCountVO.getOrderAmount().add(orderVo.getTotalPrice()));
            //退款金额
            merchantCountVO.setRefundAmount(merchantCountVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
            //有效交易基数
            merchantCountVO.setValidDealAmount(merchantCountVO.getValidDealAmount().add(orderVo.getTotalPrice().subtract(orderVo.getRefundPayPrice())));
            //用户实付
            merchantCountVO.setCustomerPaidAmount(merchantCountVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));
            //优惠金额
            merchantCountVO.setDiscountAmount(merchantCountVO.getDiscountAmount().add(orderVo.getDisCountPrice()));
        }
        List<MerchantCountVO> merchantCountVOList = new ArrayList<>(merchantCountVOMap.size());
        for (String s : merchantCountVOMap.keySet()) {
            merchantCountVOList.add(merchantCountVOMap.get(s));
        }
        merchantCountVOList = merchantCountVOList.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());

        EasyPoiUtil.exportExcel(merchantCountVOList, "流水明细", "流水", MerchantCountVO.class, "流水明细信息表.xls", response);

        return Resp.success("导出成功");
    }

    @GetMapping("/merchant_count_detail")
    public Resp merchantCountDetail(MerchantCountDetailConditionDTO condition) {
        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());
        List<OrderVo> orderVos = this.orderServiceFeign.findByMerchantIdsDesc(new String[] { condition.getMerchantId() }, startTime.toString(), endTime
                .toString(), condition.getPayWay());
        Company company = companyService.findOne(condition.getCompanyId());
        MerchantVO merchantVO = merchantBusinessService.findByMerchantId(condition.getMerchantId());
        for (OrderVo orderVo : orderVos) {
            countCommission(orderVo, merchantVO, company, condition.getType());
        }
        //第一个str为日期，第二个为支付方式
        Map<String, Map<Integer, Map<Integer, CountDataVO>>> dataMap = new LinkedHashMap<>();
        //获取日期-订单map
        orderVos.forEach(orderVo -> orderVo.setFormatPayTime(DateUtil.format(orderVo.getPayTime(), "yyyy-MM-dd")));
        Map<String, List<OrderVo>> payTimeOrderVOMap = orderVos.stream()
                .collect(Collectors.groupingBy(OrderVo::getFormatPayTime, LinkedHashMap::new, Collectors.toList()));
        payTimeOrderVOMap.forEach((payTime, orderVoList) -> {
            //获取支付方式-订单map
            Map<Integer, List<OrderVo>> payWayOrderVOMap = orderVoList.stream()
                    .collect(Collectors.groupingBy(OrderVo::getPayWay, LinkedHashMap::new, Collectors.toList()));
            Map<Integer, Map<Integer, CountDataVO>> payWayCountDataMap = new LinkedHashMap<>();
            payWayOrderVOMap.forEach((payWay, orderVoList1) -> {
                //获取支付通道-订单map
                Map<Integer, List<OrderVo>> payChanelOrderVOMap = orderVoList1.stream()
                        .collect(Collectors.groupingBy(OrderVo::getPayChannel, LinkedHashMap::new, Collectors.toList()));
                Map<Integer, CountDataVO> payChanelCountDataMap = new LinkedHashMap<>();
                payChanelOrderVOMap.forEach((payChanel, orderVoList2) -> {

                    CountDataVO countDataVO = new CountDataVO();
                    orderVoList2.forEach(orderVo -> {
                        //订单金额
                        countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(orderVo.getTotalPrice()));
                        //实付金额
                        countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));
                        //优惠金额
                        countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(orderVo.getDisCountPrice()));
                        //退款金额和退款数
                        if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus())
                                || OrderVo.Status.REFUNDTOTAL.getCode().equals(orderVo.getStatus())) {
                            //如果状态是部分退款或者全部退款，则说明该笔订单为退款订单
                            countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
                            countDataVO.setRefundNum(countDataVO.getRefundNum() + 1);
                        }
                        countDataVO.setAgentCommissionAmount(countDataVO.getAgentCommissionAmount().add(orderVo.getAgentCommissionAmount()));
                        countDataVO.setSubAgentCommissionAmount(countDataVO.getSubAgentCommissionAmount().add(orderVo.getSubAgentCommissionAmount()));
                    });
                    countDataVO.setValidDealAmount(countDataVO.getOrderAmount().subtract(countDataVO.getRefundAmount()));
                    countDataVO.setFormatPayTime(payTime);
                    countDataVO.setPayWay(payWay);
                    countDataVO.setOrderNum(orderVoList2.size());
                    countDataVO.setPayChannel(payChanel);
                    payChanelCountDataMap.put(payChanel, countDataVO);

                });

                payWayCountDataMap.put(payWay, payChanelCountDataMap);
            });
            dataMap.put(payTime, payWayCountDataMap);
        });
        List<Map<Integer, Map<Integer, CountDataVO>>> content = dataMap.entrySet().stream()
                .skip((condition.getPageNum() - 1) * condition.getPageSize())
                .limit(condition.getPageSize())
                .map(Map.Entry::getValue).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        List<CountDataVO> result = new ArrayList<>();
        content.forEach(integerCountDataVOMap -> integerCountDataVOMap
                .forEach((integer, integerCountDataVOMap1) -> result.addAll(new ArrayList<CountDataVO>(integerCountDataVOMap1.values()))));
        map.put("totalPage", content.size() % condition.getPageSize() == 0 ? content.size() / condition.getPageSize() : content.size() / condition.getPageSize() + 1);
        map.put("totalElement", content.size());
        map.put("data", result);
        return Resp.success(map);
    }

    /*
     * @author Created by zk on 2019/5/6 21:15
     * @Description 计算佣金并将相关数据计入orderVO中
     *              orderVo：订单信息  merchantVO：与该订单相关联的商户信息
     *              company：与该商户相关联的公司信息  type：1-直属商户 2-一级代理商下属二级代理商下属商户
     */
    private BigDecimal countCommission(OrderVo orderVo, MerchantVO merchantVO,
                                       Company company, int type) {
        //计算该公司分佣金额
        //订单金额-退款金额
        BigDecimal amount = orderVo.getTotalPrice().subtract(orderVo.getRefundPayPrice());
        //记录商户名
        orderVo.setMerchantName(merchantVO.getName());
        //计算抽佣金额(实际金额[订单金额-退款金额]*(商户抽佣比例-支付通道抽佣比例))
        BigDecimal merchatPayRate = getPayProrate(merchantVO.getPayProrata());
        amount = amount.multiply(merchatPayRate.subtract(orderVo.getInterestRate()));
        BigDecimal commissionAmount;
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            //如果为服务商
            //商户直属二级代理商或者二级代理商
            Company c = findCompany(merchantVO.getCompanyId());
            BigDecimal companyPayPrata = getPayProrate(c.getPayProrata());
            if (Company.Type.OPERATOR.getCode().equals(c.getType())) {
                //如果该商户上级为一级代理商
                //计算一级代理商抽佣
                orderVo.setAgentCommissionAmount(amount.multiply(companyPayPrata));
                //佣金(抽佣金额-一级代理商抽佣)
                commissionAmount = amount.subtract(orderVo.getAgentCommissionAmount());
                //记录一级代理商名
                orderVo.setAgentName(c.getName());
            } else {
                //如果该商户上级为二级代理商，则找出上级一级代理商，算出佣金(抽佣金额*(1-一级代理商分佣比例))
                //记录二级代理商名称
                orderVo.setSubAgentName(c.getName());
                Company parent = findCompany(c.getParentId());
                //记录一级代理商名称
                orderVo.setAgentName(parent.getName());
                //(一级代理商佣金+二级代理商佣金) = 抽佣金额*一级代理商分佣比例
                BigDecimal agentCommissionAmount = amount.multiply(getPayProrate(parent.getPayProrata()));
                //计算服务商获取的佣金(抽佣金额-(一级代理商佣金+二级代理商佣金))
                commissionAmount = amount.subtract(agentCommissionAmount);
                //记录二级代理商佣金((一级代理商佣金+二级代理商佣金)*二级代理商分佣比例)
                orderVo.setSubAgentCommissionAmount(agentCommissionAmount.multiply(companyPayPrata));
                //记录一级代理商佣金(agentCommissionAmount-二级代理商佣金)
                orderVo.setAgentCommissionAmount(agentCommissionAmount.subtract(orderVo.getSubAgentCommissionAmount()));

            }
        } else if (Company.Type.DISTRIBUTUTORS.getCode().equals(company.getType())) {
            //如果为二级代理商
            //找出该二级代理商的上级一级代理商
            Company parent = findCompany(company.getParentId());
            //分佣金额 = 抽佣金额*(上级一级代理商分佣比例)*(二级代理商分佣比例)
            commissionAmount = amount.multiply(getPayProrate(parent.getPayProrata())).multiply(getPayProrate(company.getPayProrata()));
            orderVo.setSubAgentName(company.getName());
            orderVo.setAgentName(parent.getName());
        } else {
            //如果为一级代理商
            orderVo.setAgentName(company.getName());
            if (type == 1) {
                //查询一级代理商下属商户
                commissionAmount = amount.multiply(getPayProrate(company.getPayProrata()));
            } else {
                //查询下属二级代理商下属商户
                //查询出该笔订单所属二级代理商
                Company child = findCompany(merchantVO.getCompanyId());
                //一级代理商和二级代理商获取的佣金总和
                BigDecimal agentCommissionAmount = amount.multiply(getPayProrate(company.getPayProrata()));
                //记录二级代理商佣金
                orderVo.setSubAgentCommissionAmount(agentCommissionAmount.multiply(getPayProrate(child.getPayProrata())));
                //该一级代理商获取的佣金为agentCommissionAmount-二级代理商佣金
                commissionAmount = agentCommissionAmount.subtract(orderVo.getSubAgentCommissionAmount());
                orderVo.setSubAgentName(child.getName());
            }
        }
        //将佣金记入vo中，方便接下来的模块调用
        orderVo.setCommissionAmount(commissionAmount);
        return commissionAmount;
    }

    private BigDecimal getPayProrate(BigDecimal payProrata) {
        return payProrata == null ? BigDecimal.ZERO : payProrata;
    }

    private List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range = DateUtil.rangeToList(startDate, endDate, dateField);
        return (List)range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
    }

    /**
     * @author Created by zk on 2019/5/1 13:27
     * @Description 通过上级companyId在company临时缓存中查询
     */
    private Company findCompany(String companyId) {
        return companyService.findOne(companyId);
    }

    /**
     * 我的佣金
     * @param userId
     * @param condition
     * @return
     */
    @GetMapping("/commissionAmount_line_chart_all")
    public Resp commissionAmountAll(@UserId String userId, StatisticsConditionDTO condition) {

        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());

        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);

        CountDataVO countDataVOMain = new CountDataVO();

        List<String> xData = dateRangeList(condition.getStartTime(), condition.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        Map<String, CommissionAndNumVo> dataMap = new LinkedHashMap<>();
        for (String data : xData) {
            dataMap.put(data, new CommissionAndNumVo());
        }


        for (DateTime dateTime : dateTimeList) {


            String tempDate = DateUtil.formatDate(dateTime);



            String today = DateUtil.today();


            if (tempDate.equals(today)) {

                Company company = (Company)this.companyService.findOne(condition.getCompanyId());
                Map<String, Object> map = this.statisticsService.getToday(company);


                CountDataVO countDataVOTempOther = (CountDataVO)map.get("countData");


                this.statisticsService.setData(countDataVOMain, countDataVOTempOther);


                List<OrderVo> listTemp = (List)map.get("orderVoList");

                continue;
            }


            String countDataKey = condition.getCompanyId() + "_" + tempDate + "_countData";

            CountDataVO countDataVOTemp = this.statisticsService.getObj(countDataKey, CountDataVO.class);

            CommissionAndNumVo commissionAndNumVo = dataMap.get(DateUtil.format(dateTime, "yyyy-MM-dd"));
            if(commissionAndNumVo != null){
                if(countDataVOTemp != null){
                    commissionAndNumVo.setCommissionAmount(commissionAndNumVo.getCommissionAmount().add(countDataVOTemp.getCommissionAmount()));
                }else {
                    commissionAndNumVo.setCommissionAmount(commissionAndNumVo.getCommissionAmount().add(BigDecimal.ZERO));
                }
            }
            log.info("countDataController缓存数据{}   ", countDataVOTemp);

        }


        List<String> yData = new ArrayList<>(xData.size());
        List<String> num = new ArrayList<>(xData.size());
        for (String s : dataMap.keySet()) {
            CommissionAndNumVo commissionAndNumVo = dataMap.get(s);
            yData.add(commissionAndNumVo.getCommissionAmount().stripTrailingZeros().toPlainString());
            num.add(commissionAndNumVo.getNum().toString());
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }


    /**
     * 计算公司下总商户每日总佣金记录
     */
    @GetMapping("/get_day_count_commission_amount")
    public Resp getDayCountCommissionAmount(StatisticsConditionDTO condition){
        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());

        List<StatisticsCountDataVo> list = new ArrayList<>();

        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);

        for (DateTime dateTime : dateTimeList) {
            String tempDate = DateUtil.formatDateTime(dateTime);
            Company company = (Company)this.companyService.findOne(condition.getCompanyId());
            StatisticsCountDataVo cacheCountDataAndOrder = this.statisticsService.getCacheCountDataAndOrderVoList(company, tempDate, tempDate);
            list.add(cacheCountDataAndOrder);
        }

        if(list.size() <= 0){
            return Resp.success("当前用户无数据");
        }
        list.sort(Comparator.comparing(StatisticsCountDataVo::getSaveDay).reversed());

        return Resp.success(list,"返回成功");

    }


    /**
     * 计算公司下单个商户每日总佣金记录
     */
    @GetMapping("/get_day_merchant_commission_amount")
    public Resp getDayMerchantCommissionAmount(MerchantConditionDTO condition){
        DateTime startTime = DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime = DateUtil.endOfDay(condition.getEndTime());

        List<StatisticsCountDataVo> list = new ArrayList<>();

        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);

        for (DateTime dateTime : dateTimeList) {

            String tempDate = DateUtil.formatDate(dateTime);

            Company company = (Company)this.companyService.findOne(condition.getCompanyId());
            StatisticsCountDataVo cacheCountDataAndOrder = this.statisticsService.getMerchantDataAndOrderVoList(company,condition.getMerchantId(), tempDate, tempDate);
            list.add(cacheCountDataAndOrder);

        }

        if(list.size() <= 0){
            return Resp.success("当前用户无数据");
        }
        list.sort(Comparator.comparing(StatisticsCountDataVo::getSaveDay).reversed());

        return Resp.success(list,"返回成功");
    }

}
