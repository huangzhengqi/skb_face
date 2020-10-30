package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionDTO;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionRightDTO;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.service.GoodsOrderService;
import com.fzy.admin.fp.member.app.service.AppStoreService;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.OrderConstant;
import com.fzy.admin.fp.order.order.EnumInterface;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.order.repository.CommissionRepository;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.vo.MerchantOverviewCountVO;
import com.fzy.admin.fp.sdk.merchant.domain.*;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.*;
import com.fzy.admin.fp.sdk.pay.feign.PayChannelServiceFeign;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单服务层
 */
@Slf4j
@Service
@Transactional
public class OrderService implements BaseService<Order> {

    @Resource
    private PayChannelServiceFeign payChannelServiceFeign;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    public MerchantUserFeign getMerchantUserFeign() {
        return merchantUserFeign;
    }

    @Resource
    private WxOrderService wxOrderService;

    @Resource
    private StoredRecoredService storedRecoredService;

    @Resource
    private AppStoreService appStoreService;

    @Resource
    private StoreService storeService;

    @Resource
    private AliOrderService aliOrderService;

    @Resource
    private HybOrderService hybOrderService;

    @Resource
    private YrmOrderService yrmOrderService;

    @Resource
    private TtsOrderService ttsOrderService;

    @Resource
    private HsfOrderService hsfOrderService;

    @Resource
    private SxfOrderService sxfOrderService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private TqSxfOrderService tqSxfOrderService;

    @Resource
    private GoodsOrderService goodsOrderService;

    @PersistenceContext
    public EntityManager em;

    public SxfOrderService getSxfOrderService() {
        return sxfOrderService;
    }

    public TqSxfOrderService getTqSxfOrderService() {
        return tqSxfOrderService;
    }

    @Resource
    private FyOrderService fyOrderService;

    public FyOrderService getFyOrderService() {
        return fyOrderService;
    }

    @Resource
    private MemberOrderService memberOrderService;
    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private CommissionService commissionService;

    @Resource
    private CommissionRepository commissionRepository;

    public MemberOrderService getMemberOrderService() {
        return memberOrderService;
    }

    public WxOrderService getWxOrderService() {
        return wxOrderService;
    }

    public AliOrderService getAliOrderService() {
        return aliOrderService;
    }

    public HybOrderService getHybOrderService() {
        return hybOrderService;
    }

    public YrmOrderService getYrmOrderService() {
        return yrmOrderService;
    }

    public TtsOrderService getTtsOrderService() {
        return ttsOrderService;
    }

    @Resource
    private OrderRepository orderRepository;

    @Override
    public OrderRepository getRepository() {
        return orderRepository;
    }


    public Order findByOrderNumber(String orderNum) {
        return this.orderRepository.findByOrderNumber(orderNum);
    }

    /**
     * 优化蜻蜓刷脸支付出现两笔订单的bug问题
     * @param model
     * @return
     */
    public Order createOrder(OrderDto model) {
        MerchantUserDTO merchantUserDTO;
        if (model.getTotalPrice() == null) {
            throw new BaseException("付款金额不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (model.getTotalPrice().scale() > 2) {
            throw new BaseException("付款金额最小为0.01元", Resp.Status.PARAM_ERROR.getCode());
        }

        if (model.getTotalPrice().toString().length() > 8) {
            throw new BaseException("金额长度不能大于8位，请正确输入金额长度", Resp.Status.PARAM_ERROR.getCode());
        }

        if (StringUtil.isNotEmpty(model.getAuthCode()) && StringUtil.isEmpty(model.getOrderNumber())) {
            Order order=this.orderRepository.findByAuthCode(model.getAuthCode());
            if (order != null && StringUtil.isNotEmpty(order.getOrderNumber())) {
                model.setOrderNumber(order.getOrderNumber());
            }
        }
        Order order=new Order();

        if (StringUtil.isNotEmpty(model.getOrderNumber())) {
            order=this.orderRepository.findByOrderNumber(model.getOrderNumber());
            if (order == null) {
                throw new BaseException("订单编号有误", Resp.Status.PARAM_ERROR.getCode());
            }
            if(order.getStatus().equals(Integer.valueOf(2))){
                log.info("检测订单是否还会重复 ============= {}" + order.toString());
                throw new BaseException("此订单已经支付成功了", Resp.Status.PARAM_ERROR.getCode());
            }
        }


        if (ParamUtil.isBlank(model.getUserId())) {
            merchantUserDTO=this.merchantUserFeign.findKey(model.getMerchantId());
        } else {
            merchantUserDTO=this.merchantUserFeign.findUser(model.getUserId());
        }
        if (ParamUtil.isBlank(merchantUserDTO)) {
            throw new BaseException("商户信息错误", Resp.Status.PARAM_ERROR.getCode());
        }

        order.setServiceProviderId(merchantUserDTO.getServiceProviderId());

        log.info("create order params：{}", model);
        log.info("storeId--------------->>>>>:{}", model.getStoreId());

        if (!ReUtil.isMatch("^\\d{5,}$", model.getStoreId())) {

            model.setStoreId(merchantUserDTO.getStoreId());
            model.setStoreName(merchantUserDTO.getStoreName());
        } else {

            String storeName=this.merchantUserFeign.findStore(model.getStoreId());
            if ("error".equals(storeName)) {
                throw new BaseException("获取门店名称错误", Resp.Status.PARAM_ERROR.getCode());
            }
            model.setStoreName(storeName);
        }

        CopyOptions copyOptions=CopyOptions.create();

        copyOptions.setIgnoreNullValue(true);

        BeanUtil.copyProperties(model, order, copyOptions);

        order.setUserId(merchantUserDTO.getId());

        order.setUserName(merchantUserDTO.getName());
        order.setMerchantId(merchantUserDTO.getMerchantId());

        //存储业务员id,后续分销模块需要使用
        Merchant merchant=merchantRepository.findOne(merchantUserDTO.getMerchantId());
        order.setManagerId(merchant.getManagerId());

        String orderNumber="";
        do {
            orderNumber=DateUtil.format(new Date(), "YYYYMMddHHmmss") + RandomUtil.randomNumbers(4);
        } while (this.orderRepository.countByOrderNumber(orderNumber) != 0L);


        if (StringUtil.isNotEmpty(model.getOrderNumber())) {
            log.info("删除重复订单：{}", model.getOrderNumber());

            orderNumber=model.getOrderNumber();
        }
        order.setOrderNumber(orderNumber);
        order.setOutTradeNo(orderNumber);
        order.setActPayPrice(model.getTotalPrice().subtract(model.getDisCountPrice()));
        order.setRefundPayPrice(BigDecimal.ZERO);
        order.setStatus((model.getStatus() == null) ? Order.Status.PLACEORDER.getCode() : model.getStatus());
        order.setPayTime(new Date());

        //商品预下单
        if (StringUtil.isNotEmpty(model.getGoodsOrderId())) {
            GoodsOrder goodsOrder=goodsOrderService.findOne(model.getGoodsOrderId());
            goodsOrder.setPayTime(new Date());
            goodsOrder.setOrderNo(orderNumber);
            goodsOrder.setPayPrice(model.getTotalPrice().subtract(model.getDisCountPrice()));
            goodsOrder.setUseType(model.getUseType());
        }

        this.orderRepository.saveAndFlush(order);

        this.commissionService.crateCommission(order, true);
        return order;
    }

    /**
     * @param merchantId 商户id
     * @param payWay     支付方式、1：微信；2：支付宝
     * @param payType    支付类型，1：scan刷卡支付；2：web网页支付
     * @author Created by wtl on 2019/4/28 22:22
     * @Description 根据商户id和支付方式判断支付通道
     */
    public PayChannelRate findMerchantChannel(String merchantId, Integer payWay, Integer payType) {
        PayChannelRate payChannelRate=payChannelServiceFeign.findMerchantChannel(merchantId, payWay, payType);
        if (!ParamUtil.isBlank(payChannelRate.getErrMsg())) {
            throw new BaseException(payChannelRate.getErrMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        return payChannelRate;
    }

    //=============================网页H5支付=====================================

    /**
     * @author Created by wtl on 2019/4/23 15:03
     * @Description 网页支付，需要判断支付通道，可能是非官方的微信或支付宝方式
     */
    public Map<String, Object> webPay(OrderDto model) throws Exception {
        Map<String, Object> resultMap=new HashMap<>();
        if (ParamUtil.isBlank(model.getPayWay())) {
            throw new BaseException("错误的支付方式", Resp.Status.PARAM_ERROR.getCode());
        }
        // 创建订单
        Order order=createOrder(model);
        //hxq 订单id传入缓存 h5支付后轮询订单状态 判断是否支付成功
        OrderConstant.ORDERID.put(model.getUuid(), order.getId());
        log.info("uuidhxq:" + model.getUuid() + ":" + order.getId());
        order.setRemarks(model.getRemarks());
        // 获取支付通道配置和对应的利率
        PayChannelRate payChannelRate;
        // 微信jsapi支付，需要获取openid
        if (Order.PayWay.WXPAY.getCode().equals(model.getPayWay())) {
            payChannelRate=findMerchantChannel(order.getMerchantId(), model.getPayWay(), 2);
            order.setInterestRate(payChannelRate.getInterestRate()); // 利率
            // 判断是否是会员和是否使用卡券，回调后处理一些业务有有用
            // (微信回调地址不能带参数，只能根据订单号查询订单，再从订单中获取消息处理后续业务)
            if (!ParamUtil.isBlank(model.getMemberId())) {
                order.setMemberId(model.getMemberId());
            }
            if (!ParamUtil.isBlank(model.getCode())) {
                order.setCode(model.getCode());
            }
            // 支付方式是微信
            order.setPayWay(Order.PayWay.WXPAY.getCode());
            int channel=payChannelRate.getWebChannel();
            if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(channel)) {
                // 微信官方通道
                order.setPayChannel(PayChannelConstant.Channel.OFFICIAL.getCode());
                return wxOrderService.wxJsPay(model.getOpenId(), order);
            }
            if (PayChannelConstant.Channel.HYB.getCode().equals(channel)) {
                // 会员宝通道
                order.setPayChannel(PayChannelConstant.Channel.HYB.getCode());
                return hybOrderService.hybWapPay(order, HybPayParam.PayType.WXPAY);
            }
            if (PayChannelConstant.Channel.YRM.getCode().equals(channel)) {
                // 易融码通道
                order.setPayChannel(PayChannelConstant.Channel.YRM.getCode());
                return yrmOrderService.yrmWapPay(order, YrmPayParam.PayType.WXPAY);
            }
            if (PayChannelConstant.Channel.SXF.getCode().equals(channel)) {
                //随行付通道
                order.setPayChannel(PayChannelConstant.Channel.SXF.getCode());
                return sxfOrderService.sxfWapPay(order, SxfPayParam.PayType.WECHAT);
            }
            if (PayChannelConstant.Channel.FY.getCode().equals(channel)) {
                //富友通道
                order.setPayChannel(PayChannelConstant.Channel.FY.getCode());
                return fyOrderService.fyWapPay(order, FyPayParam.PayType.WECHAT);
            }
            if (PayChannelConstant.Channel.TQSXF.getCode().equals(channel)) {
                //天阙随行付通道
                order.setPayChannel(PayChannelConstant.Channel.TQSXF.getCode());
                return tqSxfOrderService.tqSxfWapPay(order, TqSxfPayParam.PayType.WECHAT);
            }
        }
        // 支付宝网页支付
        if (Order.PayWay.ALIPAY.getCode().equals(model.getPayWay())) {
            payChannelRate=findMerchantChannel(order.getMerchantId(), model.getPayWay(), 2);
            order.setInterestRate(payChannelRate.getInterestRate()); // 利率
            // 支付方式是支付宝
            order.setPayWay(Order.PayWay.ALIPAY.getCode());
            int channel=payChannelRate.getWebChannel();
            if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.OFFICIAL.getCode());
                return aliOrderService.aliWapPay(order);
            }
            // 会员宝支付
            if (PayChannelConstant.Channel.HYB.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.HYB.getCode());
                return hybOrderService.hybWapPay(order, HybPayParam.PayType.ALIPAY);
            }
            // 易融码支付
            if (PayChannelConstant.Channel.YRM.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.YRM.getCode());
                return yrmOrderService.yrmWapPay(order, YrmPayParam.PayType.ALIPAY);
            }
            if (PayChannelConstant.Channel.SXF.getCode().equals(channel)) {
                //随行付通道
                order.setPayChannel(PayChannelConstant.Channel.SXF.getCode());
                return sxfOrderService.sxfWapPay(order, SxfPayParam.PayType.ALIPAY);
            }
            if (PayChannelConstant.Channel.FY.getCode().equals(channel)) {
                //富友通道
                order.setPayChannel(PayChannelConstant.Channel.FY.getCode());
                return fyOrderService.fyWapPay(order, FyPayParam.PayType.ALIPAY);
            }
            if (PayChannelConstant.Channel.TQSXF.getCode().equals(channel)) {
                //天阙随行付通道
                order.setPayChannel(PayChannelConstant.Channel.TQSXF.getCode());
                return tqSxfOrderService.tqSxfWapPay(order, TqSxfPayParam.PayType.ALIPAY);
            }
        }
        // 会员卡支付
        if (Order.PayWay.MEMBERCARD.getCode().equals(model.getPayWay())) {
            // 会员卡支付，利率为0
            order.setInterestRate(BigDecimal.ZERO);
            order.setPayWay(Order.PayWay.MEMBERCARD.getCode()); // 支付方式是会员卡
            order.setMemberId(model.getMemberId());// 会员id
            order.setCode(model.getCode()); // 核销码
            log.info("----------------------------------------------------order1{}", order);
            memberOrderService.memberPay(order, model.getMemberId());
            log.info("----------------------------------------------------order2{}", order);
        } else {
            orderRepository.save(order);
        }
        log.info("----------------------------------------------------order3{}", order);
        resultMap.put("price", order.getActPayPrice());
        resultMap.put("timeStamp", order.getCreateTime());
        resultMap.put("orderNumber", order.getOrderNumber());
        resultMap.put("merchantId", order.getMerchantId());
        resultMap.put("storeName", order.getStoreName());
        return resultMap;
    }

    //==============================条形码支付=================================================

    /**
     * @author Created by wtl on 2019/4/22 20:02
     * @Description 扫码支付,
     * 微信条码规则：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头
     * 支付宝条码规则：付款码将由原来的28开头扩充到25-30开头，长度由原来的16-18位扩充到16-24位。
     */
    public Order scanPay(OrderDto model, Order.InterFaceWay interFaceWay) throws Exception {
        if (ParamUtil.isBlank(model.getAuthCode())) {
            throw new BaseException("付款码不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 创建订单
        Order order=createOrder(model);
        PayChannelRate payChannelRate;
        // 微信扫码支付
        if (ReUtil.isMatch("^1[0-5]\\d{16}$", model.getAuthCode())) {
            // 获取支付通道和对应的利率
            payChannelRate=findMerchantChannel(order.getMerchantId(), PayChannelConstant.PayWay.WXPAY.getCode(), 1);
            order.setInterestRate(payChannelRate.getInterestRate());
            // 支付方式是微信
            order.setPayWay(Order.PayWay.WXPAY.getCode());
            // 配置的扫码通道
            int channel=payChannelRate.getScanChannel();
            // 微信官方通道，付款码支付
            if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.OFFICIAL.getCode());
                wxOrderService.wxScanPay(order, interFaceWay);
            }
            // 易融码微信扫码支付通道
            else if (PayChannelConstant.Channel.YRM.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.YRM.getCode());
                yrmOrderService.yrmScanPay(order, YrmPayParam.PayType.WXPAY, interFaceWay);
            }
            // 惠闪付微信条码支付通道
            else if (PayChannelConstant.Channel.HSF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.HSF.getCode());
                hsfOrderService.hsfScanPay(order, CommonPayParam.HsfType.WXPAY, Order.InterFaceWay.SELF);
            }
            //随行付通道
            else if (PayChannelConstant.Channel.SXF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.SXF.getCode());
                sxfOrderService.sxfScanPay(order, SxfPayParam.PayType.WECHAT, Order.InterFaceWay.SELF);
            }
            //富友通道
            else if (PayChannelConstant.Channel.FY.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.FY.getCode());
                fyOrderService.fyScanPay(order, FyPayParam.PayType.WECHAT, Order.InterFaceWay.SELF);
            }
            //天阙随行付通道
            else if (PayChannelConstant.Channel.TQSXF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.TQSXF.getCode());
                tqSxfOrderService.tqSxfScanPay(order, TqSxfPayParam.PayType.WECHAT, Order.InterFaceWay.SELF);
            }
        }
        /* 支付宝扫码支付*/
        else if (ReUtil.isMatch("^((2[5-9])|30)\\d{14,22}$", model.getAuthCode())) {
            // 获取支付通道和对应的利率
            payChannelRate=findMerchantChannel(order.getMerchantId(), PayChannelConstant.PayWay.ALIPAY.getCode(), 1);
            order.setInterestRate(payChannelRate.getInterestRate());
            // 支付方式是支付宝
            order.setPayWay(Order.PayWay.ALIPAY.getCode());
            // 支付宝支付配置的扫码支付通道
            int channel=payChannelRate.getScanChannel();
            if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.OFFICIAL.getCode());
                aliOrderService.aliScanPay(order, interFaceWay);
            }
            // 易融码支付宝扫码支付通道
            else if (PayChannelConstant.Channel.YRM.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.YRM.getCode());
                yrmOrderService.yrmScanPay(order, YrmPayParam.PayType.ALIPAY, interFaceWay);
            }
            // 惠闪付微信条码支付通道
            else if (PayChannelConstant.Channel.HSF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.HSF.getCode());
                hsfOrderService.hsfScanPay(order, CommonPayParam.HsfType.ALIPAY, Order.InterFaceWay.SELF);
            }
            //随行付通道
            else if (PayChannelConstant.Channel.SXF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.SXF.getCode());
                sxfOrderService.sxfScanPay(order, SxfPayParam.PayType.ALIPAY, Order.InterFaceWay.SELF);
            }
            //富友通道
            else if (PayChannelConstant.Channel.FY.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.FY.getCode());
                fyOrderService.fyScanPay(order, FyPayParam.PayType.ALIPAY, Order.InterFaceWay.SELF);
            }
            //天阙随行付通道
            else if (PayChannelConstant.Channel.TQSXF.getCode().equals(channel)) {
                order.setPayChannel(PayChannelConstant.Channel.TQSXF.getCode());
                tqSxfOrderService.tqSxfScanPay(order, TqSxfPayParam.PayType.ALIPAY, Order.InterFaceWay.SELF);
            }
        }
        // 会员卡付款码支付
        else if (model.getAuthCode().startsWith("MPC-")) {
            // 会员卡支付，利率为0
            order.setInterestRate(BigDecimal.ZERO);
            order.setPayWay(Order.PayWay.MEMBERCARD.getCode()); // 支付方式是会员卡
            memberOrderService.memberPay(order, null);
        } else {
            throw new BaseException("付款码错误", Resp.Status.PARAM_ERROR.getCode());
        }
        orderRepository.save(order);
        //由于createOrder的时候参数不全导致生成佣金失败，所以重新生成一次
        commissionService.crateCommission(order, true);
        return order;
    }


    //==============================退款=================================================
    public Order refund(OrderRefundDTO model) throws Exception {
        Order order=orderRepository.findByOrderNumberOrTransactionIdOrOutTradeNoOrRefundTransactionId(model.getOrderNumber(), model.getOrderNumber(), model.getOrderNumber(),
                model.getOrderNumber());
        if (ParamUtil.isBlank(order)) {
            throw new BaseException("查无此订单", Resp.Status.PARAM_ERROR.getCode());
        }
        order.setRefundUserId(model.getRefundUserId());
        order.setRefundTime(new Date());
        if (!order.getStatus().equals(Order.Status.PLACEORDER.getCode()) && !order.getStatus().equals(Order.Status.SUCCESSPAY.getCode()) && !order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
            throw new BaseException("此订单不能退款", Resp.Status.PARAM_ERROR.getCode());
        }
        // 订单类型是充值的不能退款
        if (null != order.getRechargeFlag() && Order.RechargeFlag.ISRECHARGE.getCode().equals(order.getRechargeFlag())) {
            throw new BaseException("充值订单不能退款", Resp.Status.PARAM_ERROR.getCode());
        }
        if (PayChannelConstant.Channel.HSF.getCode().equals(order.getPayChannel())) {
            throw new BaseException("惠闪付通道不支持退款操作", Resp.Status.PARAM_ERROR.getCode());
        }
        if (model.getRefundPayPrice() == null || model.getRefundPayPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("请输入正确的退款金额", Resp.Status.PARAM_ERROR.getCode());
        }
        if (model.getRefundPayPrice().scale() > 2) {
            throw new BaseException("退款金额最小为0.01", Resp.Status.PARAM_ERROR.getCode());
        }
        if (model.getRefundPayPrice().compareTo(order.getActPayPrice().subtract(order.getRefundPayPrice())) > 0) {
            throw new BaseException("可退余额不足，最多能退" + order.getActPayPrice().subtract(order.getRefundPayPrice()) + "元", Resp.Status.PARAM_ERROR.getCode());
        }
        // 微信官方退款
        if (Order.PayWay.WXPAY.getCode().equals(order.getPayWay()) && PayChannelConstant.Channel.OFFICIAL.getCode().equals(order.getPayChannel())) {
            wxOrderService.wxRefund(order, model.getRefundPayPrice());
        }
        // 支付宝官方退款
        else if (Order.PayWay.ALIPAY.getCode().equals(order.getPayWay()) && PayChannelConstant.Channel.OFFICIAL.getCode().equals(order.getPayChannel())) {
            aliOrderService.aliRefund(order, model.getRefundPayPrice());
        }
        // 会员宝退款
        else if (PayChannelConstant.Channel.HYB.getCode().equals(order.getPayChannel())) {
            order.setRefundOrderNo(DateUtil.format(new Date(), "YYYYMMddHHmmss") + RandomUtil.randomNumbers(4));
            hybOrderService.hybRefund(order, model.getRefundPayPrice());
        }
        // 易融码退款
        else if (PayChannelConstant.Channel.YRM.getCode().equals(order.getPayChannel())) {
            yrmOrderService.yrmRefund(order, model.getRefundPayPrice());
        }
        //随行付退款
        else if (PayChannelConstant.Channel.SXF.getCode().equals(order.getPayChannel())) {
            sxfOrderService.sxfRefund(order, model.getRefundPayPrice());
        }
        //富友退款
        else if (PayChannelConstant.Channel.FY.getCode().equals(order.getPayChannel())) {
            fyOrderService.fyRefund(order, model.getRefundPayPrice());
        }
        // 会员卡退款，退积分
        else if (Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay())) {
            memberOrderService.refund(order, model.getRefundPayPrice());
        }
        // 天阙随行付退款
        else if (PayChannelConstant.Channel.TQSXF.getCode().equals(order.getPayChannel())) {
            tqSxfOrderService.tqSxfRefund(order, model.getRefundPayPrice());
        }
        //处理佣金退款hxq
        Order refundOrder=new Order();
        BeanUtil.copyProperties(order, refundOrder);
        refundOrder.setRefundPayPrice(model.getRefundPayPrice());
        commissionService.crateCommission(refundOrder, true);
        return order;
    }

    /**
     * @author Created by wtl on 2019/4/25 23:11
     * @Description 查询订单状态，前端轮询
     */
    public Integer queryStatusByOrderNumber(String orderNumber) {
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("请输入订单号", Resp.Status.PARAM_ERROR.getCode());
        }
        Order order=orderRepository.findByOrderNumberAndDelFlag(orderNumber, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(order)) {
            throw new BaseException("订单有误", Resp.Status.PARAM_ERROR.getCode());
        }
        return order.getStatus();
    }

    /**
     * @author Created by wtl on 2019/5/24 9:41
     * @Description 退款结果处理
     */
    public void refundResult(Order order, BigDecimal refundPayPrice, PayRes payRes) {
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            // 退款失败
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        if (PayRes.ResultStatus.REFUNDFAIL.equals(payRes.getStatus())) {
            // 退款失败
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        //退款中
        if (PayRes.ResultStatus.REFUNDING.equals(payRes.getStatus())) {
            order.setStatus(Integer.valueOf(8));
        } else {
            // 累加退款金额
            order.setRefundPayPrice(order.getRefundPayPrice().add(refundPayPrice));
            // 是否已全部退款
            if (order.getActPayPrice().compareTo(order.getRefundPayPrice()) == 0) {
                order.setStatus(Order.Status.REFUNDTOTAL.getCode());
            } else {
                order.setStatus(Order.Status.REFUNDPART.getCode());
            }

        }
        save(order);
    }

    /**
     * 获取回调结果
     *
     * @param amount 单位：分
     */
    public String getCallBackResult(String orderNo, String resultcode, BigDecimal amount, Order order, String code) {
        if ((amount.compareTo(order.getActPayPrice().multiply(new BigDecimal(100)).stripTrailingZeros()) == 0) && orderNo.equals(order.getOrderNumber())) {
            if (code.equals(resultcode)) {
                // 订单状态为下单的才能改成已支付
                if (Order.Status.PLACEORDER.getCode().equals(order.getStatus())) {
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    order.setName(Order.Status.SUCCESSPAY.getStatus());
                    order.setPayTime(new Date());
                    this.getRepository().save(order);
                    commissionService.EditCommissionStatus(order.getId(), order.getStatus());

                    /**
                     * 会员支付需要生成消费记录和积分变化
                     */
                    if (!ParamUtil.isBlank(order.getMemberId())) {
                        this.getMemberOrderService().createMemberPayRecord(order);
                    }
                }
            }
            return "success";
        } else {
            log.info("fail");
            return "fail";
        }
    }

    public List<Order> findByMerchantIdsAsc(String[] ids, Date startTime, Date endTime) {
        return orderRepository.findByPayTimeBetweenAndMerchantIdInAndStatusNotInOrderByPayTimeAsc(startTime, endTime, ids, OrderConstant.failedOrderStatus);
    }

    public List<Order> findByMerchantIds(List<String> ids, Date startTime, Date endTime) {
        return orderRepository.findByPayTimeBetweenAndMerchantIdInOrderByPayTimeAsc(startTime, endTime, ids);
    }

    public List<Order> findByMerchantIdsDesc(String[] ids, Date startTime, Date endTime, Integer[] payWays) {
        return orderRepository.findByPayTimeBetweenAndMerchantIdInAndPayWayInAndStatusNotInOrderByPayTimeDesc(startTime, endTime, ids, payWays, OrderConstant.failedOrderStatus);
    }


    //----------------商户订单统计--------------------
    public MerchantOverviewCountVO statistics(List<Order> orderList) {
        MerchantOverviewCountVO merchantOverviewCountVO=new MerchantOverviewCountVO();
        orderList.stream()
                .filter(this::isSuccessOrder)
                .forEach(order -> {
                    //商户优惠
                    merchantOverviewCountVO.setMerchantDiscount(merchantOverviewCountVO.getMerchantDiscount().add(order.getDisCountPrice()));
                    //顾客实付
                    merchantOverviewCountVO.setRealPayAmount(merchantOverviewCountVO.getRealPayAmount().add(order.getActPayPrice()));
                    //订单金额
                    merchantOverviewCountVO.setOrderAmount(merchantOverviewCountVO.getOrderAmount().add(order.getTotalPrice()));
                    //订单数
                    merchantOverviewCountVO.setOrderTotal(merchantOverviewCountVO.getOrderTotal() + 1);
                    //退款处理
                    if (Order.Status.REFUNDPART.getCode().equals(order.getStatus())
                            || Order.Status.REFUNDTOTAL.getCode().equals(order.getStatus())) {
                        //退款数
                        merchantOverviewCountVO.setRefundCount(merchantOverviewCountVO.getRefundCount() + 1);
                        //退款总额
                        merchantOverviewCountVO.setRefundAmount(merchantOverviewCountVO.getRefundAmount().add(order.getRefundPayPrice()));
                    }
                });
        //实际营收 = 商户实收-退款总额
        merchantOverviewCountVO.setActualRevenue(merchantOverviewCountVO.getRealPayAmount().subtract(merchantOverviewCountVO.getRefundAmount()));
        //商户实退
        merchantOverviewCountVO.setSettlementRefundFee(merchantOverviewCountVO.getRefundAmount());
        //商户实收
        merchantOverviewCountVO.setSettlementTotalFee(merchantOverviewCountVO.getRealPayAmount());
        return merchantOverviewCountVO;
    }

    //-----------------------------------------------
    public boolean isSuccessOrder(Order order) {
        return !OrderConstant.failedOrderStatusSet.contains(order.getStatus());
    }

    public <T extends EnumInterface> List<PieChartDataVO> merchantRunningAccountOverviewPieChart(List<Order> orderList,
                                                                                                 Function<Order, Integer> orderProperty,
                                                                                                 Class<T> tClass) {
        List<PieChartDataVO> pieChartDataVOS=new ArrayList<>();
        if (ParamUtil.isBlank(orderList)) {
            return pieChartDataVOS;
        }
        final Map<Integer, Long> map=orderList.stream()
                .filter(order -> !ParamUtil.isBlank(orderProperty.apply(order)))
                .collect(Collectors.groupingBy(orderProperty, Collectors.counting()));
        for (T t : tClass.getEnumConstants()) {
            PieChartDataVO pieChartDataVO=new PieChartDataVO();
            pieChartDataVO.setName(t.getStatus());
            Long count=map.get(t.getCode());
            if (count == null) {
                pieChartDataVO.setValue(0);
            } else {
                pieChartDataVO.setValue(count.intValue());
            }
            pieChartDataVOS.add(pieChartDataVO);
        }
        return pieChartDataVOS;
    }

    public List<DataStatisticsLineDetailVO> getLeft(List<String> merchantIds, DataStatisticsConditionDTO condition) {

        //获取所有正常订单
        List<Integer> statusList=new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        //查询出所有有效订单
        List<Order> orderList=orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, condition.getStartTime(), condition.getEndTime(), Order.PayWay.MEMBERCARD.getCode());

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS=new ArrayList<>();
        List<String> xData=dateRangeList(condition.getStartTime(), condition.getEndTime(),
                DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        EnumInterface[] enmus=Order.PayWay.values();
        switch (condition.getType()) {
            case 1:
                for (EnumInterface enmu : enmus) {
                    List<Order> newList=orderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                    DataStatisticsLineDetailVO dataStatisticsLineDetailVO=new DataStatisticsLineDetailVO();
                    String PayName=enmu.getStatus();
                    dataStatisticsLineDetailVO.setPayName(PayName);
                    List<KeyValueVO> keyValueVOS=new ArrayList<>();
                    for (String dateTime : xData) {
                        KeyValueVO keyValueVO=new KeyValueVO();
                        keyValueVO.setDateTime(dateTime);
                        BigDecimal money=new BigDecimal(0);
                        for (Order order : newList) {
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                            if (sdf.format(order.getPayTime()).equals(dateTime)) {
                                money=order.getActPayPrice().add(money);
                            }
                        }
                        keyValueVO.setCount(money);
                        keyValueVOS.add(keyValueVO);
                    }
                    dataStatisticsLineDetailVO.setList(keyValueVOS);
                    dataStatisticsLineDetailVOS.add(dataStatisticsLineDetailVO);
                }

                break;
            case 2:
                for (EnumInterface enmu : enmus) {
                    List<Order> newList=orderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                    DataStatisticsLineDetailVO dataStatisticsLineDetailVO=new DataStatisticsLineDetailVO();
                    String PayName=enmu.getStatus();
                    dataStatisticsLineDetailVO.setPayName(PayName);
                    List<KeyValueVO> keyValueVOS=new ArrayList<>();
                    for (String dateTime : xData) {
                        KeyValueVO keyValueVO=new KeyValueVO();
                        keyValueVO.setDateTime(dateTime);
                        Integer money=0;
                        for (Order order : newList) {
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                            if (sdf.format(order.getPayTime()).equals(dateTime)) {
                                money++;
                            }
                        }
                        keyValueVO.setCount(new BigDecimal(money));
                        keyValueVOS.add(keyValueVO);
                    }
                    dataStatisticsLineDetailVO.setList(keyValueVOS);
                    dataStatisticsLineDetailVOS.add(dataStatisticsLineDetailVO);
                }
                break;
            case 3:
                for (EnumInterface enmu : enmus) {
                    List<Order> newList=orderList.stream().filter(o -> {
                        if (o.getPayWay().equals(enmu.getCode()) && (o.getStatus().equals(5) || o.getStatus().equals(6))) {
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    DataStatisticsLineDetailVO dataStatisticsLineDetailVO=new DataStatisticsLineDetailVO();
                    String PayName=enmu.getStatus();
                    dataStatisticsLineDetailVO.setPayName(PayName);
                    List<KeyValueVO> keyValueVOS=new ArrayList<>();
                    for (String dateTime : xData) {
                        KeyValueVO keyValueVO=new KeyValueVO();
                        keyValueVO.setDateTime(dateTime);
                        BigDecimal money=new BigDecimal(0);
                        for (Order order : newList) {
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                            if (sdf.format(order.getPayTime()).equals(dateTime)) {
                                //全额退款和部分推款分别处理
                                if (order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
                                    money=order.getRefundPayPrice().add(money);
                                } else {
                                    money=order.getActPayPrice().add(money);
                                }

                            }
                        }
                        keyValueVO.setCount(money);
                        keyValueVOS.add(keyValueVO);
                    }
                    dataStatisticsLineDetailVO.setList(keyValueVOS);
                    dataStatisticsLineDetailVOS.add(dataStatisticsLineDetailVO);
                }
                break;
            case 4:
                for (EnumInterface enmu : enmus) {
                    List<Order> newList=orderList.stream().filter(o -> {
                        if (o.getPayWay().equals(enmu.getCode()) && (o.getStatus().equals(5) || o.getStatus().equals(6))) {
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    DataStatisticsLineDetailVO dataStatisticsLineDetailVO=new DataStatisticsLineDetailVO();
                    String PayName=enmu.getStatus();
                    dataStatisticsLineDetailVO.setPayName(PayName);
                    List<KeyValueVO> keyValueVOS=new ArrayList<>();
                    for (String dateTime : xData) {
                        KeyValueVO keyValueVO=new KeyValueVO();
                        keyValueVO.setDateTime(dateTime);
                        Integer money=0;
                        for (Order order : newList) {
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                            if (sdf.format(order.getPayTime()).equals(dateTime)) {
                                money++;
                            }
                        }
                        keyValueVO.setCount(new BigDecimal(money));
                        keyValueVOS.add(keyValueVO);
                    }
                    dataStatisticsLineDetailVO.setList(keyValueVOS);
                    dataStatisticsLineDetailVOS.add(dataStatisticsLineDetailVO);
                }
                break;
            default:
                break;
        }
        return dataStatisticsLineDetailVOS;
    }

    private List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range=DateUtil.rangeToList(startDate, endDate, dateField);
        List<String> xData=range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
        return xData;
    }

    public List<DataStatisticsPieDetailVO> getRight(List<String> merchantIds, DataStatisticsConditionRightDTO condition) {
        //获取所有正常订单
        List<Integer> statusList=new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        //查询出所有有效订单
        List<Order> orderList;

        if (condition.getStartTime() == null) {
            orderList=orderRepository.findByStatusInAndMerchantIdInAndPayWayNot(statusList, merchantIds, Order.PayWay.MEMBERCARD.getCode());

        } else {
            orderList=orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, condition.getStartTime(), condition.getEndTime(), Order.PayWay.MEMBERCARD.getCode());

        }

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataStatisticsPieDetailVO> dataStatisticsLineDetailVOS=new ArrayList<>();
        EnumInterface[] enmus=Order.PayWay.values();
        if (condition.getType().equals(1)) {
            for (EnumInterface enmu : enmus) {
                DataStatisticsPieDetailVO dataStatisticsPieDetailVO=new DataStatisticsPieDetailVO();
                dataStatisticsPieDetailVO.setPayName(enmu.getStatus());
                BigDecimal money=new BigDecimal(0);
                List<Order> newList=orderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                for (Order order : newList) {
                    money=money.add(order.getActPayPrice());
                }
                dataStatisticsPieDetailVO.setPayMoney(money);
                dataStatisticsLineDetailVOS.add(dataStatisticsPieDetailVO);

            }
        } else {
            for (EnumInterface enmu : enmus) {
                DataStatisticsPieDetailVO dataStatisticsPieDetailVO=new DataStatisticsPieDetailVO();
                dataStatisticsPieDetailVO.setPayName(enmu.getStatus());
                List<Order> newList=orderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                dataStatisticsPieDetailVO.setPayMoney(new BigDecimal(newList.size()));
                dataStatisticsLineDetailVOS.add(dataStatisticsPieDetailVO);
            }

        }
        return dataStatisticsLineDetailVOS;

    }

    /**
     * 通过商户id 获取各个业务员发展商户占比；
     *
     * @param merchantIds
     * @param condition
     * @return
     */
    public List<DataStatisticsPieDetailVO> getAgentRight(List<String> merchantIds, DataStatisticsConditionRightDTO condition) {
        List<DataStatisticsPieDetailVO> dataStatisticsPieDetailVOS=new ArrayList<>();
        List<Merchant> merchants=merchantRepository.findByIdIn(merchantIds);
        List<String> managerIds=merchants.stream().map(Merchant::getManagerId).distinct().filter(managerId -> managerId != null).collect(Collectors.toList());
        for (String managerId : managerIds) {
            User user=userRepository.findOne(managerId);
            DataStatisticsPieDetailVO dataStatisticsPieDetailVO=new DataStatisticsPieDetailVO();
            dataStatisticsPieDetailVO.setPayName(user.getName());
            Integer count=0;
            for (Merchant merchant : merchants) {
                if (managerId.equals(merchant.getManagerId())) {
                    count++;
                }
            }
            dataStatisticsPieDetailVO.setPayMoney(new BigDecimal(count));
            dataStatisticsPieDetailVOS.add(dataStatisticsPieDetailVO);
        }
        return dataStatisticsPieDetailVOS;

    }

    public List<DataStatisticsLineDetailVO> getLeftCommission(String companyId, Date begin, Date end) {
        Company company=companyRepository.findOne(companyId);
        List<DataStatisticsLineDetailVO> result=new ArrayList<>();
        DataStatisticsLineDetailVO dataStatisticsLineDetailVO=new DataStatisticsLineDetailVO();
        dataStatisticsLineDetailVO.setPayName("佣金");
        List<KeyValueAllVO> list;
        List<KeyValueVO> resultData=new ArrayList<>();
        list=this.CommissionLine(company, begin, end);
        for (KeyValueAllVO keyValueAllVO : list) {
            KeyValueVO keyValueVO=new KeyValueVO();
            BeanUtil.copyProperties(keyValueAllVO, keyValueVO);
            resultData.add(keyValueVO);
        }
        dataStatisticsLineDetailVO.setList(resultData);
        result.add(dataStatisticsLineDetailVO);
        return result;
    }

    /**
     * 佣金曲线图
     *
     * @param company
     * @param begin
     * @param end
     * @return
     */
    private List<KeyValueAllVO> CommissionLine(Company company, Date begin, Date end) {
        List<Commission> commissionList;
        if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
            commissionList=commissionRepository.findAllBySecondIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
        } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
            commissionList=commissionRepository.findAllByFirstIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
        } else {
            commissionList=commissionRepository.findAllByOemIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
        }
        List<String> xData=dateRangeList(begin, end, DateField.DAY_OF_WEEK, "yyyy-MM-dd");
        List<KeyValueAllVO> keyValueVOS=new ArrayList<>();
        for (String dateTime : xData) {
            KeyValueAllVO keyValueVO=new KeyValueAllVO();
            keyValueVO.setDateTime(dateTime);
            keyValueVO.setDateTime(dateTime);
            //二级代理所有流水
            BigDecimal commissionMoney=new BigDecimal(0);
            for (Commission commission : commissionList) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.format(commission.getCreateTime()).equals(dateTime)) {
                    if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
                        commissionMoney=commissionMoney.add(commission.getSecondCommission());
                    } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
                        commissionMoney=commissionMoney.add(commission.getFirstCommission());
                    } else {
                        commissionMoney=commissionMoney.add(commission.getOemCommission() == null ? new BigDecimal("0"):commission.getOemCommission());
                    }
                }
            }
            keyValueVO.setCount(commissionMoney);
            keyValueVOS.add(keyValueVO);

        }
        return keyValueVOS;

    }

    /**
     * hzq  2019-12-13
     * 支付通道
     *
     * @param userId
     * @param condition
     * @return
     */
    public List<DataPayMentChannelVo> pay_channel(String userId, DataStatisticsConditionRightDTO condition) {

        //获取所有正常订单
        List<Integer> statusList=new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        //通过用户id获取商户id
        String merchantId=merchantUserFeign.findUser(userId).getMerchantId();
        List<String> merchantIds=new ArrayList<>();
        merchantIds.add(merchantId);

        MerchantUser merchantUser=merchantUserService.findOne(userId);
        //查询出当前时间段所有有效订单
        List<Order> thisOrderList;

        DateTime startTime=DateUtil.beginOfDay(condition.getStartTime());
        DateTime endTime=DateUtil.endOfDay(condition.getEndTime());
        List<DateTime> dateTimeList=DateUtil.rangeToList(startTime, endTime, DateField.DAY_OF_WEEK);


        Date lastStartTime=DateUtils.getLastday(startTime, dateTimeList.size()); //上个时间段开始日期
        Date lastEndTime=DateUtils.getLastday(endTime, dateTimeList.size()); //上个时间段结束日期


        //查询出当前时间段所有有效订单
        List<Order> lastOrderList;

        //店员
        if (merchantUser.getUserType().equals(MerchantUser.UserType.EMPLOYEES.getCode())) {

            //本次时间段的订单数量
            thisOrderList=orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(), merchantUser.getStoreId(), condition.getStartTime(), condition.getEndTime(), Order.PayWay.MEMBERCARD.getCode());

            //本次时间段的订单数量
            lastOrderList=orderRepository.findByStatusInAndUserIdAndStoreIdAndPayTimeBetweenAndPayWayNot(statusList, merchantUser.getId(), merchantUser.getStoreId(), lastStartTime, lastEndTime, Order.PayWay.MEMBERCARD.getCode());
        } else {

            //本次时间段的订单数量
            thisOrderList=orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, condition.getStartTime(), condition.getEndTime(), Order.PayWay.MEMBERCARD.getCode());

            //本次时间段的订单数量
            lastOrderList=orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, lastStartTime, lastEndTime, Order.PayWay.MEMBERCARD.getCode());
        }

        //数据处理 查询类型 1交易金额 2交易笔数 3 退款金额 4退款笔数
        List<DataPayMentChannelVo> dataPayMentChannelVoList=new ArrayList<>();
        EnumInterface[] enmus=Order.PayWay.values();
        if (condition.getType().equals(1)) {
            for (EnumInterface enmu : enmus) {
                DataPayMentChannelVo dataPayMentChannelVo=new DataPayMentChannelVo();
                dataPayMentChannelVo.setPayName(enmu.getStatus());
                BigDecimal money=new BigDecimal(0); //本次时间段的金额
                //本次时间段的订单
                List<Order> newList=thisOrderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                for (Order order : newList) {
                    money=money.add(order.getActPayPrice());
                }
                dataPayMentChannelVo.setThisPayMoney(money);//本次时间段金额


                //上个时间段的订单
                List<Order> lastNewList=lastOrderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                BigDecimal lastMoney=new BigDecimal(0); //本次时间段的金额
                for (Order order : lastNewList) {
                    lastMoney=lastMoney.add(order.getActPayPrice());
                }
                dataPayMentChannelVo.setLastPayMoney(lastMoney);//上个时间段金额

                dataPayMentChannelVoList.add(dataPayMentChannelVo);


            }
        } else {
            for (EnumInterface enmu : enmus) {
                DataPayMentChannelVo dataPayMentChannelVo=new DataPayMentChannelVo();
                dataPayMentChannelVo.setPayName(enmu.getStatus());
                //本次时间段的订单笔数
                List<Order> newList=thisOrderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                dataPayMentChannelVo.setThisPayMoney(new BigDecimal(newList.size()));

                //上次时间段的订单笔数
                List<Order> lastnewList=lastOrderList.stream().filter(o -> o.getPayWay().equals(enmu.getCode())).collect(Collectors.toList());
                dataPayMentChannelVo.setLastPayMoney(new BigDecimal(lastnewList.size()));
                dataPayMentChannelVoList.add(dataPayMentChannelVo);
            }

        }
        return dataPayMentChannelVoList;
    }


    public void rechargeCallBackByOrderNum(String out_trade_no) {

        log.info("进入rechargeCallBackByOrderNum方法");

        log.info("out_trade_no   " + out_trade_no);

        StoredRecored storedRecored=this.storedRecoredService.getRepository().findByOrderNumber(out_trade_no);

        log.info("storedRecored ----------" + storedRecored);
        if (null == storedRecored) {
            return;
        }
        storedRecored.setPayStatus(StoredRecored.PayStatus.SUCCESSPAY.getCode());
        this.storedRecoredService.update(storedRecored);

        log.info("4444444444444444444444444444");
        Member member=(Member) this.memberService.findOne(storedRecored.getMemberId());
        //StoredRule storedRule = (StoredRule)this.storedRuleService.findOne(storedRecored.getStoreRuleId());
        //log.info("储值规则表 ---------  "  + storedRule);
        //storedRule.setStoredAmount(Integer.valueOf(storedRule.getStoredAmount().intValue() + 1));
        //this.storedRuleService.update(storedRule);

        String memberLevelId=getMemberLevelId(storedRecored.getTradingMoney(), storedRecored.getMerchantId(), member);

        log.info("memberLevelId -----------" + memberLevelId);

        if (memberLevelId != null) {
            member.setMemberLevelId(memberLevelId);
            MemberLevel memberLevel=(MemberLevel) this.memberLevelService.findOne(memberLevelId);
            OrderVo orderVo=new OrderVo();
            orderVo.setMerchantId(member.getMerchantId());
            orderVo.setStoreId(storedRecored.getStoreId());
            Store store=this.storeService.findOne(storedRecored.getStoreId());
            orderVo.setStoreName(store.getName());
            orderVo.setStatus(Order.Status.SUCCESSPAY.getCode());
            orderVo.setPayWay(storedRecored.getPayWay());
            orderVo.setPayTime(new Date());
            orderVo.setUserName(storedRecored.getOperationUser());
            orderVo.setPayClient(storedRecored.getScores());
            if (memberLevel.getGiftMoney() != null && memberLevel.getGiftMoney().compareTo(BigDecimal.ZERO) > 0) {
                member.setBalance(member.getBalance().add(memberLevel.getGiftMoney()));

                StoredRecored storedRecoredNew=new StoredRecored();
                storedRecoredNew.setGiftMoney(memberLevel.getGiftMoney());
                storedRecoredNew.setTradingMoney(BigDecimal.ZERO);
                storedRecoredNew.setPostTradingMoney(member.getBalance().add((member.getFreezeBalance() == null) ? BigDecimal.ZERO : member.getFreezeBalance()));
                this.appStoreService.setStoredRecord(orderVo, member, storedRecoredNew);
                storedRecoredNew.setTradeType(StoredRecored.TradeType.MEMBERUPDATE.getCode());
                storedRecoredNew.setRemainScore(Integer.valueOf(0));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.storedRecoredService.save(storedRecoredNew);
            }

            if (memberLevel.getGiftScore() != null && memberLevel.getGiftScore().intValue() > 0) {
                Integer orgScore=member.getScores();
                member.setScores(Integer.valueOf(member.getScores().intValue() + memberLevel.getGiftScore().intValue()));
                this.appStoreService.creditsInfoDetails(orderVo, member, orgScore.intValue(), CreditsInfo.Trade.MEMBER_UPDATE_GIVEN);
            }
        }
        log.info("小程序充值最终会员表------------- " + member);
        this.memberService.update(member);
    }

    private String getMemberLevelId(BigDecimal tradingMoney, String merchantId, Member member) {
        List<MemberLevel> levels=(List) this.memberLevelService.findByMerchantId(merchantId).stream().sorted(Comparator.comparing(MemberLevel::getMemberLimitAmount)).collect(Collectors.toList());
        log.info("会员等级表 levels ----------   " + levels);
        if (levels.size() == 0)
            return null;
        if (tradingMoney.compareTo(((MemberLevel) levels.get(0)).getMemberLimitAmount()) < 0)
            return null;
        if (tradingMoney.compareTo(((MemberLevel) levels.get(levels.size() - 1)).getMemberLimitAmount()) >= 0) {
            MemberLevel memberLevel=(MemberLevel) this.memberLevelService.findOne(member.getMemberLevelId());
            if (memberLevel.getMemberLimitAmount().compareTo(((MemberLevel) levels.get(levels.size() - 1)).getMemberLimitAmount()) >= 0) {
                return null;
            }
            return ((MemberLevel) levels.get(levels.size() - 1)).getId();
        }
        for (int i=0; i < levels.size() - 1; i++) {
            MemberLevel level=(MemberLevel) levels.get(i);
            if (tradingMoney.compareTo(level.getMemberLimitAmount()) >= 0 && tradingMoney.compareTo(((MemberLevel) levels.get(i + 1)).getMemberLimitAmount()) < 0) {
                MemberLevel memberLevel=(MemberLevel) this.memberLevelService.findOne(member.getMemberLevelId());
                if (memberLevel.getMemberLimitAmount().compareTo(level.getMemberLimitAmount()) >= 0) {
                    return null;
                }
                return level.getId();
            }
        }

        return null;
    }

    public List recentTransactions(String userId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        //商户查看所有门店最近五条交易
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT create_time AS createTime ,pay_way AS payWay,`status`,act_pay_price AS actPayPrice,store_name AS storeName FROM lysj_order_order WHERE 1=1 AND");
        if(merchantUser.getUserType().equals(MerchantUser.UserType.MERCHANT.getCode())){
            sb.append(" merchant_id = :merchantId ");
        }else if(merchantUser.getUserType().equals(MerchantUser.UserType.MANAGER.getCode())){
            sb.append(" store_id = :storeId ");
        }else {
            sb.append(" user_id = :userId ");
        }
        sb.append(" ORDER BY create_time DESC LIMIT 0,5");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if(merchantUser.getUserType().equals(MerchantUser.UserType.MERCHANT.getCode())){
            query.setParameter("merchantId",merchantUser.getMerchantId());
        }else if(merchantUser.getUserType().equals(MerchantUser.UserType.MANAGER.getCode())){
            query.setParameter("storeId", merchantUser.getStoreId());
        }else {
            query.setParameter("userId",merchantUser.getId());
        }
        List resultList = query.getResultList();
        return resultList;
    }
}