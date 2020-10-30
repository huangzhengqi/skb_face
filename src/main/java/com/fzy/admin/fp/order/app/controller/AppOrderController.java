package com.fzy.admin.fp.order.app.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.*;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.service.GoodsOrderService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.merchant.app.dto.MerchantMonthDTO;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRepository;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.app.dto.OrderBindDTO;
import com.fzy.admin.fp.order.app.dto.PreOrderBindDTO;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.service.CommissionService;
import com.fzy.admin.fp.order.order.service.MemberOrderService;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.service.OrderStatisticsService;
import com.fzy.admin.fp.order.order.vo.MerchantOverviewCountVO;
import com.fzy.admin.fp.auth.vo.ChartDataVO;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.order.OrderConstant;
import com.fzy.admin.fp.order.app.dto.MerchantAppStatisticsConditionDTO;
import com.fzy.admin.fp.order.app.vo.AppOrderVO;
import com.fzy.admin.fp.order.pc.service.PcOrderService;
import com.fzy.admin.fp.order.pc.vo.OrderPcVo;
import com.fzy.admin.fp.order.pc.vo.PayResult;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.pay.domain.PayChannelRate;
import com.fzy.admin.fp.sdk.pay.feign.PayChannelServiceFeign;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-04-29 17:08
 * @description app订单接口
 */
@RestController
@RequestMapping("/order/app")
@Api(value="AppOrderController", tags={"app订单接口"})
@Slf4j
public class AppOrderController extends BaseContent {

    @Resource
    private OrderService orderService;

    @Resource
    private PcOrderService pcOrderService;

    @Resource
    private MemberRepository memberRepository;

    @Resource
    private PayChannelServiceFeign payChannelServiceFeign;

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private MemberOrderService memberOrderService;

    @Resource
    private StoredRecoredService storedRecoredService;

    @Resource
    private MerchantUserRepository merchantUserRepository;

    @Resource
    private OrderStatisticsService orderStatisticsService;

    @Resource
    private CommissionService commissionService;

    /**
     * @author Created by wtl on 2019/4/29 17:30
     * @Description app端扫码支付
     */
    @PostMapping("/scan_pay")
    public Resp scanPay(@UserId String userId, OrderDto model) throws Exception {
        if(ParamUtil.isBlank(userId)){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"token参数错误");
        }
        // APP端
        model.setPayClient(Order.PayClient.APP.getCode());
        model.setUserId(userId);

        //校验是否重复支付订单
        if(StringUtil.isNotBlank(model.getOrderNumber())){
            Order order = orderService.getRepository().findAllByOrderNumberAndAuthCode(model.getOrderNumber(), model.getAuthCode());
            if(order != null){
                if(order.getStatus().equals(Integer.valueOf(2))){
                    log.info("此订单已支付过了");
                    return new Resp().error(Resp.Status.PARAM_ERROR, "此订单已支付过了");
                }
            }
        }

        //校验是否重复支付订单
        if(StringUtil.isNotBlank(model.getOrderNumber())){
            Order order = orderService.getRepository().findAllByOrderNumber(model.getOrderNumber());
            if(order.getStatus().equals(Integer.valueOf(2))){
                log.info("此订单已经支付成功了");
                return new Resp().error(Resp.Status.PARAM_ERROR, "此订单已经支付成功了");
            }
        }

        PayResult payResult=pcOrderService.scanPay(model);
        if (Order.Status.SUCCESSPAY.getCode().equals(payResult.getStatus())) {
            return Resp.success(payResult);
        }
        if(Order.Status.FAILPAY.getCode().equals(payResult.getStatus())){
            return Resp.success(payResult);
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "支付失败");
    }

    /**
     * @author Created by wtl on 2019/5/5 22:13
     * @Description app端网页支付，用户扫码需要放行
     * <p>
     * 用户使用卡券过来获取不到收银员id，用商户id获取默认收银员
     * 普通游客和会员H5支付都是这个接口
     * 会员可能用会员卡支付，可能使用卡券（使用成功需要核销）
     */
    @PostMapping("/web_pay")
    public Resp webPay(OrderDto model) throws Exception {
        if (ParamUtil.isBlank(model.getUserId()) && ParamUtil.isBlank(model.getMerchantId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "收银员ID和商户ID不能同时为空");
        }
        model.setPayType(Integer.valueOf(1)); //扫码
        model.setPayClient(Order.PayClient.APP.getCode()); // App端
        MerchantUser merchantUser = merchantUserRepository.findOne(model.getUserId());
        model.setMerchantId(merchantUser.getMerchantId());
        PayChannelRate merchantChannel = orderService.findMerchantChannel(model.getMerchantId(), model.getPayWay(), 2);
        model.setPayChannel(merchantChannel.getWebChannel());
        return Resp.success(orderService.webPay(model));
    }

    @GetMapping({"/web_pay/bind"})
    @ApiOperation("将付款码和订单绑定")
    public Resp<Order> bindOrder(@RequestParam("authCode") String authCode, @RequestParam("orderNum") String orderNum) {
        Order order=this.orderService.findByOrderNumber(orderNum);
        if (order == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "订单号不存在");
        }
        order.setAuthCode(authCode);
        this.orderService.save(order);
        return Resp.success("绑定成功");
    }

    /**
     * @author Created by wtl on 2019/5/9 16:54
     * @Description 微信授权登录
     */
    @GetMapping("/wx_login")
    public void wxLogin(String userId, String uuid) throws IOException {
        String companyId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        log.info("wx_login->companyId,{}", companyId);
        log.info("wx_login->uuid->{}", uuid);
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String errMsg="";
        // 有异常重定向到错误页面
        if (ParamUtil.isBlank(userId)) {
            errMsg="收银员id不能为空";
            response.sendRedirect(getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + URLEncoder.encode(errMsg, "UTF-8"));
        }
        MerchantUserDTO user=orderService.getMerchantUserFeign().findUser(userId);
        if (ParamUtil.isBlank(user)) {
            errMsg="未找到收银员信息";
            response.sendRedirect(getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + URLEncoder.encode(errMsg, "UTF-8"));
        }
        // 判断是否配置通道
        PayChannelRate payChannelRate=payChannelServiceFeign.findMerchantChannel(user.getMerchantId(), Order.PayWay.WXPAY.getCode(), 2);
        if (!ParamUtil.isBlank(payChannelRate.getErrMsg())) {
            errMsg=payChannelRate.getErrMsg();
            response.sendRedirect(getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + URLEncoder.encode(errMsg, "UTF-8"));
        }
        // 授权回调到最终付款页面
        String redirectUrl=orderService.getWxOrderService().wxWebOauth(user.getServiceProviderId(), user.getMerchantId(), uuid);
        response.sendRedirect(redirectUrl);
    }

    /**
     * 微信授权登录2
     *
     * @param userId
     * @param uuid
     * @return
     * @throws IOException
     */
    @GetMapping("/wx_login2")
    public Map<String, String> wxLogin2(String userId, String uuid) throws IOException {
        Map map=new HashMap();
        String companyId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        log.info("wx_login->companyId,{}", companyId);
        log.info("wx_login->uuid->{}", uuid);
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String errMsg="";
        // 有异常重定向到错误页面
        if (ParamUtil.isBlank(userId)) {
            errMsg="收银员id不能为空";
            map.put("url", getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + errMsg);
            return map;
        }
        MerchantUserDTO user=orderService.getMerchantUserFeign().findUser(userId);
        if (ParamUtil.isBlank(user)) {
            errMsg="未找到收银员信息";
            map.put("url", getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + errMsg);
            return map;
        }
        // 判断是否配置通道
        PayChannelRate payChannelRate=payChannelServiceFeign.findMerchantChannel(user.getMerchantId(), Order.PayWay.WXPAY.getCode(), 2);
        if (!ParamUtil.isBlank(payChannelRate.getErrMsg())) {
            errMsg=payChannelRate.getErrMsg();
            map.put("url", getDomain() + "/web/pay/index.html#/wx/payError?errMsg=" + errMsg);
            return map;
        }
        // 授权回调到最终付款页面
        String redirectUrl=orderService.getWxOrderService().wxWebOauth2(user.getServiceProviderId(), user.getMerchantId(), uuid, userId);
        map.put("url", redirectUrl);
        return map;
    }


    /**
     * @author Created by wtl on 2019/5/6 14:40
     * @Description 退款
     */
    @PostMapping("/refund")
    public Resp refund(@UserId String userId, OrderRefundDTO model) throws Exception {
        pcOrderService.refund(userId, model);
        return Resp.success("退款成功");
    }

    /**
     * 扫码退款
     */
    @PostMapping("/scan_refund")
    public Resp scanRefund(@UserId String userId, OrderRefundDTO model) throws Exception {
        pcOrderService.scanRefund(userId, model);
        return Resp.success("退款成功");
    }


    /**
     * @author Created by wtl on 2019/4/29 17:12
     * @Description 查询当前商户/门店的订单
     */
    @GetMapping("/query_order")
    public Resp queryOrder(@UserId String userId, Order model, PageVo pageVo) {
        // 分页
        Pageable pageable=PageUtil.initPage(pageVo);
        //  商户、店长、店员不同条件
        MerchantUserDTO merchantUserDTO=orderService.getMerchantUserFeign().findUser(userId);
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
        Specification specification=ConditionUtil.createSpecification(model);
        Page<Order> page=orderService.getRepository().findAll(specification, pageable);
        List<Order> orders=page.getContent();
        List<OrderPcVo> orderPcVos=orders.stream()
                .map(e -> new OrderPcVo(e.getId(), e.getOrderNumber(), e.getActPayPrice(), e.getRefundPayPrice(), e.getPayWay(), e.getCreateTime(), e.getPayTime(), e.getStatus()))
                .collect(Collectors.toList());
        // 返回给前端的数据格式
        Map<String, Object> map=new HashMap<>();
        Map<String, AppOrderVO> resultMap=new LinkedHashMap<>();
        for (OrderPcVo orderPcVo : orderPcVos) {
            String date=DateUtil.format(orderPcVo.getPayTime(), "yyyy-MM-dd");
            // 该日期已存在，进行叠加计算
            if (resultMap.containsKey(date)) {
                // 已有的数据
                List<OrderPcVo> vos=resultMap.get(date).getOrders();
                BigDecimal actPrice=resultMap.get(date).getActPrice();
                BigDecimal refundPrice=resultMap.get(date).getRefundPrice();
                // 计算累加新数据
                AppOrderVO appOrderVO=new AppOrderVO();
                // 支付成功 、已退款或部分退款才需要计算支付金额和已退款
                vos.add(orderPcVo);
                appOrderVO.setOrders(vos);
                resultMap.put(date, appOrderVO);
            } else {
                // 日期不存在，创建
                List<OrderPcVo> vos=new ArrayList<>();
                AppOrderVO appOrderVO=new AppOrderVO();
                vos.add(orderPcVo);
                appOrderVO.setOrders(vos);
                resultMap.put(date, appOrderVO);
            }
        }
        map.put("content", resultMap);
        map.put("totalElements", page.getTotalElements());
        return Resp.success(map);
    }

    /**
     * 商户APP,订单查询
     * @param userId
     * @param param
     * @return
     */
    @GetMapping("/get_order")
    @ApiOperation(value="订单交易列表", notes="订单交易列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="storeId",dataType="String",value="门店ID"),
            @ApiImplicitParam(paramType="query",name="startPayTime",dataType="String",value="开始时间 yyyy-MM-dd",required = true),
            @ApiImplicitParam(paramType="query",name="endPayTime",dataType="String",value="结束时间 yyyy-MM-dd",required = true),
            @ApiImplicitParam(paramType="query",name="payWay",dataType="String",value="支付方式"),
            @ApiImplicitParam(paramType="query",name="status",dataType="String",value="状态"),
            @ApiImplicitParam(paramType="query",name="payType",dataType="String",value="类型：1：扫码，3：刷脸,为空查询全部")})
    public Resp getOrder(@UserId String userId,@RequestParam Map<String,String> param){
        if(StringUtil.isEmpty(param.get("startPayTime")) || StringUtil.isEmpty(param.get("endPayTime"))){
            return new Resp().error(Resp.Status.PARAM_ERROR, "开始时间，或结束时间不能为空");
        }
        //  商户、店长、店员不同条件
        MerchantUserDTO merchantUserDTO=orderService.getMerchantUserFeign().findUser(userId);
        // 商户
        if (MerchantUserDTO.UserType.MERCHANT.getCode().equals(merchantUserDTO.getUserType())) {
            param.put("merchantId",merchantUserDTO.getMerchantId());
            // 商户切换门店，前端传门店id
            if (!ParamUtil.isBlank(param.get("storeId"))) {
                param.put("storeId",param.get("storeId"));
            }
        }
        // 店长
        if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
            param.put("storeId",merchantUserDTO.getStoreId());
        }
        // 店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            param.put("userId",userId);
        }
        List<Map<String,Object>> mapList = findOrderList(param);
        mapList.sort(new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2){
                String i1 =  (String)o1.get("orderTime");
                String i2 =  (String)o2.get("orderTime");
                return i2.compareTo(i1);
            }
        });
        return Resp.success(mapList);
    }

    /**
     * 商户APP 订单列表
     * @param param
     * @return
     */
    public  List<Map<String,Object>> findOrderList(Map<String,String> param){
        Date startTime= com.fzy.admin.fp.common.util.DateUtil.getDateFormat(param.get("startPayTime"));
        Date endTime= com.fzy.admin.fp.common.util.DateUtil.getDateFormat(param.get("endPayTime"));
        DateTime start = DateUtil.beginOfDay(startTime);
        DateTime end = DateUtil.endOfDay(endTime);
        //获取近30天
        List<DateTime> dateTimeList = DateUtil.rangeToList(start, end, DateField.DAY_OF_WEEK);
        List<Map<String,Object>> mapList=new ArrayList<>();
        for(DateTime dateTime:dateTimeList){
            StringBuilder sb = new StringBuilder();
            Date startPayTime = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(dateTime.toString());
            DateTime endPayTime = DateUtil.endOfDay(startPayTime);
            sb.append("SELECT IFNULL(SUM(act_pay_price),0) AS actPayPrice,count(1) as totalNum FROM lysj_order_order where 1=1 ");
            //查询支付成功，和部分退款成功
            sb.append(" and `status` in ('2','6')");
//            if(StringUtil.isNotEmpty(param.get("status"))){
//                sb.append(" and `status` =:status");
//            }
            if(StringUtil.isNotEmpty(param.get("payType"))){
                sb.append(" and pay_type =:payType");
            }
            if(StringUtil.isNotEmpty(param.get("payWay"))){
                sb.append(" and pay_way =:payWay");
            }
            if(StringUtil.isNotEmpty(param.get("merchantId"))){
                sb.append(" and merchant_id =:merchantId");
            }
            if(StringUtil.isNotEmpty(param.get("storeId"))){
                sb.append(" and store_id =:storeId");
            }
            if(StringUtil.isNotEmpty(param.get("userId"))){
                sb.append(" and user_id =:userId");
            }
            sb.append(" and pay_time >=:startPayTime");
            sb.append(" and pay_time <=:endPayTime");
            Query query = em.createNativeQuery(sb.toString());
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameter("startPayTime",dateTime.toString());
            query.setParameter("endPayTime",endPayTime.toString());
            if(StringUtil.isNotEmpty(param.get("payWay"))){
                query.setParameter("payWay",param.get("payWay"));
            }
            if(StringUtil.isNotEmpty(param.get("merchantId"))){
                query.setParameter("merchantId", param.get("merchantId"));
            }
            if(StringUtil.isNotEmpty(param.get("payType"))){
                query.setParameter("payType", param.get("payType"));
            }
            if(StringUtil.isNotEmpty(param.get("storeId"))){
                query.setParameter("storeId", param.get("storeId"));
            }
            if(StringUtil.isNotEmpty(param.get("userId"))){
                query.setParameter("userId", param.get("userId"));
            }
            List rows = query.getResultList();
            Map<String,Object> objectMap= new HashMap<>();
            for (Object obj : rows) {
                Map row = (Map) obj;
                objectMap.put("actPayPrice",row.get("actPayPrice"));
                objectMap.put("totalNum",row.get("totalNum"));
                break;
            }
            String orderTime=DateUtil.format(startPayTime, "yyyy-MM-dd");
            param.put("dayTime",orderTime);
            List<Map<String,Object>> mapList1=findOrderDetailList(param);
            mapList1.sort(new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2){
                    String i1 =  (String)o1.get("payTime");
                    String i2 =  (String)o2.get("payTime");
                    return i2.compareTo(i1);
                }
            });
            objectMap.put("orderTime",orderTime);
            objectMap.put("list",mapList1);
            mapList.add(objectMap);
        }
        return mapList;
    }

    /**
     * 商户APP 订单详情列表
     * @param param
     * @return
     */
    public  List<Map<String,Object>> findOrderDetailList(Map<String,String> param){
        Date begin=com.fzy.admin.fp.common.util.DateUtil.getDateFormat(param.get("dayTime"));
        DateTime end = DateUtil.endOfDay(begin);
        //根据日期查询当天的订单列表
        List<DateTime> dateTimeList = DateUtil.rangeToList(begin, end, DateField.DAY_OF_WEEK);
        List<Map<String,Object>> mapList=new ArrayList<>();
        for(DateTime dateTime:dateTimeList){
            StringBuilder sb = new StringBuilder();
            Date startPayTime = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(dateTime.toString());
            DateTime endPayTime = DateUtil.endOfDay(startPayTime);
            sb.append("SELECT act_pay_price as actPayPrice,order_number as orderNumber,DATE_FORMAT(pay_time,'%H:%i:%s') as payTime,od.`status`,m.`name` AS merchantName,pay_way  as payWay\n" +
                    "FROM lysj_order_order od LEFT JOIN lysj_merchant_merchant m ON od.merchant_id = m.id  where 1=1 ");
            sb.append(" and pay_time >=:startPayTime");
            sb.append(" and pay_time <=:endPayTime");
            sb.append(" and od.`status`  not in ('1','4') ");
            if(StringUtil.isNotEmpty(param.get("status"))){
                sb.append(" and od.`status` =:status");
            }
            if(StringUtil.isNotEmpty(param.get("payType"))){
                sb.append(" and od.pay_type =:payType");
            }
            if(StringUtil.isNotEmpty(param.get("payWay"))){
                sb.append(" and pay_way =:payWay");
            }
            if(StringUtil.isNotEmpty(param.get("merchantId"))){
                sb.append(" and merchant_id =:merchantId");
            }
            if(StringUtil.isNotEmpty(param.get("storeId"))){
                sb.append(" and store_id =:storeId");
            }
            if(StringUtil.isNotEmpty(param.get("userId"))){
                sb.append(" and user_id =:userId");
            }
            Query query = em.createNativeQuery(sb.toString());
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameter("startPayTime", dateTime.toString());
            query.setParameter("endPayTime",endPayTime.toString());
            if(StringUtil.isNotEmpty(param.get("status"))){
                query.setParameter("status", param.get("status"));
            }
            if(StringUtil.isNotEmpty(param.get("payType"))){
                query.setParameter("payType", param.get("payType"));
            }
            if(StringUtil.isNotEmpty(param.get("payWay"))){
                query.setParameter("payWay",param.get("payWay"));
            }
            if(StringUtil.isNotEmpty(param.get("merchantId"))){
                query.setParameter("merchantId", param.get("merchantId"));
            }
            if(StringUtil.isNotEmpty(param.get("storeId"))){
                query.setParameter("storeId", param.get("storeId"));
            }
            if(StringUtil.isNotEmpty(param.get("userId"))){
                query.setParameter("userId", param.get("userId"));
            }
            List rows = query.getResultList();
            for (Object obj : rows) {
                Map<String,Object> objectMap= new HashMap<>();
                Map row = (Map) obj;
                objectMap.put("actPayPrice",row.get("actPayPrice"));
                objectMap.put("orderNumber",row.get("orderNumber"));
                objectMap.put("payTime",row.get("payTime"));
                objectMap.put("merchantName",row.get("merchantName"));
                objectMap.put("payWay",row.get("payWay"));
                objectMap.put("status",row.get("status"));
                mapList.add(objectMap);
            }

        }
        return mapList;
    }


    /**
     * @author Created by wtl on 2019/5/8 10:38
     * @Description订单详情
     */
    @GetMapping("/order_detail")
    public Resp orderDetail(String orderNumber) {
        return Resp.success(pcOrderService.orderDetail(orderNumber));
    }

    /**
     * @author Created by wtl on 2019/5/10 9:38
     * @Description 修改订单备注
     */
    @PostMapping("/edit_remarks")
    public Resp editRemarks(String orderNumber, String remarks) {
        pcOrderService.editOrderRemarks(orderNumber, remarks);
        return Resp.success("成功修改订单备注");
    }

    //商户APP统计接口,第一层统计卡片
    @GetMapping("/statistics")
    public Resp statistics(@TokenInfo(property="merchantId") String merchantId, MerchantAppStatisticsConditionDTO condition) {
        condition.formatBeginAndEnd();
        condition.setMerchantId(merchantId);
        if (StringUtils.isBlank(condition.getUserId()) || condition.getUserId().equals("undefined")) {
            condition.setUserId(TokenUtils.getUserId());
        }
        if (StringUtils.isBlank(merchantId) || merchantId.equals("undefined")) {

            condition.setMerchantId(TokenUtils.getMerchantId());
        }

        List<Order> orderList=findOrder(condition);

        MerchantOverviewCountVO merchantOverviewCountVO=this.orderService.statistics(orderList);

        List<Member> members=this.memberRepository.findByMerchantIdAndCreateTimeBetween(merchantId, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        merchantOverviewCountVO.setMemberTotalToday(Integer.valueOf(members.size()));


        return Resp.success(merchantOverviewCountVO);
    }

    //商户APP统计接口,支付方式饼图
    @GetMapping("/way_pie_chart")
    public Resp wayPieChart(@TokenInfo(property="merchantId") String merchantId, MerchantAppStatisticsConditionDTO condition) {
//        condition.formatBeginAndEnd();
//        condition.setMerchantId(merchantId);
//        final List<Order> orderList = findOrder(condition);
//        List<PieChartDataVO> pieChartDataVOS = orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayWay, Order.PayWay.class);
//        return Resp.success(pieChartDataVOS);

        condition.formatBeginAndEnd();
        condition.setMerchantId(merchantId);

        condition.setUserId(condition.getUserId());

        List<Order> orderList=findOrder(condition);
        List<PieChartDataVO> pieChartDataVOS=this.orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayWay, Order.PayWay.class);
        return Resp.success(pieChartDataVOS);
    }

    //商户APP统计接口,支付终端饼图
    @GetMapping("/client_pie_chart")
    public Resp clientPieChart(@TokenInfo(property="merchantId") String merchantId, MerchantAppStatisticsConditionDTO condition) {
//        condition.formatBeginAndEnd();
//        condition.setMerchantId(merchantId);
//        final List<Order> orderList = findOrder(condition);
//        List<PieChartDataVO> pieChartDataVOS = orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayClient, Order.PayClient.class);
//        return Resp.success(pieChartDataVOS);

        condition.formatBeginAndEnd();
        condition.setMerchantId(merchantId);
        condition.setUserId(condition.getUserId());
        List<Order> orderList=findOrder(condition);
        List<PieChartDataVO> pieChartDataVOS=this.orderService.merchantRunningAccountOverviewPieChart(orderList, Order::getPayClient, Order.PayClient.class);
        return Resp.success(pieChartDataVOS);
    }

    @PostMapping({"/face_pay/bind"})
    public Resp facePayEqu(@RequestBody OrderBindDTO orderBindDTO) {
        GoodsOrder goodsOrder=this.goodsOrderService.createGoodsOrder(orderBindDTO.getOrder(), orderBindDTO.getGoods(), null, null, orderBindDTO.getOrder().getTotalPrice(), BigDecimal.ZERO);

        Merchant merchant=(Merchant) this.merchantService.findOne(goodsOrder.getMerchantId());
        if (merchant != null) {
            goodsOrder.setMerchantId(merchant.getName());
        }
        return Resp.success(goodsOrder);
    }

    @PostMapping({"/scan_pay/ali"})
    public Resp scanPay(@RequestBody OrderDto model) throws Exception {
        String s = JSONArray.toJSON(model).toString();
        log.info("支付宝支付参数 =========> :{}",s);
        String userId=TokenUtils.getUserId();
        model.setPayClient(Order.PayClient.APP.getCode());
        model.setUserId(userId);
        PayResult payResult=this.pcOrderService.scanPay(model);
        if (Order.Status.SUCCESSPAY.getCode().equals(payResult.getStatus())) {
            return Resp.success(payResult);
        }
        return (new Resp()).error(Resp.Status.PARAM_ERROR, payResult.getName());
    }

    /**
     * @author Created by zk on 2019/6/10 16:07
     * @Description 近五日订单金额趋势 前端时间需要处理好，结束时间=日统计选择的时间 开始时间=结束时间
     */
    @GetMapping("/five_day_order_money_trend")
    public Resp fiveDayOrderMoneyTrend(@TokenInfo(property="merchantId") String merchantId, MerchantAppStatisticsConditionDTO condition) {
        //将结束时间往前推5天
        condition.setStart_payTime(DateUtil.offsetDay(condition.getEnd_payTime(), -6).toJdkDate());
        condition.formatBeginAndEnd();
        condition.setMerchantId(merchantId);
        condition.setUserId(condition.getUserId());
        List<Order> orderList=findOrder(condition);
        List<String> xData=dateRangeList(condition.getStart_payTime(), condition.getEnd_payTime(), DateField.DAY_OF_WEEK, "MM.dd");


        Map<String, List<Order>> map=(Map) orderList.stream().collect(Collectors.groupingBy(o -> DateUtil.format(o.getPayTime(), "MM.dd")));
        List<String> yData=new ArrayList<String>(5);
        for (String xDatum : xData) {
            List<Order> orders=(List) map.get(xDatum);
            if (orders == null || orders.size() == 0) {
                yData.add("0");

                continue;
            }
            BigDecimal reduce=(BigDecimal) orders.stream().filter(order -> !OrderConstant.failedOrderStatusSet.contains(order.getStatus())).map(Order::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            yData.add(reduce.stripTrailingZeros().toPlainString());
        }

        ChartDataVO chartDataVO=new ChartDataVO();
        chartDataVO.setXData(xData);
        chartDataVO.setYData(yData);
        return Resp.success(chartDataVO);
    }


    private List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range=DateUtil.rangeToList(startDate, endDate, dateField);
        List<String> xData=range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
        return xData;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    private List<Order> findOrder(MerchantAppStatisticsConditionDTO condition) {


        MerchantUser merchantUser=(MerchantUser) this.merchantUserRepository.findOne(condition.getUserId());

        //员工
        if (merchantUser.getUserType().equals("3")) {

            List<Order> orderList=getOrderListUser(merchantUser, condition.getStoreId(), condition.getStart_payTime(), condition.getEnd_payTime());
            if (ParamUtil.isBlank(orderList)) {
                return new ArrayList();
            }
            return orderList;
        }

        List<Order> orderList=getOrderListMerchant(condition.getMerchantId(), condition.getStart_payTime(), condition.getEnd_payTime());
        if (ParamUtil.isBlank(orderList)) {
            return new ArrayList();
        }

        return (List) orderList.parallelStream()
                .filter(order -> (!Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay()) && this.orderService.isSuccessOrder(order)))
                .filter(order -> {

                    if (StringUtils.isBlank(condition.getStoreId())) {


                        if (merchantUser.getUserType().equals("1")) {
                            return true;
                        }
                        if (merchantUser.getUserType().equals("2")) {

                            if (order.getStoreId().equals(merchantUser.getStoreId())) {
                                return true;
                            }
                            return false;
                        }


                        if (order.getStoreId().equals(merchantUser.getStoreId()) && order.getUserId().equals(merchantUser.getId())) {
                            return true;
                        }
                        return false;
                    }


                    if (merchantUser.getUserType().equals("1")) {
                        return order.getStoreId().equals(condition.getStoreId());
                    }
                    if (merchantUser.getUserType().equals("2")) {

                        if (order.getStoreId().equals(condition.getStoreId())) {
                            return true;
                        }
                        return false;
                    }


                    if (order.getStoreId().equals(condition.getStoreId()) && order.getUserId().equals(merchantUser.getId())) {
                        return true;
                    }
                    return false;


                }).collect(Collectors.toList());
    }

    private List<Order> getOrderListUser(MerchantUser merchantUser, String storeId, Date startDate, Date endDate) {

        String toDayStr=DateUtil.today();
        DateTime toDay=DateUtil.parse(DateUtil.now());

        List<Order> orderListMain=new ArrayList<Order>();

        List<DateTime> dateTimeList=DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_WEEK);
        for (DateTime dateTime : dateTimeList) {


            String tempDate=DateUtil.formatDate(dateTime);

            if (tempDate.equals(toDayStr)) {

                List<Order> currentList=this.orderStatisticsService.getUserTodayAll(toDay, merchantUser, storeId);

                if (currentList != null && currentList.size() > 0) {
                    orderListMain.addAll(currentList);
                }


                continue;
            }
            String key=merchantUser.getId() + "_" + tempDate + "_" + storeId + "_" + "_orderList_user";
            List<Order> orderListDate=this.orderStatisticsService.getOrderCache(key, Order.class);

            if (orderListDate == null || 0 == orderListDate.size()) {
                orderListDate=this.orderStatisticsService.getUserAppointDate(merchantUser, storeId, tempDate);
            }


            if (orderListDate != null && orderListDate.size() > 0) {
                orderListMain.addAll(orderListDate);
            }
        }
        return orderListMain;
    }


    private List<Order> getOrderListMerchant(String merchantId, Date startDate, Date endDate) {
        Merchant merchant=(Merchant) this.merchantService.getRepository().getOne(merchantId);

        System.out.println("merchant : " + merchant);


        String toDayStr=DateUtil.today();
        DateTime toDay=DateUtil.parse(DateUtil.now());

        List<Order> orderListMain=new ArrayList<Order>();


        List<DateTime> dateTimeList=DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_WEEK);
        for (DateTime dateTime : dateTimeList) {


            String tempDate=DateUtil.formatDate(dateTime);

            if (tempDate.equals(toDayStr)) {

                List<Order> currentList=this.orderStatisticsService.getMerchantTodayAll(toDay, merchant);

                if (currentList != null && currentList.size() > 0) {
                    orderListMain.addAll(currentList);
                }


                continue;
            }


            String key=merchantId + "_" + tempDate + "_orderList_merchant";
            List<Order> orderListDate=this.orderStatisticsService.getOrderCache(key, Order.class);

            if (orderListDate == null || 0 == orderListDate.size()) {
                orderListDate=this.orderStatisticsService.getMerchantAppointDate(merchant, tempDate);
            }


            if (orderListDate != null && orderListDate.size() > 0) {
                orderListMain.addAll(orderListDate);
            }
        }

        return orderListMain;
    }

    @ApiOperation(value = "删除无效订单",notes = "删除无效订单")
    @GetMapping({"/invalid_order_del"})
    @Transactional
    public Resp invalidOrderDel(String orderNum) {

        Order order = orderService.getRepository().findAllByOrderNumber(orderNum);
        log.info("删除无效订单和佣金 =============>: {}" + order.toString());
        if(order != null){
            //删除无效的佣金订单
            this.commissionService.getRepository().deleteByOrderIdAndOrderStatus(order.getId(),Order.Status.PLACEORDER.getCode());
        }
        this.orderService.getRepository().deleteByOrderNumberAndStatus(orderNum, Order.Status.PLACEORDER.getCode());
        return Resp.success("无效订单删除成功");
    }

    @PostMapping({"/face_pay/bind/wx"})
    public Resp facePayEquWx(@RequestBody OrderBindDTO orderBindDTO) {

        log.info("(orderBindDTO)------------  " + orderBindDTO);
        Order order=this.orderService.findByOrderNumber(orderBindDTO.getOrder().getOrderNumber());

        GoodsOrder goodsOrder=this.goodsOrderService.createGoodsOrder(order, orderBindDTO.getGoods(), null, null, order.getTotalPrice(), BigDecimal.ZERO);
        order.setGoodsOrderId(goodsOrder.getId());
        orderService.update(order);
        Merchant merchant=(Merchant) this.merchantService.findOne(goodsOrder.getMerchantId());
        if (merchant != null) {
            goodsOrder.setMerchantId(merchant.getName());
        }
        return Resp.success(goodsOrder);
    }

    /**
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping({"/face_pay/equ"})
    public Resp facePayEqu(@RequestBody OrderDto model) throws Exception {

        BigDecimal balance=BigDecimal.ZERO;

        if (null != model.getBalance()) {
            balance=model.getBalance();
        }
        log.info("model ---- {}",model);
//        log.info("param" + model + "==balance" + balance);
        BigDecimal totalPrice=model.getTotalPrice();
        BigDecimal disCountPrice=model.getDisCountPrice();

        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            model.setTotalPrice(model.getTotalPrice().subtract(model.getDisCountPrice()).subtract(balance));
            model.setDisCountPrice(BigDecimal.ZERO);
        }
        String userId=TokenUtils.getUserId();
        model.setPayClient(Order.PayClient.OTHER.getCode());
        model.setUserId(userId);
        Order order=null;
        try {
            order=this.orderService.scanPay(model, Order.InterFaceWay.SELF);
        } catch (Exception e) {
            order.setStatus(Order.Status.FAILPAY.getCode());
            order.setName(e.getMessage());
        }

        order.setRemarks("会员余额不足时补充支付");
        GoodsOrder goodsOrder=null;
        if (model.getGoods() != null && model.getGoods().size() > 0) {
            goodsOrder=this.goodsOrderService.createGoodsOrder(order, model.getGoods(), model.getMemberRuleId(), model.getMemberRuleMoney(), totalPrice, totalPrice
                    .subtract(disCountPrice));
        }
        if (order.getStatus().equals(Order.Status.SUCCESSPAY.getCode())) {
            if (balance.compareTo(BigDecimal.ZERO) > 0) {


                if (ParamUtil.isBlank(model.getPayWay())) {
                    throw new BaseException("错误的支付方式", Resp.Status.PARAM_ERROR.getCode());
                }

                model.setOrderNumber(null);
                model.setAuthCode(null);
                if (order.getStatus().equals(Order.Status.SUCCESSPAY.getCode())) {
                    model.setTotalPrice(totalPrice.subtract(model.getTotalPrice()));
                    model.setDisCountPrice(disCountPrice);
                }
                model.setPayWay(Order.PayWay.MEMBERCARD.getCode());

                model.setInterestRate(BigDecimal.ZERO);
                model.setPayWay(Order.PayWay.MEMBERCARD.getCode());
                model.setMemberId(model.getMemberId());
                if (null != model.getCode()) {
                    model.setCode(model.getCode());
                }
                Order createOrder=this.orderService.createOrder(model);

                log.info("订单数据----------------     " + order);

                this.memberOrderService.memberPay(createOrder, model.getMemberId());
                StoredRecored storedRecored=this.storedRecoredService.getRepository().findByOrderNumber(createOrder.getOrderNumber());
                if (goodsOrder != null) {
                    goodsOrder.setStoreRecordId(storedRecored.getId());
                    this.goodsOrderService.save(goodsOrder);
                }
                this.orderService.getRepository().deleteByOrderNumber(createOrder.getOrderNumber());
                this.orderService.save(order);
                if (Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())) {
                    return Resp.success(order);
                }
                return (new Resp()).error(Resp.Status.PARAM_ERROR, order.getName());
            }

            this.memberOrderService.equPayMemberCallBack(order, model.getMemberId());
        }

        this.orderService.save(order);
        if (Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())) {
            return Resp.success(order);
        }
        return (new Resp()).error(Resp.Status.PARAM_ERROR, order.getName());
    }

    @PostMapping({"/web_pay/equ"})
    @SystemLog(description="设备会员支付")
    public Resp webPayEqu(@RequestBody OrderDto model) throws Exception {
        log.info("设备会员支付  ------ " + model.toString());
        if (ParamUtil.isBlank(model.getUserId()) && ParamUtil.isBlank(model.getMerchantId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "收银页ID和商户ID不能同时为空");
        }
        model.setPayClient(Order.PayClient.APP.getCode());
        return Resp.success(this.orderService.webPay(model));
    }

    @PostMapping({"/pre_goods_order"})
    @ApiOperation(value="商品预下单", notes="商品预下单")
    public Resp<GoodsOrder> preGoodsOrder(@RequestBody PreOrderBindDTO preOrderBindDTO) {
        GoodsOrder goodsOrder=this.goodsOrderService.preGoodsOrder(preOrderBindDTO);
        return Resp.success(goodsOrder);
    }

    @ApiOperation(value = "最近交易",notes = "最近交易")
    @GetMapping("/recent/transactions")
    public Resp recentTransactions(@UserId String userId) {
        if(ParamUtil.isBlank(userId)){
            throw new BaseException("token有误", Resp.Status.PARAM_ERROR.getCode());
        }
        return Resp.success(orderService.recentTransactions(userId),"查询成功");
    }

}
