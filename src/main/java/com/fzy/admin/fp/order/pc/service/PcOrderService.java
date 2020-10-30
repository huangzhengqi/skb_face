package com.fzy.admin.fp.order.pc.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.order.pc.dto.OrderCountDto;
import com.fzy.admin.fp.order.pc.dto.OrderFlowDto;
import com.fzy.admin.fp.order.pc.vo.*;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-04-30 19:55
 * @description
 */
@Service
@Slf4j
public class PcOrderService {

    @Resource
    private OrderService orderService;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Resource
    private StoredRecoredService storedRecoredService;

    /**
     * bigdecimal空值null转0
     * @param bigDecimal
     * @return
     */
    public BigDecimal decimalNullToZero(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    /**
     * pc端扫码支付
     * @param model
     * @return
     * @throws Exception
     */
    public PayResult scanPay(OrderDto model) throws Exception {
        com.fzy.admin.fp.order.order.domain.Order order = orderService.scanPay(model, com.fzy.admin.fp.order.order.domain.Order.InterFaceWay.SELF);
        PayResult payResult = new PayResult();
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(order, payResult, copyOptions);
        payResult.setUserName(order.getUserName());
        return payResult;
    }

    /**
     * @author Created by wtl on 2019/4/26 14:43
     * @Description 查询订单
     */
    public Map<String, Object> queryOrder(String userId, com.fzy.admin.fp.order.order.domain.Order model, PageVo pageVo) {
        //  商户、店长、店员不同条件
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 商户
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            model.setMerchantId(merchantUserDTO.getMerchantId());
            // 商户切换门店，前端传门店id
            if (!ParamUtil.isBlank(model.getStoreId())) {
                model.setStoreId(model.getStoreId());
            }
        }
        // 店长
        if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            model.setStoreId(merchantUserDTO.getStoreId());
        }
        // 店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            model.setUserId(userId);
        }

        // 分页
        Pageable pageable = PageUtil.initPage(pageVo);
        // 查询
        Specification specification = ConditionUtil.createSpecification(model);
        Page<com.fzy.admin.fp.order.order.domain.Order> page = orderService.getRepository().findAll(specification, pageable);
        List<com.fzy.admin.fp.order.order.domain.Order> orders = page.getContent();
        List<OrderPcVo> orderPcVos = orders.stream()
                .map(e -> new OrderPcVo(e.getId(), e.getOrderNumber(), e.getActPayPrice(), e.getPayWay(), e.getCreateTime(), e.getPayTime(), e.getStatus()))
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("content", orderPcVos);
        return map;
    }

    /**
     * @author Created by wtl on 2019/5/1 21:50
     * @Description 订单详情
     */
    public OrderDetail orderDetail(String orderNumber) {
        log.info("订单编号，{}", orderNumber);
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("订单号不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        com.fzy.admin.fp.order.order.domain.Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNumber, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(order)) {
            order = orderService.getRepository().findByRefundTransactionIdAndDelFlag(orderNumber, CommonConstant.NORMAL_FLAG);
            if (ParamUtil.isBlank(order)) {
                throw new BaseException("订单不存在", Resp.Status.PARAM_ERROR.getCode());
            }

        }
        OrderDetail orderDetail = new OrderDetail();
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(order, orderDetail, copyOptions);
//        String username = merchantUserFeign.findUser(order.getUserId()).getUsername(); // 收银员
//        // 门店名称，如果是商户则没门店，名称为登录账号
//        orderDetail.setStoreName(order.getStoreId() == null ? username : storeServiceFeign.findStore(order.getStoreId()));
//        orderDetail.setUsername(username); // 收银员
        orderDetail.setUsername(order.getUserName());
        // 可退金额 = 实付金额 - 退款金额
        orderDetail.setRemainPrice(order.getActPayPrice().subtract(order.getRefundPayPrice()));

        StoredRecored storedRecored = storedRecoredService.getRepository().findByOrderNumber(orderNumber);
        if (storedRecored != null) {
            orderDetail.setPhone(storedRecored.getPhone());
            orderDetail.setScores(storedRecored.getScores());
        } else {
            orderDetail.setPhone("暂无");
            orderDetail.setScores(0);
        }
        orderDetail.setPayType(order.getPayType());
        return orderDetail;
    }

    /**
     * @author Created by wtl on 2019/5/1 22:44
     * @Description 修改订单备注
     */
    public void editOrderRemarks(String orderNumber, String remarks) {
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("订单号不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(remarks)) {
            throw new BaseException("订单备注不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        com.fzy.admin.fp.order.order.domain.Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNumber, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(order)) {
            throw new BaseException("订单不存在", Resp.Status.PARAM_ERROR.getCode());
        }
        order.setRemarks(remarks);
        orderService.save(order);
    }

    /**
     * @author Created by wtl on 2019/4/30 10:45
     * @Description 获取登录用户的员工，商户显示所有员工、店长显示门店员工、员工显示自己
     */
    public List<MerchantUserSelect> merchantUser(String userId) {
        return merchantUserFeign.findMerchantUser(userId);
    }

    /**
     * @author Created by wtl on 2019/4/29 21:34
     * @Description 退款接口
     */
    public RefundResult refund(String userId, OrderRefundDTO model) throws Exception {
        // 校验输入的密码
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        if (!BCrypt.checkpw(model.getPassword(), merchantUserDTO.getPassword())) {
            throw new BaseException("密码错误", Resp.Status.PARAM_ERROR.getCode());
        }
        // 先保存本次退款金额
        BigDecimal curRefundPrice = model.getRefundPayPrice();
        model.setRefundUserId(userId);
        com.fzy.admin.fp.order.order.domain.Order order = orderService.refund(model);
        if (order.getOrderType() != null && order.getOrderType() == 1) {
            throw new BaseException("扫码点餐订单不允许退款", Resp.Status.PARAM_ERROR.getCode());
        }
        RefundResult refundResult = new RefundResult();
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(order, refundResult, copyOptions);
        refundResult.setRefundUserName(merchantUserDTO.getName()); // 退款人名称
        refundResult.setRefundPrice(model.getRefundPayPrice());
        refundResult.setCurRefundPrice(curRefundPrice);
        return refundResult;
    }

    /**
     * @author Created by wtl on 2019/4/30 19:57
     * @Description 订单统计
     */
    public OrderCountVo countOrder(@UserId String userId) {
        OrderCountDto dayCount = null;
        OrderCountDto weekCount = null;
        OrderCountDto monthCount = null;
        // 获取用户信息
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 用户是商户
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            // 当天
            dayCount = orderService.getRepository().countMerchantOrder(merchantUserDTO.getMerchantId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
            // 近一周
            weekCount = orderService.getRepository().countMerchantOrder(merchantUserDTO.getMerchantId(), DateUtil.beginOfDay(DateUtil.lastWeek()), DateUtil.beginOfDay(new Date()));
            // 近一月
            monthCount = orderService.getRepository().countMerchantOrder(merchantUserDTO.getMerchantId(), DateUtil.beginOfDay(DateUtil.lastMonth()), DateUtil.beginOfDay(new Date()));
        }
        // 用户是店长
        if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            // 当天
            dayCount = orderService.getRepository().countStoreOrder(merchantUserDTO.getStoreId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
            // 近一周
            weekCount = orderService.getRepository().countStoreOrder(merchantUserDTO.getStoreId(), DateUtil.beginOfDay(DateUtil.lastWeek()), DateUtil.beginOfDay(new Date()));
            // 近一月
            monthCount = orderService.getRepository().countStoreOrder(merchantUserDTO.getStoreId(), DateUtil.beginOfDay(DateUtil.lastMonth()), DateUtil.beginOfDay(new Date()));
        }
        // 用户是店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            // 当天
            dayCount = orderService.getRepository().countCashierOrder(merchantUserDTO.getId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
            // 近一周
            weekCount = orderService.getRepository().countCashierOrder(merchantUserDTO.getId(), DateUtil.beginOfDay(DateUtil.lastWeek()), DateUtil.beginOfDay(new Date()));
            // 近一月
            monthCount = orderService.getRepository().countCashierOrder(merchantUserDTO.getId(), DateUtil.beginOfDay(DateUtil.lastMonth()), DateUtil.beginOfDay(new Date()));
        }
        OrderCountVo orderCountVo = new OrderCountVo();
        // 日统计
        orderCountVo.setDayOrderAmount(dayCount.getCount());
        orderCountVo.setDayOrderPrice(decimalNullToZero(dayCount.getTotalPrice()));
        orderCountVo.setDayRefundPrice(decimalNullToZero(dayCount.getRefundPrice()));
        // 周统计
        orderCountVo.setWeekOrderAmount(weekCount.getCount());
        orderCountVo.setWeekOrderPrice(decimalNullToZero(weekCount.getTotalPrice()));
        orderCountVo.setWeekRefundPrice(decimalNullToZero(weekCount.getRefundPrice()));
        // 月统计
        orderCountVo.setMonthOrderAmount(monthCount.getCount());
        orderCountVo.setMonthOrderPrice(decimalNullToZero(monthCount.getTotalPrice()));
        orderCountVo.setMonthRefundPrice(decimalNullToZero(monthCount.getRefundPrice()));
        return orderCountVo;
    }

    /**
     * @author Created by wtl on 2019/5/5 15:33
     * @Description 查询要打印流水的订单
     */
    public List<com.fzy.admin.fp.order.order.domain.Order> findFlowOrderList(MerchantUserDTO merchantUserDTO, OrderFlowDto orderFlowDto) {
        // 查询订单
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 用户类型为商户
                if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
                    predicates.add(cb.equal(root.get("merchantId"), merchantUserDTO.getMerchantId()));
                }
                // 前端传storeId，即商户切换门店
                if (!ParamUtil.isBlank(orderFlowDto.getStoreId())) {
                    predicates.add(cb.equal(root.get("storeId"), orderFlowDto.getStoreId()));
                }
                // 用户类型为店长
                if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
                    predicates.add(cb.equal(root.get("storeId"), merchantUserDTO.getStoreId()));
                }
                // 用户类型为员工
                if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
                    predicates.add(cb.equal(root.get("userId"), merchantUserDTO.getId()));
                }
                predicates.add(cb.greaterThanOrEqualTo(root.get("payTime"), orderFlowDto.getStartTime()));
                predicates.add(cb.lessThanOrEqualTo(root.get("payTime"), orderFlowDto.getEndTime()));
                Expression<String> exp = root.<String>get("status");
                predicates.add(exp.in(com.fzy.admin.fp.order.order.domain.Order.Status.SUCCESSPAY.getCode(), com.fzy.admin.fp.order.order.domain.Order.Status.REFUNDPART.getCode())); // 往in中添加所有id 实现in 查询
                predicates.add(cb.equal(root.get("delFlag"), CommonConstant.NORMAL_FLAG));
                cq.orderBy(cb.desc(root.get("payTime")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return orderService.findAll(specification);
    }

    /**
     * @author Created by wtl on 2019/5/4 20:47
     * @Description 订单流水
     */
    public OrderFlow orderFlow(String userId, OrderFlowDto orderFlowDto) {
        if (ParamUtil.isBlank(orderFlowDto.getStartTime()) || ParamUtil.isBlank(orderFlowDto.getEndTime())) {
            throw new BaseException("请输入开始和结束时间", Resp.Status.PARAM_ERROR.getCode());
        }
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 订单列表
        List<OrderFlowList> orderFlowLists = new ArrayList<>();
        // 订单统计金额，统计实收金额
        BigDecimal orderPrice = BigDecimal.ZERO;
        // 商户实收金额，实收金额减去退款金额
        BigDecimal merchantPrice = BigDecimal.ZERO;
        // 退款统计
        int refundCount = 0;
        BigDecimal refundPrice = BigDecimal.ZERO;
        // 支付方式统计
        Map<Integer, AmountPrice> map = new TreeMap<>();

        // 要打印流水的订单列表
        List<com.fzy.admin.fp.order.order.domain.Order> orders = findFlowOrderList(merchantUserDTO, orderFlowDto);
        for (com.fzy.admin.fp.order.order.domain.Order order : orders) {
            // 订单列表
            OrderFlowList orderFlowList = new OrderFlowList();
            orderFlowList.setPayWay(OrderUtil.getValueByCode(order.getPayWay()));
            orderFlowList.setPayTime(order.getPayTime());
            orderFlowList.setPrice(order.getActPayPrice() + "元");
            orderFlowLists.add(orderFlowList);
            // 订单统计
            orderPrice = orderPrice.add(order.getActPayPrice());
            merchantPrice = merchantPrice.add(order.getActPayPrice().subtract(order.getRefundPayPrice()));
            if (Order.Status.REFUNDPART.getCode().equals(order.getStatus())) {
                refundCount++;
                refundPrice = refundPrice.add(order.getRefundPayPrice());
            }
            // 支付方式统计
            if (map.get(order.getPayWay()) == null) {
                map.put(order.getPayWay(), new AmountPrice());
            }
            AmountPrice amountPrice = map.get(order.getPayWay());
            amountPrice.setAmount(amountPrice.getAmount() + 1);
            amountPrice.setPrice(amountPrice.getPrice().add(order.getActPayPrice()));
        }
        // 统计列表
        List<OrderFlowCount> orderFlowCounts = new ArrayList<>();
        // 统计订单金额
        orderFlowCounts.add(new OrderFlowCount("订单统计", orders.size() + "笔", orderPrice + "元"));
        orderFlowCounts.add(new OrderFlowCount("商户实收", orders.size() + "笔", merchantPrice + "元"));
        for (Integer integer : map.keySet()) {
            AmountPrice amountPrice = map.get(integer);
            orderFlowCounts.add(new OrderFlowCount(OrderUtil.getValueByCode(integer), amountPrice.getAmount() + "笔", amountPrice.getPrice() + "元"));
        }
        orderFlowCounts.add(new OrderFlowCount("退款统计", refundCount + "笔", refundPrice + "元"));

        OrderFlow orderFlow = new OrderFlow();
        orderFlow.setStartTime(DateUtil.format(orderFlowDto.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
        orderFlow.setEndTime(DateUtil.format(orderFlowDto.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
        orderFlow.setOrderAmount(orders.size());
        orderFlow.setFlowPrice(orderPrice); // 流水金额
        orderFlow.setActPrice(merchantPrice); // 实收金额
        orderFlow.setPrinter(merchantUserDTO.getName());
        orderFlow.setOrderFlowLists(orderFlowLists);
        orderFlow.setOrderFlowCounts(orderFlowCounts);
        return orderFlow;
    }


    public Map<String, Object> queryOrderChaJian(String userId, com.fzy.admin.fp.order.order.domain.Order model) {
        //  商户、店长、店员不同条件
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 商户
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            model.setMerchantId(merchantUserDTO.getMerchantId());
            // 商户切换门店，前端传门店id
            if (!ParamUtil.isBlank(model.getStoreId())) {
                model.setStoreId(model.getStoreId());
            }
        }
        // 店长
        if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            model.setStoreId(merchantUserDTO.getStoreId());
        }
        // 店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            model.setUserId(userId);
        }
        // 查询
        Specification specification = ConditionUtil.createSpecification(model);
        List<com.fzy.admin.fp.order.order.domain.Order> list = orderService.getRepository().findAll(specification);

        List<OrderPcVo> orderPcVos = list.stream()
                .map(e -> new OrderPcVo(e.getId(), e.getOrderNumber(), e.getActPayPrice(),e.getRefundPayPrice(), e.getPayWay(), e.getCreateTime(), e.getPayTime(), e.getStatus()))
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("content", orderPcVos);
        return map;
    }

    public RefundResult scanRefund(String userId, OrderRefundDTO model) throws Exception {
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        // 先保存本次退款金额
        BigDecimal curRefundPrice = model.getRefundPayPrice();
        model.setRefundUserId(userId);
        com.fzy.admin.fp.order.order.domain.Order order = orderService.refund(model);
        if (order.getOrderType() != null && order.getOrderType() == 1) {
            throw new BaseException("扫码点餐订单不允许退款", Resp.Status.PARAM_ERROR.getCode());
        }
        RefundResult refundResult = new RefundResult();
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(order, refundResult, copyOptions);
        refundResult.setRefundUserName(merchantUserDTO.getName()); // 退款人名称
        refundResult.setRefundPrice(model.getRefundPayPrice());
        refundResult.setCurRefundPrice(curRefundPrice);
        return refundResult;
    }
}
