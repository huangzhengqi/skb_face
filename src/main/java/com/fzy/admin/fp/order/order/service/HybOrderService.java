package com.fzy.admin.fp.order.order.service;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HybPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-25 18:02
 * @description 会员宝支付业务
 */
@Service
@Slf4j
public class HybOrderService extends BaseContent {

    @Resource
    private HybPayServiceFeign hybPayServiceFeign;

    @Resource
    private OrderService orderService;

    /**
     * @author Created by wtl on 2019/4/25 21:40
     * @Description 构建会员宝通用支付参数
     */
    public HybPayParam createHybPayParam(Order order) {
        HybPayParam hybPayParam = new HybPayParam();
        hybPayParam.setAmount(order.getActPayPrice().multiply(new BigDecimal(100).stripTrailingZeros()));
        hybPayParam.setOrderNo(order.getOrderNumber());
        hybPayParam.setMerchantId(order.getMerchantId());
        return hybPayParam;
    }

    /**
     * @author Created by wtl on 2019/4/25 21:39
     * @Description 会员宝网页支付
     */
    public Map<String, Object> hybWapPay(Order order, HybPayParam.PayType payType) throws Exception {
        // 构建会员宝通用支付参数
        HybPayParam hybPayParam = createHybPayParam(order);
        hybPayParam.setPayType(payType.getCode());
        // 调用会员宝微信支付
        String result = hybPayServiceFeign.hybWapPay(hybPayParam);
        Map<String, Object> map = new HashMap<>();
        map.put("payUrl", result);
//        map.put("openWay", PayChannelConstant.OpenWay.OPEN.getCode());
        map.put("channel", PayChannelConstant.Channel.HYB.getCode());
        return map;
    }

    /**
     * @author Created by wtl on 2019/5/10 17:09
     * @Description 会员宝退款
     */
    public boolean hybRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        // TODO: 会员宝退款目前只支持全额退款
        if (order.getActPayPrice().compareTo(refundPayPrice) != 0) {
            throw new BaseException("会员宝暂时只支持全额退款", Resp.Status.PARAM_ERROR.getCode());
        }
        // 构建微信通用支付参数
        HybPayParam hybPayParam = createHybPayParam(order);
        hybPayParam.setRefundOrderNo(order.getRefundOrderNo());
        hybPayParam.setRefundAmount(refundPayPrice);
        // 调用会员宝退款
        PayRes payRes = hybPayServiceFeign.hybRefund(hybPayParam);
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            // 退款失败
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
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
        orderService.getRepository().save(order);
        return true;
    }


    /**
     * @author Created by wtl on 2019/4/24 11:40
     * @Description 会员宝支付回调
     */
    public String hybOrderCallBack() throws Exception {
        log.info("进入会员宝异步回调");
        Map<String, String[]> requestParams = request.getParameterMap();
        // 回调结果转map
        Map<String, String> params = OrderUtil.params2Map(requestParams);
        Map map = JacksonUtil.toStringMap(params.get("data"));
        // 商户订单号
        String orderNo = (String) map.get("orderNo");
        // 交易状态
        String resultcode = (String) map.get("resultcode");
        Integer amount = (Integer) map.get("amount");
//        String paySign = (String) map.get("paySign");
        //查询出本系统订单记录
        Order order = orderService.getRepository().findByOrderNumberAndDelFlag(orderNo, CommonConstant.NORMAL_FLAG);
        // 校验签名
        String sign = (String) map.get("paySign");

        if ((new BigDecimal(amount).compareTo(order.getActPayPrice().multiply(new BigDecimal(100)).stripTrailingZeros()) == 0) && orderNo.equals(order.getOrderNumber())) {
            if ("0000".equals(resultcode)) {
                // 订单状态为下单的才能改成已支付
                if (Order.Status.PLACEORDER.getCode().equals(order.getStatus())) {
                    order.setStatus(Order.Status.SUCCESSPAY.getCode());
                    orderService.getRepository().save(order);
                    /**
                     * 会员支付需要生成消费记录和积分变化
                     */
                    if (!ParamUtil.isBlank(order.getMemberId())) {
                        orderService.getMemberOrderService().createMemberPayRecord(order);
                    }
                }
            }
            return "success";
        } else {
            return "fail";
        }
    }


}
