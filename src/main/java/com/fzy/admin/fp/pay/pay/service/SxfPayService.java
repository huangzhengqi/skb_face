package com.fzy.admin.fp.pay.pay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.repository.SxfConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.WxPayFaceAuthInfoVO;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.pay.pay.domain.SxfConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.util.RSASignature;
import com.fzy.admin.fp.pay.pay.util.SxfHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Created by wtl on 2019-05-31 9:54
 * @description 随行付 支付业务
 */
@Slf4j
@Service
public class SxfPayService extends PayService {
    //测试公钥
//    String sxfPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC24O1MfH/LS5QMb0zkOC6KDWOKA+Rv/E/ZS3qk9XgOp1dQEMeyWexruxp5WhJOFgVFrFGNIRAu/hXbT1O8k9sg+NKDdNTx47ml2HMu3B4ohEEqJV0fx5YGK9f96oGN0Tx+PLdAlCGF+HXN78XmC5uueJSljKetnRgF13l+OB3pYQIDAQAB";

    /**
     * 随行付生产公钥
     */
    String sxfPublic =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjo1+KBcvwDSIo+nMYLeOJ19Ju4ii0xH66ZxFd869EWFWk/EJa3xIA2+4qGf/Ic7m7zi/NHuCnfUtUDmUdP0JfaZiYwn+1Ek7tYAOc1+1GxhzcexSJLyJlR2JLMfEM+rZooW4Ei7q3a8jdTWUNoak/bVPXnLEVLrbIguXABERQ0Ze0X9Fs0y/zkQFg8UjxUN88g2CRfMC6LldHm7UBo+d+WlpOYH7u0OTzoLLiP/04N1cfTgjjtqTBI7qkOGxYs6aBZHG1DJ6WdP+5w+ho91sBTVajsCxAaMoExWQM2ipf/1qGdsWmkZScPflBqg7m0olOD87ymAVP/3Tcbvi34bDfwIDAQAB";


    // 测试接口域名
    //  private final String URL = "https://payapi-test.suixingpay.com/";
    /**
     * 正式接口域名
     */
    private final String URL = "https://icm-management.suixingpay.com/";

    private String mno;
    private String privateKey;
    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private MerchantService merchantService;

    @Resource
    private SxfConfigRepository sxfConfigRepository;

    public SxfConfigRepository getSxfConfigRepository() {
        return sxfConfigRepository;
    }


    /**
     * 构建通用请求参数
     */
    public Map<String, String> createParam(String merchantId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 根据商户id查询支付参数
        SxfConfig sxfConfig = sxfConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(sxfConfig)) {
            throw new BaseException("随行付通道支付参数未配置", Resp.Status.PARAM_ERROR.getCode());
        }
        String serviceProviderId = merchantService.findOne(merchantId).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        Map<String, String> params = new TreeMap<>();
        mno = sxfConfig.getMno();
        privateKey = topConfig.getSxfPrivateKey();

        params.put("orgId", topConfig.getSxfOrgId());
        params.put("reqId", ParamUtil.uuid());
        params.put("signType", "RSA");
        params.put("timestamp", sdf.format(new Date()));
        params.put("version", "1.0");
        return params;
    }

    /**
     * 微信公众号/服务窗支付下单接口（用户扫商户）
     */
    public PayRes jsapiPay(SxfPayParam model) throws Exception {
        Map<String, Object> params = new HashMap<>(5);
        params.put("ordNo", model.getOutTradeNo());
        params.put("amt", model.getTotalFee().toPlainString());
        params.put("subject", merchantService.findOne(model.getMerchantId()).getName());
        params.put("payType", model.getPayType());
        params.put("notifyUrl", getDomain() + "/order/callback/sxf_order_callback");
        return payLaunch(model.getMerchantId(), params, "management/qr/activeScan", "扫码支付", "0000");

    }


    /**
     * 条码支付
     */
    public PayRes micropay(SxfPayParam model) throws Exception {
        // 根据商户id查询支付参数
        SxfConfig sxfConfig = sxfConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (sxfConfig == null || StringUtils.isEmpty(sxfConfig.getSubMchId())) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置随行付子商户相关参数");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("submerchId", sxfConfig.getSubMchId());
        params.put("authCode", model.getAuthCode());
        params.put("amt", model.getTotalFee().toPlainString());
        params.put("ordNo", model.getOutTradeNo());
        params.put("subject", merchantService.findOne(model.getMerchantId()).getName());
        params.put("payType", model.getPayType());
        return payLaunch(model.getMerchantId(), params, "management/qr/reverseScan", "条码支付", "0000");


    }


    /**
     * 查询订单状态
     *
     * @param model
     * @return
     */
    public PayRes query(CommonQueryParam model) throws Exception {
        Map<String, Object> params = new HashMap<>(2);
        params.put("ordNo", model.getOrderNumber());
        return payLaunch(model.getMerchantId(), params, "management/qr/query", "订单查询", "0000");
    }

    /**
     * 退款
     *
     * @param model
     * @return
     */
    public PayRes refund(SxfPayParam model) throws Exception {
        Map<String, Object> params = new HashMap<>(5);
        params.put("amt", model.getRefundAmount().toPlainString());
        params.put("ordNo", model.getOutTradeNo());
        params.put("origSxfUuid", model.getTransactionId());
        params.put("notifyUrl", "");
        return payLaunch(model.getMerchantId(), params, "management/qr/refund", "退款", "0000");


    }

    public PayRes qrcodeProductSetup(BigDecimal wxRate, BigDecimal aliRate, String merchantId) throws Exception {
        log.info("==进入设置");
        Map<String, String> wxQrcodeList = new HashMap<>(2);
        wxQrcodeList.put("rate", wxRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        wxQrcodeList.put("rateType", "01");
        Map<String, String> aliQrcodeList = new HashMap<>(2);
        aliQrcodeList.put("rate", aliRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        aliQrcodeList.put("rateType", "02");
        List<Map<String, String>> qrcodeList = new ArrayList<>();
        qrcodeList.add(wxQrcodeList);
        qrcodeList.add(aliQrcodeList);

        Map<String, Object> params = new HashMap<>(1);
        params.put("qrcodeList", qrcodeList);
        log.info("费率设置参数" + params);
        return payLaunch(merchantId, params, "management/product/qrcodeProductSetup", "二维码费率设置", "00");
    }

    /**
     * 获取AuthInfo
     *
     * @param sxfPayFaceAuthInfoDTO
     */
    public SxfPayFaceAuthInfoVO getsxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, SxfConfig sxfConfig) throws Exception {
        Map<String, Object> params = new HashMap<>(6);
        params.put("ordNo", sxfPayFaceAuthInfoDTO.getOrdNo());
        params.put("storeId", sxfPayFaceAuthInfoDTO.getStoreId());
        params.put("rawdata", sxfPayFaceAuthInfoDTO.getRawdata());
        params.put("wxTrmNo", sxfPayFaceAuthInfoDTO.getDeviceId());
        params.put("subMchId", sxfConfig.getSubMchId());
        return getsxfAuthInfo(sxfConfig.getMerchantId(), params, "management/qr/getAuthInfo", "交易成功", "0000");
    }

    /**
     * 获取getSubOpenid
     *
     * @param authCode
     * @param sxfConfig
     * @return
     */
    public SxfPayFaceAuthInfoVO getSxfSubopenid(String authCode, SxfConfig sxfConfig) throws Exception {
        Map<String, Object> params = new HashMap<>(4);
        params.put("subMchId", sxfConfig.getSubMchId());
        params.put("subAppid", sxfConfig.getSubAppid());
        params.put("authCode", authCode);
        log.info("sxfConfig -------------  " + sxfConfig);
        return getSxfSubopenid(sxfConfig.getMerchantId(), params, "management/weChat/getSubOpenid", "获取getSubOpenid", "0000");
    }


    /**
     * 获取getSubOpenid
     */
    private SxfPayFaceAuthInfoVO getSxfSubopenid(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {

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

        if ("SXF0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            log.info("resultStr -----------  " + resultStr);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            log.info("resultSign密匙 -----------  " + resultSign);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                System.out.println("验签成功");
                log.info("验签成功");
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (code.equals(map.get("bizCode"))) {
                    if ("交易成功".equals(msg)) {
                        return new SxfPayFaceAuthInfoVO(map.get("subOpenid"));
                    }
                    return new SxfPayFaceAuthInfoVO(map.get("subOpenid"));
                }
                if ("0001".equals(code)) {
                    return new SxfPayFaceAuthInfoVO("ERROR", "失败", map.get("bizMsg"), null);
                }
                return new SxfPayFaceAuthInfoVO("ERROR", "失败", map.get("bizMsg"), null);
            } else {
                return new SxfPayFaceAuthInfoVO("ERROR", "验签失败", null, null);
            }
        } else {
            if ("00".equals(code)) {
                return new SxfPayFaceAuthInfoVO("ERROR", "失败", null, null);
            }
            return new SxfPayFaceAuthInfoVO("ERROR", "失败", null, null);
        }
    }

    /**
     * 发起验证
     */
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

        if ("SXF0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            log.info("resultStr -----------  " + resultStr);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            log.info("resultSign密匙 -----------  " + resultSign);
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                System.out.println("验签成功");
                log.info("验签成功");
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("map------------  " + map);
                if (code.equals(map.get("bizCode"))) {
                    if ("交易成功".equals(msg)) {
                        return new SxfPayFaceAuthInfoVO("SUCCESS", "成功", map.get("subMchId"), map.get("authInfo"), map.get("expiresIn").length());
                    }
                    return new SxfPayFaceAuthInfoVO("SUCCESS", "成功", map.get("subMchId"), map.get("authInfo"), map.get("expiresIn").length());
                }
                if ("0001".equals(code)) {
                    return new SxfPayFaceAuthInfoVO("ERROR", "失败", map.get("bizMsg"), null);
                }
                return new SxfPayFaceAuthInfoVO("ERROR", "失败", map.get("bizMsg"), null);
            } else {
                return new SxfPayFaceAuthInfoVO("ERROR", "验签失败", null, null);
            }
        } else {
            if ("00".equals(code)) {
                return new SxfPayFaceAuthInfoVO("ERROR", "失败", null, null);
            }
            return new SxfPayFaceAuthInfoVO("ERROR", "失败", null, null);
        }
    }

    /**
     * 发起支付
     *
     * @param merchantId
     * @param params
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
        log.info("请求随行付的参数 --------->   {}", reqStr);
        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
        log.info("支付结果:  resultMap返回的值 --------->    {}", resultMap);

        if ("SXF0000".equals(resultMap.get("code"))) {
            //验签
            String signResult = resultMap.get("sign").toString();
            resultMap.remove("sign");
            String resultStr = RSASignature.getOrderContent(resultMap);
            //sign
            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
            log.info("resultSign配置密钥 :   【" + resultSign + "】");
            //组装加密串
            if (RSASignature.doCheck(resultStr, signResult, sxfPublic)) {
                log.info("验签成功");
                Object respData = resultMap.get("respData");
                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
                log.info("respData:   -------->  {}", map);
                if (code.equals(map.get("bizCode"))) {
                    if ("订单查询".equals(msg)) {
                        String status = map.get("tranSts");
                        log.info("订单查询", map.get("tranSts"));
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
                if ("00".equals(code)) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.FAIL);
                }
                if ("2068".equals(map.get("bizCode"))) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.PAYING);
                }
                //限额ID1457-单笔:2500￥
                if ("2032".equals(map.get("bizCode"))) {
                    return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.FAIL);
                }
                return new PayRes(map.get("bizMsg"), PayRes.ResultStatus.FAIL);
            } else {
                return new PayRes("验签失败", PayRes.ResultStatus.FAIL);
            }
        } else {
            if ("00".equals(code)) {
                return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
            }
            return new PayRes(msg + "失败，" + resultMap.get("code"), PayRes.ResultStatus.FAIL);
        }
    }


}
