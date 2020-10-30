package com.fzy.admin.fp.pay.pay.service;

import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.pay.pay.repository.HsfConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.pay.pay.domain.HsfConfig;
import com.fzy.admin.fp.pay.pay.repository.HsfConfigRepository;
import com.fzy.admin.fp.pay.pay.util.PayUtil;
import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.CommonRefundParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-05-31 9:54
 * @description 惠闪付 支付业务
 */
@Slf4j
@Service
public class HsfPayService extends PayService {

    // 接口域名
    private final String URL = "http://pay.congmingpay.com/";

    private String appKey;

    @Resource
    private HsfConfigRepository hsfConfigRepository;

    public HsfConfigRepository getHsfConfigRepository() {
        return hsfConfigRepository;
    }

    /**
     * @author Created by wtl on 2019/5/31 10:07
     * @Description 构建通用请求参数
     */
    public Map<String, Object> createParam(String merchantId) {
        // 根据商户id查询惠闪付支付参数
        HsfConfig hsfConfig = hsfConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(hsfConfig)) {
            throw new BaseException("惠闪付通道支付参数未配置", Resp.Status.PARAM_ERROR.getCode());
        }
        appKey = hsfConfig.getAppKey();
        Map<String, Object> params = new TreeMap<>();
        params.put("shopId", hsfConfig.getShopId());
        return params;
    }


    /**
     * @author Created by wtl on 2019/6/15 15:55
     * @Description 条码支付
     */
    public PayRes micropay(CommonPayParam model) {
        Map<String, Object> params;
        try {
            params = createParam(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        params.put("orderId", System.currentTimeMillis() + "");
        params.put("money", model.getTotalFee());
        params.put("authCode", model.getAuthCode());
        params.put("sign", PayUtil.yrmSign(params, appKey));
        String result = HttpUtil.get(URL + "pay/micropay.do", params);
        log.info("惠闪付扫码条码支付结果，{}", result);
        Map<String, String> map = JacksonUtil.toStringMap(result);
        if ("SUCCESS".equals(map.get("return_code"))) {
            // 支付成功
            if ("SUCCESS".equals(map.get("pay_status"))) {
                // 需保存通道流水号，用来退款
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS, map.get("transaction_id"));
            }
            // 支付中
            if ("PAYING".equals(map.get("pay_status"))) {
                return new PayRes("支付中", PayRes.ResultStatus.PAYING, map.get("transaction_id"));
            }
        }
        return new PayRes(map.get("pay_msg"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/31 11:17
     * @Description 查询订单
     */
    public PayRes query(CommonQueryParam model) {
        Map<String, Object> params;
        try {
            params = createParam(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        params.put("orderId", model.getOrderNumber());
        params.put("sign", PayUtil.yrmSign(params, appKey));
        String result = HttpUtil.get(URL + "pay/query.do", params);
        log.info("惠闪付订单查询结果，{}", result);
        Map<String, String> map = JacksonUtil.toStringMap(result);
        if ("SUCCESS".equals(map.get("result_code"))) {
            if ("1".equals(map.get("status"))) {
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
            }
            if ("2".equals(map.get("status"))) {
                return new PayRes("支付中", PayRes.ResultStatus.PAYING);
            }
            return new PayRes("支付失败", PayRes.ResultStatus.FAIL);
        }

        return new PayRes(map.get("result_msg"), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/6/16 20:46
     * @Description 惠闪付退款
     */
    public PayRes refund(CommonRefundParam model) {
        Map<String, Object> params;
        try {
            params = createParam(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        params.put("orderid", model.getTransactionId()); //
        params.put("money", model.getRefundFee());
        params.put("sign", PayUtil.yrmSign(params, appKey));
        String result = HttpUtil.get(URL + "pay/refund.do", params);
        log.info("惠闪付退款结果，{}", result);
        Map<String, String> map = JacksonUtil.toStringMap(result);
        if ("success".equals(map.get("status"))) {
            return new PayRes("退款成功", PayRes.ResultStatus.REFUND);
        }
        if ("processing".equals(map.get("status"))) {
            return new PayRes("退款中", PayRes.ResultStatus.REFUNDING);
        }
        return new PayRes(map.get("msg"), PayRes.ResultStatus.FAIL);
    }

    public boolean hsfInit(String merchantId, String shop_id, String hsfKey) {
        HsfConfig hsfConfig = hsfConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(hsfConfig)) {
            hsfConfig.setShopId(shop_id);
            hsfConfig.setAppKey(hsfKey);
            hsfConfigRepository.save(hsfConfig);
            return true;
        }
        return false;
    }


}
