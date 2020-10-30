package com.fzy.admin.fp.order.order.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.vo.ChartDataVO;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.order.OrderConstant;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.MerchantRunningAccountListConditionDTO;
import com.fzy.admin.fp.order.order.dto.MerchantRunningAccountOverviewConditionDTO;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.vo.CountDataVO;
import com.fzy.admin.fp.order.order.vo.MerchantOverviewCountVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-08 20:05
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/order/running_account")
@Component("orderStatisticsController")
public class StatisticsController extends BaseContent {
    @Resource
    private OrderService orderService;
    @Resource
    private MerchantUserFeign merchantUserFeign;

    /**
     * @author Created by zk on 2019/5/12 22:16
     * @Description 商户流水概览第一块汇总卡片
     */
    @GetMapping("/merchant_running_account_overview_count_data")
    public Resp merchantRunningAccountOverviewCountData(@UserId String userId,
                                                        MerchantRunningAccountOverviewConditionDTO condition) {
        condition.initDate();
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        List<Order> orderList;
        //判断商户还是店长
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            //商户 根据商户Id查询订单
            if (ParamUtil.isBlank(condition.getStoreId())) {
                condition.setMerchantId(merchantUserDTO.getMerchantId());
            }
        } else if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            //店长 根据门店Id查询订单
            condition.setStoreId(merchantUserDTO.getStoreId());
        } else {
            return Resp.success(new MerchantOverviewCountVO());
        }
        Specification specification = ConditionUtil.createSpecification(condition);
        orderList = orderService.findAll(specification);
        if (ParamUtil.isBlank(orderList)) {
            orderList = new ArrayList<>();
        }
        orderList = orderList.parallelStream()
                .filter(order -> !Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay()))
                .collect(Collectors.toList());
        MerchantOverviewCountVO merchantOverviewCountVO = orderService.statistics(orderList);
        OrderConstant.MERCHANT_STATISTICS_CACHE.put(userId, orderList);
        return Resp.success(merchantOverviewCountVO);
    }

    /**
     * @author Created by zk on 2019/5/28 21:03
     * @Description 流水概览实际营收曲线图
     */
    @GetMapping("/merchant_running_account_overview_order_chart")
    public Resp merchantRunningAccountOverviewOrderChart(@UserId String userId,
                                                         MerchantRunningAccountOverviewConditionDTO condition) {
        condition.initDate();
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        if (ParamUtil.isBlank(orderList)) {
            return Resp.success("");
        }
        if (DateUtil.betweenDay(condition.getStart_payTime(), condition.getEnd_payTime(), true) < 6) {
            return Resp.success("");
        }
        //根据传上来的日期区间得出X坐标轴参数
        final List<String> xData = dateRangeList(condition.getStart_payTime(), condition.getEnd_payTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        //根据付款日期初始化值(yyyy-MM-dd)对订单进行分组
        final Map<String, List<Order>> dateAndOrderListMap = orderList.stream()
                .sorted(Comparator.comparing(Order::getPayTime))
                .collect(Collectors.groupingBy(o -> DateUtil.format(o.getPayTime(), "yyyy-MM-dd")));
        //定义订单金额列表
        List<String> yData = new ArrayList<>(xData.size());
        //定义订单数量列表
        List<String> num = new ArrayList<>(xData.size());
        for (String date : xData) {
            List<Order> orders = dateAndOrderListMap.get(date);
            //若对应日期无订单，则设置默认值
            if (ParamUtil.isBlank(orders)) {
                yData.add("0");
                num.add("0");
            } else {
                num.add(String.valueOf(orders.size()));
                final String amount = orders.stream()
                        //过滤支付成功或者部分退款的订单
                        .filter(order -> Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())
                                || Order.Status.REFUNDPART.getCode().equals(order.getStatus()))
                        //匹配用户实付
                        .map(Order::getActPayPrice)
                        //累加
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        //转string
                        .stripTrailingZeros().toPlainString();
                yData.add(amount);
            }
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }

    /**
     * @author Created by zk on 2019/5/28 21:03
     * @Description 流水概览实际营收曲线图
     */
    @GetMapping("/merchant_running_account_overview_income_chart")
    public Resp merchantRunningAccountOverviewIncomeChart(@UserId String userId,
                                                          MerchantRunningAccountOverviewConditionDTO condition) {
        condition.initDate();
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        //根据传上来的日期区间得出X坐标轴参数
        final List<String> xData = dateRangeList(condition.getStart_payTime(), condition.getEnd_payTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");

        if (ParamUtil.isBlank(orderList)) {
            List<String> yData = new ArrayList<>();
            for (String date : xData) {
                yData.add("0");
            }
            ChartDataVO chartDataVO = new ChartDataVO(null, xData, yData);
            return Resp.success(chartDataVO);
        }
        //根据付款日期初始化值(yyyy-MM-dd)对订单进行分组
        final Map<String, List<Order>> dateAndOrderListMap = orderList.stream()
                .sorted(Comparator.comparing(Order::getPayTime))
                .collect(Collectors.groupingBy(o -> DateUtil.format(o.getPayTime(), "yyyy-MM-dd")));
        //定义实际营收列表
        List<String> yData = new ArrayList<>(xData.size());
        for (String date : xData) {
            List<Order> orders = dateAndOrderListMap.get(date);
            //若对应日期无订单，则设置默认值
            if (ParamUtil.isBlank(orders)) {
                yData.add("0");
            } else {
                String amount = orders.stream()
                        //过滤支付成功或者部分退款的订单
                        .filter(order -> Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())
                                || Order.Status.REFUNDPART.getCode().equals(order.getStatus()))
                        //匹配用户实付
                        .map(order -> {
                            if (Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())) {
                                return order.getActPayPrice();
                            } else {
                                return order.getActPayPrice().subtract(order.getRefundPayPrice());
                            }
                        })
                        //累加
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        //转string
                        .stripTrailingZeros().toPlainString();
                yData.add(amount);
            }
        }
        ChartDataVO chartDataVO = new ChartDataVO(null, xData, yData);
        return Resp.success(chartDataVO);
    }

    /**
     * @author Created by zk on 2019/5/28 21:03
     * @Description 流水概览退款曲线图
     */
    @GetMapping("/merchant_running_account_overview_refund_chart")
    public Resp merchantRunningAccountOverviewRefundChart(@UserId String userId,
                                                          MerchantRunningAccountOverviewConditionDTO condition) {
        condition.initDate();
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        //根据传上来的日期区间得出X坐标轴参数
        final List<String> xData = dateRangeList(condition.getStart_payTime(), condition.getEnd_payTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");

        if (ParamUtil.isBlank(orderList)) {
            List<String> yData = new ArrayList<>();
            for (String date : xData) {
                yData.add("0");
            }
            ChartDataVO chartDataVO = new ChartDataVO(null, xData, yData);
            return Resp.success(chartDataVO);
        }
        //根据付款日期初始化值(yyyy-MM-dd)对订单进行分组
        final Map<String, List<Order>> dateAndOrderListMap = orderList.stream()
                //过滤全额退款或者部分退款的订单
                .filter(order -> Order.Status.REFUNDTOTAL.getCode().equals(order.getStatus())
                        || Order.Status.REFUNDPART.getCode().equals(order.getStatus()))
                .sorted(Comparator.comparing(Order::getPayTime))
                .collect(Collectors.groupingBy(o -> DateUtil.format(o.getPayTime(), "yyyy-MM-dd")));
        //定义订单金额列表
        List<String> yData = new ArrayList<>(xData.size());
        //定义订单数量列表
        List<String> num = new ArrayList<>(xData.size());
        for (String date : xData) {
            List<Order> orders = dateAndOrderListMap.get(date);
            //若对应日期无订单，则设置默认值
            if (ParamUtil.isBlank(orders)) {
                yData.add("0");
                num.add("0");
            } else {
                num.add(String.valueOf(orders.size()));
                final String amount = orders.stream()
                        //匹配用户实付
                        .map(Order::getRefundPayPrice)
                        //累加
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        //转string
                        .stripTrailingZeros().toPlainString();
                yData.add(amount);
            }
        }
        OrderConstant.MERCHANT_STATISTICS_CACHE.put(userId, orderList);
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return Resp.success(chartDataVO);
    }

    /**
     * @author Created by zk on 2019/5/28 21:06
     * @Description 流水概览订单状态饼图
     */
    @GetMapping("/merchant_running_account_overview_status_pie_chart")
    public Resp merchantRunningAccountOverviewStatusPieChart(@UserId String userId) {
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        List<PieChartDataVO> pieChartDataVOS = orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getStatus, Order.Status.class);
        return Resp.success(pieChartDataVOS);
    }

    /**
     * @author Created by zk on 2019/5/28 21:06
     * @Description 流水概览支付方式饼图
     */
    @GetMapping("/merchant_running_account_overview_way_pie_chart")
    public Resp merchantRunningAccountOverviewWayPieChart(@UserId String userId) {
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        List<PieChartDataVO> pieChartDataVOS = orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayWay, Order.PayWay.class);
        return Resp.success(pieChartDataVOS);
    }

    /**
     * @author Created by zk on 2019/5/28 21:06
     * @Description 流水概览支付终端饼图
     */
    @GetMapping("/merchant_running_account_overview_client_pie_chart")
    public Resp merchantRunningAccountOverviewClientPieChart(@UserId String userId) {
        List<Order> orderList = OrderConstant.MERCHANT_STATISTICS_CACHE.get(userId);
        List<PieChartDataVO> pieChartDataVOS = orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayClient, Order.PayClient.class);
        return Resp.success(pieChartDataVOS);
    }


    /**
     * @author Created by zk on 2019/5/12 22:16
     * @Description 商户查看汇总流水列表
     */
    @GetMapping("/merchant_running_account_list_for_merchant")
    public Resp merchantRunningAccountListForMerchant(@UserId String userId,
                                                      MerchantRunningAccountListConditionDTO condition,
                                                      int pageNum,
                                                      int pageSize) {
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
            Map<String, Object> content = new HashMap<>();
            if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
                //如果是商户，返回该商户下所有订单信息
                condition.setMerchantId(merchantUserDTO.getMerchantId());
                Specification specification = ConditionUtil.createSpecification(condition);
                List<Order> orderList = orderService.findAll(specification);
                //商户查看流水列表，不需要支付失败的订单，所以过滤掉
                orderList = orderList.stream()
                        .sorted((o1, o2) -> o2.getPayTime().compareTo(o1.getPayTime()))
                        .filter(this::isSuccessOrder)
                        .collect(Collectors.toList());
                CountDataVO countDataVO = new CountDataVO();
                //汇总计算
                orderList.forEach(order -> countData(order, countDataVO));
                content.put("totalCount", countDataVO);
                LinkedHashMap<String, List<Order>> storeIdOrderMap = orderList.stream()
                        .collect(Collectors.groupingBy(o -> {
                            if (ParamUtil.isBlank(o.getStoreId())) {
                                o.setStoreId("");
                            }
                            return o.getStoreId();
                        }, LinkedHashMap::new, Collectors.toList()));
                List<CountDataVO> listData = new ArrayList<>(storeIdOrderMap.size());//根据门店分组后的统计数据
                storeIdOrderMap.forEach((s, orders) -> {
                    CountDataVO c = new CountDataVO();
                    orders.forEach(order -> countData(order, c));
                    listData.add(c);
                });
            content.put("totalPage", listData.size() % pageSize == 0 ? listData.size() / pageSize : listData.size() / pageSize + 1);
            content.put("totalElement", listData.size());
            content.put("listData", listData.stream()
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList()));
        }
        return Resp.success(content);
    }

    @GetMapping("/export/merchant_running_account_list_for_merchant")
    public Resp exportmerchantRunningAccountListForMerchant(String userId,
                                                            MerchantRunningAccountListConditionDTO condition,
                                                            int pageNum,
                                                            int pageSize){

        List<CountDataVO> listData = null;
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        Map<String, Object> content = new HashMap<>();
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            //如果是商户，返回该商户下所有订单信息
            condition.setMerchantId(merchantUserDTO.getMerchantId());
            Specification specification = ConditionUtil.createSpecification(condition);
            List<Order> orderList = orderService.findAll(specification);
            //商户查看流水列表，不需要支付失败的订单，所以过滤掉
            orderList = orderList.stream()
                    .sorted((o1, o2) -> o2.getPayTime().compareTo(o1.getPayTime()))
                    .filter(this::isSuccessOrder)
                    .collect(Collectors.toList());
            CountDataVO countDataVO = new CountDataVO();
            //汇总计算
            orderList.forEach(order -> countData(order, countDataVO));
            content.put("totalCount", countDataVO);
            LinkedHashMap<String, List<Order>> storeIdOrderMap = orderList.stream()
                    .collect(Collectors.groupingBy(o -> {
                        if (ParamUtil.isBlank(o.getStoreId())) {
                            o.setStoreId("");
                        }
                        return o.getStoreId();
                    }, LinkedHashMap::new, Collectors.toList()));
            listData = new ArrayList<>(storeIdOrderMap.size());//根据门店分组后的统计数据
            List<CountDataVO> finalListData = listData;
            storeIdOrderMap.forEach((s, orders) -> {
                CountDataVO c = new CountDataVO();
                orders.forEach(order -> countData(order, c));
                finalListData.add(c);
            });

            listData.stream()
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }

        EasyPoiUtil.exportExcel(listData,"商户流水统计","商户流水统计",CountDataVO.class,"商户流水统计.xls",response);
        return Resp.success("导出成功");
    }

    /**
     * @author Created by zk on 2019/5/12 22:17
     * @Description 店长查看门店流水列表
     */
    @GetMapping("/merchant_running_account_list_for_store")
    public Resp merchantRunningAccountListForStore(@UserId String userId,
                                                   MerchantRunningAccountListConditionDTO condition,
                                                   int pageNum,
                                                   int pageSize) {
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        Map<String, Object> content = new HashMap<>();
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())
                || MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            if (ParamUtil.isBlank(condition.getStoreId())) {
                condition.setStoreId(merchantUserDTO.getStoreId());
            }
            Specification specification = ConditionUtil.createSpecification(condition);
            List<Order> orderList = orderService.findAll(specification);
            CountDataVO countDataVO = new CountDataVO();
            orderList = orderList.stream()
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            orderList.forEach(order -> countData(order, countDataVO));
            content.put("totalCount", countDataVO);
            content.put("totalPage", orderList.size() % pageSize == 0 ? orderList.size() / pageSize : orderList.size() / pageSize + 1);
            content.put("totalElement", orderList.size());
            content.put("listData", orderList.stream()
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList()));
        }
        return Resp.success(content);
    }


    //门店流水导出
    @GetMapping("/export/merchantList")
    public void outletFlowExport(String userId, MerchantRunningAccountListConditionDTO condition/*, int start, int end*/){
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
//        Map<String, Object> content = new HashMap<>();
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())
                || MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            if (ParamUtil.isBlank(condition.getStoreId())) {
                condition.setStoreId(merchantUserDTO.getStoreId());
            }
            Specification specification = ConditionUtil.createSpecification(condition);
            List<Order> orderList = orderService.findAll(specification);

            EasyPoiUtil.exportExcel(orderList, "门店流水信息", "流水", Order.class, "门店流水信息.xls", response);

        }
    }





    private void countData(Order order, CountDataVO countDataVO) {
        if (isSuccessOrder(order) && !Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay())) {
            countDataVO.setStoreName(order.getStoreName());
            countDataVO.setStoreId(order.getStoreId());
            countDataVO.setOrderId(order.getId());
            countDataVO.setOrderNum(countDataVO.getOrderNum() + 1);
            countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(order.getTotalPrice()));
            countDataVO.setRefundNum(countDataVO.getRefundNum() + 1);
            countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(order.getRefundPayPrice() == null ? new BigDecimal(0) : order.getRefundPayPrice()));
            countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(order.getActPayPrice().subtract(order.getRefundPayPrice() == null ? new BigDecimal(0) : order.getRefundPayPrice())));
            countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(order.getDisCountPrice() == null ? new BigDecimal(0) : order.getDisCountPrice()));
        }
    }

    private boolean isSuccessOrder(Order order) {
        return !OrderConstant.failedOrderStatusSet.contains(order.getStatus());
    }

    private List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range = DateUtil.rangeToList(startDate, endDate, dateField);
        List<String> xData = range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
        return xData;
    }
}
