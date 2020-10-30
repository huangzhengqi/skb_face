package com.fzy.admin.fp.order.order.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionDTO;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionRightDTO;
import com.fzy.admin.fp.auth.dto.OrderCountDTO;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.service.OrderCountService;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.vo.DealDetailStatisticsVO;
import com.fzy.admin.fp.order.pc.vo.OrderPcVo;
import com.fzy.admin.fp.sdk.merchant.domain.*;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-05-06 16:04
 * @description 商户订单统计接口
 */
@RestController
@RequestMapping("/order/count")
public class OrderCountController extends BaseContent {


    @Resource
    private OrderService orderService;
    @Resource
    private OrderCountService orderCountService;
    @Resource
    private MerchantUserFeign merchantUserFeign;
    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private StoreService storeService;

    @Resource
    private MemberService memberService;

//    @GetMapping("/dealDetailStatistics")
//    public Resp dealDetailStatistics(@UserId String userId, OrderCountDTO orderCountDTO){
//        return Resp.success(orderCountService.dealDetailStatistics(userId,orderCountDTO));
//    }

    @GetMapping("/dealDetailStatistics")
    public Resp dealDetailStatistics(@UserId String userId, OrderCountDTO orderCountDTO){

        return Resp.success(orderCountService.dealDetailStatistics1(userId,orderCountDTO));
    }

    /**
     * 今日交易概况曲线图
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("/dealStatistics")
    public Resp dealStatistics(@UserId String userId,Integer type){
        return Resp.success(orderCountService.dealStatistics(userId,type));
    }


    /**
     * 店铺信息
     */
    @GetMapping("/shop_info")
    public Resp shopInfo(@UserId String userId) {
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
        MerchaInfoVO merchaInfoVO = new MerchaInfoVO();
        BeanUtil.copyProperties(mchInfo, merchaInfoVO);
        return Resp.success(merchaInfoVO);
    }

    /**
     * @author Created by wtl on 2019/5/6 16:06
     * @Description 今天和昨天订单统金额和数量统计
     */
    @GetMapping("/order_count_new")
    public Resp orderCount(@UserId String userId, OrderCountDTO orderCountDTO) {
        return Resp.success(orderCountService.orderCountNew(userId, orderCountDTO));
    }

    /**
     * @author Created by wtl on 2019/5/6 16:06
     * @Description 今天和昨天订单统金额和数量统计
     */
    @GetMapping("/order_count")
    public Resp orderCount(@UserId String userId) {
        return Resp.success(orderCountService.orderCount(userId));
    }

    /**
     * @author Created by wtl on 2019/5/6 17:49
     * @Description 近16天，每天订单总金额和总笔数统计
     */
    @GetMapping("/order_count_line")
    public Resp orderCountLine(@UserId String userId) {
        return Resp.success(orderCountService.orderCountLine(userId));
    }

    /**
     * 左侧图表
     *
     * @param userId
     * @param condition
     * @return
     */
    @GetMapping("/data_statistics_left")
    public Resp DataStatistics(@UserId String userId, DataStatisticsConditionDTO condition) {
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS = orderService.getLeft(merchantIds, condition);

        return Resp.success(dataStatisticsLineDetailVOS);
    }


    /**
     * 右侧图表
     *
     * @param userId
     * @param condition
     * @return
     */
    @GetMapping("/data_statistics_right")
    public Resp DataStatisticsRight(@UserId String userId, DataStatisticsConditionRightDTO condition) {
        //通过用户id获取商户id
        String merchantId = merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds = new ArrayList<>();
        merchantIds.add(merchantId);
        List<DataStatisticsPieDetailVO> dataStatisticsLineDetailVOS = orderService.getRight(merchantIds, condition);
        return Resp.success(dataStatisticsLineDetailVOS);
    }

    @GetMapping("/query_order")
    public Resp queryOrder(@UserId String userId, Order model, PageVo pageVo) {
        // 分页
        Pageable pageable = PageUtil.initPage(pageVo);
        //  商户、店长、店员不同条件
        MerchantUserDTO merchantUserDTO = orderService.getMerchantUserFeign().findUser(userId);
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

        // 查询1
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Order> page = orderService.getRepository().findAll(specification, pageable);
        List<Order> orders = page.getContent();
        List<OrderPcVo> orderPcVos = orders.stream()
                .map(e -> new OrderPcVo(e.getId(), e.getOrderNumber(), e.getActPayPrice(), e.getRefundPayPrice(), e.getPayWay(), e.getCreateTime(), e.getPayTime(), e.getStatus()))
                .collect(Collectors.toList());
        return Resp.success(orderPcVos);
    }

    @GetMapping("/index")
    public Resp listRewrite(@UserId String userId) {
        Map<String,Object> map=new HashMap<>();
        MerchantUser merchantUserDTO = merchantUserService.findOne(userId);
        Order model=new Order();
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
        Specification specification = ConditionUtil.createSpecification(model);
        //用户列表减一，去掉商户本身
        map.put("userNum",merchantUserService.countByMerchantId(merchantUserDTO.getMerchantId())-1);
        map.put("memberNum",memberService.countNewMemberByMerchantId(merchantUserDTO.getMerchantId()));
        map.put("orderNum",orderService.getRepository().count(specification));
        if (!MerchantUser.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            map.put("storeNum",1);
        }else{
            map.put("storeNum",storeService.countStore(merchantUserDTO.getMerchantId()));
        }
        return Resp.success(map);
    }


    /**
     * 今日交易概况
     * hzq 2019-12-12
     * @param userId
     * @param orderCountDTO
     * @return
     */
    @GetMapping("/order_count_day_new")
    public Resp order_count_day_new(@UserId String userId, OrderCountDTO orderCountDTO) {
        return Resp.success(orderCountService.orderCountDayNew(userId, orderCountDTO));
    }


    /**
     * hzq 2012-12-12
     * 商户首页交易数据统计
     */
    @GetMapping("/order_count_period")
    public Resp order_count_period(@UserId String userId, OrderCountDTO orderCountDTO){
        return Resp.success(orderCountService.order_count_period(userId, orderCountDTO));
    }


    /**
     * hzq 2019-12-13
     * 支付通道
     */
    @GetMapping("/pay_channel")
    public Resp pay_channel(@UserId String userId, DataStatisticsConditionRightDTO condition){

        List<DataPayMentChannelVo> dataPayMentChannelVos = orderService.pay_channel(userId, condition);
        return Resp.success(dataPayMentChannelVos);
    }

}
