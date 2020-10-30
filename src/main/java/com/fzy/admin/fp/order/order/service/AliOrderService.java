package com.fzy.admin.fp.order.order.service;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.HuaBeiConfig;
import com.fzy.admin.fp.merchant.merchant.repository.HuabeiConfigRepository;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.pay.domain.AliHuaBeiPay;
import com.fzy.admin.fp.sdk.pay.domain.AliPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.AliPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-24 17:27
 * @description 支付宝订单业务
 */
@Service
@Slf4j
@Transactional
public class AliOrderService extends BaseContent {

    @Resource
    private OrderService orderService;
    @Resource
    private CommissionService commissionService;
    @Resource
    private HuabeiConfigRepository huabeiConfigRepository;

    @Resource
    private AliPayServiceFeign aliPayServiceFeign;

    public AliPayServiceFeign getAliPayServiceFeign() {
        return aliPayServiceFeign;
    }

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    /**
     * @author Created by wtl on 2019/4/24 11:35
     * @Description 构建支付宝支付参数
     */
    public AliPayParam createAliPayParam(Order model) {
        // 构建支付宝支付参数
        AliPayParam aliPayParam = new AliPayParam();
        aliPayParam.setMerchantId(model.getMerchantId());
        aliPayParam.setOut_trade_no(model.getOrderNumber());
        aliPayParam.setTotal_amount(String.valueOf(model.getActPayPrice()));
        aliPayParam.setAuth_code(model.getAuthCode());
        if (model.getHbFqNum() != null) {
            AliHuaBeiPay aliHuaBeiPay = new AliHuaBeiPay();
            aliHuaBeiPay.setHb_fq_num(model.getHbFqNum());
            HuaBeiConfig huaBeiConfig = huabeiConfigRepository.findByStoreIdAndEquipmentId(model.getStoreId(), model.getEquipmentId());
            if(huaBeiConfig == null){
                aliHuaBeiPay.setHb_fq_seller_percent("0");
            }else {
                aliHuaBeiPay.setHb_fq_seller_percent(huaBeiConfig.getInterest() == null ? "0" : huaBeiConfig.getInterest());
            }
            aliPayParam.setExtend_params(aliHuaBeiPay);
        }
        return aliPayParam;
    }

    /**
     * @author Created by wtl on 2019/4/23 23:36
     * @Description 支付宝条形码支付
     */
    public void aliScanPay(Order order, Order.InterFaceWay interFaceWay) throws Exception {
        // 构建支付宝通用支付参数
        AliPayParam aliPayParam = createAliPayParam(order);
        // 支付宝扫码支付
        PayRes payRes = aliPayServiceFeign.aliScanPay(aliPayParam);
        // 支付失败
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            order.setName("支付宝扫码支付失败");
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 支付成功
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            // 支付成功
            order.setName(payRes.getMsg());
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setPayTime(new Date());
            return;
        }
        // 开放接口给第三方，不需要这里进行轮询查询订单
        if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
            order.setPayTime(new Date());
            return;
        }
        // 需要输入支付密码
        boolean queryFlag = OrderUtil.payQuery(() -> aliPayServiceFeign.aliOrderQuery(order.getMerchantId(), order.getOrderNumber()), payRes);
        // 支付成功
        order.setStatus(Order.Status.SUCCESSPAY.getCode());
        order.setName(Order.Status.SUCCESSPAY.getStatus());
        // 支付时间
        order.setPayTime(new Date());
        if (!queryFlag) {
            // 订单错误或者超时，撤销订单
            aliPayServiceFeign.aliReverse(order.getMerchantId(), order.getOrderNumber());
            // 撤销支付
            order.setName(Order.Status.PLACEORDER.getStatus());
//            order.setStatus(Order.Status.CANCELPAY.getCode());
            order.setStatus(Order.Status.PLACEORDER.getCode());
        }
    }

    /**
     * @author Created by wtl on 2019/4/24 11:28
     * @Description 支付宝扫码支付
     */
    public Map<String, Object> aliWapPay(Order order) throws Exception {
        // 构建支付宝通用支付参数
        AliPayParam aliPayParam = createAliPayParam(order);
        // 调用支付宝wap支付
        PayRes payRes = aliPayServiceFeign.aliWapPay(aliPayParam);
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        Map<String, Object> map = new HashMap<>();
        log.info("支付宝扫码支付官方返回数据{}", payRes.toString());
        map.put("payUrl", payRes.getObject());
        return map;
    }

    /**
     * @author Created by wtl on 2019/4/24 11:40
     * @Description 支付宝支付回调
     */
    public String aliOrderCallBack() throws Exception {
        log.info("进入支付宝异步回调");
        // 获取支付宝POST过来反馈信息
        Map<String, String[]> requestParams = request.getParameterMap();
        // 回调结果转map
        Map<String, String> params = OrderUtil.params2Map(requestParams);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        log.info("交易订单：{}，回调成功", out_trade_no);
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        log.info("交易订单：{}，回调成功", trade_no);
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
        //查询出本系统订单记录
        Order order = orderService.getRepository().findByOrderNumberAndDelFlag(out_trade_no, CommonConstant.NORMAL_FLAG);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
        // 计算得出通知验证结果
        boolean verify_result = AlipaySignature.rsaCheckV1(params, aliConfigServiceFeign.getPublicKey(order.getMerchantId()), "utf-8", "RSA2");
        if (verify_result && (new BigDecimal(total_amount).compareTo(order.getActPayPrice()) == 0) && out_trade_no.equals(order.getOrderNumber())) {
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                // 订单状态为下单的才能改成已支付
                if (order.getStatus().equals(Order.Status.PLACEORDER.getCode())) {
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    order.setPayTime(new Date());
                    order.setName(Order.Status.SUCCESSPAY.getStatus());
                    orderService.getRepository().save(order);
                    //hxq 支付成功之后 修改佣金状态
                    commissionService.EditCommissionStatus(order.getId(), order.getStatus());
                }
            }
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * @author Created by wtl on 2019/4/24 16:57
     * @Description 支付宝退款
     */
    public void aliRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        // 构建支付宝通用支付参数
        AliPayParam aliPayParam = createAliPayParam(order);
        aliPayParam.setRefund_amount(refundPayPrice.toPlainString());
        // 调用支付宝退款
        PayRes payRes = aliPayServiceFeign.aliRefund(aliPayParam);
        orderService.refundResult(order, refundPayPrice, payRes);
    }

}
