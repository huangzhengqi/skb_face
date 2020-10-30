package com.fzy.admin.fp.pay.pay.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.repository.FyConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.domain.FyConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.FyConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.util.FuiouHttpUtils;
import com.fzy.admin.fp.pay.pay.util.FuiouUtils;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-31 9:54
 * @description 富友 支付业务
 */
@Slf4j
@Service
public class FyPayService extends PayService {

    // 测试接口域名
    //  private final String URL = "https://fundwx.fuiou.com/";
    //富友测试公钥  用于验签
//    private String fyPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDz2fCOYaaU6sztFql4cOmiFRq2LRk1XuGfrJnMFa09QMXMXOEn9YNYC44zV1AE/q9b0BKGbM74YPoge/7qsW+Heao76Drv6HujP+rXLFbsXT5f9rcID2GCzDc+DXjb+NfwSa8vS9KJ3dau2xm87zpjdQ9zER6VH4UcZTgj7LbzgwIDAQAB";

    // 生产接口域名
    private final String URL = "https://spay-xs.fuioupay.com/";

    //富友生产公钥  用于验签
    private String fyPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnYmz+rYyJIYwFheOIlKsDOk8wmC6uduB+4CeH/Gu8ZJ+mVPuPg+mWt8hZ0/8U0QEKUuk3MzgGubGV5gIhJVPCI7R4t9F63AghYCr7ea3ykwADsypNY7lnfSdSq/bFjAXTWAilkgBb5A6Qw6KDBUky/a6zcK62ItArlSzzJ2KVIQIDAQAB";

    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private MerchantService merchantService;

    @Resource
    private FyConfigRepository fyConfigRepository;


    public FyConfigRepository getFyConfigRepository() {
        return fyConfigRepository;
    }

    private String insPrivateKey;

    private String orderPre;

    /**
     * 构建通用请求参数
     */
    public Map<String, String> createParam(String merchantId) {
        // 根据商户id查询支付参数
        FyConfig fyConfig = fyConfigRepository.findByMerchantIdAndDelFlag(merchantId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(fyConfig)) {
            throw new BaseException("富友通道支付参数未配置", Resp.Status.PARAM_ERROR.getCode());
        }
        String serviceProviderId = merchantService.findOne(merchantId).getServiceProviderId();
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        Map<String, String> map = new HashMap<>();
        insPrivateKey = topConfig.getFyInsPrivateKey();
        orderPre = topConfig.getFyPreOrder();
        map.put("ins_cd", topConfig.getFyInsCd());
        map.put("mchnt_cd", fyConfig.getMchntCd());
        map.put("version", "1");
        map.put("term_id", "12345678");
        map.put("random_str", ParamUtil.uuid());
        map.put("sign", "");
        return map;

    }

    /**
     * 微信公众号/服务窗支付下单接口（用户扫商户）
     */
    public PayRes jsapiPay(FyPayParam model) throws Exception {
        Map<String, String> params;
        try {
            params = createParam(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf_ts = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("txn_begin_ts", sdf_ts.format(calendar.getTime()));
        params.put("term_ip", "127.0.0.1");
        params.put("order_amt", model.getTotalFee().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
        params.put("mchnt_order_no", orderPre + model.getOutTradeNo());
        params.put("goods_des", merchantService.findOne(model.getMerchantId()).getName());
        params.put("order_type", model.getPayType());
        params.put("reserved_expire_minut", "30");
        params.put("curr_type", "");
        params.put("goods_detail", "");
        params.put("addn_inf", "");
        params.put("goods_tag", "");
        params.put("reserved_sub_appid", "");
        params.put("reserved_limit_pay", "");
        params.put("notify_url", getDomain() + "/order/callback/fuiou_order_callback");
        return payLaunch(params, "preCreate", "扫码支付");


    }


    /**
     * 条码支付
     */
    public PayRes micropay(FyPayParam model) throws Exception {
        Map<String, String> params;
        try {
            params = createParam(model.getMerchantId());
        } catch (Exception e) {
            return new PayRes(e.getMessage(), PayRes.ResultStatus.FAIL);
        }
        params.put("term_ip", "127.0.0.1");
        params.put("auth_code", model.getAuthCode());
        params.put("order_amt", model.getTotalFee().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
        params.put("mchnt_order_no", orderPre + model.getOutTradeNo());
        params.put("goods_des", merchantService.findOne(model.getMerchantId()).getName());
        params.put("order_type", model.getPayType());
        params.put("curr_type", "");
        params.put("sence", "1");
        params.put("goods_detail", "");
        params.put("addn_inf", "");
        params.put("goods_tag", "");
        params.put("reserved_sub_appid", "");
        params.put("reserved_limit_pay", "");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf_ts = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("txn_begin_ts", sdf_ts.format(calendar.getTime()));
        //  params.put("notify_url", getDomain() + "/order/callback/fuiou_order_callback");
        return payLaunch(params, "micropay", "条码支付");
    }

    /**
     * 查询订单状态
     *
     * @param model
     * @return
     */
    public PayRes query(FyPayParam model) throws Exception {
        Map<String, String> params = createParam(model.getMerchantId());
        params.put("order_type", model.getPayType());
        params.put("mchnt_order_no", orderPre + model.getOutTradeNo());
        return payLaunch(params, "commonQuery", "订单查询");
    }

    /**
     * 退款
     *
     * @param model
     * @return
     */
    public PayRes refund(FyPayParam model) throws Exception {
        Map<String, String> params = createParam(model.getMerchantId());
        params.put("mchnt_order_no", orderPre + model.getOutTradeNo());
        params.put("order_type", model.getPayType());
        params.put("refund_order_no", orderPre + model.getTransactionId());
        params.put("operator_id", "");
        params.put("reserved_fy_term_id", "1234567");
        params.put("total_amt", model.getTotalFee().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
        params.put("refund_amt", model.getRefundAmount().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
        return payLaunch(params, "commonRefund", "退款");

    }


    /**
     * 发起支付
     */
    private PayRes payLaunch(Map<String, String> map, String payUrl, String msg) throws Exception {
        log.info("param" + map);
        Map<String, String> reqs = new HashMap<>();
        Map<String, String> nvs = new HashMap<>();
        reqs.putAll(map);
        String sign = FuiouUtils.getSign(reqs, insPrivateKey);
        reqs.put("sign", sign);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Iterator it = reqs.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = reqs.get(key);
            root.addElement(key).addText(value);
        }
        String reqBody = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>" + doc.getRootElement().asXML();
        System.out.println("==============================待编码字符串==============================\r\n" + reqBody);
        reqBody = URLEncoder.encode(reqBody, "GBK");
        System.out.println("==============================编码后字符串==============================\r\n" + reqBody);
        nvs.put("req", reqBody);
        StringBuffer result = new StringBuffer("");
        FuiouHttpUtils.post(URL + payUrl, nvs, result);
        String rspXml = URLDecoder.decode(result.toString(), "GBK");
        System.out.println("==============================响应报文==============================\r\n" + rspXml);
        //响应报文验签
        Map<String, String> resMap = FuiouUtils.xmlStr2Map(rspXml);
        System.out.println("===resp" + resMap);
        String str = resMap.get("sign");
        System.out.println("str:" + str);
        if (FuiouUtils.verifySign(resMap, fyPublicKey, str)) {

            if ("000000".equals(resMap.get("result_code"))) {
                if ("订单查询".equals(msg)) {
                    String status = resMap.get("trans_stat");
                    if ("SUCCESS".equals(status)) {
                        return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
                    }
                    if ("USERPAYING".equals(status) || "NOTPAY".equals(status)) {
                        return new PayRes("支付中", PayRes.ResultStatus.PAYING);
                    }
                    if ("REFUND".equals(status)) {
                        return new PayRes("已退款", PayRes.ResultStatus.REFUND);
                    }
                }
                return new PayRes(msg + "成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(resMap));
            }
            return new PayRes(msg + "失败，" + resMap.get("result_code"), PayRes.ResultStatus.FAIL);
        } else {
            return new PayRes("验签失败", PayRes.ResultStatus.FAIL);
        }


    }


}
