package com.fzy.admin.fp.pay.pay.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.pay.pay.repository.WxConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.pay.pay.vo.JsPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-22 10:37:06
 * @description 微信支付服务层
 */
@Slf4j
@Service
@Transactional
public class WxPayService extends PayService {

    private String subMchId; // 子商户id

    @Resource
    private WxConfigRepository wxConfigRepository;

    public WxConfigRepository getWxConfigRepository() {
        return wxConfigRepository;
    }

    /**
     * 初始化wxconfig
     */
    public MyWxConfig initWxConfig(String merchantId) throws Exception {
        Merchant merchant = merchantService.getRepository().findOne(merchantId);
        // 通过商户id获取微信支付配置参数
        WxConfig config = wxConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        subMchId = config.getSubMchId();
        // 服务商参数
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(merchant.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(topConfig)) {
            throw new BaseException("微信服务商支付参数未配置");
        }
        return new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
    }

    /**
     * 构造参数
     */
    public Map<String, String> createParam(WxPayParam model) {
        Map<String, String> params = new HashMap<>();
        // 订单号
        params.put("out_trade_no", model.getOut_trade_no());
        params.put("total_fee", new BigDecimal(model.getTotal_fee()).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString()); // 价格
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("sub_mch_id", subMchId);
        return params;
    }

    /**
     * @author Created by wtl on 2019/6/19 11:24
     * @Description 微信小程序支付
     */
    public PayRes appletPay(WxPayParam model) throws Exception {
        MyWxConfig config = null;
        try {
            config = initWxConfig(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        // 构建通用参数
        Map<String, String> params = createParam(model);
        // 额外参数
        params.put("sub_appid", model.getAppletAppId());
        params.put("body", "小程序支付");
        params.put("sub_openid", model.getOpenid());
        params.put("notify_url", getDomain() + "/order/callback/wx_order_callback");
        params.put("trade_type", "JSAPI");
        Map<String, String> response = new WXPay(config).unifiedOrder(params);
        log.info("统一下单结果：{}", response);
        if (WXPayConstants.FAIL.equals(response.get("return_code"))) {
            return new PayRes(response.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.FAIL.equals(response.get("result_code"))) {
            return new PayRes(response.get("err_code_des"), PayRes.ResultStatus.FAIL);
        }
        String prepay_id = response.get("prepay_id");
        Map<String, String> wxPayMap = new HashMap<>();
        String packages = "prepay_id=" + prepay_id;
        wxPayMap.put("appId", model.getAppletAppId());
        wxPayMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
        wxPayMap.put("nonceStr", WXPayUtil.generateNonceStr());
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "MD5");
        wxPayMap.put("sign", WXPayUtil.generateSignature(wxPayMap, config.getKey()));
        return new PayRes("下单成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(wxPayMap));
    }


    /**
     * @author Created by wtl on 2019/4/22 17:29
     * @Description 微信支付，jsapi
     * 点击支付按钮时，执行一个Ajax到后台
     * 后台通过前台的部分信息（如商品名额，金额等），将其组装成符合微信要求格式的xml，然后调用微信的“统一下单接口”
     * 调用成功后微信会返回一个组装好的xml，我们提取之中的消息（预支付id也在其中）以JSON形式返回给前台
     * 前台将该JSON传参给微信内置JS的方法中，调其微信支付
     * 支付成功后，微信会将本次支付相关信息返回给我们的服务器
     */
    public PayRes jsPay(WxPayParam model) throws Exception {
        MyWxConfig config = null;
        try {
            config = initWxConfig(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        // 构建通用参数
        Map<String, String> params = createParam(model);
        // 额外参数
        params.put("body", "微信js支付");
        params.put("openid", model.getOpenid());
        params.put("notify_url", httpServletRequest.getAttribute(CommonConstant.DOMAIN_NAME) + "/order/callback/wx_order_callback");
        params.put("trade_type", "JSAPI");
        Map<String, String> response = new WXPay(config).unifiedOrder(params);
        if (WXPayConstants.FAIL.equals(response.get("return_code"))) {
            return new PayRes(response.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.FAIL.equals(response.get("result_code"))) {
            return new PayRes(response.get("err_code_des"), PayRes.ResultStatus.FAIL);
        }

        Map<String, Object> map = new HashMap<>();
        String prepay_id = response.get("prepay_id");
        // ******************************************
        //
        //  前端调起微信支付必要参数，需要重新签名
        //
        // ******************************************
        Map<String, String> wxPayMap = new HashMap<>();
        String packages = "prepay_id=" + prepay_id;
        wxPayMap.put("appId", config.getAppID());
        wxPayMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000L));
        wxPayMap.put("nonceStr", WXPayUtil.generateNonceStr());
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "MD5");
        // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
        String sign = WXPayUtil.generateSignature(wxPayMap, config.getKey());
        // 返回给前端的数据格式
        JsPayResponse jsPayResponse = new JsPayResponse();
        jsPayResponse.setAppId(config.getAppID());
        jsPayResponse.setTimeStamp(wxPayMap.get("timeStamp"));
        jsPayResponse.setNonceStr(wxPayMap.get("nonceStr"));
        jsPayResponse.setPkg(wxPayMap.get("package"));
        jsPayResponse.setSignType("MD5");
        jsPayResponse.setPaySign(sign);
        jsPayResponse.setOrderNumber(model.getOut_trade_no());
        jsPayResponse.setPrice(model.getTotal_fee());
        map.put("jsPayResponse", jsPayResponse);
        return new PayRes("预下单成功", PayRes.ResultStatus.SUCCESS, map);
    }


    /**
     * @author Created by wtl on 2019/4/22 10:40
     * @Description 微信刷卡支付、扫码枪支付
     */
    public PayRes microPay(WxPayParam model,Order order) throws Exception {
        MyWxConfig config = null;
        try {
            // 初始化配置
            config = initWxConfig(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }

        // 构建通用参数
        Map<String, String> params = createParam(model);
        // 额外参数
        params.put("body", "微信付款码支付");
        params.put("auth_code", model.getAuth_code());
        Map<String, String> result = new WXPay(config).microPay(params);
        log.info("微信支付结果，{}", result);
        String settlementTotalFee = result.get("settlement_total_fee");
        log.info("settlement_total_fee ========= " + settlementTotalFee);
        // 通信失败
        if (WXPayConstants.FAIL.equals(result.get("return_code"))) {
            return new PayRes(result.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.SUCCESS.equals(result.get("result_code"))) {
            if (settlementTotalFee != null) {
                //应结订单金额
                BigDecimal settlementTotalFee1 = new BigDecimal(settlementTotalFee);
                order.setDisCountPrice(settlementTotalFee1);
                log.info("计算应结订单金额============    "+order.getActPayPrice().subtract(settlementTotalFee1));
                order.setActPayPrice(order.getActPayPrice().subtract(settlementTotalFee1));
            }
            return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
        }
        if ("USERPAYING".equals(result.get("err_code"))) {
            return new PayRes("支付中", PayRes.ResultStatus.PAYING);
        }
        return new PayRes(result.get("err_code_des"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/4/22 11:10
     * @Description 微信支付-查询订单
     */
    public PayRes orderQuery(String merchantId, Order order) throws Exception {
        MyWxConfig config = initWxConfig(merchantId);
        WXPay wxPay = new WXPay(config);
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", order.getOrderNumber());
        params.put("sub_mch_id", subMchId);
        Map<String, String> result = wxPay.orderQuery(params);
        log.info("微信支付订单查询结果，{}", result);
        String settlementTotalFee = result.get("settlement_total_fee");
        log.info("应结订单金额===============   " + settlementTotalFee);
        if (WXPayConstants.FAIL.equals(result.get("return_code"))) {
            return new PayRes(result.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.FAIL.equals(result.get("result_code"))) {
            return new PayRes(result.get("err_code_des"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.SUCCESS.equals(result.get("trade_state"))) {
            if (settlementTotalFee != null) {
                //应结订单金额
                BigDecimal settlementTotalFee1 = new BigDecimal(settlementTotalFee);
                order.setDisCountPrice(settlementTotalFee1);
                log.info("计算金额============    "+order.getActPayPrice().subtract(settlementTotalFee1));
                order.setActPayPrice(order.getActPayPrice().subtract(settlementTotalFee1));
            }
            return new PayRes("订单付款成功", PayRes.ResultStatus.SUCCESS);
        }
        if ("USERPAYING".equals(result.get("trade_state"))) {
            return new PayRes("订单支付中", PayRes.ResultStatus.PAYING);
        }
        /**
         * SUCCESS—支付成功
         * REFUND—转入退款
         * NOTPAY—未支付
         * CLOSED—已关闭
         * REVOKED—已撤销(刷卡支付)
         * USERPAYING--用户支付中
         * PAYERROR--支付失败(其他原因，如银行返回失败)
         */
        return new PayRes(result.get("trade_state_des"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/4/22 14:47
     * @Description 微信支付-撤销订单
     */
    public PayRes reverse(String merchantId, String orderNumber) throws Exception {
        MyWxConfig config = initWxConfig(merchantId);
        WXPay wxPay = new WXPay(config);
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", orderNumber);
        params.put("sub_mch_id", subMchId);
        Map<String, String> result = wxPay.reverse(params);
        log.info("撤销结果，{}", result);
        return apiResult(result);
    }

    /**
     * @author Created by wtl on 2019/4/24 20:42
     * @Description 微信退款
     */
    public PayRes wxRefund(WxPayParam model) throws Exception {
        MyWxConfig config = initWxConfig(model.getMerchantId());
        WXPay wxPay = new WXPay(config);
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", model.getOut_trade_no());
        params.put("out_refund_no", ParamUtil.uuid());
        params.put("sub_mch_id", subMchId);
        params.put("total_fee", new BigDecimal(model.getTotal_fee()).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString());
        params.put("refund_fee", new BigDecimal(model.getRefund_fee()).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString());
        Map<String, String> result = wxPay.refund(params);
        log.info("退款结果，{}", result);
        return apiResult(result);
    }

    /**
     * @author Created by wtl on 2019/5/23 10:57
     * @Description 调用api结果
     */
    public PayRes apiResult(Map<String, String> result) {
        // 通信错误
        if (WXPayConstants.FAIL.equals(result.get("return_code"))) {
            return new PayRes(result.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        // 业务错误
        if (WXPayConstants.FAIL.equals(result.get("result_code"))) {
            return new PayRes(result.get("err_code_des"), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("业务处理成功", PayRes.ResultStatus.SUCCESS);
    }

}