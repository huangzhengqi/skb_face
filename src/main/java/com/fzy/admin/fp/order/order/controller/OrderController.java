package com.fzy.admin.fp.order.order.controller;


import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单控制层
 */
@Slf4j
@RestController
@RequestMapping("/order/order")
public class OrderController extends BaseController<Order> {


    @Resource
    private OrderService orderService;

    @Override
    public OrderService getService() {
        return orderService;
    }

    /**
     * @author Created by wtl on 2019/4/26 9:49
     * @Description 查询订单状态，前端轮询
     */
    @GetMapping("/query_by_order_number")
    public Resp queryOrderByNo(String orderNumber) {
        return Resp.success(orderService.queryStatusByOrderNumber(orderNumber));
    }

    @GetMapping("/find_pay_way")
    public Resp findPayWay() {
        List<SelectItem> selectItemList = new ArrayList<>();
        for (Order.PayWay payWay : Order.PayWay.values()) {
            SelectItem selectItem = new SelectItem();
            selectItem.setName(payWay.getStatus());
            selectItem.setValue(payWay.getCode() + "");
            selectItemList.add(selectItem);
        }
        return Resp.success(selectItemList);
    }

    @GetMapping("/find_order_status")
    public Resp findOrderStatus() {
        List<SelectItem> selectItemList = new ArrayList<>();
        for (Order.Status status : Order.Status.values()) {
            SelectItem selectItem = new SelectItem();
            selectItem.setName(status.getStatus());
            selectItem.setValue(status.getCode() + "");
            selectItemList.add(selectItem);
        }
        return Resp.success(selectItemList);
    }


}
