package com.fzy.admin.fp.pay.pay.service;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.pay.pay.domain.AliConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.AliConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-23 16:50
 * @description 支付宝支付业务
 */
@Slf4j
@Service
@Transactional
public class AliPayService extends PayService {

    @Resource
    private AliConfigRepository aliConfigRepository;

    public AliConfigRepository getAliConfigRepository() {
        return aliConfigRepository;
    }

    /**
     * @author Created by wtl on 2019/4/23 21:44
     * @Description 初始化sdk客户端，不同商户不同的参数
     */
    public AlipayClient initAlipayClient(String merchantId) {
        Merchant merchant = merchantService.findOne(merchantId);
        // 根据商户id查询对应的支付配置
        AliConfig config = aliConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        appAuthToken = config.getAppAuthToken();
        // 获取支付宝服务商通用参数
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(merchant.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(topConfig)) {
            throw new BaseException("支付宝服务商参数未配置");
        }
        return new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", topConfig.getAliAppId(), topConfig.getAliPrivateKey(), "json", "utf-8", topConfig.getAliPublicKey(), "RSA2");
    }


    /**
     * @author Created by wtl on 2019/4/23 21:58
     * @Description 支付宝条形码支付
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.Yhpibd&treeId=194&articleId=105170&docType=1#s4
     * 根据公共返回参数中的code，这笔交易可能有四种状态：支付成功（10000），支付失败（40004），等待用户付款（10003）和未知异常（20000）。
     */
    public PayRes tradePay(AliPayParam model) throws AlipayApiException {

        AlipayClient alipayClient = null;
        try {
            alipayClient = initAlipayClient(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        model.setScene("bar_code");
        model.setSubject("支付宝条形码支付");
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        // 填充业务参数
        request.setBizContent(JacksonUtil.toJson(model));
        // 通过alipayClient调用API，获得对应的response类
        AlipayTradePayResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("支付宝条形码支付结果，{}", response.getBody());
            if ("10000".equals(response.getCode())) {
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
            }
            if ("10003".equals(response.getCode())) {
                return new PayRes("支付中", PayRes.ResultStatus.PAYING);
            }
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        // 网关调用失败
        return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/4/23 23:22
     * @Description 支付宝交易查询，客户端轮询
     */
    public PayRes tradeQuery(String merchantId, String orderNumber) throws AlipayApiException {
        AlipayClient alipayClient = initAlipayClient(merchantId);
        Map<String, Object> map = new HashMap<>();
        map.put("subject", "支付宝交易查询");
        map.put("out_trade_no", orderNumber);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JacksonUtil.toJson(map));
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("支付宝查询结果，{}", response.getBody());
        if (response.isSuccess()) {
            if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
                return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
            }
            if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {
                return new PayRes("等待付款", PayRes.ResultStatus.PAYING);
            }
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes(response.getMsg(), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/4/23 23:26
     * @Description 支付宝订单撤销
     */
    public PayRes tradeCancel(String merchantId, String orderNumber) throws AlipayApiException {
        AlipayClient alipayClient = initAlipayClient(merchantId);
        Map<String, Object> map = new HashMap<>();
        map.put("subject", "支付宝订单撤销");
        map.put("out_trade_no", orderNumber);
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JacksonUtil.toJson(map));
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        log.info("支付宝订单撤销结果，{}", response);
        if (response.isSuccess()) {
            if ("ACQ.TRADE_HAS_SUCCESS".equals(response.getSubCode())) {
                return new PayRes("撤销成功", PayRes.ResultStatus.SUCCESS);
            }
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/5/7 18:01
     * @Description 支付宝统一收单线下交易预创建
     */
    public PayRes preCreate(AliPayParam model) throws AlipayApiException {
        AlipayClient alipayClient = null;
        try {
            alipayClient = initAlipayClient(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        model.setSubject("支付宝扫码支付");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(getDomain() + "/order/callback/ali_order_callback");
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JacksonUtil.toJson(model));

        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            log.info("支付宝支付结果：{}", response.getBody());
            if (response.isSuccess() && "10000".equals(response.getCode())) {
                return new PayRes("下单成功", PayRes.ResultStatus.SUCCESS, response.getQrCode());
            }
            return new PayRes("下单失败，" + response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }catch (AlipayApiException e){
            return new PayRes(e.getMessage() , PayRes.ResultStatus.FAIL);
        }


    }

    /**
     * @author Created by wtl on 2019/4/24 15:44
     * @Description 支付宝退款
     */
    public PayRes refund(AliPayParam model) throws AlipayApiException {
        AlipayClient alipayClient = initAlipayClient(model.getMerchantId());
        model.setSubject("支付宝退款");
        model.setOut_request_no(ParamUtil.uuid());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizContent(JacksonUtil.toJson(model));
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        // 通信 失败
        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("退款申请成功", PayRes.ResultStatus.SUCCESS);
    }

    public PayRes freeze(AlifreezeParam paramAlifreezeParam) throws AlipayApiException {

        AlipayClient alipayClient = initAlipayClient(paramAlifreezeParam.getMerchantId());

        AliConfig config = this.aliConfigRepository.findByMerchantIdAndDelFlag(paramAlifreezeParam.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (StringUtil.isEmpty(config.getPid())) {
            return new PayRes("商户pid未配置", PayRes.ResultStatus.FAIL);
        }
        paramAlifreezeParam.setPayee_user_id(config.getPid());
        paramAlifreezeParam.setOrder_title("冻结资金");
        AlipayFundAuthOrderFreezeRequest request = new AlipayFundAuthOrderFreezeRequest();
        request.putOtherTextParam("app_auth_token", this.appAuthToken);
        request.setBizContent(JacksonUtil.toJson(paramAlifreezeParam));
        AlipayFundAuthOrderFreezeResponse response = (AlipayFundAuthOrderFreezeResponse)alipayClient.execute(request);


        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("冻结成功", PayRes.ResultStatus.SUCCESS, response);
    }

    public PayRes freezePay(AlifreezePayParam paramAlifreezePayParam) throws AlipayApiException {

        AlipayClient alipayClient = initAlipayClient(paramAlifreezePayParam.getMerchantId());

        AliConfig config = this.aliConfigRepository.findByMerchantIdAndDelFlag(paramAlifreezePayParam.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (StringUtil.isEmpty(config.getPid())) {
            return new PayRes("商户pid未配置", PayRes.ResultStatus.FAIL);
        }
        paramAlifreezePayParam.setSubject("押金消费");
        paramAlifreezePayParam.setSeller_id(config.getPid());
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.putOtherTextParam("app_auth_token", this.appAuthToken);
        request.setBizContent(JacksonUtil.toJson(paramAlifreezePayParam));
        AlipayTradePayResponse response = (AlipayTradePayResponse)alipayClient.execute(request);

        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("押金消费成功", PayRes.ResultStatus.SUCCESS);
    }

    public PayRes refundDeposit(AliRefundDepoistParam paramAliRefundDepoistParam) throws AlipayApiException {

        AlipayClient alipayClient = initAlipayClient(paramAliRefundDepoistParam.getMerchantId());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.putOtherTextParam("app_auth_token", this.appAuthToken);
        request.setBizContent(JacksonUtil.toJson(paramAliRefundDepoistParam));
        AlipayTradeRefundResponse response = (AlipayTradeRefundResponse)alipayClient.execute(request);

        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("退款申请成功", PayRes.ResultStatus.SUCCESS);
    }

    public PayRes unfreeze(AliUnFreezeParam paramAliUnFreezeParam) throws AlipayApiException {

        AlipayClient alipayClient = initAlipayClient(paramAliUnFreezeParam.getMerchantId());
        paramAliUnFreezeParam.setRemark("解冻资金");
        AlipayFundAuthOrderUnfreezeRequest request = new AlipayFundAuthOrderUnfreezeRequest();
        request.putOtherTextParam("app_auth_token", this.appAuthToken);
        request.setBizContent(JacksonUtil.toJson(paramAliUnFreezeParam));
        AlipayFundAuthOrderUnfreezeResponse response = (AlipayFundAuthOrderUnfreezeResponse)alipayClient.execute(request);

        if (!response.isSuccess() || !"10000".equals(response.getCode())) {
            return new PayRes(response.getSubMsg(), PayRes.ResultStatus.FAIL);
        }
        return new PayRes("解冻成功", PayRes.ResultStatus.SUCCESS);
    }
}
