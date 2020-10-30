package com.fzy.admin.fp.pay.pay.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.pay.repository.YrmConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.DomainInterface;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.pay.domain.YrmConfig;
import com.fzy.admin.fp.pay.pay.repository.YrmConfigRepository;
import com.fzy.admin.fp.pay.pay.util.HttpUtil;
import com.fzy.admin.fp.pay.pay.util.PayUtil;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.YrmPayParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-05-16 15:52
 * @description 易融码支付业务
 */
@Service
@Slf4j
public class YrmPayService extends PayService {

    // 接口连接地址
    private static final String URL = "https://xpay.yrmpay.com/";

    private String key;

    @Resource
    private YrmConfigRepository yrmConfigRepository;

    public YrmConfigRepository getYrmConfigRepository() {
        return yrmConfigRepository;
    }

    /**
     * @author Created by wtl on 2019/5/21 14:56
     * @Description 构建易融码通用请求参数
     */
    public Map<String, String> createParam(String merchantId) {
        // 根据商户id查询易融码支付参数
        YrmConfig config = yrmConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        key = config.getAppKey();
        Map<String, String> params = new TreeMap<>();
        params.put("mid", config.getMid());
        params.put("nonceStr", ParamUtil.uuid());
        return params;
    }


    /**
     * @author Created by wtl on 2019/5/16 15:57
     * @Description 微信公众号/服务窗支付下单接口（用户扫商户）
     */
    public PayRes jsapiPay(YrmPayParam model) throws Exception {
        Map<String, String> params = createParam(model.getMerchantId());
        params.put("totalFee", model.getTotalFee().stripTrailingZeros().toPlainString());
        params.put("outTradeNo", model.getOutTradeNo());
        params.put("payChannel", model.getPayType());
        params.put("notifyUrl", getDomain() + "/order/callback/yrm_order_callback");
        params.put("sign", PayUtil.yrmSign(params, key));
        String xml = WXPayUtil.mapToXml(params);
        String result = HttpUtil.sendXmlPost(URL + "YRMPayGateway/hyb/jsapiPay", xml);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
        log.info("扫码支付结果，{}", resultMap);
        // 下单成功
        if ("success".equals(resultMap.get("resultCode"))) {
            Map<String, String> extraMap = new HashMap<>();
            extraMap.put("codeUrl", resultMap.get("codeUrl"));
            extraMap.put("transactionId", resultMap.get("transactionId"));
            return new PayRes("下单成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(extraMap));
        }
        return new PayRes("下单失败，" + resultMap.get("errDes"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/21 14:27
     * @Description 易融码扫码支付，商家扫用户
     */
    public PayRes microPay(YrmPayParam model) throws Exception {
        Map<String, String> params = createParam(model.getMerchantId());
        params.put("totalFee", model.getTotalFee().stripTrailingZeros().toPlainString());
        params.put("outTradeNo", model.getOutTradeNo());
        params.put("channel", model.getPayType());
        params.put("authCode", model.getAuthCode());
        params.put("sign", PayUtil.yrmSign(params, "010016540a3253c0dd5f40d1ac3ff9feb41b3235"));
        String xmlParams = WXPayUtil.mapToXml(params);
        String xmlResult = HttpUtil.sendXmlPost(URL + "YRMPayGateway/hyb/micropay", xmlParams);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        log.info("支付结果，{}", resultMap);
        // 通信成功
        if ("success".equals(resultMap.get("resultCode"))) {
            // 支付成功，返回易融码的交易单号，用于后续查询和退款
            if ("success".equals(resultMap.get("payCode"))) {
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS, resultMap.get("transactionId"));
            }
            // 支付中
            if ("paying".equals(resultMap.get("payCode"))) {
                return new PayRes("支付中", PayRes.ResultStatus.PAYING, resultMap.get("transactionId"));
            }
        }
        return new PayRes(resultMap.get("errDes"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/21 14:50
     * @Description 查询订单
     */
    public PayRes query(String merchantId, String orderNumber) throws Exception {
        Map<String, String> params = createParam(merchantId);
        params.put("outTradeNo", orderNumber);
        params.put("sign", PayUtil.yrmSign(params, key));
        String xmlResult = HttpUtil.sendXmlPost(URL + "YRMPayGateway/hyb/orderquery", WXPayUtil.mapToXml(params));
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        log.info("查询结果，{}", resultMap);
        // 查询成功
        if ("success".equals(resultMap.get("resultCode"))) {
            String status = resultMap.get("orderStatus");
            if ("success".equals(status)) {
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
            }
            if ("paying".equals(status)) {
                return new PayRes("支付中", PayRes.ResultStatus.PAYING);
            }
            if ("refunding".equals(status)) {
                return new PayRes("退款中", PayRes.ResultStatus.REFUNDING);
            }
            if ("refund".equals(status)) {
                return new PayRes("已退款", PayRes.ResultStatus.REFUND);
            }
        }
        return new PayRes(resultMap.get("errDes"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/21 16:28
     * @Description 退款
     * 1>系统会限制退款次数，最大可退款次数为50次；
     * 2>已申请过部分退款的订单，再次申请时不传退款金额，系统认为默认退全部金额，系统返回失败并给出相应提示；
     * 3>申请退款（含退款成功和正在退款中）会占用该笔订单可退款金额，如可退款金额不足，请等待退款完成（约三分钟）后再发起退款。
     * 4>退款当日，需在当日有大于退款金额的交易，系统会有相应提示。
     */
    public PayRes refund(YrmPayParam model) throws Exception {
        Map<String, String> params = createParam(model.getMerchantId());
        params.put("refundAmount", model.getRefundAmount().stripTrailingZeros().toPlainString());
        params.put("transactionId", model.getTransactionId());
        params.put("sign", PayUtil.yrmSign(params, key));
        String xmlResult = HttpUtil.sendXmlPost(URL + "YRMPayGateway/hyb/refund", WXPayUtil.mapToXml(params));
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        log.info("退款结果，{}", resultMap);
        // 退款申请成功
        if ("success".equals(resultMap.get("resultCode"))) {
            return new PayRes("退款申请成功", PayRes.ResultStatus.SUCCESS);
        }
        return new PayRes("退款申请失败，" + resultMap.get("errDes"), PayRes.ResultStatus.FAIL);
    }

}
