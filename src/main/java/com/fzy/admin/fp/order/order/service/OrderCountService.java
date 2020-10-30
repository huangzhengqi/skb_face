package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.order.order.dto.*;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.auth.dto.OrderCountDTO;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.order.OrderConstant;
import com.fzy.admin.fp.order.order.EnumInterface;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.vo.DealDetailStatisticsVO;
import com.fzy.admin.fp.order.order.vo.DealStatisticsVO;
import com.fzy.admin.fp.sdk.merchant.domain.DataStatisticsLineDetailVO;
import com.fzy.admin.fp.sdk.merchant.domain.KeyValueVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-05-06 16:40
 * @description 后台订单统计业务
 */
@Service
public class OrderCountService {

    @Resource
    private OrderService orderService;

    @Resource
    private MerchantUserFeign merchantUserFeign;
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private MerchantUserService merchantUserService;


    /**
     * @author Created by wtl on 2019/5/6 17:58
     * @Description 今日和昨日订单统计
     */
    public OrderIndexCount orderCount(@UserId String userId) {
        // 用户信息
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        OrderIndexCount orderIndexCount = new OrderIndexCount();
        // 今日订单列表
        List<Order> todayOrders = orderService.getRepository().findOrderByTime(merchantUserDTO.getMerchantId(), OrderConstant.failedOrderStatusSet, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        for (Order todayOrder : todayOrders) {
            orderIndexCount.setTodayOrderPrice(orderIndexCount.getTodayOrderPrice().add(todayOrder.getActPayPrice().subtract(todayOrder.getRefundPayPrice())));
        }
        orderIndexCount.setTodayOrderAmount(todayOrders.size());
        // 昨日订单列表
        List<Order> yesterdayOrders = orderService.getRepository().findOrderByTime(merchantUserDTO.getMerchantId(), OrderConstant.failedOrderStatusSet, DateUtil.beginOfDay(DateUtil.yesterday()), DateUtil.endOfDay(DateUtil.yesterday()));
        for (Order yesterdayOrder : yesterdayOrders) {
            orderIndexCount.setYesterdayOrderPrice(orderIndexCount.getYesterdayOrderPrice().add(yesterdayOrder.getActPayPrice().subtract(yesterdayOrder.getRefundPayPrice())));
        }
        orderIndexCount.setYesterdayOrderAmount(yesterdayOrders.size());
        return orderIndexCount;
    }

    /**
     * @author Created by wtl on 2019/5/6 17:58
     * @Description 近15天，每天订单总金额和总笔数统计
     */
    public Map<String, Object> orderCountLine(@UserId String userId) {
        // 用户信息
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 金额集合
        List<BigDecimal> sum = new LinkedList<>();
        // 数量集合
        List<Integer> number = new LinkedList<>();

        // 昨天
        Date date = DateUtil.yesterday();
        List<DateTime> range = DateUtil.rangeToList(DateUtil.offsetDay(date, -14), date, DateField.DAY_OF_WEEK);
        List<String> xData = range.stream().map(dateTime -> DateUtil.format(dateTime, "yyyy-MM-dd")).collect(Collectors.toList());
        // 近15天订单
        List<Order> orders = orderService.getRepository().findOrderByTime(merchantUserDTO.getMerchantId(), OrderConstant.failedOrderStatusSet, DateUtil.beginOfDay(DateUtil.offsetDay(date, -14)), DateUtil.endOfDay(date));
        Map<String, ChartDTO> orderMap = new LinkedHashMap<>();
        for (String s : xData) {
            orderMap.put(s, new ChartDTO());
        }
        for (Order order : orders) {
            ChartDTO chartDTO = orderMap.get(DateUtil.format(order.getPayTime(), "yyyy-MM-dd"));
            if (chartDTO != null) {
                chartDTO.setAmount(chartDTO.getAmount() + 1);
                chartDTO.setPrice(chartDTO.getPrice().add(order.getActPayPrice().subtract(order.getRefundPayPrice())));
            }
        }
        for (String s : orderMap.keySet()) {
            ChartDTO chartDTO = orderMap.get(s);
            sum.add(chartDTO.getPrice());
            number.add(chartDTO.getAmount());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("xData", xData);
        map.put("sum", sum);
        map.put("number", number);
        return map;
    }

    public OrderIndexCountNew orderCountNew(String userId, OrderCountDTO orderCountDTO) {
        OrderIndexCountNew orderIndexCountNew = new OrderIndexCountNew();
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        //查询出所有有效订单
        List<Order> orderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS = new ArrayList<>();
        EnumInterface[] enmus = Order.PayWay.values();
        //交易金额 交易笔数
        BigDecimal total = new BigDecimal(0);
        Integer totalCount = 0;
        for (Order order : orderList) {
            total = total.add(order.getActPayPrice());
            totalCount++;
        }
        //退款金额 退款笔数
        List<Order> refundOrderList = orderList.stream().filter(order -> {
            if (order.getStatus().equals(5)) {
                return true;
            } else if (order.getStatus().equals(6)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        //交易金额 交易笔数
        BigDecimal refundtotal = new BigDecimal(0);
        Integer refundtotalCount = 0;
        for (Order order : refundOrderList) {
            refundtotal = refundtotal.add(order.getActPayPrice());
            refundtotalCount++;
        }
        // 实际营收 交易金额-退款金额
        BigDecimal ActualMoney = total.subtract(refundtotal);
        //支付宝交易笔数
        Integer zfb = orderList.stream().filter(order -> order.getPayWay().equals(2)).collect(Collectors.toList()).size();
        //微信交易笔数
        Integer wx = orderList.stream().filter(order -> order.getPayWay().equals(1)).collect(Collectors.toList()).size();
        //其他方式交易笔数
        Integer other = orderList.stream().filter(order -> {
            if (order.getPayWay().equals(1)) {
                return false;
            } else if (order.getPayWay().equals(2)) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList()).size();
        orderIndexCountNew.setTotal(total);
        orderIndexCountNew.setTotalCount(totalCount);
        orderIndexCountNew.setRefund(refundtotal);
        orderIndexCountNew.setRefundCount(refundtotalCount);
        orderIndexCountNew.setActualMoney(ActualMoney);
        orderIndexCountNew.setZfbTotalPrice(zfb);
        orderIndexCountNew.setWxTotalPrice(wx);
        orderIndexCountNew.setOhterTotalPrice(other);
        return orderIndexCountNew;
    }


    /**
     * hzq 2019-12-12
     * @param userId
     * @param orderCountDTO
     * @return
     */
    public OrderIndexDayCount orderCountDayNew(String userId, OrderCountDTO orderCountDTO) {

        OrderIndexDayCount orderIndexDayCount = new OrderIndexDayCount();


        List<Order> todayOrderList = new ArrayList<>();
        List<Order> yesterdayOrderList = new ArrayList<>();

        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        Date yesterdayStartTime = DateUtils.getYesterday(orderCountDTO.getStartTime());//昨天开始时间
        Date yesterdayEndTime = DateUtils.getYesterday(orderCountDTO.getEndTime());//昨天结束时间
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);

        //店员
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        if(merchantUser.getUserType().equals(MerchantUser.UserType.EMPLOYEES.getCode())){

            //查询出今日所有有效订单
            todayOrderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId(), orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());

            //查询出昨日所有有效订单
            yesterdayOrderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId() ,yesterdayStartTime, yesterdayEndTime,Order.PayWay.MEMBERCARD.getCode());
        }else {

            //查询出今日所有有效订单
            todayOrderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());

            //查询出昨日所有有效订单
            yesterdayOrderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, yesterdayStartTime, yesterdayEndTime,Order.PayWay.MEMBERCARD.getCode());
        }

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS = new ArrayList<>();
        EnumInterface[] enmus = Order.PayWay.values();
        //交易金额 交易笔数
        BigDecimal total = new BigDecimal(0);
        Integer totalCount = 0;
        for (Order order : todayOrderList) {
            total = total.add(order.getActPayPrice());
            totalCount++;
        }
        //退款金额 退款笔数
        List<Order> refundOrderList = todayOrderList.stream().filter(order -> {
            if (order.getStatus().equals(5)) {
                return true;
            } else if (order.getStatus().equals(6)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        //交易金额 交易笔数
        BigDecimal refundtotal = new BigDecimal(0);
        Integer refundtotalCount = 0;
        for (Order order : refundOrderList) {
            refundtotal = refundtotal.add(order.getActPayPrice());
            refundtotalCount++;
        }
        // 实际营收 交易金额-退款金额
        BigDecimal ActualMoney = total.subtract(refundtotal);

        orderIndexDayCount.setTodayOrderPrice(total); //今日交易金额
        orderIndexDayCount.setTodayOrderAmount(totalCount); //今日交易笔数量
        orderIndexDayCount.setTodayRefund(refundtotal); //今日退款金额
        orderIndexDayCount.setTodayRefundCount(refundtotalCount);  //今日退款次数
        orderIndexDayCount.setTodayTotal(ActualMoney); //今日实际营收

        //昨日交易金额  昨日交易笔数
        BigDecimal yesterdayTotal = new BigDecimal(0);
        Integer yesterdayTotalCount = 0;
        for (Order order : yesterdayOrderList) {
            yesterdayTotal = yesterdayTotal.add(order.getActPayPrice());
            yesterdayTotalCount++;
        }
        //退款金额 退款笔数
        List<Order> yesterdayRefundOrderList = yesterdayOrderList.stream().filter(order -> {
            if (order.getStatus().equals(5)) {
                return true;
            } else if (order.getStatus().equals(6)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        //交易金额 交易笔数
        BigDecimal yesterdayRefundtotal = new BigDecimal(0);
        Integer yesterdayRefundtotalCount = 0;
        for (Order order : yesterdayRefundOrderList) {
            yesterdayRefundtotal = yesterdayRefundtotal.add(order.getActPayPrice());
            yesterdayRefundtotalCount++;
        }
        // 实际营收 交易金额-退款金额
        BigDecimal yesterdayActualMoney = yesterdayTotal.subtract(yesterdayRefundtotal);
        orderIndexDayCount.setYesterdayTotal(yesterdayActualMoney);//昨日实际营收
        orderIndexDayCount.setYesterdayRefund(yesterdayRefundtotal);;//昨日退款金额
        orderIndexDayCount.setYesterdayRefundCount(yesterdayRefundtotalCount);//昨日退款笔数
        orderIndexDayCount.setYesterdayOrderPrice(yesterdayTotal);//昨日订单金额
        orderIndexDayCount.setYesterdayOrderAmount(yesterdayTotalCount);//昨日订单数量

        return orderIndexDayCount;

    }

    //查询交易详情数据
    public List<DealDetailStatisticsVO> dealDetailStatistics(String userId,OrderCountDTO orderCountDTO){
        List<String> dayData = DateUtils.dateRangeList(orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        List<DealDetailStatisticsVO> dayDealDetailStatisticsVOList = new ArrayList<>(); //日期

        //查询出今日所有有效订单
        List<Order> orderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());
        List<DealDetailStatisticsVO> dealDetailStatisticsVOList = new ArrayList<>();

        for (String dateTime : dayData) {
            DealDetailStatisticsVO dealDetailStatisticsVO1 =new DealDetailStatisticsVO();


            //日期
            for (Order order : orderList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.format(order.getPayTime()).equals(dateTime)) {
                    DealDetailStatisticsVO dealDetailStatisticsVO=new DealDetailStatisticsVO();
                    //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
                    //交易金额 交易笔数
                    BigDecimal total = new BigDecimal(0);
                    int totalCount = 0;
                    for (Order o : orderList) {
                        total = total.add(o.getActPayPrice());
                        totalCount++;
                    }
                    //退款金额 退款笔数
                    List<Order> refundOrderList = orderList.stream().filter(o -> {
                        if (o.getStatus().equals(5)) {
                            return true;
                        } else if (o.getStatus().equals(6)) {
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    //交易金额 交易笔数
                    BigDecimal refundtotal = new BigDecimal(0);
                    Integer refundtotalCount = 0;
                    for (Order o : refundOrderList) {
                        refundtotal = refundtotal.add(o.getActPayPrice());
                        refundtotalCount++;
                    }
                    // 实际营收 交易金额-退款金额
                    BigDecimal ActualMoney = total.subtract(refundtotal);

                    dealDetailStatisticsVO.setTotal(total);
                    dealDetailStatisticsVO.setTotalCount(totalCount);
                    dealDetailStatisticsVO.setRefund(refundtotal);
                    dealDetailStatisticsVO.setRefundCount(refundtotalCount);
                    dealDetailStatisticsVO.setActualMoney(ActualMoney);
                    dealDetailStatisticsVO.setPayTime(dateTime);
                    int zfb =0;
                    int wx=0;
                    int bank=0;
                    int vip=0;
                    int other =0;
                    if(order.getPayWay().equals(1)){//微信_1
                        wx++;
                    }else if(order.getPayWay().equals(2)){//支付宝_2
                        zfb++;
                    }else if(order.getPayWay().equals(3)){//银行卡_3
                        bank++;
                    }else if(order.getPayWay().equals(4)){//会员卡_4
                        vip++;
                    }else if(order.getPayWay().equals(99)){//未知_99
                        other++;
                    }
                    dealDetailStatisticsVO.setZfbTotalPrice(zfb);
                    dealDetailStatisticsVO.setWxTotalPrice(wx);
                    dealDetailStatisticsVO.setOhterTotalPrice(other);
                    dealDetailStatisticsVO.setBkTotalPrice(bank);
                    dealDetailStatisticsVO.setVipTotalPrice(vip);
                    dealDetailStatisticsVOList.add(dealDetailStatisticsVO);
                }
            }
            BigDecimal total = dealDetailStatisticsVOList.stream().map(DealDetailStatisticsVO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add); //当天订单总额
            double totalCount = dealDetailStatisticsVOList.stream().mapToDouble(DealDetailStatisticsVO::getTotalCount).sum();//当天订单总额
            dealDetailStatisticsVO1.setTotalCount(new Double(totalCount).intValue());
            dealDetailStatisticsVO1.setTotal(total);
            dealDetailStatisticsVO1.setPayTime(dateTime);
            dayDealDetailStatisticsVOList.add(dealDetailStatisticsVO1);

        }

        return dayDealDetailStatisticsVOList;
    }

    //查询交易金额数据
    public Map<String,Object> dealStatistics(String userId,Integer type){
        //得到今天0点0分0秒的时间
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date beginOfDate = cal.getTime();

        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());
        List<String> todayData = DateUtils.dateRangeList(beginOfDate, new Date(),
                DateField.HOUR_OF_DAY, "yyyy-MM-dd HH:00:00");

        //得到昨天0点0分0秒的时间
        cal.add(Calendar.DATE, -1);
        //得到昨天0点0分0秒的时间
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
        Date yestBeginOfDate = cal.getTime();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 0, 0);
        Date yestEndOfDate = cal.getTime();
        //cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH-1), 0, 0, 0);
        //Date endOfTime = cal.getTime();
        List<String> yesterdayData = DateUtils.dateRangeList(yestBeginOfDate, yestEndOfDate,
                DateField.HOUR_OF_DAY, "yyyy-MM-dd HH:00:00");

        List<Order> orderList = new ArrayList<>();
        List<Order> yesterdayOrderList = new ArrayList<>();



        MerchantUser merchantUser = merchantUserService.findOne(userId);
        //店员
        if(merchantUser.getUserType().equals(MerchantUser.UserType.EMPLOYEES.getCode())){
            //查询出所有有效订单
            orderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId() ,beginOfDate, new Date(),Order.PayWay.MEMBERCARD.getCode());
            yesterdayOrderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId(), yestBeginOfDate, beginOfDate,Order.PayWay.MEMBERCARD.getCode());

        }else {

            //查询出所有有效订单
            orderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, beginOfDate, new Date(),Order.PayWay.MEMBERCARD.getCode());
            yesterdayOrderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, yestBeginOfDate, beginOfDate,Order.PayWay.MEMBERCARD.getCode());
        }





        List<DealStatisticsVO> yesterdayList=new ArrayList<>();//当天的订单
        List<DealStatisticsVO> todayList =new ArrayList<>();//当天的订单
        Map<String,Object> result=new HashMap();
        if(type==1){//得到分时的数据
            yesterdayList = getDealStatisticsList(yesterdayData, yesterdayOrderList);//昨天的订单
            todayList = getDealStatisticsList(todayData, orderList);//当天的订单
        }else{
            yesterdayList = getDealStatisticsListTotal(yesterdayData, yesterdayOrderList);//昨天的订单
            todayList = getDealStatisticsListTotal(todayData, orderList);//当天的订单
        }
        BigDecimal totalFee = yesterdayOrderList.stream().map(Order::getActPayPrice).reduce(BigDecimal.ZERO,BigDecimal::add); //昨天的交易金额
        result.put("yesterdayTotalFee",totalFee);
        result.put("yesterdayList",yesterdayList);
        result.put("todayList",todayList);
        return result;
    }

    //得到分时的数据
    private List<DealStatisticsVO> getDealStatisticsList(List<String> xData, List<Order> orderList){
        List<DealStatisticsVO> dealStatisticsVOList =new ArrayList<>();
        for (String dateTime : xData) {
            BigDecimal total = new BigDecimal(0);//交易金额
            Integer totalCount = 0;
            DealStatisticsVO dealStatisticsVO =new DealStatisticsVO();
            //判断得到时间段支付的金额
            for (Order order : orderList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                if (sdf.format(order.getPayTime()).equals(dateTime)) {
                    if(order.getStatus().equals(5)||order.getStatus().equals(6)){//减掉退款金额
                        total = total.subtract(order.getActPayPrice());
                    }else{
                        total = total.add(order.getActPayPrice());
                    }
                    totalCount++;
                }
            }
            dealStatisticsVO.setTotal(total);
            dealStatisticsVO.setTotalCount(totalCount);
            dealStatisticsVO.setPayTime(dateTime);
            dealStatisticsVOList.add(dealStatisticsVO);
        }
        return dealStatisticsVOList;
    }

    //得到累计的数据
    private List<DealStatisticsVO> getDealStatisticsListTotal(List<String> xData, List<Order> orderList){
        List<DealStatisticsVO> dealStatisticsVOList =new ArrayList<>();
        BigDecimal total = new BigDecimal(0);//交易金额
        Integer totalCount = 0;
        for (String dateTime : xData) {
            DealStatisticsVO dealStatisticsVO =new DealStatisticsVO();
            //判断得到时间段支付的金额
            for (Order order : orderList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                if (sdf.format(order.getPayTime()).equals(dateTime)) {
                    if(order.getStatus().equals(5)||order.getStatus().equals(6)){//减掉退款金额
                        total = total.subtract(order.getActPayPrice());
                    }else{
                        total = total.add(order.getActPayPrice());
                    }
                    totalCount++;
                }
            }
            dealStatisticsVO.setTotal(total);
            dealStatisticsVO.setTotalCount(totalCount);
            dealStatisticsVO.setPayTime(dateTime);
            dealStatisticsVOList.add(dealStatisticsVO);
        }
        return dealStatisticsVOList;
    }




    /**
     * hzq  2019-12-12
     * @param userId
     * @param orderCountDTO
     * @return
     */
    public OrderPeriodCount order_count_period(String userId, OrderCountDTO orderCountDTO) {

        OrderPeriodCount orderPeriodCount = new OrderPeriodCount();
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());
        List<Order> orderList = new ArrayList<>();
        List<Order> lastOrderList = new ArrayList<>();



        DateTime startTime = DateUtil.beginOfDay(orderCountDTO.getStartTime());
        DateTime endTime = DateUtil.endOfDay(orderCountDTO.getEndTime());
        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);



        Date lastStartTime = DateUtils.getLastday(startTime, dateTimeList.size()); //上个时间段开始日期
        Date lastEndTime = DateUtils.getLastday(endTime, dateTimeList.size()); //上个时间段结束日期

        MerchantUser merchantUser = merchantUserService.findOne(userId);

        if(merchantUser.getUserType().equals(MerchantUser.UserType.EMPLOYEES.getCode())){
            //查询出所有有效订单
            orderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId(), orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());

            //查询出上个时间段所有有效订单
            lastOrderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId(), lastStartTime, lastEndTime,Order.PayWay.MEMBERCARD.getCode());
        }else {
            //查询出所有有效订单
            orderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, orderCountDTO.getStartTime(), orderCountDTO.getEndTime(),Order.PayWay.MEMBERCARD.getCode());

            //查询出上个时间段所有有效订单
            lastOrderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, lastStartTime, lastEndTime,Order.PayWay.MEMBERCARD.getCode());
        }

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS = new ArrayList<>();
        EnumInterface[] enmus = Order.PayWay.values();
        //交易金额 交易笔数
        BigDecimal total = new BigDecimal(0);
        Integer totalCount = 0;
        for (Order order : orderList) {
            total = total.add(order.getActPayPrice());
            totalCount++;
        }
        //退款金额 退款笔数
        List<Order> refundOrderList = orderList.stream().filter(order -> {
            if (order.getStatus().equals(5)) {
                return true;
            } else if (order.getStatus().equals(6)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        //交易金额 交易笔数
        BigDecimal refundtotal = new BigDecimal(0);
        Integer refundtotalCount = 0;
        for (Order order : refundOrderList) {
            refundtotal = refundtotal.add(order.getActPayPrice());
            refundtotalCount++;
        }
        // 实际营收 交易金额-退款金额
        BigDecimal ActualMoney = total.subtract(refundtotal);

        orderPeriodCount.setThisOrderPrice(total); //本次时间段交易金额
        orderPeriodCount.setThisOrderAmount(totalCount);//本次时间段交易数量
        orderPeriodCount.setThisRefund(refundtotal);//本次时间段退款金额
        orderPeriodCount.setThisRefundCount(refundtotalCount);//本次时间段退款笔数
        orderPeriodCount.setThisTotal(ActualMoney);//本次时间段实际营收

        BigDecimal lastTotal = new BigDecimal(0); //上个时间段交易金额
        Integer lastTotalCount = 0; //上个时间段交易笔数
        for (Order order : lastOrderList) {
            lastTotal = lastTotal.add(order.getActPayPrice());
            lastTotalCount++;
        }
        //退款金额 退款笔数
        List<Order> lastRefundOrderList = lastOrderList.stream().filter(order -> {
            if (order.getStatus().equals(5)) {
                return true;
            } else if (order.getStatus().equals(6)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        BigDecimal lastRefundtotal = new BigDecimal(0);//上个时间段退款金额
        Integer lastRefundtotalCount = 0; //上个时间段退款笔数
        for (Order order : lastRefundOrderList) {
            lastRefundtotal = lastRefundtotal.add(order.getActPayPrice());
            lastRefundtotalCount++;
        }
        // 上个时间段实际营收 交易金额-退款金额
        BigDecimal lastActualMoney = lastTotal.subtract(lastRefundtotal);
        orderPeriodCount.setLastOrderPrice(lastTotal);//上个时间段交易金额
        orderPeriodCount.setLastOrderAmount(lastTotalCount);//上个时间段交易笔数
        orderPeriodCount.setLastRefund(lastRefundtotal);//上个时间段退款金额
        orderPeriodCount.setLastRefundCount(lastRefundtotalCount);//上个时间段退款笔数
        orderPeriodCount.setLastTotal(lastActualMoney);//上个时间段实际营收
        return orderPeriodCount;
    }

    public List<DealDetailStatisticsVO> dealDetailStatistics1(String userId, OrderCountDTO orderCountDTO) {

        DateTime startTime = DateUtil.beginOfDay(orderCountDTO.getStartTime());
        DateTime endTime = DateUtil.endOfDay(orderCountDTO.getEndTime());

        List<DateTime> dateTimeList = DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);

        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        List<DealDetailStatisticsVO> dataStatisticsLineDetailVOS = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        MerchantUser merchantUser = merchantUserService.findOne(userId);

        for (DateTime dateTime:dateTimeList) {

            DateTime dayStartTime = DateUtil.beginOfDay(dateTime);
            DateTime dayEndTime = DateUtil.endOfDay(dateTime);

            DealDetailStatisticsVO dealDetailStatisticsVO = new DealDetailStatisticsVO();
            List<Order> orderList = new ArrayList<>();


            if(merchantUser.getUserType().equals(MerchantUser.UserType.EMPLOYEES.getCode())){

                //查询出今日所有有效订单
                orderList = orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(),merchantUser.getStoreId(), dayStartTime, dayEndTime, Order.PayWay.MEMBERCARD.getCode());

            }else {
                //查询出今日所有有效订单
                orderList = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, dayStartTime, dayEndTime, Order.PayWay.MEMBERCARD.getCode());
            }



            BigDecimal total = new BigDecimal(0);//交易金额
            Integer totalCount = 0;//交易笔数
            BigDecimal refundtotal = new BigDecimal(0);//退款金额
            Integer refundtotalCount = 0;//退款笔数
            BigDecimal ActualMoney = new BigDecimal(0);// 实际营收 交易金额-退款金额
            Integer zfb = 0;
            Integer wx = 0;
            Integer bank = 0;
            Integer vip = 0;
            Integer other = 0;

            //日期订单
            for (Order order : orderList) {

                total = total.add(order.getActPayPrice());
                totalCount++;

                //退款金额 退款笔数
                List<Order> refundOrderList = orderList.stream().filter(o -> {
                    if (o.getStatus().equals(5)) {
                        return true;
                    } else if (o.getStatus().equals(6)) {
                        return true;
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
                for (Order o : refundOrderList) {
                    refundtotal = refundtotal.add(o.getActPayPrice());
                    refundtotalCount++;
                }



                if (order.getPayWay().equals(1)) {//微信_1
                    wx++;
                } else if (order.getPayWay().equals(2)) {//支付宝_2
                    zfb++;
                } else if (order.getPayWay().equals(3)) {//银行卡_3
                    bank++;
                } else if (order.getPayWay().equals(4)) {//会员卡_4
                    vip++;
                } else if (order.getPayWay().equals(99)) {//未知_99
                    other++;
                }
            }
            ActualMoney = total.subtract(refundtotal);//实际营收

            dealDetailStatisticsVO.setTotal(total);
            dealDetailStatisticsVO.setTotalCount(totalCount);
            dealDetailStatisticsVO.setRefund(refundtotal);
            dealDetailStatisticsVO.setRefundCount(refundtotalCount);
            dealDetailStatisticsVO.setActualMoney(ActualMoney);
            dealDetailStatisticsVO.setZfbTotalPrice(zfb);
            dealDetailStatisticsVO.setWxTotalPrice(wx);
            dealDetailStatisticsVO.setOhterTotalPrice(other);
            dealDetailStatisticsVO.setBkTotalPrice(bank);
            dealDetailStatisticsVO.setVipTotalPrice(vip);
            String payTime = format.format(dateTime);
            dealDetailStatisticsVO.setPayTime(payTime);
            dataStatisticsLineDetailVOS.add(dealDetailStatisticsVO);
        }
        return dataStatisticsLineDetailVOS;
    }
}
