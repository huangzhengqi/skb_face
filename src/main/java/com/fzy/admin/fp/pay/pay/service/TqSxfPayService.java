package com.fzy.admin.fp.pay.pay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.utils.AddressUtil;
import com.fzy.admin.fp.merchant.merchant.domain.HuaBeiConfig;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.repository.HuabeiConfigRepository;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.pay.pay.domain.SxfConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.repository.TqSxfConfigRepository;
import com.fzy.admin.fp.pay.pay.util.RSASignature;
import com.fzy.admin.fp.pay.pay.util.SxfHttpUtils;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfIncomeVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfQueryMerchantInfoVO;
import com.fzy.admin.fp.pay.pay.vo.UploadPictureVO;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.*;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 天阙随行付 支付业务
 */
@Slf4j
@Service
public class TqSxfPayService extends PayService {

    //天阙随行付测试公钥
//    String tqSxfPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOmsrFtFPTnEzfpJ/hDl5RODBxw4i9Ex3NmmG/N7A1+by032zZZgLLpdNh8y5otjFY0E37Nyr4FGKFRSSuDiTk8vfx3pv6ImS1Rxjjg4qdVHIfqhCeB0Z2ZPuBD3Gbj8hHFEtXZq8+msAFu/";

    //天阙随行付生产公钥
    String tqSxfPublic = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjo1+KBcvwDSIo+nMYLeOJ19Ju4ii0xH66ZxFd869EWFWk/EJa3xIA2+4qGf/Ic7m7zi/NHuCnfUtUDmUdP0JfaZiYwn+1Ek7tYAOc1+1GxhzcexSJLyJlR2JLMfEM+rZooW4Ei7q3a8jdTWUNoak/bVPXnLEVLrbIguXABERQ0Ze0X9Fs0y/zkQFg8UjxUN88g2CRfMC6LldHm7UBo+d+WlpOYH7u0OTzoLLiP/04N1cfTgjjtqTBI7qkOGxYs6aBZHG1DJ6WdP+5w+ho91sBTVajsCxAaMoExWQM2ipf/1qGdsWmkZScPflBqg7m0olOD87ymAVP/3Tcbvi34bDfwIDAQAB";

    // 测试接口域名
//      private final String URL = "https://openapi-test.tianquetech.com/";
    //正式接口域名
    private final String URL = "https://openapi.tianquetech.com/";

    private String mno;
    private String privateKey;

    private final String SUCCESS_CODE = "0000";

    @Resource
    private TqSxfConfigRepository tqSxfConfigRepository;

    @Resource
    private HuabeiConfigRepository huabeiConfigRepository;

    public TqSxfConfigRepository getSxfConfigRepository() {
        return tqSxfConfigRepository;
    }


    /**
     * 天阙随行付二维码设置费率
     *
     * @param wxInterestRate
     * @param aliInterestRate
     * @param merchantId
     * @return
     * @throws Exception
     */
    public PayRes qrcodeProductSetup(BigDecimal wxInterestRate, BigDecimal aliInterestRate, String merchantId) throws Exception {
        //注：费率修改成功后，次日 0 点生效
        log.info("==进入费率设置");
        Map<String, String> wxQrcodeList = new HashMap<>(2);
        wxQrcodeList.put("rate", wxInterestRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        wxQrcodeList.put("rateType", "01");
        Map<String, String> aliQrcodeList = new HashMap<>(2);
        aliQrcodeList.put("rate", aliInterestRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        aliQrcodeList.put("rateType", "02");
        // 银联单笔小于 1000
        Map<String, String> yinLianQrcodeList = new HashMap<>(2);
        yinLianQrcodeList.put("rate", "0.35");
        yinLianQrcodeList.put("rateType", "06");
        // 银联单笔大于 1000
        Map<String, String> yinLianQrcodeList2 = new HashMap<>(2);
        yinLianQrcodeList2.put("rate", "0.61");
        yinLianQrcodeList2.put("rateType", "07");
        List<Map<String, String>> qrcodeList = new ArrayList<>();
        qrcodeList.add(wxQrcodeList);
        qrcodeList.add(aliQrcodeList);
        qrcodeList.add(yinLianQrcodeList);
        qrcodeList.add(yinLianQrcodeList2);

        Map<String, Object> params = new HashMap<>(1);
        params.put("qrcodeList", qrcodeList);
        log.info("费率设置参数" + params);
        return merchantSetupA(merchantId, params, "merchant/merchantSetup", "二维码费率设置", "0000", Integer.valueOf(2));

    }

    /**
     * 天阙随行付统一发起请求
     *
     * @param merchantId
     * @param params
     * @param payUrl
     * @param msg
     * @param code
     * @return
     * @throws Exception
     */
    private PayRes payLaunch(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        //组装加密串
        String signContent = RSASignature.getOrderContent(reqMap);
        log.info("==配置密钥【" + privateKey + "】=====");
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new PayRes("密钥配置异常", PayRes.ResultStatus.FAIL);
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        log.info("req" + reqStr);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        log.info("支付-结果，{}", resultMap);

        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("respData =========> :{}  ", JSON.toJSONString(respData).toString());
                if (code.equals(map.get("bizCode"))) {
                    if ("订单查询".equals(msg)) {
                        String status = map.get("tranSts");
                        if ("SUCCESS".equals(status)) {
                            return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(map));
                        }
                        if ("PAYING".equals(status)) {
                            return new PayRes("支付中", PayRes.ResultStatus.PAYING);
                        }
                        if ("REFUND".equals(status)) {
                            return new PayRes("已退款", PayRes.ResultStatus.REFUND);
                        }
                    }
                    return new PayRes(msg + "成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(map));
                }
                if ("0001".equals(map.get("bizCode"))) {
                    return new PayRes(msg + "失败，" + map.get("bizMsg"), PayRes.ResultStatus.FAIL);
                }
                //单笔金额超限
                if ("3002".equals(map.get("bizCode"))) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.FAIL);
                }
                return new PayRes(map.get("bizMsg") + map.get("bizCode"), PayRes.ResultStatus.PAYING);
            } else {
                return new PayRes("验签失败", PayRes.ResultStatus.FAIL);
            }
        } else {
            if ("0001".equals(resultMap.get("code"))) {
                return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
            }
            return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
        }
    }

    /**
     * 统一创建构建参数
     *
     * @param merchantId
     * @return
     */
    private Map<String, String> createParam(String merchantId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 根据商户id查询支付参数
        TqSxfConfig tqSxfConfig = tqSxfConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(tqSxfConfig)) {
            throw new BaseException("天阙随行付通道支付参数未配置", Resp.Status.PARAM_ERROR.getCode());
        }
        String serviceProviderId = merchantService.findOne(merchantId).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        Map<String, String> params = new TreeMap<>();
        mno = tqSxfConfig.getMno();
        privateKey = topConfig.getTqSxfPrivateKey();
        params.put("orgId", topConfig.getTqSxfOrgId());
        params.put("reqId", ParamUtil.uuid());
        params.put("signType", "RSA");
        params.put("timestamp", sdf.format(new Date()));
        params.put("version", "1.0");
        return params;
    }

    /**
     * 首次进件构造参数
     *
     * @param merchantId
     * @return
     */
    private Map<String, String> firstCreateParam(String merchantId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String serviceProviderId = merchantService.findOne(merchantId).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        Map<String, String> params = new TreeMap<>();
        privateKey = topConfig.getTqSxfPrivateKey();
        params.put("orgId", topConfig.getTqSxfOrgId());
        params.put("reqId", ParamUtil.uuid());
        params.put("signType", "RSA");
        params.put("timestamp", sdf.format(new Date()));
        params.put("version", "1.0");
        return params;
    }

    /**
     * 天阙随行付支付接口 （C扫B）
     */
    public PayRes jsapiPay(TqSxfPayParam model) throws Exception {
        log.info(" 天阙随行付C扫B订单 -----   " + model.getOutTradeNo());
        Map<String, Object> params = new HashMap<>(15);
        params.put("ordNo", model.getOutTradeNo());
        params.put("amt", model.getTotalFee().toPlainString());
        params.put("subject", merchantService.findOne(model.getMerchantId()).getName());
        params.put("payType", model.getPayType());
        if (model.getHbFqNum() != null) {
            params.put("hbFqNum", model.getHbFqNum());
            HuaBeiConfig huaBeiConfig = huabeiConfigRepository.findByStoreIdAndEquipmentId(model.getStoreId(), model.getEquipmentId());
            params.put("hbFqPercent", huaBeiConfig.getInterest() == null ? "0" : huaBeiConfig.getInterest());
        }
        params.put("tradeSource", "01");
        params.put("trmIp", "47.107.132.130");
        params.put("notifyUrl", getDomain() + "/order/callback/tq_sxf_order_callback");
        String serviceProviderId = merchantService.findOne(model.getMerchantId()).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        params.put("subAppid",topConfig.getWxAppId());
        return payLaunch(model.getMerchantId(), params, "order/activeScan", "扫码支付", "0000");
    }

    /**
     * 天阙随行付支付接口 （B扫C）
     */
    public PayRes micropay(TqSxfPayParam model) throws Exception {
        log.info(" 天阙随行付B扫C订单 -----   " + model.getOutTradeNo());
        Map<String, Object> params = new HashMap<>(15);
        params.put("ordNo", model.getOutTradeNo());
        params.put("authCode", model.getAuthCode());
        params.put("amt", model.getTotalFee().toPlainString());
        params.put("subject", merchantService.findOne(model.getMerchantId()).getName());
        params.put("tradeSource", "01");
        params.put("trmIp", "47.107.132.130");
        params.put("payType", model.getPayType());
        if (model.getHbFqNum() != null) {
            if (model.getHbFqNum().equals("3")) {
                throw new BaseException("该通道不支持3期花呗分期，请联系客服", Resp.Status.PARAM_ERROR.getCode());
            }
            params.put("hbFqNum", model.getHbFqNum());
            HuaBeiConfig huaBeiConfig = huabeiConfigRepository.findByStoreIdAndEquipmentId(model.getStoreId(), model.getEquipmentId());
            if (huaBeiConfig != null) {
                params.put("hbFqPercent", huaBeiConfig.getInterest() == null ? "100" : huaBeiConfig.getInterest());
            }
        }
        String serviceProviderId = merchantService.findOne(model.getMerchantId()).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        params.put("subAppid",topConfig.getWxAppId());
        return payLaunch(model.getMerchantId(), params, "order/reverseScan", "条码支付", "0000");
    }

    /**
     * 天阙随行付支付查询订单
     */
    public PayRes query(CommonQueryParam model) throws Exception {
        log.info(" 查询订单 ");
        Map<String, Object> params = new HashMap<>(2);
        params.put("ordNo", model.getOrderNumber());
        return payLaunch(model.getMerchantId(), params, "query/tradeQuery", "订单查询", "0000");
    }

    /**
     * 天阙随行付退款
     */
    public PayRes refund(TqSxfPayParam model) throws Exception {
        Map<String, Object> params = new HashMap<>(5);
        params.put("amt", model.getRefundAmount().toPlainString());
        params.put("ordNo", model.getOutTradeNo());
        params.put("origSxfUuid", model.getTransactionId());
        params.put("notifyUrl", "");
        return refund(model.getMerchantId(), params, "order/refund", "退款", "0000");
    }

    public SxfPayFaceAuthInfoVO getTqSxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, TqSxfConfig tqSxfConfig) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", sxfPayFaceAuthInfoDTO.getOrdNo());
        params.put("storeNo", sxfPayFaceAuthInfoDTO.getStoreId());
        params.put("rawdata", sxfPayFaceAuthInfoDTO.getRawdata());
        params.put("deviceId", sxfPayFaceAuthInfoDTO.getDeviceId());
        params.put("subMchId", tqSxfConfig.getSubMchId());
        return getsxfAuthInfo(tqSxfConfig.getMerchantId(), params, "query/wechatLoseFace", "成功", "0000");
    }

    private SxfPayFaceAuthInfoVO getsxfAuthInfo(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        log.info("==========pay" + req);
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        //组装加密串
        String signContent = RSASignature.getOrderContent(reqMap);
        log.info("==配置密钥【" + privateKey + "】=====");
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new SxfPayFaceAuthInfoVO("ERROR", "密钥配置异常", null, null);
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        log.info("req" + reqStr);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        log.info("支付结果，{}", resultMap);

        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            System.out.println(resultStr);
            log.info("resultStr -----------  " + resultStr);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            System.out.println("resultSign:" + resultSign);
            log.info("resultSign密匙 -----------  " + resultSign);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                System.out.println("验签成功");
                log.info("验签成功");
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (code.equals(map.get("bizCode"))) {
                    if ("成功".equals(msg)) {
                        return new SxfPayFaceAuthInfoVO("SUCCESS", "获取成功", map.get("authInfo"), map.get("expireTime").length());
                    }
                    return new SxfPayFaceAuthInfoVO("SUCCESS", "获取成功", map.get("authInfo"), map.get("expireTime").length());
                }
                if ("0001".equals(code)) {
                    return new SxfPayFaceAuthInfoVO("ERROR", "获取失败", map.get("bizMsg"), null);
                }
                return new SxfPayFaceAuthInfoVO("ERROR", "获取失败", map.get("bizMsg"), null);
            } else {
                return new SxfPayFaceAuthInfoVO("ERROR", "验签失败", null, null);
            }
        } else {
            if ("00".equals(code)) {
                return new SxfPayFaceAuthInfoVO("ERROR", "获取失败", null, null);
            }
            return new SxfPayFaceAuthInfoVO("ERROR", "获取失败", null, null);
        }
    }

    /**
     * 图片上传
     *
     * @param idCardCopyPath
     * @return
     */
    public UploadPictureVO upLoadPicture(String idCardCopyPath, String pictureType) {
        return SxfHttpUtils.upLoadPicture(URL + "merchant/uploadPicture", idCardCopyPath, pictureType, "0000");
    }


    /**
     * 天阙退款
     *
     * @param merchantId
     * @param params
     * @param payUrl
     * @param msg
     * @param code
     * @return
     * @throws Exception
     */
    private PayRes refund(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        //组装加密串
        String signContent = RSASignature.getOrderContent(reqMap);
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new PayRes("密钥配置异常", PayRes.ResultStatus.FAIL);
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (code.equals(map.get("bizCode"))) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.REFUND, JacksonUtil.toJson(map));
                }
                if ("0002".equals(map.get("bizCode"))) {
                    return new PayRes("订单号不可重复提交", PayRes.ResultStatus.REFUNDING);
                }
                //退款中
                if ("2002".equals(map.get("bizCode"))) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.REFUNDING);
                }
                return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.REFUNDFAIL);
            } else {
                return new PayRes("验签失败", PayRes.ResultStatus.REFUNDING);
            }
        } else {
            if ("0002".equals(code)) {
                return new PayRes("订单号不可重复提交", PayRes.ResultStatus.REFUNDING);
            }
            return new PayRes(msg + "失败，" + resultMap.get("code"), PayRes.ResultStatus.REFUNDFAIL);
        }
    }

    /**
     * 查询退款状态
     *
     * @param model
     * @return
     */
    public PayRes TqSxfRefundQuery(CommonQueryParam model) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("ordNo", model.getOrderNumber());
        return refundQuery(model.getMerchantId(), params, "query/refundQuery", "成功", "0000");
    }

    /**
     * 查询退款状态
     *
     * @param merchantId
     * @param params
     * @param payUrl
     * @param msg
     * @param code
     * @return
     */
    private PayRes refundQuery(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        //组装加密串
        String signContent = RSASignature.getOrderContent(reqMap);
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new PayRes("密钥配置异常", PayRes.ResultStatus.FAIL);
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (code.equals(map.get("bizCode"))) {
                    if ("REFUNDSUC".equals(map.get("tranSts"))) {
                        log.info("退款成功");
                        return new PayRes(msg + "退款成功", PayRes.ResultStatus.REFUND, JacksonUtil.toJson(map));
                    }
                    if ("REFUNDFAIL".equals(map.get("tranSts"))) {
                        log.info("退款失败");
                        return new PayRes(msg + "退款失败", PayRes.ResultStatus.REFUNDFAIL, JacksonUtil.toJson(map));
                    }
                    log.info("退款中，请稍后进行查询退款状态");
                    return new PayRes(msg + "退款中，请稍后进行查询退款状态", PayRes.ResultStatus.REFUNDING, JacksonUtil.toJson(map));
                }
                if ("0002".equals(code)) {
                    return new PayRes("订单号不可重复提交", PayRes.ResultStatus.REFUNDING);
                }
                return new PayRes(msg + "失败，" + map.get("bizCode"), PayRes.ResultStatus.REFUNDING);
            } else {
                return new PayRes("验签失败", PayRes.ResultStatus.REFUNDING);
            }
        } else {
            if ("0002".equals(code)) {
                return new PayRes("订单号不可重复提交", PayRes.ResultStatus.REFUNDING);
            }
            return new PayRes(msg + "失败，" + resultMap.get("code"), PayRes.ResultStatus.REFUNDING);
        }
    }

    /**
     * 天阙随行付商户入驻
     *
     * @param params
     */
    public TqSxfIncomeVO income(Map<String, Object> params, String merchantId) throws Exception {
        Map<String, String> req;
        req = firstCreateParam(merchantId);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new TqSxfIncomeVO("0001", "密匙配置失败");
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        log.info("reqStr{}", reqStr);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + "/merchant/income", reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        log.info("查询结果，{}", resultMap);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if ("0000".equals(map.get("bizCode"))) {
                    return new TqSxfIncomeVO(map.get("applicationId"), map.get("bizCode"), map.get("bizMsg"), map.get("mno"));
                }
                return new TqSxfIncomeVO("0001", map.get("bizMsg"));
            } else {
                return new TqSxfIncomeVO("0001", "验签失败");
            }
        }
        String bizMsg = (String) resultMap.get("bizMsg");
        return new TqSxfIncomeVO("0001", bizMsg);
    }

    public TqSxfQueryMerchantInfoVO queryMerchantInfo(Map<String, Object> params, String merchantId) throws Exception {
        Map<String, String> req;
        req = firstCreateParam(merchantId);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        //sign
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new TqSxfQueryMerchantInfoVO("0001", "密匙配置失败");
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + "/merchant/queryMerchantInfo", reqStr);
        log.info("查询结果，{}", resultJson);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if ("0000".equals(map.get("bizCode"))) {
                    //审核中
                    if ("0".equals(map.get("taskStatus"))) {
                        JSONArray repoInfoJSONArray = JSONObject.parseArray(JSON.toJSONString(map.get("repoInfo")));
                        if (repoInfoJSONArray != null && repoInfoJSONArray.size() > 0) {
                            for (int i = 0; i < repoInfoJSONArray.size(); i++) {
                                JSONObject jsonObject1 = (JSONObject) repoInfoJSONArray.get(i);
                                if (jsonObject1.get("childNoType").equals("WX")) {
                                    map.put("wx", jsonObject1.get("childNo").toString());
                                    return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), "此状态为【秒审通过】商户可进行交易，商户享受秒审额度", map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"), map.get("wx"));
                                }
                            }
                        } else {
                            return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), "此状态为【人工工单】需等待审单人员审核", map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"));
                        }
                    }
                    //入驻成功
                    if ("1".equals(map.get("taskStatus"))) {
                        JSONArray repoInfoJSONArray = JSONObject.parseArray(JSON.toJSONString(map.get("repoInfo")));
                        for (int i = 0; i < repoInfoJSONArray.size(); i++) {
                            JSONObject jsonObject1 = (JSONObject) repoInfoJSONArray.get(i);
                            if (jsonObject1.get("childNoType").equals("WX")) {
                                map.put("wx", jsonObject1.get("childNo").toString());
                            }
                        }
                        return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), "入驻成功", map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"), map.get("wx"));
                    }

                    //入驻驳回
                    if ("2".equals(map.get("taskStatus"))) {
                        return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), map.get("bizMsg"), map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"));
                    }

                    //入驻图片驳回
                    if ("3".equals(map.get("taskStatus"))) {
                        //复审
                        JSONArray repoInfoJSONArray = JSONObject.parseArray(JSON.toJSONString(map.get("repoInfo")));
                        if (repoInfoJSONArray != null && repoInfoJSONArray.size() > 0) {

                            for (int i = 0; i < repoInfoJSONArray.size(); i++) {
                                JSONObject jsonObject1 = (JSONObject) repoInfoJSONArray.get(i);
                                if (jsonObject1.get("childNoType").equals("WX")) {
                                    map.put("wx", jsonObject1.get("childNo").toString());
                                    return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), "此状态为【秒审通过】复审驳回——需要在 30 天之内重新上驳回的相关 图片否则将暂停商户交易(可以进行交易)", map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"), map.get("wx"));
                                }
                            }
                        } else {
                            return new TqSxfQueryMerchantInfoVO(map.get("bizCode"), map.get("bizMsg"), map.get("applicationId"), map.get("mno"), map.get("taskStatus"), map.get("suggestion"));
                        }
                    }
                    return new TqSxfQueryMerchantInfoVO("0000", "入驻审核中,请稍后查询审核结果", map.get("applicationId"), map.get("mno"), "0");
                }
                return new TqSxfQueryMerchantInfoVO("0001", "入驻失败", map.get("applicationId"), map.get("mno"));
            } else {
                return new TqSxfQueryMerchantInfoVO("0001", "验签失败");
            }
        }
        return new TqSxfQueryMerchantInfoVO("0001", "验签失败");
    }

    public TqSxfIncomeVO updateMerchantInfo(Map<String, Object> params, String merchantId) throws Exception {
        Map<String, String> req;
        req = firstCreateParam(merchantId);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new TqSxfIncomeVO("0001", "密匙配置失败");
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + "/merchant/updateMerchantInfo", reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map{}", map);
                if ("0000".equals(map.get("bizCode"))) {
                    return new TqSxfIncomeVO(map.get("applicationId"), map.get("bizCode"), map.get("bizMsg"), map.get("mno"));
                }
                return new TqSxfIncomeVO(map.get("applicationId"), "0001", map.get("bizMsg"));
            } else {
                return new TqSxfIncomeVO("0001", "验签失败");
            }
        }
        String bizMsg = (String) resultMap.get("bizMsg");
        return new TqSxfIncomeVO("0001", bizMsg);
    }

    public PayRes merchantSetup(String mno) throws Exception {
        Map<String, Object> map = new HashMap<>(7);
        map.put("mecAuthority", "01");
        return merchantSetupA("1281908250542604288", map, "merchant/merchantSetup", "商户设置", mno, Integer.valueOf(1));
    }


    /**
     * 撤销订单
     * @param merchantId
     * @param orderNumber
     */
    public void tqSxfReverse(String merchantId, String orderNumber) throws Exception {
        Map<String, Object> params = new HashMap<>(3);
        params.put("origOrderNo", orderNumber);
        this.cancel(merchantId,params);

    }


    private void cancel(String merchantId, Map<String, Object> params) throws Exception {
        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            log.info("密钥配置异常");
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + "query/cancel", reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if (SUCCESS_CODE.equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (SUCCESS_CODE.equals(map.get("bizCode").toString())) {
                    log.info("撤销成功");
                }else {
                    log.info("撤销失败");
                }
            } else {
                log.info("验签失败");
            }
        } else {
            if ("0001".equals(resultMap.get("code"))) {
                log.info("撤销失败");
            }
            log.info("撤销失败");
        }
    }

    public Resp querySettlement(String mno, String queryTime) throws Exception {
        Map<String, Object> map = new HashMap<>(7);
        map.put("queryTime", queryTime);
        return querySettlementA("1281908250542604288", map, "capital/query/querySettlement", "结算查询接口");
    }

    private PayRes merchantSetupA(String merchantId, Map<String, Object> params, String payUrl, String msg, String code, Integer num) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        if (num.equals(Integer.valueOf(1))) {
            params.put("mno", code);
        }else {
            params.put("mno", mno);
        }
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new PayRes("密钥配置异常", PayRes.ResultStatus.FAIL);
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (SUCCESS_CODE.equals(map.get("bizCode").toString())) {
                    return new PayRes(map.get("bizCode").toString(), PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(map));
                }
                return new PayRes(map.get("bizMsg") + map.get("bizCode"), PayRes.ResultStatus.FAIL);
            } else {
                return new PayRes("验签失败", PayRes.ResultStatus.FAIL);
            }
        } else {
            if ("0001".equals(resultMap.get("code"))) {
                return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
            }
            return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
        }
    }

    private Resp querySettlementA(String merchantId, Map<String, Object> params, String payUrl, String msg) throws Exception {

        Map<String, String> req;
        req = createParam(merchantId);
        params.put("mno", mno);
        req.put("reqData", JSON.toJSONString(params));
        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
        String signContent = RSASignature.getOrderContent(reqMap);
        String sign = null;
        try {
            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
        } catch (InvalidKeySpecException e) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"密钥配置异常");
        }
        reqMap.put("sign", sign);
        String reqStr = JSON.toJSONString(reqMap);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        if ("0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if ("00".equals(map.get("bizCode").toString())) {
                    return Resp.success(map,"返回成功");
                }
                return new Resp().error(Resp.Status.PARAM_ERROR,map.get("bizMsg"));
            } else {
                return new Resp().error(Resp.Status.PARAM_ERROR,"验签失败");
            }
        } else {
            if ("0001".equals(resultMap.get("code"))) {
                return new Resp().error(Resp.Status.PARAM_ERROR,"失败");
            }
            return new Resp().error(Resp.Status.PARAM_ERROR,"失败");
        }
    }
}
