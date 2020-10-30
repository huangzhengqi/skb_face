package com.fzy.admin.fp.order.order.feignImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.member.applet.dto.AppletOrderDTO;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.order.feign.OrderServiceFeign;
import com.fzy.admin.fp.sdk.pay.domain.PayChannelRate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-05 21:03
 * @description
 */
@Service
public class OrderServiceFeignImpl implements OrderServiceFeign {
    @Resource
    private OrderService orderService;

    @Override
    public List<OrderVo> findByMerchantIdsAsc(String[] ids, String startTime, String endTime) {
        Date start = DateUtil.beginOfDay(DateUtil.parse(startTime));
        Date end = DateUtil.endOfDay(DateUtil.parse(endTime));
        List<Order> orderList = orderService.findByMerchantIdsAsc(ids, start, end);
        return orderList.stream().filter(order -> !Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay()))
                .map(order -> {
                    OrderVo orderVo = new OrderVo();
                    BeanUtils.copyProperties(order, orderVo);
                    return orderVo;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderVo> findByMerchantIdsDesc(String[] ids, String startTime, String endTime, Integer[] payWays) {
        Date start = DateUtil.beginOfDay(DateUtil.parse(startTime));
        Date end = DateUtil.endOfDay(DateUtil.parse(endTime));
        List<Order> orderList = orderService.findByMerchantIdsDesc(ids, start, end, payWays);
        return orderList.stream().filter(order -> !Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay()))
                .map(order -> {
                    OrderVo orderVo = new OrderVo();
                    BeanUtils.copyProperties(order, orderVo);
                    return orderVo;
                }).collect(Collectors.toList());
    }

    /**
     * @author Created by wtl on 2019/6/19 17:07
     * @Description APP会员模块储值调用扫码支付
     */
    @Override
    public OrderVo scanPay(String userId, BigDecimal totalPrice, String authCode) {
        OrderVo orderVo = new OrderVo();
        OrderDto orderDto = new OrderDto();
        // app端储值支付
        orderDto.setPayClient(Order.PayClient.APP.getCode());
        orderDto.setUserId(userId);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setAuthCode(authCode);
        orderDto.setRechargeFlag(Order.RechargeFlag.ISRECHARGE.getCode());
        try {
            Order order = orderService.scanPay(orderDto, Order.InterFaceWay.SELF);
            //获取Hutool拷贝实例
            CopyOptions copyOptions = CopyOptions.create();
            //忽略为null值得属性
            copyOptions.setIgnoreNullValue(true);
            //进行属性拷贝
            BeanUtil.copyProperties(order, orderVo, copyOptions);
        } catch (Exception e) {
            e.printStackTrace();
            orderVo.setError(e.getMessage());
        }
        return orderVo;
    }

    /**
     * @author Created by wtl on 2019/6/19 15:34
     * @Description 小程序充值（member模块），通过feign创建订单
     */
//    @Override
//    public String createOrder(String merchantId, String appletStore, BigDecimal totalPrice) {
//        OrderDto orderDto = new OrderDto();
//        // 将会员id和储值金额、赠送数值存快照
//        orderDto.setAppletStore(appletStore);
//        orderDto.setMerchantId(merchantId);
//        orderDto.setTotalPrice(totalPrice);
//        orderDto.setPayClient(Order.PayClient.OTHER.getCode());

//        orderDto.setPayWay(Order.PayWay.WXPAY.getCode());
//        // 订单类型是充值订单
//        orderDto.setRechargeFlag(Order.RechargeFlag.ISRECHARGE.getCode());
//        // 微信官方利率
//        PayChannelRate payChannelRate = orderService.findMerchantChannel(merchantId, Order.PayWay.WXPAY.getCode(), 2);
//        orderDto.setInterestRate(payChannelRate.getInterestRate());
//        Order order = orderService.createOrder(orderDto);
//        if (ParamUtil.isBlank(order)) {
//            return null;
//        }
//        return order.getOrderNumber();
//    }

    public String createOrder(String merchantId, String appletStore, BigDecimal totalPrice, String memberId) {
        OrderDto orderDto = new OrderDto();

        orderDto.setAppletStore(appletStore);
        orderDto.setMemberId(memberId);
        orderDto.setMerchantId(merchantId);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setPayClient(Order.PayClient.OTHER.getCode());
        orderDto.setPayWay(Order.PayWay.WXPAY.getCode());

        orderDto.setRechargeFlag(Order.RechargeFlag.ISRECHARGE.getCode());

        PayChannelRate payChannelRate = this.orderService.findMerchantChannel(merchantId, Order.PayWay.WXPAY.getCode(), Integer.valueOf(2));
        orderDto.setInterestRate(payChannelRate.getInterestRate());
        Order order = this.orderService.createOrder(orderDto);
        if (ParamUtil.isBlank(order)) {
            return null;
        }
        return order.getOrderNumber();
    }

    public String createSmdcOrder(String merchantId, AppletOrderDTO appletOrderDTO) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderType(Integer.valueOf(1));
        orderDto.setRemarks("扫码点餐订单");
        orderDto.setSmdcOrderNum(JSONUtil.parseObj(appletOrderDTO).toString());
        orderDto.setMerchantId(merchantId);
        orderDto.setTotalPrice(appletOrderDTO.getTotal().divide(new BigDecimal(100)));
        orderDto.setPayClient(Order.PayClient.OTHER.getCode());
        orderDto.setPayWay(Order.PayWay.WXPAY.getCode());

        orderDto.setRechargeFlag(Order.RechargeFlag.NOTRECHARGE.getCode());

        PayChannelRate payChannelRate = this.orderService.findMerchantChannel(merchantId, Order.PayWay.WXPAY.getCode(), Integer.valueOf(2));
        orderDto.setInterestRate(payChannelRate.getInterestRate());
        Order order = this.orderService.createOrder(orderDto);
        if (ParamUtil.isBlank(order)) {
            return null;
        }
        return order.getOrderNumber();
    }

}
