package com.fzy.admin.fp.pay.pay.util;

import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;

import java.util.Map;

public class WxDepositUtils
{
    private static final String FACE_PAY_URL = "https://api.mch.weixin.qq.com/deposit/facepay";
    private static final String MICRO_PAY_URL = "https://api.mch.weixin.qq.com/deposit/micropay";
    private static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/deposit/orderquery";
    private static final String REVERSE_URL = "https://api.mch.weixin.qq.com/deposit/reverse";
    private static final String CONSUME_URL = "https://api.mch.weixin.qq.com/deposit/consume";
    private static final String REFUND_URL = "https://api.mch.weixin.qq.com/deposit/refund";
    private static final String REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/deposit/refundquery";
    private static final String SIGN_TYPR = "sign_type";
    private static final String HMAC_SHA256 = "HMAC-SHA256";
    private static final String MD5 = "MD5";

    public static Map<String, String> facePay(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        System.out.println("reqData ========= " + reqData.toString());
        return post(FACE_PAY_URL, reqData, myWxConfig, Boolean.valueOf(false));
    }









    public static Map<String, String> microPay(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(MICRO_PAY_URL, reqData, myWxConfig, Boolean.valueOf(false));
    }









    public static Map<String, String> orderqQuery(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(ORDER_QUERY_URL, reqData, myWxConfig, Boolean.valueOf(false));
    }









    public static Map<String, String> reverse(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(REVERSE_URL, reqData, myWxConfig, Boolean.valueOf(true));
    }









    public static Map<String, String> consume(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(CONSUME_URL, reqData, myWxConfig, Boolean.valueOf(true));
    }









    public static Map<String, String> refund(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(REFUND_URL, reqData, myWxConfig, Boolean.valueOf(true));
    }






    public static Map<String, String> refundquery(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post(REFUND_QUERY_URL, reqData, myWxConfig, Boolean.valueOf(false));
    }

    public static Map<String, String> getSerialNo(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post("https://api.mch.weixin.qq.com/risk/getcertficates", reqData, myWxConfig, Boolean.valueOf(true));
    }

    /**
     * 2020-2-17 13:46:53 yy
     * 微信自动进件
     * @param reqData
     * @param myWxConfig
     * @return
     * @throws Exception
     */
    public static Map<String, String> micro(Map<String, String> reqData, MyWxConfig myWxConfig) throws Exception {
        if (!((String)reqData.get("sign_type")).equals("HMAC-SHA256")) {
            throw new Exception("签名方式错误");
        }
        return post("https://api.mch.weixin.qq.com/applyment/micro/submit", reqData, myWxConfig, Boolean.valueOf(true));
    }

    private static Map<String, String> post(String strUrl, Map<String, String> reqData, MyWxConfig myWxConfig, Boolean needCert) throws Exception {
        String xmlStr = "";
        WXPay wxPay = new WXPay(myWxConfig, WXPayConstants.SignType.HMACSHA256);
        if (needCert.booleanValue()) {
            xmlStr = wxPay.requestWithCert(strUrl, reqData, myWxConfig.getHttpConnectTimeoutMs(), myWxConfig.getHttpReadTimeoutMs());
        } else {
            xmlStr = wxPay.requestWithoutCert(strUrl, reqData, myWxConfig.getHttpConnectTimeoutMs(), myWxConfig.getHttpReadTimeoutMs());
        }
        return wxPay.processResponseXml(xmlStr);
    }







    public static void sign(Map<String, String> params, String key) throws Exception {
        String sign = WXPayUtil.generateSignature(params, key, WXPayConstants.SignType.HMACSHA256);
        params.put("sign", sign);
    }
}
