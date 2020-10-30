package com.fzy.admin.fp.pay.pay.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.pay.repository.HybConfigRepository;
import com.fzy.admin.fp.sdk.pay.config.PayResultConstant;
import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.pay.pay.domain.HybConfig;
import com.fzy.admin.fp.pay.pay.repository.HybConfigRepository;
import com.fzy.admin.fp.pay.pay.util.PayUtil;
import com.fzy.admin.fp.sdk.pay.config.PayResultConstant;
import com.fzy.admin.fp.sdk.pay.domain.HybPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-04-25 11:35
 * @description 会员宝支付通道
 */
@Service
@Slf4j
public class HybPayService extends PayService {

    @Resource
    private HybConfigRepository hybConfigRepository;

    public HybConfigRepository getHybConfigRepository() {
        return hybConfigRepository;
    }


    /**
     * @author Created by wtl on 2019/4/25 16:26
     * @Description 会员宝微信或支付宝网页支付
     */
    public String wapPay(HybPayParam model) {
        // 通过商户id查询会员宝支付参数
        HybConfig config = hybConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        Map<String, Object> params = new TreeMap<>();
        params.put("appid", config.getAppId());
        params.put("orderNo", model.getOrderNo());
        params.put("payType", model.getPayType());
        params.put("amount", model.getAmount());
        params.put("dateTime", DateUtil.format(new Date(), "YYYYMMddHHmmss"));
        params.put("productName", "会员宝支付");
        params.put("province", 0);
        params.put("city", 0);
        params.put("area", 0);
        params.put("asynNotifyUrl", getDomain() + "/order/callback/hyb_order_callback");
        params.put("sign", PayUtil.sign(params, config.getAppKey()));
        String result = HttpUtil.post("https://api.Kkww502.com/ts/scanpay/pay", params);
        log.info("支付结果，{}", result);
        Map map = JacksonUtil.toStringMap(result);
        Map dataMap = JacksonUtil.toStringMap((String) map.get("data"));
        return (String) dataMap.get("payUrl");
    }


    /**
     * @author Created by wtl on 2019/4/25 16:26
     * @Description 会员宝支付查询
     */
    public boolean payOrder(HybPayParam model) {
        // 通过商户id查询会员宝支付参数
        HybConfig config = hybConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        Map<String, Object> params = new TreeMap<>();
        params.put("appid", config.getAppId());
        params.put("orderNo", model.getOrderNo());
        params.put("sign", PayUtil.sign(params, config.getAppKey()));
        String result = HttpUtil.post("https://api.kkww502.com/query/payOrder", params);
        Map map = JacksonUtil.toStringMap(result);
        if ("success".equals(map.get("code"))) {
            Map dataMap = JacksonUtil.toStringMap((String) map.get("data"));
            if ("0000".equals(dataMap.get("resultcode"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author Created by wtl on 2019/5/10 15:55
     * @Description 会员宝退款申请
     */
    public PayRes refund(HybPayParam model) {
        // 通过商户id查询会员宝支付参数
        HybConfig config = hybConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        Map<String, Object> params = new TreeMap<>();
        params.put("appid", config.getAppId());
        params.put("orderNo", model.getOrderNo());
        params.put("dateTime", DateUtil.format(new Date(), "YYYYMMddHHmmss"));
        params.put("refundOrderNo", model.getOrderNo());
        // TODO: 目前只支持全额退款2019/5/16
        params.put("amount", model.getRefundAmount().multiply(new BigDecimal(100)).stripTrailingZeros());
        params.put("sign", PayUtil.sign(params, config.getAppKey()));
        String result = HttpUtil.post("https://api.Kkww502.com/ts/refund/apply", params);
        Map map = JacksonUtil.toStringMap(result);
        log.info("退款申请结果，{}", map);
        if ("success".equals(map.get("code"))) {
            Map dataMap = JacksonUtil.toStringMap((String) map.get("data"));
            if ("P000".equals(dataMap.get("resultcode"))) {
                return new PayRes("退款申请成功", PayRes.ResultStatus.SUCCESS);
            }
        }
        return new PayRes("退款申请失败，" + map.get("message"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/10 16:24
     * @Description 退款查询
     */
    public Integer refundQuery(HybPayParam model) {
        // 通过商户id查询会员宝支付参数
        HybConfig config = hybConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        Map<String, Object> params = new TreeMap<>();
        params.put("appid", config.getAppId());
        params.put("refundOrderNo", ParamUtil.uuid());
        params.put("sign", PayUtil.sign(params, config.getAppKey()));
        String result = HttpUtil.post("https://api.kkww502.com/ts/refund/query", params);
        Map map = JacksonUtil.toStringMap(result);
        if ("success".equals(map.get("code"))) {
            Map dataMap = JacksonUtil.toStringMap((String) map.get("data"));
            String resultCode = (String) dataMap.get("resultcode");
            // 退款成功
            if ("0000".equals(resultCode)) {
                return PayResultConstant.Refund.SUCCESS.getCode();
            }
            // 退款失败
            if ("p888".equals(resultCode)) {
                return PayResultConstant.Refund.FAIL.getCode();
            }
            // 退款中
            if ("P000".equals(resultCode)) {
                return PayResultConstant.Refund.WAITING.getCode();
            }
        }
        // 退款异常
        return PayResultConstant.Refund.EXCEPTION.getCode();
    }


}





